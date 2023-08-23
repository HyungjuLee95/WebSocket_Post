package test.com.webSocket.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import test.com.webSocket.Model.ChatDAO;
import test.com.webSocket.Model.ChatRoom;
import test.com.webSocket.Model.ChatRoomVO;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
	
	@Autowired
	ChatDAO dao;
	@Autowired
	HttpSession session;
	
	private final ObjectMapper objectMapper;
	private Map<String, ChatRoom> chatRooms;


	
	@PostConstruct
	private void init() {
		chatRooms = new LinkedHashMap<String, ChatRoom>();
	}

	public List<ChatRoom> findAllRoom() {
		System.out.println("chatRooms.values()"+chatRooms.values());
		return new ArrayList<ChatRoom>(chatRooms.values());
	}
	
	public List<ChatRoomVO> selectAllRoom(String id){
		return dao.selectAll(id);
	}

	public ChatRoom findRoomById(String roomId) {
		return chatRooms.get(roomId);
	}

	public ChatRoom createRoom(String name) {
		String randomId = UUID.randomUUID().toString();
		ChatRoom chatRoom = ChatRoom.builder().roomId(randomId).name(name).build();
		chatRooms.put(randomId, chatRoom);
		System.out.println("chatromms" + chatRooms.toString());
		System.out.println("name" + chatRoom.getName());
		System.out.println("uuid" + chatRoom.getRoomId());
		
		
		ChatRoomVO vo= new ChatRoomVO();
		vo.setRoomid(randomId);
		vo.setGroupname(name);
		vo.setGroupmaster((String) session.getAttribute("user_id"));
		vo.setGroupmember((String) session.getAttribute("user_id"));
		dao.insert(vo);
		return chatRoom;
	}

	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
