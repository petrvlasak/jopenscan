package net.petrvlasak.jopenscan.ui.component;

import de.agilecoders.wicket.jquery.function.Function;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import java.io.Serial;

import static de.agilecoders.wicket.jquery.JQuery.*;

public class AjaxFormComponentValidationBehavior  extends AjaxFormComponentUpdatingBehavior {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean updateModelObject = false;

    public AjaxFormComponentValidationBehavior() {
        super("input blur");
    }

    public AjaxFormComponentValidationBehavior setUpdateModelObject(boolean updateModelObject) {
        this.updateModelObject = updateModelObject;
        return this;
    }

    private static class JSReference extends JQueryPluginResourceReference {

        @Serial
        private static final long serialVersionUID = 1L;

        private static final JSReference INSTANCE = new JSReference();

        private JSReference() {
            super(AjaxFormComponentValidationBehavior.class, "js/jquery.wb.validation.onblur.js");
        }

    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        response.render(JavaScriptHeaderItem.forReference(JSReference.INSTANCE));
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        Component component = getComponent();
        if (updateModelObject) {
            component.setDefaultModelObject(((FormComponent<?>) component).getConvertedInput());
        }
        target.appendJavaScript($()
                .chain(new Function("wb_validation_onblur.hideError", plain($(component).build())))
                .get()
        );
    }

    @Override
    protected void onError(AjaxRequestTarget target, RuntimeException e) {
        super.onError(target, e);
        String errorMessage = getErrorMessage();
        if (errorMessage != null) {
            target.appendJavaScript($()
                    .chain(new Function("wb_validation_onblur.showError", plain($(getComponent()).build()), quoted(errorMessage)))
                    .get()
            );
        }
    }

    private String getErrorMessage() {
        FeedbackMessages messages = getComponent().getFeedbackMessages();
        if (messages != null && !messages.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (FeedbackMessage message : messages.messages(ErrorLevelFeedbackMessageFilter.ALL)) {
                if (!message.isRendered()) {
                    String msg = message.getMessage().toString();
                    if (msg != null && !msg.isEmpty()) {
                        sb.append(msg);
                        message.markRendered();
                    }
                }
            }
            String messageString = sb.toString();
            if (!messageString.isEmpty()) {
                return messageString;
            }
        }
        return null;
    }

}
