package net.petrvlasak.jopenscan.ui.component;

import net.petrvlasak.jopenscan.ui.WicketApplication;
import net.petrvlasak.jopenscan.ui.event.OnWebSocketEventRefreshBehavior;
import net.petrvlasak.jopenscan.ui.event.WebSocketEventType;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;

import java.io.Serial;

public abstract class ThreadStarterLink extends AjaxLink<Void> implements Runnable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final WebSocketEventType startEventType;
    private final WebSocketEventType stopEventType;

    public ThreadStarterLink(String id, WebSocketEventType startEventType, WebSocketEventType stopEventType) {
        super(id);
        this.startEventType = startEventType;
        this.stopEventType = stopEventType;
        add(new OnWebSocketEventRefreshBehavior(startEventType, stopEventType) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onEventInternal(Component component, WebSocketRequestHandler handler, WebSocketEventType eventType) {
                component.setEnabled(eventType == stopEventType);
                super.onEventInternal(component, handler, eventType);
                if (eventType == startEventType) {
                    onStart(handler);
                } else {
                    onStop(handler);
                }
            }
        });
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        WicketApplication application = WicketApplication.get();
        Thread thread = Thread.ofVirtual().start(this);
        Thread.ofVirtual().start(() -> {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
            application.sendPushMessage(stopEventType);
        });
        application.sendPushMessage(startEventType);
    }

    protected void onStart(WebSocketRequestHandler handler) {
    }

    protected void onStop(WebSocketRequestHandler handler) {
    }

}
