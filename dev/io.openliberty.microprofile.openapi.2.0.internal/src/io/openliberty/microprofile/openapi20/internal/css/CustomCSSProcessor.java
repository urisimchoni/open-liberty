/*******************************************************************************
 * Copyright (c) 2020, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.microprofile.openapi20.internal.css;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.eclipse.microprofile.openapi.OASConfig;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import com.ibm.websphere.ras.Tr;
import com.ibm.websphere.ras.TraceComponent;
import com.ibm.ws.ffdc.annotation.FFDCIgnore;
import com.ibm.wsspi.kernel.filemonitor.FileMonitor;
import com.ibm.wsspi.kernel.service.location.MalformedLocationException;
import com.ibm.wsspi.kernel.service.location.WsLocationAdmin;
import com.ibm.wsspi.kernel.service.location.WsResource;
import com.ibm.wsspi.kernel.service.utils.AtomicServiceReference;
import com.ibm.wsspi.kernel.service.utils.PathUtils;
import com.ibm.wsspi.kernel.service.utils.ServerQuiesceListener;

import io.openliberty.microprofile.openapi20.internal.ConfigProcessor;
import io.openliberty.microprofile.openapi20.internal.services.ConfigFieldProvider;
import io.openliberty.microprofile.openapi20.internal.utils.Constants;
import io.openliberty.microprofile.openapi20.internal.utils.LoggingUtils;
import io.openliberty.microprofile.openapi20.internal.utils.MessageConstants;

/**
 * Processor for custom CSS documents specified at /mpopenapi/customization.css. This class extracts the sections that
 * start with ".swagger-ui .headerbar " from the CSS document and passes that to a CustomCSSWABUpdater which updates the
 * OpenAPI UI WABs with a custom banner.
 *
 * It currently makes the following assumptions:
 *
 * 1) the CSS document is encoded in UTF-8
 * 2) the header contains* sections with the exact text ".swagger-ui .headerbar "
 * 3) the CSS is a valid document.
 */
@Component(service = { CustomCSSProcessor.class, ServerQuiesceListener.class }, configurationPolicy = ConfigurationPolicy.IGNORE, immediate = true,
           property = { "service.vendor=IBM" })
public final class CustomCSSProcessor implements FileMonitor, ServerQuiesceListener {

    private static final TraceComponent tc = Tr.register(CustomCSSProcessor.class);

    private static final String KEY_EXECUTOR_SERVICE_REF = "executorService";
    private final AtomicServiceReference<ScheduledExecutorService> executorServiceRef = new AtomicServiceReference<>(KEY_EXECUTOR_SERVICE_REF);

    @Reference
    private ConfigFieldProvider configFieldProvider;

    private volatile WsLocationAdmin locationAdminProvider;

    private ServiceRegistration<FileMonitor> fileMonitor;
    private final List<String> filesToMonitor = new ArrayList<>();
    private final CustomCSSWABUpdater updater;

    private final Object cssUpdaterLock = new Object();
    private final ConcurrentLinkedQueue<CSSUpdate> cssUpdates = new ConcurrentLinkedQueue<>();

    public static class CSSUpdate {
        Map<String, Object> updateData;
    }

    public CustomCSSProcessor() {
        this.updater = new CustomCSSWABUpdater();
    }

    @Activate
    protected void activate(ComponentContext cc, Map<String, Object> properties) {
        executorServiceRef.activate(cc);
        normalizeDefaultCSSLocationPath();
        activateFileMonitor(cc);
        process(locationAdminProvider, executorServiceRef.getService());
    }

    @Deactivate
    protected void deactivate(ComponentContext cc, int reason) {
        deactivateFileMonitor();
        executorServiceRef.deactivate(cc);
    }

    private File normalizePath(String path) {
        String normalizedPath = PathUtils.normalize(path);
        WsResource resource = locationAdminProvider.resolveResource(normalizedPath);
        File customizationFile = resource.asFile();
        return customizationFile;
    }

    private void normalizeDefaultCSSLocationPath() {
        filesToMonitor.add(normalizePath(Constants.DEFAULT_LOCATION_CSS).getAbsolutePath());
    }

    private synchronized void activateFileMonitor(ComponentContext cc) {
        final int pollingInterval;
        try (ConfigProcessor configProcessor = new ConfigProcessor(CustomCSSProcessor.class.getClassLoader(), configFieldProvider)) {
            pollingInterval = configProcessor.getFilePollingInterval();
        }
        if (LoggingUtils.isEventEnabled(tc)) {
            Tr.event(this, tc, OASConfig.EXTENSIONS_PREFIX + "liberty.file.polling.interval=" + pollingInterval);
        }
        if (pollingInterval > 0) {
            final BundleContext bundleContext = cc.getBundleContext();
            final Dictionary<String, Object> props = new Hashtable<>();
            props.put(org.osgi.framework.Constants.SERVICE_VENDOR, "IBM");
            props.put(FileMonitor.MONITOR_INTERVAL, String.valueOf(pollingInterval) + "s");
            props.put(FileMonitor.MONITOR_FILES, filesToMonitor);
            if (fileMonitor == null) {
                fileMonitor = bundleContext.registerService(FileMonitor.class, this, props);
                if (LoggingUtils.isEventEnabled(tc)) {
                    Tr.event(this, tc,
                             "Registered FileMonitor service : fileLocations=" + filesToMonitor.stream().map(String::toString).collect(Collectors.joining(", ", "{", "}")));
                }
            } else {
                fileMonitor.setProperties(props);
                if (LoggingUtils.isEventEnabled(tc)) {
                    Tr.event(this, tc, "Updated FileMonitor service : fileLocations=" + filesToMonitor.stream().map(String::toString).collect(Collectors.joining(", ", "{", "}")));
                }
            }
        } else {
            deactivateFileMonitor();
            if (LoggingUtils.isEventEnabled(tc)) {
                Tr.event(this, tc,
                         "FileMonitor service has been disabled : fileLocations=" + filesToMonitor.stream().map(String::toString).collect(Collectors.joining(", ", "{", "}")));
            }
        }
    }

    private synchronized void deactivateFileMonitor() {
        if (fileMonitor != null) {
            fileMonitor.unregister();
            fileMonitor = null;
            if (LoggingUtils.isEventEnabled(tc)) {
                Tr.event(this, tc, "Deactivated FileMonitor service.");
            }
        }
    }

    @FFDCIgnore({ IOException.class,
                  SecurityException.class,
                  MalformedLocationException.class,
                  IllegalArgumentException.class })
    public void process(WsLocationAdmin locationAdminProvider, ScheduledExecutorService executor) {
        if (locationAdminProvider == null || executor == null) {
            return;
        }
        boolean cssProcessed = false;
        try {
            WsResource resource = locationAdminProvider.resolveResource(Constants.DEFAULT_LOCATION_CSS);
            if (resource.exists()) {
                String uri = resource.toExternalURI().toString();
                try {
                    InputStream is = resource.get();
                    String content = getCSSSections(is);
                    if (content != null) {
                        Map<String, Object> cssData = new HashMap<>();
                        cssData.put(Constants.HEADER_CSS_URL_KEY, uri);
                        cssData.put(Constants.HEADER_CSS_CONTENT_KEY, content);
                        CSSUpdate update = new CSSUpdate();
                        update.updateData = cssData;
                        cssUpdates.add(update);
                        cssProcessed = true;
                    } else {
                        // Report a warning that the custom CSS section header was not found.
                        Tr.warning(tc, MessageConstants.CSS_SECTION_NOT_FOUND, uri);
                    }
                } catch (IOException ioe) {
                    Tr.warning(tc, MessageConstants.CSS_NOT_PROCESSED, uri, ioe.getClass().getName(), ioe.getMessage());
                } catch (SecurityException se) {
                    Tr.warning(tc, MessageConstants.CSS_NOT_PROCESSED, uri, se.getClass().getName(), se.getMessage());
                }
            } else {
                if (LoggingUtils.isEventEnabled(tc)) {
                    Tr.event(this, tc, "Restore to default CSS");
                }
                cssUpdates.add(new CSSUpdate());
                cssProcessed = true;
            }
        } catch (MalformedLocationException mle) {
            Tr.warning(tc, MessageConstants.CSS_NOT_PROCESSED, Constants.DEFAULT_LOCATION_CSS, mle.getClass().getName(), mle.getMessage());
        } catch (IllegalArgumentException iae) {
            Tr.warning(tc, MessageConstants.CSS_NOT_PROCESSED, Constants.DEFAULT_LOCATION_CSS, iae.getClass().getName(), iae.getMessage());
        }

        if (!cssProcessed) {
            if (LoggingUtils.isEventEnabled(tc)) {
                Tr.event(this, tc, "CSS was not processed - error occurred. So restore to default.");
            }
            cssUpdates.add(new CSSUpdate());
        }

        processCSSUpdates(executor);
    }

    private void processCSSUpdates(ScheduledExecutorService executor) {
        if (cssUpdates.size() > 0) {
            final Object cssUpdator = cssUpdaterLock;
            // Create a Runnable to process updates.
            // The ExecutorService may run it in a new thread.
            Runnable bundleUpdater = () -> {
                synchronized (cssUpdator) {
                    Iterator<CSSUpdate> it = cssUpdates.iterator();
                    while (it.hasNext()) {
                        CSSUpdate update = it.next();
                        if (update.updateData != null) {
                            updater.update(update.updateData);
                        } else {
                            updater.restoreDefaults();
                        }
                        it.remove();
                    }
                }
            };
            executor.execute(bundleUpdater);
        }
    }

    private String getCSSSections(InputStream is) throws IOException {
        //extract all sections that start with '.swagger-ui .headerbar '
        final String content = getContent(is);
        StringBuilder cssSections = new StringBuilder();
        boolean foundCSSSection = false;
        int startIndex = content.indexOf(Constants.CUSTOM_CSS_SECTION_START, 0);
        while (startIndex != -1) {
            int endIndex = content.indexOf(Constants.CUSTOM_CSS_SECTION_END, startIndex);
            if (endIndex == -1) {
                break;
            }
            cssSections.append(content.substring(startIndex, endIndex + 1));
            foundCSSSection = true;
            startIndex = content.indexOf(Constants.CUSTOM_CSS_SECTION_START, endIndex + 1);
        }

        return foundCSSSection ? cssSections.toString() : null;
    }

    private String getContent(InputStream is) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8.name()))) {
            final StringBuilder builder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
            return builder.toString();
        }
    }

    @Reference(name = KEY_EXECUTOR_SERVICE_REF, service = ScheduledExecutorService.class)
    protected void setExecutorService(ServiceReference<ScheduledExecutorService> ref) {
        executorServiceRef.setReference(ref);
    }

    protected void unsetExecutorService(ServiceReference<ScheduledExecutorService> ref) {
        executorServiceRef.unsetReference(ref);
    }

    @Reference(service = WsLocationAdmin.class)
    protected void setLocationAdmin(WsLocationAdmin provider) {
        this.locationAdminProvider = provider;
    }

    protected void unsetLocationAdmin(WsLocationAdmin provider) {
        this.locationAdminProvider = null;
    }

    @Reference(service = ConfigProviderResolver.class, cardinality = ReferenceCardinality.MANDATORY)
    protected void setConfigProvider(ConfigProviderResolver configResolver) {
        //makes sure config provider resolver is started
    }

    //
    // FileMonitor methods
    //

    @Override
    public void onBaseline(Collection<File> baseline) {}

    @Override
    public void onChange(Collection<File> createdFiles, Collection<File> modifiedFiles, Collection<File> deletedFiles) {
        process(locationAdminProvider, executorServiceRef.getService());
    }

    /** {@inheritDoc} */
    @Override
    public void serverStopping() {
        updater.serverStopping();
    }
}
