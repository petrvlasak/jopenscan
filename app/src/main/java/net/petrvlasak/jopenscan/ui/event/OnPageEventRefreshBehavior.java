package net.petrvlasak.jopenscan.ui.event;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;

import java.io.Serial;
import java.util.Set;

public class OnPageEventRefreshBehavior extends OnPageEventBehavior {

    @Serial
    private static final long serialVersionUID = 1L;

    public OnPageEventRefreshBehavior(IPageEventType... observedEventTypes) {
        super(observedEventTypes);
    }

    public OnPageEventRefreshBehavior(Set<IPageEventType> observedEventTypes) {
        super(observedEventTypes);
    }

    @Override
    public void bind(Component component) {
        component.setOutputMarkupPlaceholderTag(true);
    }

    @Override
    protected void onEventInternal(Component component, IPageEventType eventType) {
        RequestCycle.get().find(AjaxRequestTarget.class).ifPresent(t -> t.add(component));
    }

}
