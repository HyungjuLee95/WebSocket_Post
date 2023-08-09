package test.com.webSocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


import lombok.RequiredArgsConstructor;
import test.com.webSocket.Handler.WebSocketHandler;


@RequiredArgsConstructor
@Configuration
@EnableWebSocket
@Lazy
public class WebSocketConfig implements WebSocketConfigurer{
	private final WebSocketHandler webSocketHandler;

		@Override
		 public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	        registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
	    }
		
		
		
}
