package wang.xin.robocode.server.ws;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import wang.xin.robocode.server.data.model.ActiveWebSocketUser;
import wang.xin.robocode.server.data.repository.ActiveWebSocketUserRepository;

import java.util.Arrays;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
public class DisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {
    private ActiveWebSocketUserRepository repository;
    private SimpMessageSendingOperations messagingTemplate;

    public DisconnectHandler(SimpMessageSendingOperations messagingTemplate,
                             ActiveWebSocketUserRepository repository) {
        super();
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
        this.messagingTemplate.convertAndSend("/topic/friends/signout",
                Arrays.asList(user.getUsername()));

    }
}
