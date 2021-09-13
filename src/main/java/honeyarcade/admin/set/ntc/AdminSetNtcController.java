package honeyarcade.admin.set.ntc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * 설정 - 공지사항
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/admin/set")
@Slf4j
public class AdminSetNtcController {
	
	@GetMapping("/ntc/list")
	public String ntcList() throws Exception{
		log.info("call ntc list");

		
		return "admin/set/ntc/adminSetNtcList";
	}

	
	@GetMapping("/ntc/view")
	public String ntcView() throws Exception{
		
		return "admin/set/ntc/adminSetNtcView";
	}
}
