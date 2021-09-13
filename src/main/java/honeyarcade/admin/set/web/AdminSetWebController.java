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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@GetMapping("/web/list")
	public String webList(Model model, HttpServletRequest request) throws Exception{
		
		AdminSetWebVO honeyArcadeVO = adminSetWebService.getHoneyArcadeImg();
		AdminSetWebVO benefitVO = adminSetWebService.getBenefitImg();
		model.addAttribute("honeyArcadeVO", honeyArcadeVO);
		model.addAttribute("benefitVO", benefitVO);
		
		return "admin/set/web/adminSetWebList";
	}
	
	@ResponseBody
	@RequestMapping(value="/web/saveImg", method=RequestMethod.POST)
	public ResponseEntity saveImg(@ModelAttribute AdminSetWebVO adminSetWebVO, HttpServletRequest req) throws Exception {
		Map resultMap = new HashMap();
		
		boolean result = true;							//	통신 플러그
		boolean upload_result = true;
		
		String uri = req.getRequestURI();				//	/admin/set/web/list
		String url = req.getRequestURL().toString();	//	http://localhost:10001/admin/set/web/list
		url = url.replaceAll(uri, "");					//	http://localhost:10001
		
		Map map = null;
		
		try {
			
			adminSetWebVO.setPre_url(url);
			adminSetWebService.saveImg(adminSetWebVO);
			
		} catch (Exception e) {
			
			log.debug(e.toString());
			log.debug(adminSetWebVO.toString());
			
			
			upload_result = false;
			
			
			resultMap.put("errorMsg", e.toString());
			
		} finally {
			resultMap.put("result", result);
			resultMap.put("upload_result", upload_result);
			
		} 
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
	
	

}
