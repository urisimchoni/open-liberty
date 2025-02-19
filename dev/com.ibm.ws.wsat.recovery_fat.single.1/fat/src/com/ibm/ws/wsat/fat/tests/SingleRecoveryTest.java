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
package com.ibm.ws.wsat.fat.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ibm.tx.jta.ut.util.XAResourceImpl;
import com.ibm.websphere.simplicity.ProgramOutput;
import com.ibm.websphere.simplicity.ShrinkHelper;
import com.ibm.websphere.simplicity.log.Log;
import com.ibm.ws.transaction.fat.util.FATUtils;
import com.ibm.ws.wsat.fat.util.WSATTest;

import componenttest.custom.junit.runner.FATRunner;
import componenttest.exception.TopologyException;
import componenttest.topology.impl.LibertyServer;
import componenttest.topology.impl.LibertyServerFactory;
import componenttest.topology.utils.HttpUtils;

@RunWith(FATRunner.class)
public class SingleRecoveryTest {

	protected static LibertyServer server1 = LibertyServerFactory
			.getLibertyServer("WSATSingleRecovery");
	protected static String BASE_URL = "http://" + server1.getHostname() + ":"
			+ server1.getHttpDefaultPort();
	
	private final static int REQUEST_TIMEOUT = 10;

	@BeforeClass
	public static void beforeTests() throws Exception {
		ShrinkHelper.defaultDropinApp(server1, "recoveryClient", "com.ibm.ws.wsat.fat.client.recovery.*");
		ShrinkHelper.defaultDropinApp(server1, "recoveryServer", "com.ibm.ws.wsat.fat.server.*");

		server1.setServerStartTimeout(600000);

		FATUtils.startServers(server1);
	}
	
	@Before
	public void before() throws IOException {
		WSATTest.callClearResourcesServlet("recoveryServer", server1);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		FATUtils.stopServers((String[])null, server1);
	}

	protected void recoveryTest(String id) throws Exception {
		final String method = "recoveryTest";
		String result = null;
		String logKeyword = "Jordan said in test: ";
		System.out.println(logKeyword + "========== recoveryTest " + id
				+ " start ==========");
		try {
			// We expect this to fail since it is gonna crash the server
			System.out.println(logKeyword + "callServlet with setupRec and "
					+ id + " start");
			result = callServlet("setupRec" + id);
			System.out.println(logKeyword + "callServlet with setupRec and "
					+ id + " end");
		} catch (IOException e) {
			Log.info(this.getClass(), method, "setupRec" + id + " failed to return. As expected.");
		}

		System.out.println(logKeyword + "waitForStringInLog Dump State start");
        assertNotNull(server1.getServerName() + " didn't crash properly", server1.waitForStringInLog(XAResourceImpl.DUMP_STATE));
		System.out.println(logKeyword + "waitForStringInLog Dump State end");
        server1.postStopServerArchive(); // must explicitly collect since server start failed

		ProgramOutput po;
		// Some tests kill the server twice so we need an extra restart here
		if (id.equals("42") || id.equals("41")) {
			try {
				po = server1.startServerAndValidate(false, false, false, true);
				System.out.println("Start server return code: " + po.getReturnCode());
			} catch (TopologyException e) {
				e.printStackTrace(System.out);
			}

			// make sure it's dead
	        assertNotNull(server1.getServerName() + " didn't crash properly", server1.waitForStringInLog(XAResourceImpl.DUMP_STATE));
		}

		po = server1.startServerAndValidate(false, false, false);
		System.out.println("Start server return code: " + po.getReturnCode());

		// Server appears to have started ok
		server1.waitForStringInTrace("Performed recovery for "+server1.getServerName(), FATUtils.LOG_SEARCH_TIMEOUT);

		server1.validateAppsLoaded();
		
		try {
			result = FATUtils.runWithRetries(()->callServlet("checkRec" + id));
		} catch (Exception e) {
			// Something is seriously wrong with this server instance. Reset so the next test has a chance
			FATUtils.stopServers(server1);
			FATUtils.startServers(server1);
			throw e;
		}

		Log.info(this.getClass(), method, "checkRec" + id + " returned: "
				+ result);
		System.out.println(logKeyword + "********** recoveryTest " + id
				+ " end **********");
	}

	private String callServlet(String testMethod) throws IOException {
		String servletName = "";
		int expectedConnectionCode = HttpURLConnection.HTTP_OK;
		int testNumber = Integer.parseInt(testMethod.substring(8, 10));
		if (testMethod.startsWith("setupRec")) {
			servletName = "RecoverySetupServlet";
			if (testNumber == 1)
				expectedConnectionCode = HttpURLConnection.HTTP_NOT_FOUND;
		} else if (testMethod.startsWith("checkRec")) {
			servletName = "RecoveryCheckServlet";
			expectedConnectionCode = HttpURLConnection.HTTP_OK;
		}

		String providerURL = BASE_URL;
		String urlStr = BASE_URL + "/recoveryClient/" + servletName
				+ "?number=" + testNumber + "&baseurl=" + providerURL;
		System.out.println(testMethod + " URL: " + urlStr);
		String result = "";
		HttpURLConnection con = HttpUtils.getHttpConnection(new URL(urlStr),
				expectedConnectionCode, REQUEST_TIMEOUT);
		try {
			BufferedReader br = HttpUtils.getConnectionStream(con);
			result = br.readLine();
		} finally {
			con.disconnect();
		}
		assertNotNull(result);
		System.out
				.println("Recover test " + testNumber + " Result : " + result);
		assertTrue("Get empty reply from server: " + result,
				!result.equals(""));
		assertTrue("Cannot get expected success reply from server: " + result,
				result.contains("get resource states successfully"));
		return "";
	}
}