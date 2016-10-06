package wang.xin.robocode.server.ws.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Xin on 2016/10/6.
 */
@Controller
@MessageMapping("/app")
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingMessage greeting(HelloMessage message) throws Exception {
        return new GreetingMessage("Hello, " + message.getName() + "!");
    }

    static class HelloMessage {

        private String name;

        public HelloMessage() {
        }

        public HelloMessage(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class GreetingMessage {

        private String content;

        public GreetingMessage() {
        }

        public GreetingMessage(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
