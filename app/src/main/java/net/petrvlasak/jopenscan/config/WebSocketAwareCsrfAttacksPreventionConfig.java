package net.petrvlasak.jopenscan.config;

import com.giffing.wicket.spring.boot.starter.configuration.extensions.core.csrf.CsrfAttacksPreventionConfig;
import com.giffing.wicket.spring.boot.starter.configuration.extensions.core.csrf.CsrfAttacksPreventionProperties;
import org.apache.wicket.protocol.http.FetchMetadataResourceIsolationPolicy;
import org.apache.wicket.protocol.http.OriginResourceIsolationPolicy;
import org.apache.wicket.protocol.http.ResourceIsolationRequestCycleListener;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.ws.WebSocketAwareResourceIsolationRequestCycleListener;
import org.apache.wicket.protocol.ws.api.WebSocketMessageBroadcastHandler;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;
import com.giffing.wicket.spring.boot.context.extensions.boot.actuator.WicketAutoConfig;
import com.giffing.wicket.spring.boot.context.extensions.boot.actuator.WicketEndpointRepository;

/**
 * The copy/paste of the {@link CsrfAttacksPreventionConfig}. There is only one change here.
 * The {@link ResourceIsolationRequestCycleListener#isChecked} method has been overwritten to support WebSockets.
 * The {@link WebSocketAwareResourceIsolationRequestCycleListener} cannot be used because it only has the constructor
 * without parameters.
 *
 * @author Petr Vlasak
 */
@ApplicationInitExtension
@EnableConfigurationProperties({ CsrfAttacksPreventionProperties.class })
public class WebSocketAwareCsrfAttacksPreventionConfig implements WicketApplicationInitConfiguration {

    @Autowired
    private CsrfAttacksPreventionProperties props;

    @Autowired
    private WicketEndpointRepository wicketEndpointRepository;

    @Override
    public void init(WebApplication webApplication) {
        OriginResourceIsolationPolicy originResourceIsolationPolicy = new OriginResourceIsolationPolicy();
        props.getAcceptedOrigins().forEach(originResourceIsolationPolicy::addAcceptedOrigin);

        ResourceIsolationRequestCycleListener listener = new ResourceIsolationRequestCycleListener(
                new FetchMetadataResourceIsolationPolicy(),
                originResourceIsolationPolicy) {
            @Override
            protected boolean isChecked(IRequestHandler handler) {
                if (handler instanceof WebSocketRequestHandler || handler instanceof WebSocketMessageBroadcastHandler) {
                    return false;
                }
                return super.isChecked(handler);
            }
        };
        listener.setUnknownOutcomeAction(props.gtUnknownOutcomeAction());
        listener.setDisallowedOutcomeAction(props.getDisallowedOutcomeAction());
        listener.setErrorCode(props.getErrorCode());
        listener.setErrorMessage(props.getErrorMessage());
        webApplication.getRequestCycleListeners().add(listener);

        wicketEndpointRepository.add(new WicketAutoConfig.Builder(this.getClass())
                .withDetail("properties", props)
                .build());
    }

}
