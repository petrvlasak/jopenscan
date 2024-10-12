package net.petrvlasak.jopenscan.ui.event;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;

import java.io.Serial;
import java.util.Optional;
import java.util.Set;

public abstract class OnPageEventBehavior extends Behavior {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Set<IPageEventType> observedEventTypes;

    public OnPageEventBehavior(IPageEventType... observedEventTypes) {
        this(Set.of(observedEventTypes));
    }

    public OnPageEventBehavior(Set<IPageEventType> observedEventTypes) {
        this.observedEventTypes = observedEventTypes;
    }

    @Override
    public void onEvent(Component component, IEvent<?> event) {
        Optional.ofNullable(event.getPayload())
                .filter(observedEventTypes::contains)
                .ifPresent(p -> onEventInternal(component, (IPageEventType) p));
    }

    protected abstract void onEventInternal(Component component, IPageEventType eventType);

}
