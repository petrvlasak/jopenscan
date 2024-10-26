package net.petrvlasak.jopenscan.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MachineSettings implements Serializable {

    @Serial
    private static final long serialVersionUID = 848570664965812504L;

    private CameraType cameraType;
    private Boolean rotorEnabled;
    private Boolean turntableEnabled;
    private RinglightLedsOn ringlightLedsOn;
    private Byte ringlightIntensity;

}
