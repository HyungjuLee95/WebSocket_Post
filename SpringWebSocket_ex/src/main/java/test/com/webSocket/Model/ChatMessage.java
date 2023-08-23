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