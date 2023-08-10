# messaging_with_websocket
messaging을 웹소켓을 이용하여 구현해보자(브라우져 통신 방식: 웹소켓 공부 및 구현)

## 기존 사용 방식

    기존 message 구현은 롱폴링 방식으로 구현한 방식이었다면, 이번에는 WebSocket을 이용하여 실시간 쪽지, 
    흔히 말해 채팅을 구현해 볼 것입니다.
    웹소켓을 공부해보고 생각해본 후 정리해보았습니다.

    
    1) 서버와 클라이언트 간의 양방향 통신이 가능하다.
    -> 즉 하나의 HTTP 접속을 통하여 양방향 메시지를 자유롭게 주고받을 수 있습니다.
    2) 웹소켓 연결은 HTTP 프로토콜을 통해 이루어진다.
    3) stateful protocol이기 때문에 서버와 클라인트 간의 연결을 항상 유지해야 하며, 정상적이지 않게 연결이 끊어진다면 적절한 대응이 필요하다.
    4) 보안이 필요하다.

## 구현하기 전에 생각해볼 내용
---
    1) 어떻게 연결시킬 것인가?
        --> configure를 통해 웹소켓을 활성화 시킨 후, handler를 이용하여 진행

    2) 어떻게 단계를 나눌 것인가
        --> 1. 휘발성으로 방을 만들면 roomId가 나오고, 해당 방에 접속하면 접속자의 정보에서 roomId를 매칭하여 접속할 수 있게함
---
## 겪었던 문제
    1) 코드를 구현한 후, 실행을 했을 때에 config 파일에서 bean을 찾을 수 없다는 오류가 발생하였다.
    bean을 찾을 수 없는 경우, 우선 annotation들이 올바르게 기재가 되어 있는지 확인하였습니다. 모든 클래스에 대한 annotation이 정확한 것을 확인한 후, 가장 근본적이었던 Java 버전과 dependenct의 호환이 되는 버전인지 다시 점검을 하였으며,     버전이 달라 에러가 발생함을 확인하였습니다. 하여 해당 버전을 수정한 후 정상적으로 작동이 되었습니다.
---
## "채팅 기록이 남을 수 있도록 해보자"라는 목표에 대해 구현할 방향 생각
---
	1) 채팅 기록이 남는다 
 		-> 방을 개설할 경우, 우리는 방 접속을 roomId(UUID)를 통해 해당 방에 접속할 수 있도록 한다.
   		-> 전달하는 내용은 "type(최초 접속인지, 이야기를 하는 것인지), roomId, sender(발신자), Message(보내는 내용)"에 해단 json 타입이다.<br>
     	
	2) 그렇다면 아주 간단한 dp 화면을 생각해 보았을 때, a)로그인페이지, b)로그인 확인 페이지, c) 접속할 채팅방 목록, d)해당 방을 선택하면 해당 roomId에 접속 후, 채팅 내용 확인 및 전송 페이지
      	
	3) 지금 만들어진 방은 Post방식으로 header에 name이라는 항목으로 채팅방 이름을 잘성하면 아래 화면과 같이 개성이 된다.
       		-> 그렇다면 roomId와 채팅방 이름을 매칭 시켜줄 db가 필요하지 않을까?
	 	-> 채팅 내용을 유지하는 것에도 db가 따로 필요하지 않을까?<br>
   
   	4) 3)의 방식에 대한 조사 및 나의 생각에 대한 결론을 봤을 때, db가 필요하다면 해당 내용을 저장하는 가장 합리적인 방법은 무엇일까?<br>
    	5) 별개로 json 방식으로 데이터를 주고 받는다면 어떠한 라이브러리 혹은 api를 써야할까? 

	---
 	우선 사용자를 받아줄 페이지를 생성한다 > LoginPage > 단순한 사용자 명 입력과 로그인 버튼만 있으며, 로그인 후에는 ChatMain으로 이동이 되며, json 방식으로 받아왔던 chatList 들이 모두 나타난다.
  	chatList에서 해당 내용을 선택 후, 입장을 누르면 해당 chatroom으로 이동하게 한다. > roomId와 chatroom 이름을 기입해주는 
  
## 실행 화면
우선, CSS를 구성하지 않고, 기존에 설정을 해두었던 /chat 주소로 Post요청을 보내서 방이 정상적으로 개설이 되는지 확인하였습니다.
![image](https://github.com/HyungjuLee95/WebSocket_Post/assets/111270174/2e88fe61-6957-4fa3-9a78-d51fb4e29471)

크롬 확장 프로그램인 "PostMan"을 사용하여 설정했던 http://localhost:8080/webSocket/chat주소로 요청을 보내었습니다. 정상적으로 rommId가 생성되는 것이 확인되었습니다.
---

해당 생성된 방을 통해 json의 형식으로 사용자를 입장시키는 요청을 전송하였고, 이에 따른 입장 메시지입니다.
차례대로 사용자 1이 입장했을 때, 사용자 2가 입장했을 때 입니다.
요청문
```
{
"type":"ENTER",
 "roomId":"814ad32f-8a7e-40da-994d-adbc4720181d",
  "sender":"사용자1",
  "message":"안녕"
}
```
## 사용자 1 입장
![image](https://github.com/HyungjuLee95/WebSocket_Post/assets/111270174/001c84fe-f6f7-426c-9749-884da7711084)


## 사용자 2 입장 
요청문
```
{
"type":"ENTER",
 "roomId":"9c1f6e76-ac86-4445-a47c-25061e27a3ac",
  "sender":"사용자2",
  "message":"안녕"
}
```
## 아래 사진 좌측은 사용자 2의 화면, 우측은 사용자 1의 화면
![image](https://github.com/HyungjuLee95/WebSocket_Post/assets/111270174/f2cecae8-5b9b-491b-9ad9-38cd6b4ab7c0)
정상적으로 입장 메시지가 확인이 되었습니다.

## 아래는 사용자 2의 채팅 전송입니다.
요청문
```
{
"type":"TALK",
 "roomId":"814ad32f-8a7e-40da-994d-adbc4720181d",
  "sender":"사용자2",
  "message":"안녕하세요~"
}
```
![image](https://github.com/HyungjuLee95/WebSocket_Post/assets/111270174/e93e48ee-8ecf-4b4f-992c-eaeb2ca48fb5)
정상적으로 실시간 채팅 전송이 되는 모습을 확인하였습니다.

# 아래는 코드이며, 웹소켓 완성이 아닌, 추가적으로 채팅 내용의 기억과 현재 json형식으로 주고 받는 내용에 대해서 간단한 채팅창에 보일 수 있도록 수정할 것입니다.

---

## configure 구현 
   ### ObjectMapper
    ```
    package test.com.webSocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```

### WebSocketConfig

```
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
```

### Handler
```
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
```

### DTO

```
package test.com.webSocket.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK
    }
//    enum을 통해서 들어오는 것(Enter), 채팅을 보내는 것(Talk)으로 타입을 선언

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
```
