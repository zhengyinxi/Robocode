package wang.xin.robocode.server.ws.handlers;

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
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
@Component
public class ConnectHandler implements ApplicationListener<SessionConnectEvent> {

    private SimpMessageSendingOperations messagingTemplate;
    private ActiveWebSocketUserRepository repository;

    @Autowired
    public ConnectHandler(SimpMessageSendingOperations messagingTemplate,
                          ActiveWebSocketUserRepository repository) {
        this.messagingTemplate = messagingTemplate;
        this.repository = repository;
    }

    public void onApplicationEvent(SessionConnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        Principal user = SimpMessageHeaderAccessor.getUser(headers);
        if (user == null) {
            return;
        }

        String id = SimpMessageHeaderAccessor.getSessionId(headers);
        this.repository.save(new ActiveWebSocketUser(id, user.getName(), Calendar.getInstance()));
        this.messagingTemplate.convertAndSend("/topic/friends/signin", Arrays.asList(user.getName()));
    }
}
