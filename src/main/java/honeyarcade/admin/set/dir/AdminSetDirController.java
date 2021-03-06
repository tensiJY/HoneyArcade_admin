package honeyarcade.admin.set.dir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/set")
public class AdminSetDirController {
	
	@Autowired
	private AdminSetDirService adminSetDirService;
	
	/**
	 * 관리자 목록
	 * @param nowPage
	 * @param listCount
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dir/list")
	public String dirList (
			@RequestParam(defaultValue = "1") int nowPage
			, @RequestParam(defaultValue = "25") int listCount
			, Model model) throws Exception{
		Integer totalCount = adminSetDirService.getTotalCount();
		
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		
		AdminSetDirVO paramVO = new AdminSetDirVO();
		paramVO.setStart_num(pageUtil.getStartNum());
		paramVO.setEnd_num(pageUtil.getEndNum());
		
		List<AdminSetDirVO> adminList = adminSetDirService.getAdminList(paramVO);
		List<AdminSetDirVO> dongList  = adminSetDirService.getDongList(1);
		
		model.addAttribute("dongList", dongList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("paramVO", paramVO);
		model.addAttribute("adminList", adminList);
		return "admin/set/dir/adminSetDirList";
	}
	
	/**
	 * 관리자 아이디 삭제	
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/dir/delProc", method=RequestMethod.POST)
	public ResponseEntity delProc(@RequestBody HashMap paramMap) throws Exception{
		
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = null;
		
		try {
			String admin_id = String.valueOf(paramMap.get("admin_id"));
			
			AdminSetDirVO paramVO = new AdminSetDirVO();
			paramVO.setAdmin_id(admin_id);
			paramVO.setMod_id(SessionUtil.getAdminId());
			
			adminSetDirService.deleteAdminId(paramVO);
			adminSetDirService.deleteAdminGrade(paramVO);
			msg = "삭제에 성공하였습니다";
			
		}catch(Exception e) {
			log.debug("관리자 아이디 삭제 에러");
			log.debug(e.getMessage());
			log.debug("param : " + paramMap.toString());
			msg = "데이터 처리 중 에러가 발생하였습니다. 관리자에게 문의 바랍니다";
			result = false;
			
		}finally {
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 건물 목록 가져오기 가나다 순
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/dir/getBuild", method=RequestMethod.POST)
	public ResponseEntity getBuild(@RequestBody HashMap paramMap) throws Exception{
		
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = null;
		
		List<AdminSetDirVO> buildList = new ArrayList<AdminSetDirVO>();
		
		try {
			Integer dong_seq = Integer.parseInt(String.valueOf(paramMap.get("dong_seq")));
			buildList = adminSetDirService.getBuildList(dong_seq);
		}catch(Exception e) {
			log.debug(e.getMessage());
			log.debug("param : " + paramMap.toString());
			msg = "데이터 처리 중 에러가 발생하였습니다. 관리자에게 문의 바랍니다";
			result = false;
		}finally {
			resultMap.put("buildList", buildList);
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 관리자 추가 저장 프로세스
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/dir/saveProc", method=RequestMethod.POST)
	public ResponseEntity saveProc(@RequestBody HashMap paramMap) throws Exception{
		
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = null;
		log.debug(paramMap.toString());
		
		try {
			
			adminSetDirService.saveProc(paramMap);
			msg = "저장에 성공하였습니다";
		}catch(Exception e) {
			
			log.debug(e.getMessage());
			log.debug("param : " + paramMap.toString());
			msg = e.getMessage();
			result = false;
			
		}finally {
		
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/dir/modProc", method=RequestMethod.POST)
	public ResponseEntity modProc(@RequestBody HashMap paramMap) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = null;
		log.debug(paramMap.toString());
		try {
			adminSetDirService.modProc(paramMap);
			msg = "수정에 성공하였습니다";
		}catch(Exception e) {
			log.debug(e.getMessage());
			log.debug("param : " + paramMap.toString());
			msg = e.getMessage();
			result = false;
		}finally {
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
