package honeyarcade.admin.member.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/member")
@Slf4j
public class AdminMemberUserController {
	
	@GetMapping("/user/list")
	public String userList() {
		log.info("call user list");
		
		return "admin/member/user/adminMemberUserList";
	}

}
