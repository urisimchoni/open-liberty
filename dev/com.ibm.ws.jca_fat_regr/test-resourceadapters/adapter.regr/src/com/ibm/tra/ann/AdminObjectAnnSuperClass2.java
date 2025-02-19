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
package com.ibm.tra.ann;

import java.util.LinkedList;

import javax.jms.Message;
import javax.naming.NamingException;
import javax.naming.Reference;

import com.ibm.tra.inbound.base.TRAAdminObject1;
import com.ibm.tra.inbound.base.TRAAdminObject2;
import com.ibm.tra.trace.DebugTracer;

@SuppressWarnings("serial")
public class AdminObjectAnnSuperClass2 implements TRAAdminObject1, TRAAdminObject2 {

    protected Reference _ref = null;
    @SuppressWarnings("unchecked")
    protected LinkedList _queue;
    protected String _className = this.getClass().getName();
    protected String _factoryName = "com.ibm.tra.inbound.impl.TRAObjectFactory";

    public AdminObjectAnnSuperClass2() {
        super();
    }

    public int countMessages() {
        int size = 0;
        synchronized (_queue) {
            size = _queue.size();
        }
        return size;
    }

    public Reference getReference() throws NamingException {
        if (_ref == null) {
            _ref = new Reference(this.getClass().getName(), _factoryName, null);
        }
        return _ref;
    }

    @SuppressWarnings("unchecked")
    public void putMsg(Message msg) {
        synchronized (_queue) {
            if (DebugTracer.isDebugMessages()) {
                DebugTracer.getPrintStream().println("Adding message to queue: " + msg.toString());
            }
            _queue.addLast(msg);
        }
    }

    public void deleteMsgs() {
    }

}
