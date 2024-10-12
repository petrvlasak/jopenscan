package net.petrvlasak.jopenscan.ui.page.control;

import com.googlecode.wicketforge.annotations.ComponentFactory;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.ui.WicketApplication;
import net.petrvlasak.jopenscan.ui.component.AjaxFormComponentValidationBehavior;
import net.petrvlasak.jopenscan.ui.component.RangeInputAndValuePanel;
import net.petrvlasak.jopenscan.ui.page.ControlPageEventType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.validation.validator.PatternValidator;

import java.io.Serial;

public class CurrentJobPanel extends GenericPanel<JobSettings> {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Form<Void> projectForm;

    public CurrentJobPanel(String id, IModel<JobSettings> model) {
        super(id, model);

        add(new RangeInputAndValuePanel<>("photosPerRotation",
                LambdaModel.of(model, JobSettings::getPhotosPerRotation, JobSettings::setPhotosPerRotation),
                String::valueOf, Model.of("Photos per Rotation:")
        ).setMinimum((byte) 3).setMaximum((byte) 20));
        add(new RangeInputAndValuePanel<>("verticalPositions",
                LambdaModel.of(model, JobSettings::getVerticalPositions, JobSettings::setVerticalPositions),
                String::valueOf, Model.of("Vertical Positions:")
        ).setMinimum((byte) 2).setMaximum((byte) 20));
        add(new RangeInputAndValuePanel<>("startDeflection",
                LambdaModel.of(model, JobSettings::getStartDeflection, JobSettings::setStartDeflection),
                (n) -> n + "°", Model.of("Start Deflection:")
        ).setMinimum((byte) 0).setMaximum((byte) 90));
        add(new RangeInputAndValuePanel<>("endDeflection",
                LambdaModel.of(model, JobSettings::getEndDeflection, JobSettings::setEndDeflection),
                (n) -> n + "°", Model.of("End Deflection:")
        ).setMinimum((byte) 0).setMaximum((byte) 90));
        add(projectForm = newProjectForm("projectForm"));
        add(newSettingsFile());
        add(newCurrentStatus("currentStatus"));
        add(newStart("start"));
        add(newStop("stop"));
    }

    @ComponentFactory
    private Form<Void> newProjectForm(String id) {
        Form<Void> form = new Form<>(id);
        form.add(newProjectName("projectName"));
        return form;
    }

    private TextField<String> newProjectName(String id) {
        TextField<String> input = new TextField<>(id,
                LambdaModel.of(getModel(), JobSettings::getProjectName, JobSettings::setProjectName)
        );
        input.setRequired(true);
        input.add(new PatternValidator("[a-zA-Z0-9_-]{3,}"));
        input.add(new AjaxFormComponentValidationBehavior().setUpdateModelObject(true));
        return input;
    }

    private Form<Void> newSettingsFile() {
        NotificationPanel feedback = new NotificationPanel("settingsFileFeedback");
        feedback.setOutputMarkupId(true);

        FileUploadField file = new FileUploadField("settingsFile");

        AjaxButton load = new AjaxButton("settingsFileLoad") {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                target.add(feedback);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                target.add(feedback);
            }
        };

        AjaxLink<Void> reset = new AjaxLink<>("resetSettings") {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                WicketApplication.get().resetSettings();
                ControlPageEventType.SETTINGS_UPDATED.send(this);
            }
        };

        Form<Void> form = newSettingsFileForm("settingsFileForm", file);
        form.add(feedback, file, load, reset);

        return form;
    }

    @ComponentFactory
    private Form<Void> newSettingsFileForm(String id, FileUploadField file) {
        Form<Void> form = new Form<>(id) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                FileUpload upload = file.getFileUpload();
                if (upload == null) {
                    error("Please select a settings file");
                    return;
                }
                try {
                    WicketApplication.get().loadSettings(upload.getInputStream());
                    ControlPageEventType.SETTINGS_UPDATED.send(this);
                    info("The settings have been loaded successfully");
                } catch (Exception e) {
                    error("Error during loading of the settings file: " + e.getMessage());
                }
            }
        };
        form.setMaxSize(Bytes.kilobytes(100));
        return form;
    }

    @ComponentFactory
    private Label newCurrentStatus(String id) {
        return new Label(id, LambdaModel.of(WicketApplication::get).map(WicketApplication::getCurrentJobStatus));
    }

    @ComponentFactory
    private AjaxButton newStart(String id) {
        return new AjaxButton(id, projectForm) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                //TODO
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                target.add(projectForm);
            }
        };
    }

    @ComponentFactory
    private AjaxLink<Void> newStop(String id) {
        return new AjaxLink<>(id) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                //TODO
            }
        };
    }

}
