/*******************************************************************************
 * Copyright (c) 2020, 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.grpc.internal.client.monitor;

/**
 * Holds metrics used for client-side monitoring of gRPC services. </br>
 * Statistic monitored:
 * <ul>
 * <li>Total number of RPCs started on the client.
 * <li>Total number of RPCs completed on the client, regardless of success or
 * failure.
 * <li>Histogram of RPC response latency for completed RPCs, in milliseconds.
 * <li>Total number of stream messages received from the server.
 * <li>Total number of stream messages sent by the client.
 * </ul>
 */
public class GrpcClientStatsMonitor {

	private final GrpcMethod method;
	private long latency;

	public GrpcClientStatsMonitor(GrpcMethod method) {
		this.method = method;
	}

	public void recordCallStarted() {
	}

	public void recordClientHandled() {
	}

	public void recordMsgReceived() {
	}

	public void recordMsgSent() {
	}

	public void recordLatency(long latencyMs) {
		this.latency = latencyMs;
	}

	public String getServiceName() {
		return method.serviceName();
	}

	public GrpcMethod getMethod() {
		return method;
	}
	
	public long getLatency() {
		return latency;
	}
}
