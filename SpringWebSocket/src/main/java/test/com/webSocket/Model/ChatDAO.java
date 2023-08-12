package test.com.webSocket.Model;

import java.util.List;

public interface ChatDAO {
	public int insert(ChatRoomVO vo);
	public List<ChatRoomVO> selectAll(String id);

}
