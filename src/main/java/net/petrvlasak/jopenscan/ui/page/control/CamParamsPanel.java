package net.petrvlasak.jopenscan.ui.page.control;

import com.googlecode.wicketforge.annotations.ComponentFactory;
import net.petrvlasak.jopenscan.ui.component.RangeInputAndValuePanel;
import net.petrvlasak.jopenscan.domain.CameraSettings;
import net.petrvlasak.jopenscan.domain.CameraType;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;

import java.io.Serial;

public class CamParamsPanel extends GenericPanel<CameraSettings> {

    @Serial
    private static final long serialVersionUID = 1L;

    public CamParamsPanel(String id, IModel<CameraSettings> model, IModel<CameraType> cameraTypeModel) {
        super(id, model);

        add(newBrightness("brightness").add(new VisibleByCameraTypeBehavior(cameraTypeModel, CameraType.RPI)));
        add(newContrast("contrast").add(new VisibleByCameraTypeBehavior(cameraTypeModel, CameraType.RPI)));
        add(newShutterSpeed("shutterSpeed").add(new VisibleByCameraTypeBehavior(cameraTypeModel, CameraType.RPI)));
        add(newCropX("cropX").add(new VisibleByCameraTypeBehavior(cameraTypeModel, CameraType.RPI)));
        add(newCropY("cropY").add(new VisibleByCameraTypeBehavior(cameraTypeModel, CameraType.RPI)));
        add(newTimePerPhoto("timePerPhoto").add(new VisibleByCameraTypeBehavior(cameraTypeModel, CameraType.EXT)));
        add(newReleaseTime("releaseTime").add(new VisibleByCameraTypeBehavior(cameraTypeModel, CameraType.EXT)));
    }

    @ComponentFactory
    private RangeInputAndValuePanel<Byte> newBrightness(String id) {
        return new RangeInputAndValuePanel<>(id,
                LambdaModel.of(getModel(), CameraSettings::getBrightness, CameraSettings::setBrightness),
                (n) -> n + "%", Model.of("Brightness:")
        ).setMinimum((byte) 0).setMaximum((byte) 100);
    }

    @ComponentFactory
    private RangeInputAndValuePanel<Byte> newContrast(String id) {
        return new RangeInputAndValuePanel<>(id,
                LambdaModel.of(getModel(), CameraSettings::getContrast, CameraSettings::setContrast),
                (n) -> n + "%", Model.of("Contrast:")
        ).setMinimum((byte) 0).setMaximum((byte) 100);
    }

    @ComponentFactory
    private RangeInputAndValuePanel<Short> newShutterSpeed(String id) {
        return new RangeInputAndValuePanel<>(id,
                LambdaModel.of(getModel(), CameraSettings::getShutterSpeed, CameraSettings::setShutterSpeed),
                (n) -> n + "ms", Model.of("Shutter Speed:")
        ).setMinimum((short) 1).setMaximum((short) 700);
    }

    @ComponentFactory
    private RangeInputAndValuePanel<Byte> newCropX(String id) {
        return new RangeInputAndValuePanel<>(id,
                LambdaModel.of(getModel(), CameraSettings::getCropX, CameraSettings::setCropX),
                (n) -> n + "%", Model.of("Crop X:")
        ).setMinimum((byte) 0).setMaximum((byte) 99);
    }

    @ComponentFactory
    private RangeInputAndValuePanel<Byte> newCropY(String id) {
        return new RangeInputAndValuePanel<>(id,
                LambdaModel.of(getModel(), CameraSettings::getCropY, CameraSettings::setCropY),
                (n) -> n + "%", Model.of("Crop Y:")
        ).setMinimum((byte) 0).setMaximum((byte) 99);
    }

    @ComponentFactory
    private RangeInputAndValuePanel<Float> newTimePerPhoto(String id) {
        return new RangeInputAndValuePanel<>(id,
                LambdaModel.of(getModel(), CameraSettings::getTimePerPhoto, CameraSettings::setTimePerPhoto),
                (n) -> String.format("%3.1fs", n), Model.of("Time per Photo:")
        ).setMinimum(0.5f).setMaximum(10f).setStep(0.5f);
    }

    @ComponentFactory
    private RangeInputAndValuePanel<Short> newReleaseTime(String id) {
        return new RangeInputAndValuePanel<>(id,
                LambdaModel.of(getModel(), CameraSettings::getReleaseTime, CameraSettings::setReleaseTime),
                (n) -> n + "ms", Model.of("Release Time:")
        ).setMinimum((short) 10).setMaximum((short) 1000);
    }

}
