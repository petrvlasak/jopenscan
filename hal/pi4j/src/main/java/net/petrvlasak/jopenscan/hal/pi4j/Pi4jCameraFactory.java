package net.petrvlasak.jopenscan.hal.pi4j;

import com.pi4j.context.Context;
import net.petrvlasak.jopenscan.hal.Camera;
import net.petrvlasak.jopenscan.hal.CameraFactory;
import org.springframework.stereotype.Component;

@Component
public class Pi4jCameraFactory implements CameraFactory {

    private final Camera rpiCamera;
    private final Camera externalCamera;

    public Pi4jCameraFactory(Context context) {
        rpiCamera = new Pi4jRPiCamera(context);
        externalCamera = new Pi4jExternalCamera(context);
    }

    @Override
    public Camera getRPiCamera() {
        return rpiCamera;
    }

    @Override
    public Camera getExternalCamera() {
        return externalCamera;
    }

}
