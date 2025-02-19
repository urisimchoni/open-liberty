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
package test.jakarta.concurrency.web;

import java.io.Serializable;
import java.util.concurrent.Executor;

/**
 * Interface class for testing contextual proxies.
 */
public interface Ser1Executor extends Executor, Serializable {
}