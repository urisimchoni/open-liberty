/*******************************************************************************
 * Copyright (c) 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.cdi.visibility.tests.sharedlib.crossInjectionLib2Jar;

import javax.enterprise.context.ApplicationScoped;

import com.ibm.ws.cdi.visibility.tests.sharedlib.crossInjectionLib1Jar.Child;

@ApplicationScoped
public class Parent extends Child {}

