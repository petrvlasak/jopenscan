package net.petrvlasak.jopenscan.config;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;
import de.agilecoders.wicket.core.Bootstrap;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.protocol.http.WebApplication;

@ApplicationInitExtension
public class BootstrapConfig implements WicketApplicationInitConfiguration {

    @Override
    public void init(WebApplication webApplication) {
        Bootstrap.install(webApplication);
        webApplication.getCspSettings().blocking()
                .add(CSPDirective.IMG_SRC, "data:");
    }

}
