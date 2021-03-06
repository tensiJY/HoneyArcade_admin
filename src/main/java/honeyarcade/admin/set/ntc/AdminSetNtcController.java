package honeyarcade.admin.set.ntc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.ad.area.AdminAdAreaVO;
import honeyarcade.admin.util.SessionUtil;
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
	
	@Autowired
	private AdminSetNtcService adminSetNtcService;
	
	/**
	 * 공지사항 목록
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/ntc/list")
	public String ntcList(Model model) throws Exception{
		//	App 내 공지사항
		List<AdminSetNtcVO> appList = adminSetNtcService.getAppList();
		
		//	사장님 페이지 공지사항
		List<AdminSetNtcVO> ntcList = adminSetNtcService.getNtcList();
		
		//	사장님 페이지 점포 회원 전용 공지사항
		List<AdminSetNtcVO> ownerNtcList = adminSetNtcService.getOwnerNtcList();
		
		model.addAttribute("appList", appList);
		model.addAttribute("ntcList", ntcList);
		model.addAttribute("ownerNtcList", ownerNtcList);
		return "admin/set/ntc/adminSetNtcList";
	}
	
	/**
	 * 공지사항 삭제 프로세스
	 * @param adminSetNtcVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ntc/delProc")
	public ResponseEntity ntcDelProc(@RequestBody AdminSetNtcVO adminSetNtcVO) throws Exception{

		log.info("공지사항 - 삭제 데이터 : " + adminSetNtcVO.toString());
		log.info("공지사항 - 삭제 관리자 아이디 : " + SessionUtil.getAdminId());
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminSetNtcVO.setMod_id(SessionUtil.getAdminId());
			adminSetNtcService.ntcDelProc(adminSetNtcVO);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 공지사항 작성 폼
	 * @param model
	 * @param adminSetNtcVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ntc/write")
	public String ntcWrite(Model model, @ModelAttribute AdminSetNtcVO adminSetNtcVO) throws Exception{
		model.addAttribute("adminSetNtcVO", adminSetNtcVO);
		return "admin/set/ntc/adminSetNtcWrite";
	}
	
	/**
	 * 공지사항 저장 프로세스
	 * @param adminSetNtcVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ntc/writeProc")
	public ResponseEntity writeProc(@RequestBody AdminSetNtcVO adminSetNtcVO) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminSetNtcVO.setAdd_id(SessionUtil.getAdminId());
			adminSetNtcService.writeProc(adminSetNtcVO);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
			log.error(adminSetNtcVO.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 공지사항 수정- 공지사항 내용 가져오기
	 * @param model
	 * @param adminSetNtcVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ntc/mod")
	public String ntcMod(Model model, @ModelAttribute AdminSetNtcVO adminSetNtcVO) throws Exception{
		model.addAttribute("adminSetNtcVO", adminSetNtcService.getNtc(adminSetNtcVO));
		return "admin/set/ntc/adminSetNtcMod";
	}
	
	/**
	 * 공지사항 수정 프로세스
	 * @param adminSetNtcVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ntc/modProc")
	public ResponseEntity ntcModProc(@RequestBody AdminSetNtcVO adminSetNtcVO) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		
		try {
			adminSetNtcVO.setMod_id(SessionUtil.getAdminId());
			adminSetNtcService.ntcModProc(adminSetNtcVO);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
			log.error(adminSetNtcVO.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
