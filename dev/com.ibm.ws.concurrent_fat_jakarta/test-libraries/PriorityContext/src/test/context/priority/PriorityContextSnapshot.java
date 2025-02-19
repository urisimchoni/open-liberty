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
package test.context.priority;

import jakarta.enterprise.concurrent.spi.ThreadContextRestorer;
import jakarta.enterprise.concurrent.spi.ThreadContextSnapshot;

/**
 * Example third-party thread context provider, to be used for testing purposes.
 * This context provider makes the priority of a thread part of the context that
 * gets propagated.
 */
public class PriorityContextSnapshot implements ThreadContextSnapshot {
    private final int priority;

    PriorityContextSnapshot(int priority) {
        this.priority = priority;
    }

    @Override
    public ThreadContextRestorer begin() {
        ThreadContextRestorer restorer = new PriorityContextRestorer(Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(priority);
        return restorer;
    }

    @Override
    public final int hashCode() {
        return priority;
    }

    @Override
    public String toString() {
        return "PriorityContextSnapshot@" + Integer.toHexString(hashCode());
    }
}
