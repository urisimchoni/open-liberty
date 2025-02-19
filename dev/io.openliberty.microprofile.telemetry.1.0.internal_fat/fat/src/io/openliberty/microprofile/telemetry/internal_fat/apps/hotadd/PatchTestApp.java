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
package io.openliberty.microprofile.telemetry.internal_fat.apps.hotadd;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;

@ApplicationPath("/rest")
@Path("/test")
public class PatchTestApp extends Application {

    @GET
    public Response get() {
        return Response.ok("heartbeat").build();
    }

    @PATCH
    public Response patch() {
        System.out.println("inside PatchTestApp.patch()");
        return Response.ok("patch-success").build();
    }

}