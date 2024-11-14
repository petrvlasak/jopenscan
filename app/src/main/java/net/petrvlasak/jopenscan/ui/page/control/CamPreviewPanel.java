package net.petrvlasak.jopenscan.ui.page.control;

import net.petrvlasak.jopenscan.domain.CameraSettings;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import java.io.Serial;

public class CamPreviewPanel extends GenericPanel<CameraSettings> {

    @Serial
    private static final long serialVersionUID = 1L;

    public CamPreviewPanel(String id, IModel<CameraSettings> model) {
        super(id, model);
    }

}
