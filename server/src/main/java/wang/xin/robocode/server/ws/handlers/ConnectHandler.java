package wang.xin.robocode.server.ws.handlers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import wang.xin.robocode.server.data.models.ActiveWebSocketUser;
import wang.xin.robocode.server.data.models.User;
import wang.xin.robocode.server.data.repositories.ActiveWebSocketUserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

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

        if (!OAuth2Authentication.class.isInstance(principal)) {
            return;
        }

        OAuth2Authentication authentication = OAuth2Authentication.class.cast(principal);
        Authentication token = authentication.getUserAuthentication();
        Map<String, Object> map = Map.class.cast(token.getDetails());
        Integer userId = Integer.class.cast(map.get("_user_id"));
        if (userId == null) {
            return;
        }

        ActiveWebSocketUser activeUser = new ActiveWebSocketUser();
        activeUser.setSessionId(sessionId);
        activeUser.setConnectTime(LocalDateTime.now());
        User user = new User();
        user.setId(userId);
        activeUser.setUser(user);
        this.activeWebSocketUserRepository.save(activeUser);
        this.messagingTemplate.convertAndSend("/topic/friends/signin", Lists.newArrayList(activeUser));
    }
}
