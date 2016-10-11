package wang.xin.robocode.server.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.session.ExpiringSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import wang.xin.robocode.server.data.repository.ActiveWebSocketUserRepository;
import wang.xin.robocode.server.ws.ConnectHandler;
import wang.xin.robocode.server.ws.DisconnectHandler;

/**
 * Created by Xin on 2016/10/6.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/");
        config.setApplicationDestinationPrefixes("/");
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.simpMessageDestMatchers("/**").denyAll()
                .simpSubscribeDestMatchers("/**").permitAll()
                .anyMessage().authenticated();
    }

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Bean
    @Autowired
    public <S extends ExpiringSession> ConnectHandler<S> webSocketConnectHandler(
            SimpMessageSendingOperations messagingTemplate,
            ActiveWebSocketUserRepository repository) {
        return new ConnectHandler<S>(messagingTemplate, repository);
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Bean
    @Autowired
    public <S extends ExpiringSession> DisconnectHandler<S> webSocketDisconnectHandler(
            SimpMessageSendingOperations messagingTemplate,
            ActiveWebSocketUserRepository repository) {
        return new DisconnectHandler<S>(messagingTemplate, repository);
    }
}
