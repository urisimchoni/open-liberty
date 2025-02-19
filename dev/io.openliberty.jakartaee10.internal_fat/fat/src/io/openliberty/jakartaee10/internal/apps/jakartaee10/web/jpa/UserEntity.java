/*******************************************************************************
 * Copyright (c) 2017, 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package io.openliberty.jakartaee10.internal.apps.jakartaee10.web.jpa;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    public long id;

    @Basic
    public String strData;

    @Version
    public long version;

    public UserEntity() {
    }

    public UserEntity(String data) {
        strData = data;
    }

    @Override
    public String toString() {
        return "SimpleTestEntity [id=" + id + ", strData=" + strData + ", version=" + version + "]";
    }

}