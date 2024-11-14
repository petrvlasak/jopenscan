package net.petrvlasak.jopenscan.ui.component;

import net.petrvlasak.jopenscan.ui.WicketApplication;
import net.petrvlasak.jopenscan.ui.event.OnWebSocketEventRefreshBehavior;
import net.petrvlasak.jopenscan.ui.event.WebSocketEventType;
import org.apache.wicket.Component;

import java.io.Serial;
import java.util.Set;

public class DisabledDuringScanningBehavior extends OnWebSocketEventRefreshBehavior {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean reverse = false;

    public DisabledDuringScanningBehavior(WebSocketEventType... observedEventTypes) {
        super(observedEventTypes);
    }

    public DisabledDuringScanningBehavior(Set<WebSocketEventType> observedEventTypes) {
        super(observedEventTypes);
    }

    public DisabledDuringScanningBehavior setReverse(boolean reverse) {
        this.reverse = reverse;
        return this;
    }

    @Override
    public void onConfigure(Component component) {
        component.setEnabled(WicketApplication.get().isScanningInProgress() == reverse);
    }

}
