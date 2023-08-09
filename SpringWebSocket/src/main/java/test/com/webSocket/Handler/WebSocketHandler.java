package test.com.webSocket.Handler;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import test.com.webSocket.Model.ChatMessage;
import test.com.webSocket.Model.ChatRoom;
import test.com.webSocket.Service.ChatService;

@Slf4j
@RequiredArgsConstructor
@Component
@Lazy
public class WebSocketHandler extends TextWebSocketHandler {
	private final ObjectMapper objectMapper;
//	Object 맵퍼는 정보를 받아  joson 데이터로 반환
	private final ChatService chatService;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("{}", payload);
		ChatMessage chatmessage = objectMapper.readValue(payload,ChatMessage.class);
		
		
		 ChatRoom chatRoom = chatService.findRoomById(chatmessage.getRoomId());
	        chatRoom.HandlerActions(session, chatmessage, chatService);
	}
	
// 설명을 간단하게 한다면  메시지를 json의 형태로 웹소켓을 통해서 서버로 보내면, Handler는 이를 받아 ObjectMapper를 통해서 json 데이터를 chatMessage.clss에 맞게 파싱
// ChatMessgae 객체로 변환을 시킴 / 이 json 데이터에 들어있는 roomId를 이용해서 해당 채팅방을 찾아 HandlerAction이라는 메서드를 실행시킬것임.
}
