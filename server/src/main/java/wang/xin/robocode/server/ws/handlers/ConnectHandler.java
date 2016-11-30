package wang.xin.robocode.server.ws.handlers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import wang.xin.robocode.server.data.models.ActiveWebSocketUser;
import wang.xin.robocode.server.data.repositories.ActiveWebSocketUserRepository;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
@Component
public class ConnectHandler implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ActiveWebSocketUserRepository repository;

    public void onApplicationEvent(SessionConnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        Principal ssUser = SimpMessageHeaderAccessor.getUser(headers);
        if (ssUser == null) {
            return;
        }

        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        ActiveWebSocketUser wsUser = new ActiveWebSocketUser();
        wsUser.setSessionId(sessionId);
        wsUser.setConnectTime(LocalDateTime.now());
        wsUser.setUser(null);
        this.repository.save(wsUser);
        this.messagingTemplate.convertAndSend("/topic/friends/signin", Lists.newArrayList(wsUser));
    }
}
