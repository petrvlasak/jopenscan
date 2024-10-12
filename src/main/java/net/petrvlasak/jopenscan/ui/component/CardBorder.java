package net.petrvlasak.jopenscan.ui.component;

import de.agilecoders.wicket.core.markup.html.bootstrap.helpers.ColorAndBackgroundBehavior;
import de.agilecoders.wicket.core.util.Attributes;
import de.agilecoders.wicket.core.util.Components;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

import java.io.Serial;

public class CardBorder extends Border {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Label headerLabel;

    /**
     * @see Component#Component(String)
     */
    public CardBorder(String id) {
        this(id, null);
    }

    /**
     * @see Component#Component(String, IModel)
     */
    public CardBorder(String id, IModel<?> model) {
        super(id, model);
        headerLabel = new Label("header") {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(getDefaultModelObject() != null);
            }
        };
        addToBorder(headerLabel);
    }

    public CardBorder withHeader(String header) {
        return withHeader(Model.of(header));
    }

    public CardBorder withHeader(IModel<String> headerModel) {
        headerLabel.setDefaultModel(headerModel);
        return this;
    }

    public CardBorder withHeaderBgColor(ColorAndBackgroundBehavior.Color headerBgColor) {
        Args.notNull(headerBgColor, "headerBgColor");
        return withHeaderBgColor(Model.of(headerBgColor));
    }

    public CardBorder withHeaderBgColor(IModel<ColorAndBackgroundBehavior.Color> headerBgColorModel) {
        Args.notNull(headerBgColorModel, "headerBgColorModel");
        headerLabel.add(new ColorAndBackgroundBehavior(headerBgColorModel));
        return this;
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        Components.assertTag(this, tag, "div");
        Attributes.addClass(tag, "card");
    }

}
