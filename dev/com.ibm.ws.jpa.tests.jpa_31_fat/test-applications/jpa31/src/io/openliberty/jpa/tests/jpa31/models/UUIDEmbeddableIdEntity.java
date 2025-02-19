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

package io.openliberty.jpa.tests.jpa31.models;

import jakarta.persistence.Basic;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class UUIDEmbeddableIdEntity {
    @EmbeddedId
    private EmbeddableUUID_ID id;

    @Basic
    private String strData;

    public UUIDEmbeddableIdEntity() {

    }

    /**
     * @return the id
     */
    public EmbeddableUUID_ID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(EmbeddableUUID_ID id) {
        this.id = id;
    }

    /**
     * @return the strData
     */
    public String getStrData() {
        return strData;
    }

    /**
     * @param strData the strData to set
     */
    public void setStrData(String strData) {
        this.strData = strData;
    }

    @Override
    public String toString() {
        return "UUIDEmbeddableIdEntity [id=" + id + ", strData=" + strData + "]";
    }

}
