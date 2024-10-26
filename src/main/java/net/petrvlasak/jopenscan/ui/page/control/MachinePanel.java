package net.petrvlasak.jopenscan.ui.page.control;

import com.googlecode.wicketforge.annotations.ComponentFactory;
import net.petrvlasak.jopenscan.domain.CameraType;
import net.petrvlasak.jopenscan.domain.MachineSettings;
import net.petrvlasak.jopenscan.domain.RinglightLedsOn;
import net.petrvlasak.jopenscan.hal.RinglightFactory;
import net.petrvlasak.jopenscan.hal.StepperMotorFactory;
import net.petrvlasak.jopenscan.ui.WicketApplication;
import net.petrvlasak.jopenscan.ui.component.RangeInputAndValuePanel;
import net.petrvlasak.jopenscan.ui.component.StepperMotorTurnLink;
import net.petrvlasak.jopenscan.ui.event.WebSocketEventType;
import net.petrvlasak.jopenscan.ui.page.ControlPageEventType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;

import java.io.Serial;
import java.util.Arrays;

public class MachinePanel extends GenericPanel<MachineSettings> {

    @Serial
    private static final long serialVersionUID = 1L;

    @SpringBean
    private StepperMotorFactory stepperMotorFactory;

    @SpringBean
    private RinglightFactory ringlightFactory;

    public MachinePanel(String id, IModel<MachineSettings> model) {
        super(id, model);

        add(newCameraType("cameraType"));
        add(newRotorWrapper("rotor"));
        add(newTurntableWrapper("turntable"));
        add(newRinglightLeds("ringlightLeds"));
        add(new RangeInputAndValuePanel<>("ringlightIntensity",
                LambdaModel.of(model, MachineSettings::getRinglightIntensity, MachineSettings::setRinglightIntensity),
                (n) -> n + "%", Model.of("Ringlight Intensity:")
        ) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                ringlightFactory.getRinglight().setIntensity(getModelObject());
            }
        }.setMinimum((byte) 0).setMaximum((byte) 100));
    }

    @ComponentFactory
    private DropDownChoice<CameraType> newCameraType(String id) {
        DropDownChoice<CameraType> input = new DropDownChoice<>(id,
                LambdaModel.of(getModel(),
                        MachineSettings::getCameraType,
                        (m, t) -> WicketApplication.get().changeCurrentCameraType(t)),
                Arrays.asList(CameraType.values()), new EnumChoiceRenderer<>()
        );
        input.add(new OnChangeAjaxBehavior() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                input.setModelObject(input.getConvertedInput());
                ControlPageEventType.CAMERA_TYPE_CHANGED.send(input);
            }
        });
        return input;
    }

    @ComponentFactory
    private WebMarkupContainer newRotorWrapper(String id) {
        WebMarkupContainer container = new WebMarkupContainer(id);
        container.setOutputMarkupId(true);
        container.add(new WebMarkupContainer("rotorLabelColumn")
                .add(newRotorEnabled("rotorEnabled", container))
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-6" : "col-lg-4"))));
        container.add(new WebMarkupContainer("rotorUpColumn")
                .add(newRotor("rotorUp", -5f))
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-3" : "col-lg-4"))));
        container.add(new WebMarkupContainer("rotorDownColumn")
                .add(newRotor("rotorDown", 5f))
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-3" : "col-lg-4"))));
        return container;
    }

    private CheckBox newRotorEnabled(String id, WebMarkupContainer container) {
        return newStepperMotorEnabled(id,
                LambdaModel.of(getModel(), MachineSettings::getRotorEnabled, MachineSettings::setRotorEnabled),
                (enabled, target) -> {
                    stepperMotorFactory.getRotor().setEnabled(enabled);
                    target.add(container);
                });
    }

    private CheckBox newStepperMotorEnabled(String id, IModel<Boolean> model,
                                            SerializableBiConsumer<Boolean, AjaxRequestTarget> onUpdate) {
        CheckBox checkBox = new CheckBox(id, model);
        checkBox.add(new AjaxFormComponentUpdatingBehavior("click") {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Boolean enabled = checkBox.getConvertedInput();
                checkBox.setModelObject(enabled);
                onUpdate.accept(enabled, target);
            }
        });
        return checkBox;
    }

    private StepperMotorTurnLink newRotor(String id, float angle) {
        return new StepperMotorTurnLink(id, WebSocketEventType.ROTOR_RUNNING_START,
                WebSocketEventType.ROTOR_RUNNING_STOP, () -> stepperMotorFactory.getRotor(), angle) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isEnabled() {
                return super.isEnabled() && MachinePanel.this.getModelObject().getRotorEnabled();
            }
        };
    }

    @ComponentFactory
    private WebMarkupContainer newTurntableWrapper(String id) {
        WebMarkupContainer container = new WebMarkupContainer(id);
        container.setOutputMarkupId(true);
        container.add(new WebMarkupContainer("turntableLabelColumn")
                .add(newTurntableEnabled("turntableEnabled", container))
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-6" : "col-lg-4"))));
        container.add(new WebMarkupContainer("turntableLeftColumn")
                .add(newTurntable("turntableLeft", 15f))
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-3" : "col-lg-4"))));
        container.add(new WebMarkupContainer("turntableRightColumn")
                .add(newTurntable("turntableRight", -15f))
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-3" : "col-lg-4"))));
        return container;
    }

    private CheckBox newTurntableEnabled(String id, WebMarkupContainer container) {
        return newStepperMotorEnabled(id,
                LambdaModel.of(getModel(), MachineSettings::getTurntableEnabled, MachineSettings::setTurntableEnabled),
                (enabled, target) -> {
                    stepperMotorFactory.getTurntable().setEnabled(enabled);
                    target.add(container);
                });
    }

    private StepperMotorTurnLink newTurntable(String id, float angle) {
        return new StepperMotorTurnLink(id, WebSocketEventType.TURNTABLE_RUNNING_START,
                WebSocketEventType.TURNTABLE_RUNNING_STOP, () -> stepperMotorFactory.getTurntable(), angle) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isEnabled() {
                return super.isEnabled() && MachinePanel.this.getModelObject().getTurntableEnabled();
            }
        };
    }

    @ComponentFactory
    private WebMarkupContainer newRinglightLeds(String id) {
        WebMarkupContainer container = new WebMarkupContainer(id);
        container.add(new WebMarkupContainer("ringlightLedsLabelColumn")
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "" : "col-lg-4"))));
        container.add(new WebMarkupContainer("ringlightLedsValueColumn")
                .add(newRinglightLedsGroup("ringlightLedsGroup"))
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "" : "col-lg-8"))));
        return container;
    }

    @ComponentFactory
    private RadioGroup<RinglightLedsOn> newRinglightLedsGroup(String id) {
        RadioGroup<RinglightLedsOn> group = new RadioGroup<>(id,
                LambdaModel.of(getModel(), MachineSettings::getRinglightLedsOn, MachineSettings::setRinglightLedsOn)
        );
        group.setRenderBodyOnly(false);
        group.add(new Radio<RinglightLedsOn>("ringlightLedsOff", Model.of()));
        group.add(new Radio<>("ringlightLeds1", Model.of(RinglightLedsOn.LED1)));
        group.add(new Radio<>("ringlightLeds2", Model.of(RinglightLedsOn.LED2)));
        group.add(new Radio<>("ringlightLedsBoth", Model.of(RinglightLedsOn.BOTH)));
        group.add(new AjaxFormChoiceComponentUpdatingBehavior() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                group.setModelObject(group.getConvertedInput());
                ringlightFactory.getRinglight().switchOn(group.getModelObject());
            }
        });
        return group;
    }

}
