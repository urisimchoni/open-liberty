/*******************************************************************************
 * Copyright (c) 2019,2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.jaxws.fat;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import componenttest.rules.repeater.EmptyAction;
import componenttest.rules.repeater.FeatureReplacementAction;
import componenttest.rules.repeater.RepeatTests;

/*
 * TODO: Lite Mode
 */
@RunWith(Suite.class)
@SuiteClasses({
                EJBServiceRefBndTest.class,
                HandlerChainTest.class,
                HandlerChainWithWebServiceClientTest.class,
                POJOServiceSecurityTest.class,
                PortComponentRefTest.class,
                WebServiceInWebXMLTest.class,
                WsBndEndpointOverrideTest.class,
                WsBndServiceRefOverrideTest.class,
                ColocatedDynamicPolicyAttachmentsTest.class
})
public class FATSuite {

    @ClassRule
    public static RepeatTests r = RepeatTests.with(new EmptyAction()).andWith(FeatureReplacementAction.EE9_FEATURES().removeFeature("jaxwstest-2.2").addFeature("xmlwstest-3.0"));
}
