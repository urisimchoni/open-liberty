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
package com.ibm.ws.transactionalEJB.web;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;

import componenttest.app.FATServlet;

/**
 * Servlet implementation class TransactionalEJBTest
 *
 * App should never deploy because TestEJB has @Transactional annotation
 */
@SuppressWarnings("serial")
@WebServlet("/transactionalEJB")
public class TransactionalEJBTestServlet extends FATServlet {

    public void testNoTransactionalEJB() throws NamingException {
        @SuppressWarnings("unused")
        final TestEJB t = (TestEJB) new InitialContext().lookup("java:module/TestEJB");
    }
}