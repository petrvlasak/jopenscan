package net.petrvlasak.jopenscan.ui.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serial;

@MountPath("results")
public class ResultsPage extends BasePage<Void> {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResultsPage(PageParameters parameters) {
        super(parameters);
        addTitlePart("Results");
    }

}
