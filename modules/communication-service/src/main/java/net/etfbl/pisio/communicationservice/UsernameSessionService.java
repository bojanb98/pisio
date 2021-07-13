package net.etfbl.pisio.communicationservice;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsernameSessionService {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addUserSession(String username, WebSocketSession socketSession) {
        sessions.put(username, socketSession);
    }

    public void deleteUserSession(String username) {
        sessions.remove(username);
    }

    public WebSocketSession getUserSession(String username) {
        return sessions.getOrDefault(username, null);
    }
}
