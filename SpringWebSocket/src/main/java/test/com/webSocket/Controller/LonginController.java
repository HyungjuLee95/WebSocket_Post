package test.com.webSocket.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import test.com.webSocket.Model.ChatRoomVO;
import test.com.webSocket.Service.ChatService;

@Controller
public class LonginController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ChatService service;
	
	@RequestMapping(value = "/login.do" , method = RequestMethod.GET)
	public String Login() {
	return "login";
	}
	
	
	@RequestMapping(value = "/loginCheck.do" , method = RequestMethod.GET)
	public String loginCheck(String id, Model model) {
		session.setAttribute("user_id", id);
		
		List<ChatRoomVO> vo2 = service.selectAllRoom(id);
		System.out.println("vo2="+vo2);
		System.out.println("id="+id);
		model.addAttribute("vo2", vo2);
	return "chatmain";
	}
}
