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
public class CameraSettings implements Serializable {

    @Serial
    private static final long serialVersionUID = 7482024486802964609L;

    private Byte brightness;
    private Byte contrast;
    private Short shutterSpeed;
    private Byte cropX;
    private Byte cropY;
    private Float timePerPhoto;
    private Short releaseTime;

}
