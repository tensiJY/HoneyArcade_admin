package honeyarcade.admin.set.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import honeyarcade.admin.set.ntc.AdminSetNtcVO;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 설정 - 점주 웹 이미지
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/admin/set")
@Slf4j
public class AdminSetWebController {
	
	@Autowired
	private AdminSetWebService adminSetWebService;
	
	/**
	 * 설정 - 점주 웹 이미지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/web/list")
	public String webList(Model model) throws Exception{
		AdminSetWebVO honeyArcadeVO = adminSetWebService.getHoneyArcadeImg();
		AdminSetWebVO benefitVO = adminSetWebService.getBenefitImg();
		model.addAttribute("honeyArcadeVO", honeyArcadeVO);
		model.addAttribute("benefitVO", benefitVO);
		return "admin/set/web/adminSetWebList";
	}
	
	/**
	 * 점주 웹 이미지 저장 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/web/writeProc")
	public ResponseEntity writeProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		
		try {
			adminSetWebService.writeProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("web writeProc" + e.toString());
			log.error(dataMap.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

}
