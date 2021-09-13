package honeyarcade.admin.im.push;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/im")
@Slf4j
public class AdminImPushController {

	@GetMapping("/push/list")
	public String pushList()throws Exception{
		log.info("call push list");
		
		return "admin/im/push/adminImPushList";
	}
}
