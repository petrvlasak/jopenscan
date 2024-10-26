package net.petrvlasak.jopenscan.ui.page;

import com.googlecode.wicketforge.annotations.ComponentFactory;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarDropDownButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.utilities.BackgroundColorBehavior;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.validation.SimpleMessageValidation;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesome6CssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesome6IconType;
import net.petrvlasak.jopenscan.config.AppConfig;
import net.petrvlasak.jopenscan.ui.component.CustomCssReference;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

abstract class BasePage<T> extends GenericWebPage<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String FIRST_TITLE_PART = "JOpenScan";

    private final StringBuilder title = new StringBuilder(FIRST_TITLE_PART);

    @SpringBean
    private AppConfig appConfig;

    public BasePage() {
        super();
    }

    public BasePage(IModel<T> model) {
        super(model);
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("title", title));
        add(newNavbar("navbar"));
        add(newAppVersion("appVersion"));
        add(new SimpleMessageValidation());
    }

    @ComponentFactory
    protected Navbar newNavbar(String id) {
        Navbar navbar = new Navbar(id)
                .fluid(false)
                .setPosition(Navbar.Position.STICKY_TOP)
                .setCollapseBreakdown(Navbar.CollapseBreakpoint.Small)
                .setBackgroundColor(BackgroundColorBehavior.Color.Dark)
                .setInverted(true)
                .setBrandName(Model.of(FIRST_TITLE_PART));

        navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT,
                new NavbarButton<Void>(ControlPage.class, Model.of("Control")).setIconType(FontAwesome6IconType.sliders_s),
                new NavbarButton<Void>(ResultsPage.class, Model.of("Results")).setIconType(FontAwesome6IconType.images_r)
        ));

        navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.RIGHT,
                new NavbarDropDownButton(Model.of("System")) {
                    @Serial
                    private static final long serialVersionUID = 1L;

                    @Override
                    public boolean isActive(Component item) {
                        return false;
                    }

                    @Override
                    protected List<AbstractLink> newSubMenuButtons(String buttonMarkupId) {
                        List<AbstractLink> subMenu = new ArrayList<>();
                        subMenu.add(new BootstrapAjaxLink<Void>(buttonMarkupId, null, Buttons.Type.Menu, Model.of("Shutdown")) {
                            @Serial
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void onClick(AjaxRequestTarget target) {
                                //TODO
                            }
                        });
                        subMenu.add(new BootstrapAjaxLink<Void>(buttonMarkupId, null, Buttons.Type.Menu, Model.of("Reboot")) {
                            @Serial
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void onClick(AjaxRequestTarget target) {
                                //TODO
                            }
                        });
                        return subMenu;
                    }
                }.setIconType(FontAwesome6IconType.power_off_s),
                new NavbarDropDownButton(Model.of("[username]")) {
                    @Serial
                    private static final long serialVersionUID = 1L;

                    @Override
                    public boolean isActive(Component item) {
                        return false;
                    }

                    @Override
                    protected List<AbstractLink> newSubMenuButtons(String buttonMarkupId) {
                        List<AbstractLink> subMenu = new ArrayList<>();
                        subMenu.add(new BootstrapAjaxLink<Void>(buttonMarkupId, null, Buttons.Type.Menu, Model.of("Logout")) {
                            @Serial
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void onClick(AjaxRequestTarget target) {
                                //TODO
                            }
                        });
                        return subMenu;
                    }
                }.setIconType(FontAwesome6IconType.user_s)
        ));

        return navbar;
    }

    @ComponentFactory
    private Label newAppVersion(String id) {
        return new Label(id, LambdaModel.of(() -> appConfig.getVersion()));
    }

    protected void addTitlePart(String titlePart) {
        title.append(" - ").append(titlePart);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(FontAwesome6CssReference.instance()));
        response.render(CssHeaderItem.forReference(CustomCssReference.instance()));
    }

}
