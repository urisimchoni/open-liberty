/*******************************************************************************
 * Copyright (c) 2014, 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ejbcontainer.interceptor.v32.aroundconstruct.ejb;

import javax.annotation.PostConstruct;
import javax.interceptor.InvocationContext;

public class PostConstructInterceptor1 {

    @PostConstruct
    public Object postConstruct(InvocationContext ctx) throws Exception {
        Object o = ctx.proceed();

        if (o != null) {
            throw new Exception("Excepted ctx.proceed() to return null even though postConstruct in PostConstructInterceptor2 returned a String.");
        }

        return o;
    }

}
