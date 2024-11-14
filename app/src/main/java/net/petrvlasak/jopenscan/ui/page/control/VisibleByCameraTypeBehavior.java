package net.petrvlasak.jopenscan.ui.page.control;

import net.petrvlasak.jopenscan.domain.CameraType;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.util.Set;

public class VisibleByCameraTypeBehavior extends Behavior {

    @Serial
    private static final long serialVersionUID = 1L;

    private final IModel<CameraType> cameraTypeModel;
    private final Set<CameraType> allowedCameraTypes;

    public VisibleByCameraTypeBehavior(IModel<CameraType> cameraTypeModel, CameraType... allowedCameraTypes) {
        this(cameraTypeModel, Set.of(allowedCameraTypes));
    }

    public VisibleByCameraTypeBehavior(IModel<CameraType> cameraTypeModel, Set<CameraType> allowedCameraTypes) {
        super();
        this.cameraTypeModel = cameraTypeModel;
        this.allowedCameraTypes = allowedCameraTypes;
    }

    @Override
    public void onConfigure(Component component) {
        component.setVisible(allowedCameraTypes.contains(cameraTypeModel.getObject()));
    }

}
