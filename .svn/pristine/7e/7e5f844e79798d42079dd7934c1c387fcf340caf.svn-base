package honeyarcade.admin.im.data;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/im")
public class AdminImDataController {
	
	@GetMapping("/data/list")
	public String dataList() throws Exception{
		log.info("call data list");
		
		return "admin/im/data/adminImDataList";
	}

}
