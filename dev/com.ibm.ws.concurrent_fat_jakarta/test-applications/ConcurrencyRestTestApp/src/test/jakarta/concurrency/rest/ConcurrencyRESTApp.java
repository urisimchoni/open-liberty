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
package test.jakarta.concurrency.rest;

import jakarta.enterprise.concurrent.Asynchronous;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;

@ApplicationPath("/testapp")
@Path("/test")
public class ConcurrencyRESTApp extends Application {
    @Asynchronous
    @GET
    @Path("/threadname")
    public void info(final @Suspended AsyncResponse response) {
        response.resume(Response.ok(Thread.currentThread().getName()).build());
    }
}