package net.etfbl.pisio.communicationservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@AllArgsConstructor
public class JobSocketHandler extends TextWebSocketHandler {

    private final UsernameSessionService usernameSessionService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String username = getSessionUser(session);
        usernameSessionService.addUserSession(username, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
       String username = getSessionUser(session);
       usernameSessionService.deleteUserSession(username);
    }

    private String getSessionUser(WebSocketSession session) {
        return session.getAttributes().get("username").toString();
    }
}
