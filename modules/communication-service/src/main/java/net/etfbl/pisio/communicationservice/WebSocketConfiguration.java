package net.etfbl.pisio.communicationservice;


import lombok.AllArgsConstructor;
import net.etfbl.pisio.authfilter.TokenValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private static final String WEBSOCKET_TICKET_HEADER = "sec-websocket-protocol";

    private final TokenValidationService tokenValidationService;
    private final UsernameSessionService usernameSessionService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(new JobSocketHandler(usernameSessionService), "/ws")
                .addInterceptors(handshakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new HandshakeInterceptor() {

            @Override
            public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws IOException {
                List<String> tickets = serverHttpRequest.getHeaders().getOrEmpty(WEBSOCKET_TICKET_HEADER);
                if (tickets.isEmpty()) {
                    return false;
                }

                String username = tokenValidationService.validateToken(tickets.get(0));

                if (username == null) {
                    return false;
                }

                map.put("username", username);
                serverHttpResponse.getHeaders().add(WEBSOCKET_TICKET_HEADER, tickets.get(0));
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
            }
        };
    }
}