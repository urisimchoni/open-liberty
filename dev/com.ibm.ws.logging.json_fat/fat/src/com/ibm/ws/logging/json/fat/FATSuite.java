/*******************************************************************************
 * Copyright (c) 2018, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.logging.json.fat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
                MessagesLogDisabledTest.class,
                MessagesLogEnvTest.class,
                ConsoleLogTest.class,
                ServerConfigUpdateTest.class,
                JsonConfigTest.class,
                ConsoleFormatTest.class,
                JsonConfigBootstrapTest.class,
                ContainerEnvVarTest.class,
                AppNameExtensionTest.class,
                ExtensionsMessagesLogTest.class
})
public class FATSuite {

}
