package wang.xin.robocode.server.ws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.xin.robocode.server.data.repositories.ActiveWebSocketUserRepository;
import wang.xin.robocode.server.ws.models.InstantMessage;

import java.util.List;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
@Controller
@RequestMapping("/messages")
public class MessageController {

    private SimpMessageSendingOperations messagingTemplate;
    private ActiveWebSocketUserRepository activeUserRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public MessageController(ActiveWebSocketUserRepository activeUserRepository,
                             SimpMessageSendingOperations messagingTemplate) {
        this.activeUserRepository = activeUserRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping("/")
    public String im() {
        return "index";
    }

    @MessageMapping("/im")
    @PreAuthorize("isAuthenticated()")
    public void im(InstantMessage im, Authentication authentication) {
        String name = authentication.getName();
        im.setFrom(name);
        this.messagingTemplate.convertAndSendToUser(im.getTo(), "/queue/messages", im);
        this.messagingTemplate.convertAndSendToUser(im.getFrom(), "/queue/messages", im);
    }

    @SubscribeMapping("/users")
    public List<String> subscribeMessages() throws Exception {
        return this.activeUserRepository.findAllActiveUsers();
    }
}
