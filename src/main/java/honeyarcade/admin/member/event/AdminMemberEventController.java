package honeyarcade.admin.member.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *  유료 서비스
 * 
 *
 */
@Controller
@RequestMapping("/admin/member")
@Slf4j
public class AdminMemberEventController {
	
	@Autowired
	private AdminMemberEventService adminMemberEventService;
	
	/**
	 * 유료 서비스 : 목록
	 * @param model
	 * @param nowPage
	 * @param listCount
	 * @param type
	 * @param search_text
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/event/list")
	public String eventList( Model model
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="listCount", defaultValue = "25") int listCount
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="search_text", defaultValue="") String search_text )throws Exception{
		
		AdminMemberEventVO vo = new AdminMemberEventVO();
		vo.setType(type);
		vo.setSearch_text(search_text);
		
		Integer totalCount = adminMemberEventService.getTotalCount(vo);
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		vo.setStart_num(pageUtil.getStartNum());
		vo.setEnd_num(pageUtil.getEndNum());
		
		List<AdminMemberEventVO> eventList = adminMemberEventService.getEventList(vo);
		
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("eventList", eventList);
		model.addAttribute("search_text", search_text);
		model.addAttribute("type", type);
		model.addAttribute("listCount", listCount);
		model.addAttribute("nowPage", nowPage);
		return "admin/member/event/adminMemberEventList";
	}
	
	/**
	 * 유료 서비스 : 프로모션 데이터 가져오기
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/event/getData")
	public ResponseEntity eventGetData(@RequestBody AdminMemberEventVO adminMemberEventVO) throws Exception{
		
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
		
			AdminMemberEventVO data = adminMemberEventService.getData(adminMemberEventVO);
			resultMap.put("data", data);
		}catch(Exception e) {
			
			result = false;
			log.error("evnet getData : " + SessionUtil.getAdminId());
			log.error(adminMemberEventVO.toString());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 유료 서비스 : 프로모션 삭제
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/event/delete")
	public ResponseEntity eventDelete(@RequestBody AdminMemberEventVO adminMemberEventVO) throws Exception{
		String admin_id = SessionUtil.getAdminId();
		boolean result = true;
		
		log.info("프로모션 삭제 - 관리자 아이디 : " + admin_id);
		log.info("프로모션 삭제 - 프로모션 데이터 : " + adminMemberEventVO.toString());
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMemberEventVO.setAdmin_id(admin_id);
			adminMemberEventService.delete(adminMemberEventVO);
			
		}catch(Exception e) {
			
			result = false;
			log.error("evnet delete : " + SessionUtil.getAdminId());
			log.error(adminMemberEventVO.toString());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 프로모션 : 저장 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/event/saveProc")
	public ResponseEntity eventSaveProc(@RequestBody HashMap dataMap) throws Exception{
		
		boolean result = true;
		
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMemberEventService.saveProc(dataMap);
			
		}catch(Exception e) {
			
			result = false;
			log.error("evnet saveProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	

	@RequestMapping("/event/modProc")
	public ResponseEntity eventModProc(@RequestBody HashMap dataMap) throws Exception{
		
		boolean result = true;
		
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMemberEventService.modProc(dataMap);
			
		}catch(Exception e) {
			
			result = false;
			log.error("evnet modProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
}
