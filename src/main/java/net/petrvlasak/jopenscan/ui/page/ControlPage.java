package net.petrvlasak.jopenscan.ui.page;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import com.googlecode.wicketforge.annotations.ComponentFactory;
import de.agilecoders.wicket.core.markup.html.bootstrap.helpers.ColorAndBackgroundBehavior;
import net.petrvlasak.jopenscan.ui.WicketApplication;
import net.petrvlasak.jopenscan.ui.component.CardBorder;
import net.petrvlasak.jopenscan.ui.event.OnPageEventRefreshBehavior;
import net.petrvlasak.jopenscan.domain.CameraType;
import net.petrvlasak.jopenscan.domain.JobSettings;
import net.petrvlasak.jopenscan.domain.MachineSettings;
import net.petrvlasak.jopenscan.ui.page.control.*;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serial;

@WicketHomePage
@MountPath("control")
public class ControlPage extends BasePage<JobSettings> {

    @Serial
    private static final long serialVersionUID = 1L;

    public ControlPage(PageParameters parameters) {
        super(parameters);
        addTitlePart("Control");
        IModel<JobSettings> model = LambdaModel.of(WicketApplication::get).map(WicketApplication::getCurrentJobSettings);
        setModel(model);

        add(newCamPreview("camPreview"));
        add(newMachineColumn("machineColumn"));
        add(newCamParamsColumn("camParamsColumn"));
        add(newCurrentJob("currentJob"));
    }

    @ComponentFactory
    private static CardBorder newCardBorder(String id, String header) {
        return new CardBorder(id).withHeader(header).withHeaderBgColor(ColorAndBackgroundBehavior.Color.Primary);
    }

    @ComponentFactory
    private Component newCamPreview(String id) {
        IModel<JobSettings> model = getModel();
        return newCardBorder(id, "Camera Preview")
                .add(new CamPreviewPanel("camPreviewPanel", model.map(JobSettings::getCamera)))
                .add(new VisibleByCameraTypeBehavior(model.map(JobSettings::getMachine).map(MachineSettings::getCameraType), CameraType.RPI))
                .add(new OnPageEventRefreshBehavior(ControlPageEventType.CAMERA_TYPE_CHANGED, ControlPageEventType.SETTINGS_UPDATED));
    }

    @ComponentFactory
    private Component newMachineColumn(String id) {
        return new ColumnWrapper(id)
                .add(newCardBorder("machine", "Machine")
                        .add(new MachinePanel("machinePanel", getModel().map(JobSettings::getMachine))))
                .add(new OnPageEventRefreshBehavior(ControlPageEventType.CAMERA_TYPE_CHANGED, ControlPageEventType.SETTINGS_UPDATED));
    }

    @ComponentFactory
    private Component newCamParamsColumn(String id) {
        return new ColumnWrapper(id)
                .add(newCardBorder("camParams", "Camera Parameters")
                        .add(new CamParamsPanel("camParamsPanel", getModel().map(JobSettings::getCamera),
                                getModel().map(JobSettings::getMachine).map(MachineSettings::getCameraType))))
                .add(new OnPageEventRefreshBehavior(ControlPageEventType.CAMERA_TYPE_CHANGED, ControlPageEventType.SETTINGS_UPDATED));
    }

    @ComponentFactory
    private Component newCurrentJob(String id) {
        return newCardBorder(id, "Current Job")
                .add(new CurrentJobPanel("currentJobPanel", getModel()))
                .add(new OnPageEventRefreshBehavior(ControlPageEventType.SETTINGS_UPDATED));
    }

    private class ColumnWrapper extends WebMarkupContainer {

        @Serial
        private static final long serialVersionUID = 1L;

        private ColumnWrapper(String id) {
            super(id);
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            tag.append("class", getModelObject().getMachine().getCameraType() == CameraType.RPI ? "col-lg-3 col-6" : "col-6", " ");
        }

    }

}
