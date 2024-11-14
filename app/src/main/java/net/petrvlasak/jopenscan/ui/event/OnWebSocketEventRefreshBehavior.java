package net.petrvlasak.jopenscan.ui.event;

import org.apache.wicket.Component;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;

import java.io.Serial;
import java.util.Set;

public class OnWebSocketEventRefreshBehavior extends OnWebSocketEventBehavior {

    @Serial
    private static final long serialVersionUID = 1L;

    public OnWebSocketEventRefreshBehavior(WebSocketEventType... observedEventTypes) {
        super(observedEventTypes);
    }

    public OnWebSocketEventRefreshBehavior(Set<WebSocketEventType> observedEventTypes) {
        super(observedEventTypes);
    }

    @Override
    public void bind(Component component) {
        component.setOutputMarkupPlaceholderTag(true);
    }

    @Override
    protected void onEventInternal(Component component, WebSocketRequestHandler handler, WebSocketEventType eventType) {
        handler.add(component);
    }

}
