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
package io.openliberty.security.jakartasec;

import java.lang.annotation.Annotation;
import java.util.Map;

import jakarta.security.enterprise.authentication.mechanism.http.openid.ClaimsDefinition;

public class TestClaimsDefinition {

    protected static String CALLER_NAME_CLAIM = "callerNameClaim";
    protected static String CALLER_GROUPS_CLAIM = "callerGroupsClaim";
    protected static String CALLER_NAME_CLAIM_DEFAULT = "preferred_username";
    protected static String CALLER_GROUPS_CLAIM_DEFAULT = "groups";

    protected static ClaimsDefinition getInstanceofAnnotation(final Map<String, Object> overrides) {
        ClaimsDefinition annotation = new ClaimsDefinition() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String callerNameClaim() {
                return (overrides != null && overrides.containsKey(CALLER_NAME_CLAIM)) ? (String) overrides.get(CALLER_NAME_CLAIM) : CALLER_NAME_CLAIM_DEFAULT;
            }

            @Override
            public String callerGroupsClaim() {
                return (overrides != null && overrides.containsKey(CALLER_GROUPS_CLAIM)) ? (String) overrides.get(CALLER_GROUPS_CLAIM) : CALLER_GROUPS_CLAIM_DEFAULT;
            }

        };

        return annotation;
    }

}