package honeyarcade.admin.ad.req;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.ad.area.AdminAdAreaVO;
import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/ad")
@Slf4j
public class AdminAdReqController {
	@Autowired
	private CommonApiService commonApiService;
	
	@GetMapping("/req/list")
	public String reqList(Model model, @ModelAttribute AdminAdAreaVO adminAdAreaVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "25")int listCount
			, @RequestParam(value="req_type", defaultValue = "1") int area_type) throws Exception{
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		
		
		
		return "admin/ad/req/adminAdReqList";
	}

}
