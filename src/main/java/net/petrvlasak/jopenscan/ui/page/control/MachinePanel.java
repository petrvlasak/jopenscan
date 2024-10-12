package net.petrvlasak.jopenscan.ui.page.control;

import com.googlecode.wicketforge.annotations.ComponentFactory;
import net.petrvlasak.jopenscan.domain.CameraType;
import net.petrvlasak.jopenscan.domain.MachineSettings;
import net.petrvlasak.jopenscan.domain.RinglightLedsOn;
import net.petrvlasak.jopenscan.ui.WicketApplication;
import net.petrvlasak.jopenscan.ui.component.RangeInputAndValuePanel;
import net.petrvlasak.jopenscan.ui.page.ControlPageEventType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.util.Arrays;

public class MachinePanel extends GenericPanel<MachineSettings> {

    @Serial
    private static final long serialVersionUID = 1L;

    public MachinePanel(String id, IModel<MachineSettings> model) {
        super(id, model);

        add(newCameraType("cameraType"));
        add(newRotor("rotor"));
        add(newTurntable("turntable"));
        add(newRinglightLeds("ringlightLeds"));
        add(new RangeInputAndValuePanel<>("ringlightIntensity",
                LambdaModel.of(model, MachineSettings::getRinglightIntensity, MachineSettings::setRinglightIntensity),
                (n) -> n + "%", Model.of("Ringlight Intensity:")
        ).setMinimum((byte) 0).setMaximum((byte) 100));
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
    private WebMarkupContainer newRotor(String id) {
        WebMarkupContainer container = new WebMarkupContainer(id);
        container.add(new WebMarkupContainer("rotorLabelColumn")
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-6" : "col-lg-4"))));
        container.add(new WebMarkupContainer("rotorUpColumn")
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-3" : "col-lg-4"))));
        container.add(new WebMarkupContainer("rotorDownColumn")
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-3" : "col-lg-4"))));
        return container;
    }

    @ComponentFactory
    private WebMarkupContainer newTurntable(String id) {
        WebMarkupContainer container = new WebMarkupContainer(id);
        container.add(new WebMarkupContainer("turntableLabelColumn")
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-6" : "col-lg-4"))));
        container.add(new WebMarkupContainer("turntableLeftColumn")
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-3" : "col-lg-4"))));
        container.add(new WebMarkupContainer("turntableRightColumn")
                .add(AttributeAppender.append("class", getModel()
                        .map(MachineSettings::getCameraType)
                        .map(ct -> ct == CameraType.RPI ? "col-lg-3" : "col-lg-4"))));
        return container;
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
            }
        });
        return group;
    }

}
