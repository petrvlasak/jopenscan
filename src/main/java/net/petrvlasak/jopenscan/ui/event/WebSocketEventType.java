package net.petrvlasak.jopenscan.ui.event;

import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;

public enum WebSocketEventType implements IWebSocketPushMessage {

    SCANNING_STATE_CHANGED,
    SCANNING_NEW_PROGRESS

}
