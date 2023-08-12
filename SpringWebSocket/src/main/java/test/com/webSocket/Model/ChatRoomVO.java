package test.com.webSocket.Model;

import lombok.Data;

@Data
public class ChatRoomVO {
	private String roomid;
	private String groupmaster;
	private String groupmember;
	private String groupname;
	private String user_id;
}
