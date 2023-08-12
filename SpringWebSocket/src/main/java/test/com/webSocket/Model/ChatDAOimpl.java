package test.com.webSocket.Model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;


@Repository
@Slf4j
public class ChatDAOimpl implements ChatDAO {
	@Autowired
	SqlSession sqlsession;
	
	@Override
	public int insert(ChatRoomVO vo) {
		return sqlsession.insert("ROOM_INSERT", vo);
	}

	@Override
	public List<ChatRoomVO> selectAll(String id) {
		// TODO Auto-generated method stub
		return sqlsession.selectList("SELECT_ALL", id);
	}

}
