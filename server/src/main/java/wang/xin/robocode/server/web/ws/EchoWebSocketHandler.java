package wang.xin.robocode.server.web.ws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by Xin on 2016/10/6.
 */
public class EchoWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.debug("Opened new session in instance " + this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        String echoMessage = this.getMessage(message.getPayload());
        logger.debug(echoMessage);
        session.sendMessage(new TextMessage(echoMessage));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }

    private String getMessage(String content) {
        return "echo: " + content;
    }
}
