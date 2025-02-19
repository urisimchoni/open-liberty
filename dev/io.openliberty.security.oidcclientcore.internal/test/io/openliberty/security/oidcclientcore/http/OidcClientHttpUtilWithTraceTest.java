/*******************************************************************************
 * Copyright (c) 2012, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.security.oidcclientcore.http;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Drives the extended JUnit tests but with all trace enabled.
 * Used to drive out any lingering bugs which may only be discovered
 * when tracing is enabled.
 */
public class OidcClientHttpUtilWithTraceTest extends OidcClientHttpUtilTest {
    @BeforeClass
    public static void traceSetUp() {
        outputMgr.trace("*=all");
    }

    @AfterClass
    public static void traceTearDown() {
        outputMgr.trace("*=all=disabled");
    }
}
