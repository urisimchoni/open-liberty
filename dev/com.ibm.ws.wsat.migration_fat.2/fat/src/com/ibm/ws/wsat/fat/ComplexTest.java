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
package com.ibm.ws.wsat.fat;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ibm.websphere.simplicity.ShrinkHelper;

import componenttest.annotation.AllowedFFDC;
import componenttest.annotation.ExpectedFFDC;
import componenttest.custom.junit.runner.FATRunner;
import componenttest.custom.junit.runner.Mode;
import componenttest.custom.junit.runner.Mode.TestMode;
import componenttest.topology.impl.LibertyServer;
import componenttest.topology.impl.LibertyServerFactory;
import componenttest.topology.utils.HttpUtils;
import com.ibm.ws.transaction.fat.util.FATUtils;
import com.ibm.ws.wsat.fat.util.DBTestBase;

@Mode(TestMode.FULL)
@AllowedFFDC(value = { "javax.transaction.SystemException" })
@RunWith(FATRunner.class)
public class ComplexTest extends DBTestBase {

	private static LibertyServer server1;
	private static String BASE_URL;

	private static LibertyServer server2;
	private static String BASE_URL2;

	private static LibertyServer server3;
	private static String BASE_URL3;

	@BeforeClass
	public static void beforeTests() throws Exception {
		server1 = LibertyServerFactory.getLibertyServer("MigrationServer1");
		BASE_URL = "http://" + server1.getHostname() + ":" + server1.getHttpDefaultPort();
		server2 = LibertyServerFactory.getLibertyServer("MigrationServer2");
		server2.setHttpDefaultPort(9992);
		BASE_URL2 = "http://" + server2.getHostname() + ":" + server2.getHttpDefaultPort();
		server3 = LibertyServerFactory.getLibertyServer("MigrationServer3");
		server3.setHttpDefaultPort(9993);
		BASE_URL3 = "http://" + server3.getHostname() + ":" + server3.getHttpDefaultPort();

		DBTestBase.initWSATTest(server1);
		DBTestBase.initWSATTest(server2);
		DBTestBase.initWSATTest(server3);

		ShrinkHelper.defaultDropinApp(server1, "simpleClient", "com.ibm.ws.wsat.simpleclient.client.simple");
		ShrinkHelper.defaultDropinApp(server2, "simpleServer", "com.ibm.ws.wsat.simpleserver.server");
		ShrinkHelper.defaultDropinApp(server3, "simpleServer", "com.ibm.ws.wsat.simpleserver.server");

		server1.setServerStartTimeout(START_TIMEOUT);
		server2.setServerStartTimeout(START_TIMEOUT);
		server3.setServerStartTimeout(START_TIMEOUT);

		FATUtils.startServers(server1, server2, server3);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		FATUtils.stopServers((String[])null, server1, server2, server3);

		DBTestBase.cleanupWSATTest(server1);
		DBTestBase.cleanupWSATTest(server2);
		DBTestBase.cleanupWSATTest(server3);
	}

	@Test
	public void testWSATRE064FVT() {
		callServlet("WSATRE064FVT");		
	}

	@Test
	public void testWSATRE065FVT() {
		callServlet("WSATRE065FVT");
	}

	@Test
	@Mode(TestMode.LITE)
	@AllowedFFDC(value = { "javax.transaction.xa.XAException", "javax.transaction.RollbackException", "javax.transaction.SystemException" })
	public void testWSATRE066FVT() {
		callServlet("WSATRE066FVT");
	}

	@Test
	@AllowedFFDC(value = { "javax.transaction.xa.XAException", "javax.transaction.RollbackException", "javax.transaction.SystemException" })
	public void testWSATRE067FVT() {
		callServlet("WSATRE067FVT");
	}

	@Test
	@ExpectedFFDC(value = { "javax.transaction.xa.XAException", "javax.transaction.RollbackException" })
	public void testWSATRE068FVT() {
		callServlet("WSATRE068FVT");
	}

	@Test
	@Mode(TestMode.LITE)
	public void testWSATRE069FVT() {
		callServlet("WSATRE069FVT");
	}

	@Test
	public void testWSATRE070FVT() {
		callServlet("WSATRE070FVT");
	}

	@Test
	@AllowedFFDC(value = { "javax.transaction.xa.XAException", "javax.transaction.RollbackException", "javax.transaction.SystemException" })
	public void testWSATRE071FVT() {
		callServlet("WSATRE071FVT");
	}

	@Test
	@Mode(TestMode.LITE)
	@AllowedFFDC(value = { "javax.transaction.xa.XAException", "javax.transaction.RollbackException", "javax.transaction.SystemException" })
	public void testWSATRE072FVT() {
		callServlet("WSATRE072FVT");
	}

	@Test
	@AllowedFFDC(value = { "javax.transaction.xa.XAException", "javax.transaction.RollbackException", "javax.transaction.SystemException" })
	public void testWSATRE073FVT() {
		callServlet("WSATRE073FVT");
	}

	private void callServlet(String testMethod){
		try {
			String urlStr = BASE_URL + "/simpleClient/SimpleClientServlet"
					+ "?method=" + Integer.parseInt(testMethod.substring(6, 9))
					+ "&baseurl=" + BASE_URL2 +"&baseurl2=" + BASE_URL3 ;
			System.out.println(testMethod + " URL: " + urlStr);
			HttpURLConnection con = getHttpConnection(new URL(urlStr), 
					HttpURLConnection.HTTP_OK, REQUEST_TIMEOUT);
			BufferedReader br = HttpUtils.getConnectionStream(con);
			String result = br.readLine();
			assertNotNull(result);
			System.out.println(testMethod + " Result : " + result);
			assertTrue("Cannot get expected reply from server, result = '" + result + "'",
					result.contains("Test passed"));
		} catch (Exception e) {
			fail("Exception happens: " + e.toString());
		}
	}
}
