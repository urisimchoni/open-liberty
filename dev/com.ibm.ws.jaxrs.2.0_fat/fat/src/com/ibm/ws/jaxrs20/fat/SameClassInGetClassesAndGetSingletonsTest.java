/*******************************************************************************
 * Copyright (c) 2019, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.jaxrs20.fat;

import static com.ibm.ws.jaxrs20.fat.TestUtils.asString;
import static com.ibm.ws.jaxrs20.fat.TestUtils.getBaseTestUri;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ibm.websphere.simplicity.ShrinkHelper;

import componenttest.annotation.Server;
import componenttest.annotation.SkipForRepeat;
import componenttest.custom.junit.runner.FATRunner;
import componenttest.topology.impl.LibertyServer;

@RunWith(FATRunner.class) 
@SkipForRepeat({"EE9_FEATURES", "EE10_FEATURES"}) // EE9/10 do not process classes in getSingletons if also in getClasses, so this test is moot for EE9 and beyond.
public class SameClassInGetClassesAndGetSingletonsTest {

    @Server("com.ibm.ws.jaxrs.fat.getCgetS")
    public static LibertyServer server;

    private static CloseableHttpClient client;
    private static final String appwar = "getCgetS";

    @BeforeClass
    public static void setUp() throws Exception {
        ShrinkHelper.defaultDropinApp(server, appwar, "com.ibm.ws.jaxrs.fat.getCgetS.server");

        // Make sure we don't fail because we try to start an
        // already started server
        try {
            server.startServer(true);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (server != null) {
            server.stopServer();
        }
    }

    @Before
    public void getHttpClient() {
        client = HttpClientBuilder.create().build();
    }

    @After
    public void resetHttpClient() throws IOException {
        client.close();
    }

    /**
     * test getSingletons is higher than getClasses
     *
     * @throws IOException
     */
    @Test
    public void testGetSingletonHigherThanGetClass() throws IOException {
        String url = getBaseTestUri(appwar, "app1", "res1") + "/testGetSingletonHigherThanGetClass";
        HttpGet getMethod = new HttpGet(url);
        HttpResponse resp = client.execute(getMethod);
        assertEquals(200, resp.getStatusLine().getStatusCode());
        assertEquals("ID=1580149", asString(resp));
    }

    @Test
    public void testOnlyOneInstanceInGetSingleton() throws IOException {
        String url = getBaseTestUri(appwar, "app1", "res1") + "/testGetSingletonHigherThanGetClass";
        HttpGet getMethod = new HttpGet(url);
        HttpResponse resp = client.execute(getMethod);
        assertEquals(200, resp.getStatusLine().getStatusCode());
        assertEquals("ID=1580149", asString(resp));
    }

}