package net.petrvlasak.jopenscan.ui.event;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.protocol.ws.api.BaseWebSocketBehavior;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.event.WebSocketPushPayload;

import java.io.Serial;
import java.util.Optional;
import java.util.Set;

public abstract class OnWebSocketEventBehavior extends BaseWebSocketBehavior {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Set<WebSocketEventType> observedEventTypes;

    public OnWebSocketEventBehavior(WebSocketEventType... observedEventTypes) {
        this(Set.of(observedEventTypes));
    }

    public OnWebSocketEventBehavior(Set<WebSocketEventType> observedEventTypes) {
        this.observedEventTypes = observedEventTypes;
    }

    @Override
    public void onEvent(Component component, IEvent<?> event) {
        if (event.getPayload() instanceof WebSocketPushPayload payload) {
            WebSocketRequestHandler handler = payload.getHandler();
            if (payload.getMessage() instanceof WebSocketEventType eventType) {
                Optional.of(eventType)
                        .filter(observedEventTypes::contains)
                        .ifPresent(et -> onEventInternal(component, handler, et));
            }
        }
    }

    protected abstract void onEventInternal(Component component, WebSocketRequestHandler handler,
                                            WebSocketEventType eventType);

}
