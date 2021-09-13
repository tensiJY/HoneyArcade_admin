package honeyarcade.admin.member.event;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/member")
@Slf4j
public class AdminMemberEventController {
	
	@GetMapping("/event/list")
	public String eventList()throws Exception{
		log.info("call event list");
		
		return "admin/member/event/adminMemberEventList";
	}

}
