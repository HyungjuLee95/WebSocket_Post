package test.com.webSocket.Model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import test.com.webSocket.Service.ChatService;

@Slf4j
@Getter
public class ChatRoom {

	private String roomId;
	private String name;
	private Set<WebSocketSession> sessions = new HashSet<WebSocketSession>();
	
	
	@Builder
	public ChatRoom(String roomId, String name) {
		this.name = name;
		this.roomId = roomId;
	} //@Builder 에너테이션을 통해 생성자를 초디화시킴
	
	public void HandlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
		if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
			sessions.add(session);
			chatMessage.setMessage(chatMessage.getSender()+"님이 입장했습니다."
					+ "");
		}
		sendMessage(chatMessage, chatService);
	}

	private <T> void sendMessage(T message, ChatService chatService) {
		log.info("message");
		sessions.parallelStream().forEach(session->chatService.sendMessage(session, message));
		
	}
}
