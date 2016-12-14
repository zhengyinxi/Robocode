package wang.xin.robocode.server.ws.handlers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import wang.xin.robocode.server.data.models.ActiveWebSocketUser;
import wang.xin.robocode.server.data.repositories.ActiveWebSocketUserRepository;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class DisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ActiveWebSocketUserRepository activeWebSocketUserRepository;

    public void onApplicationEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        if (sessionId == null) {
            return;
        }

        ActiveWebSocketUser activeUser = this.activeWebSocketUserRepository.findOne(sessionId);
        if (activeUser == null) {
            return;
        }

        this.activeWebSocketUserRepository.delete(sessionId);
        this.messagingTemplate.convertAndSend("/su/disconnected", Lists.newArrayList(activeUser));
    }
}
