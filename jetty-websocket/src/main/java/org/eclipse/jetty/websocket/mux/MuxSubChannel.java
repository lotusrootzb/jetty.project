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
import org.eclipse.jetty.websocket.WebSocket.OnBinaryMessage;
import org.eclipse.jetty.websocket.WebSocket.OnControl;
import org.eclipse.jetty.websocket.WebSocket.OnTextMessage;
import org.eclipse.jetty.websocket.WebSocketConnectionRFC6455;

/**
 * MuxSubChannel is a {@link MuxChannel} for communications, with id > 0.
 * 
 * @see <a href="http://tools.ietf.org/html/draft-tamplin-hybi-google-mux-02">draft-tamplin-hybi-google-mux-02</a>
 */
public class MuxSubChannel implements MuxChannel
{
    private WebSocket _webSocket;

    private WebSocket.OnTextMessage _onTextMessage;
    private WebSocket.OnBinaryMessage _onBinaryMessage;
    private WebSocket.OnControl _onControl;

    public MuxSubChannel(WebSocket websocket)
    {
        _webSocket = websocket;
        _onTextMessage = _webSocket instanceof OnTextMessage?(OnTextMessage)_webSocket:null;
        _onBinaryMessage = _webSocket instanceof OnBinaryMessage?(OnBinaryMessage)_webSocket:null;
        _onControl = _webSocket instanceof OnControl?(OnControl)_webSocket:null;
    }

    public void onMuxData(int channelId, byte[] data, int offset, int length)
    {
        // Send to underlying websocket impl
        if (_onBinaryMessage != null)
        {
            _onBinaryMessage.onMessage(data,offset,length);
        }
    }

    public void onMuxOpen(int channelId, MuxLogicalConnection connection)
    {
        // Notify MUX of open connection
        if (_onControl != null)
        {
            _onControl.onOpen(connection);
        }
    }

    public void onMuxClose(int channelId, MuxLogicalConnection connection)
    {
        // Notify MUX of close connection
        if (_onControl != null)
        {
            _onControl.onClose(WebSocketConnectionRFC6455.CLOSE_NORMAL,"channel closed normally");
        }
    }
}