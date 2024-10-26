package net.petrvlasak.jopenscan.ui.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableFunction;

import java.io.Serial;

public class RangeInputAndValuePanel<N extends Number & Comparable<N>> extends GenericPanel<N> {

    @Serial
    private static final long serialVersionUID = 1L;

    private final RangeTextField<N> field;
    private final Label valueLabel;

    public RangeInputAndValuePanel(String id, SerializableFunction<? super N, String> toStringMapper, IModel<?> labelModel) {
        this(id, null, toStringMapper, labelModel);
    }

    public RangeInputAndValuePanel(String id, IModel<N> model, SerializableFunction<? super N, String> toStringMapper, IModel<?> labelModel) {
        super(id, model);
        add(field = new RangeTextField<N>("field", model));
        add(valueLabel = new Label("valueLabel", model.map(toStringMapper)));
        field.add(new OnChangeAjaxBehavior() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                field.setModelObject(field.getConvertedInput());
                target.add(valueLabel);
                RangeInputAndValuePanel.this.onUpdate(target);
            }
        });
        valueLabel.setOutputMarkupId(true);
        add(new Label("label", labelModel) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("for", field.getMarkupId());
            }
        });
    }

    /**
     * @see NumberTextField#setMinimum(N)
     */
    public RangeInputAndValuePanel<N> setMinimum(N minimum) {
        field.setMinimum(minimum);
        return this;
    }

    /**
     * @see NumberTextField#setMaximum(N)
     */
    public RangeInputAndValuePanel<N> setMaximum(N maximum) {
        field.setMaximum(maximum);
        return this;
    }

    /**
     * @see NumberTextField#setStep(N)
     */
    public RangeInputAndValuePanel<N> setStep(N step) {
        field.setStep(step);
        return this;
    }

    /**
     * @see NumberTextField#setMinimum(IModel)
     */
    public RangeInputAndValuePanel<N> setMinimum(IModel<N> minimum) {
        field.setMinimum(minimum);
        return this;
    }

    /**
     * @see NumberTextField#setMaximum(IModel)
     */
    public RangeInputAndValuePanel<N> setMaximum(IModel<N> maximum) {
        field.setMaximum(maximum);
        return this;
    }

    /**
     * @see NumberTextField#setStep(IModel)
     */
    public RangeInputAndValuePanel<N> setStep(IModel<N> step) {
        field.setStep(step);
        return this;
    }

    /**
     * @see OnChangeAjaxBehavior#onUpdate(AjaxRequestTarget)
     */
    protected void onUpdate(AjaxRequestTarget target) {
    }

}
