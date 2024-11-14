package net.petrvlasak.jopenscan.ui.event;

import org.apache.wicket.Component;
import org.apache.wicket.event.Broadcast;

public interface IPageEventType {

    default void send(Component component) {
        component.send(component.getPage(), Broadcast.BREADTH, this);
    }

}
