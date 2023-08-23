package test.com.webSocket.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import test.com.webSocket.Model.ChatRoom;
import test.com.webSocket.Service.ChatService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
	  private final ChatService chatService;

	    @PostMapping
	    public ChatRoom createRoom(@RequestBody String name) {
	    	log.info("createRoom",chatService.createRoom(name) );
	        return chatService.createRoom(name);
	    }

	    @GetMapping
	    public List<ChatRoom> findAllRoom() {
	        return chatService.findAllRoom();
	    }

}
