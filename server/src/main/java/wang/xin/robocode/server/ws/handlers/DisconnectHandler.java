package wang.xin.robocode.server.ws.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import wang.xin.robocode.server.data.models.ActiveWebSocketUser;
import wang.xin.robocode.server.data.repositories.ActiveWebSocketUserRepository;

import java.util.Arrays;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
@Component
public class DisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {

    private SimpMessageSendingOperations messagingTemplate;
    private ActiveWebSocketUserRepository repository;

    @Autowired
    public DisconnectHandler(SimpMessageSendingOperations messagingTemplate,
                             ActiveWebSocketUserRepository repository) {
        this.messagingTemplate = messagingTemplate;
        this.repository = repository;
    }

    public void onApplicationEvent(SessionDisconnectEvent event) {
        String id = event.getSessionId();
        if (id == null) {
            return;
        }

        ActiveWebSocketUser user = this.repository.findOne(id);
        if (user == null) {
            return;
        }

        this.repository.delete(id);
        this.messagingTemplate.convertAndSend("/topic/friends/signout", Arrays.asList(user.getUsername()));
    }
}
