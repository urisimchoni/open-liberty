/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.security.auth.data.fat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import com.ibm.websphere.simplicity.log.Log;

import componenttest.annotation.MaximumJavaLevel;
import componenttest.custom.junit.runner.FATRunner;
import componenttest.custom.junit.runner.Mode;
import componenttest.custom.junit.runner.Mode.TestMode;
import componenttest.topology.impl.LibertyServer;
import componenttest.topology.impl.LibertyServerFactory;
import componenttest.topology.utils.FATServletClient;

@RunWith(FATRunner.class)
@Mode(TestMode.LITE)
@MaximumJavaLevel(javaLevel = 17)
public class Java2SecurityPassUtils11Test extends FATServletClient {

    private final Class<Java2SecurityPassUtils11Test> thisClass = Java2SecurityPassUtils11Test.class;
    private static LibertyServer server;

    @Rule
    public final TestName testName = new TestName();

    @BeforeClass
    public static void setUp() throws Exception {
        server = LibertyServerFactory.getLibertyServer("com.ibm.ws.security.auth.data.fat.dpm.java2.pu11");
        AuthDataFatUtils.runWithPasswordUtilities11(server);
        AuthDataFatUtils.transformApps(server, "DefaultPrincipalMappingApp.war");
        server.addInstalledAppForValidation("DefaultPrincipalMappingApp");
        server.startServer("Java2SecurityTest.log");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stopServer();
    }

    @Test
    public void programmaticLoginWithDefaultPrincipalMappingAndJava2Security() throws Exception {
        String method = testName.getMethodName();
        Log.info(thisClass, method, "Executing " + method);
        runTest(server, "DefaultPrincipalMappingApp/DefaultPrincipalMappingServlet", AuthDataFatUtils.normalizeTestName(testName));
    }

}
