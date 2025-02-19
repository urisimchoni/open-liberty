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
package concurrent.cdi.web;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import jakarta.enterprise.concurrent.Asynchronous;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Provides an asynchronous method for its subclass, RequestScopedBean.
 */
public class ReqBeanSuperclass {
    @Asynchronous(executor = "java:comp/concurrent/appContextExecutor")
    public CompletableFuture<Long> getThreadId() {
        try {
            // prove that the application component's namespace is propagated to the executing thread,
            InitialContext.doLookup("java:comp/concurrent/appContextExecutor");
        } catch (NamingException x) {
            throw new CompletionException(x);
        }

        return Asynchronous.Result.complete(Thread.currentThread().getId());
    }
}
