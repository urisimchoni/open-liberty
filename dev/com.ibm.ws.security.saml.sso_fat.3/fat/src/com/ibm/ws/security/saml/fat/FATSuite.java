/*******************************************************************************
 * Copyright (c) 2014, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.security.saml.fat;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibm.ws.security.fat.common.actions.SecurityTestFeatureEE9RepeatAction;
import com.ibm.ws.security.fat.common.actions.SecurityTestRepeatAction;
import com.ibm.ws.security.saml.fat.IDPInitiated.PkixIDPInitiatedTests;
import com.ibm.ws.security.saml.fat.IDPInitiated.TimeIDPInitiatedTests;
import com.ibm.ws.security.saml.fat.IDPInitiated.TrustedIssuerIDPInitiatedTests;
import com.ibm.ws.security.saml.fat.SPInitiated.PkixSolicitedSPInitiatedTests;
import com.ibm.ws.security.saml.fat.SPInitiated.PkixUnsolicitedSPInitiatedTests;
import com.ibm.ws.security.saml.fat.SPInitiated.TimeSolicitedSPInitiatedTests;
import com.ibm.ws.security.saml.fat.SPInitiated.TimeUnsolicitedSPInitiatedTests;
import com.ibm.ws.security.saml.fat.SPInitiated.TrustedIssuerSolicitedSPInitiatedTests;
import com.ibm.ws.security.saml.fat.SPInitiated.TrustedIssuerUnsolicitedSPInitiatedTests;

import componenttest.rules.repeater.EmptyAction;
import componenttest.rules.repeater.RepeatTests;

@RunWith(Suite.class)
@SuiteClasses({

        TimeIDPInitiatedTests.class,
        TimeSolicitedSPInitiatedTests.class,
        TimeUnsolicitedSPInitiatedTests.class, // no lite
        PkixIDPInitiatedTests.class,
        PkixSolicitedSPInitiatedTests.class,
        PkixUnsolicitedSPInitiatedTests.class, // no lite
        TrustedIssuerIDPInitiatedTests.class,
        TrustedIssuerSolicitedSPInitiatedTests.class,
        TrustedIssuerUnsolicitedSPInitiatedTests.class // no lite

})
/**
 * Purpose: This suite collects and runs all known good test suites.
 */
public class FATSuite {

    @ClassRule
    public static RepeatTests repeat = RepeatTests.with(new EmptyAction().liteFATOnly())
            .andWith(new SecurityTestRepeatAction().onlyOnWindows().fullFATOnly())
            .andWith(new SecurityTestFeatureEE9RepeatAction().notOnWindows().fullFATOnly());

    @BeforeClass
    public static void setup() throws Exception {
        /*
         * Force the tests to use local LDAP server
         */
        System.setProperty("fat.test.really.use.local.ldap", "true");
    }

}
