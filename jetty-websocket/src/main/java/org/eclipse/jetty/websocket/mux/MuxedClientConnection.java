/*******************************************************************************
 * Copyright (c) 1995-2012 Mort Bay Consulting Pty Ltd.
 * ======================================================================
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *   The Eclipse Public License is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *
 *   The Apache License v2.0 is available at
 *   http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 *******************************************************************************/
package org.eclipse.jetty.websocket.mux;

import org.eclipse.jetty.websocket.WebSocket;

public class MuxedClientConnection extends MuxedConnection
{
    public MuxedClientConnection(Connection physicalConnection)
    {
        super(physicalConnection);
    }

    public MuxLogicalConnection requestLogicalChannel(WebSocket websocket)
    {
        // TODO: if running in client mode ...
        // TODO: verify add channel with server?
        // TODO: can verification process be async?
        // TODO: do new channel ids have to be sequential?
        
        int channelNum = requestUnusedChannelNum();
        
        return super.addLogicalChannel(channelNum,websocket);
    }
}