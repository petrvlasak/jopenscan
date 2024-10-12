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
public class JobSettings implements Serializable {

    @Serial
    private static final long serialVersionUID = -1391706746500062580L;

    private MachineSettings machine;
    private CameraSettings camera;
    private Byte photosPerRotation;
    private Byte verticalPositions;
    private Byte startDeflection;
    private Byte endDeflection;
    private String projectName;

}
