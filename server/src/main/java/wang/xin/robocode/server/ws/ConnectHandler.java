package wang.xin.robocode.server.ws;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import wang.xin.robocode.server.data.model.ActiveWebSocketUser;
import wang.xin.robocode.server.data.repository.ActiveWebSocketUserRepository;

import java.security.Principal;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
public class ConnectHandler<S> implements ApplicationListener<SessionConnectEvent> {
    private ActiveWebSocketUserRepository repository;
    private SimpMessageSendingOperations messagingTemplate;

    public ConnectHandler(SimpMessageSendingOperations messagingTemplate,
                                   ActiveWebSocketUserRepository repository) {
        super();
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
        this.repository.save(
                new ActiveWebSocketUser(id, user.getName(), Calendar.getInstance()));
        this.messagingTemplate.convertAndSend("/topic/friends/signin",
                Arrays.asList(user.getName()));
    }
}
