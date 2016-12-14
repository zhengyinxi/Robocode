package wang.xin.robocode.server.ws.handlers;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import wang.xin.robocode.server.data.models.ActiveWebSocketUser;
import wang.xin.robocode.server.data.models.OAuthSource;
import wang.xin.robocode.server.data.models.User;
import wang.xin.robocode.server.data.repositories.ActiveWebSocketUserRepository;
import wang.xin.robocode.server.data.repositories.OAuthUserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class ConnectHandler implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ActiveWebSocketUserRepository activeWebSocketUserRepository;

    public void onApplicationEvent(SessionConnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        Principal principal = SimpMessageHeaderAccessor.getUser(headers);
        if (principal == null) {
            return;
        }

        Assert.state(OAuth2Authentication.class.isInstance(principal));
        OAuth2Authentication authentication = OAuth2Authentication.class.cast(principal);
        Authentication token = authentication.getUserAuthentication();
        boolean isSu = this.hasRole(token.getAuthorities(), "ROLE_SU");
        if (!isSu) {
            return;
        }

        Triple<OAuthSource, String, Integer> sourceAndId = Triple.class.cast(token.getPrincipal());
        Assert.state(sourceAndId.getRight() != null);

        ActiveWebSocketUser activeUser = new ActiveWebSocketUser();
        activeUser.setSessionId(sessionId);
        activeUser.setConnectTime(LocalDateTime.now());
        User user = new User();
        user.setId(sourceAndId.getRight());
        activeUser.setUser(user);
        this.activeWebSocketUserRepository.save(activeUser);
        this.messagingTemplate.convertAndSend("/su/connected", Lists.newArrayList(activeUser));
    }

    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        Assert.notNull(authorities);
        Assert.hasLength(role);
        return authorities.stream().anyMatch(a -> role.equals(a.getAuthority()));
    }
}
