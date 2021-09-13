package honeyarcade.admin.mgt.build;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.set.web.AdminSetWebVO;
import honeyarcade.admin.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 건물관리 - 건물 등록
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/admin/mgt")
@Slf4j
public class AdminMgtBuildController {

	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private AdminMgtBuildService adminMgtBuildService;
	
	@Autowired
	private CommonApiService commonApiService;
	
	//	건물 등록 - 목록
	@GetMapping(value="/build/list")
	public String buildList(Model model, @ModelAttribute AdminMgtBuildVO adminMgtBuildVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "20")int listCount) throws Exception{
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = null;
		List<CommonApiVO> dongList = null;
		
		if(type.equals("search")) {	//	검색 조건 일때
			CommonApiVO commonApiVO = new CommonApiVO();
			
			if(adminMgtBuildVO.getSido_seq() != null) {
				commonApiVO.setSido_seq(adminMgtBuildVO.getSido_seq());
				sigunguList = commonApiService.getSigungu(commonApiVO);
				model.addAttribute("sigunguList", sigunguList);

			}
			
			if(adminMgtBuildVO.getSigungu_seq() != null) {
				commonApiVO.setSigungu_seq(adminMgtBuildVO.getSigungu_seq());
				
				dongList = commonApiService.getDong(commonApiVO);
				model.addAttribute("dongList", dongList);
			}
		}
		
		adminMgtBuildVO.setType(type);
		
		int totalCount = adminMgtBuildService.getBuildTotalCount(adminMgtBuildVO);
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		adminMgtBuildVO.setStart_num(pageUtil.getStartNum());
		adminMgtBuildVO.setEnd_num(pageUtil.getEndNum());
		
		List<AdminMgtBuildVO> buildList = adminMgtBuildService.getBuildList(adminMgtBuildVO);
		
		model.addAttribute("AdminMgtBuildVO", adminMgtBuildVO);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("buildList", buildList);
		model.addAttribute("type", type);
		model.addAttribute("listCount", listCount);
		
		return "admin/mgt/build/adminMgtBuildList";
	}
	
	//	건물 등록 - 추가
	@GetMapping(value="/build/write")
	public String buildWrite(Model model) throws Exception{
		List<CommonApiVO> sidoList = commonApiService.getSido();
		
		model.addAttribute("sidoList", sidoList);
		return "admin/mgt/build/adminMgtBuildWrite";
	}
	
	//	건물 등록 - 저장
	@PostMapping(value="/build/writeProc")
	public String buildSave(Model model, @ModelAttribute AdminMgtBuildVO adminMgtBuildVO ) throws Exception{
		
		boolean result = true;
		String msg = null;
		
		try {
			
			adminMgtBuildService.buildInsert(adminMgtBuildVO);
			msg = messageSource.getMessage("save.success", null, Locale.KOREA);
			
		}catch(Exception e) {
			
			log.error(adminMgtBuildVO.toString());
			log.error(e.toString());
			result = false;
			msg = messageSource.getMessage("error.fail", null, Locale.KOREA);
			
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("result", result);
		
		return "admin/mgt/build/adminMgtBuildWriteProc";
	}
	
	//	건물 등록 - 활성화 (ajax)
	@RequestMapping(value="/build/enableProc")
	public ResponseEntity enableProc(@RequestBody List<AdminMgtBuildVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMgtBuildService.enableProc(list); 
			
		}catch(Exception e) {
			
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
			result = false;
			
		}finally {
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	//	건물 등록 - 비활성화 (ajax)
	@RequestMapping(value="/build/disableProc")
	public ResponseEntity disableProc(@RequestBody List<AdminMgtBuildVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMgtBuildService.disableProc(list); 
			
		}catch(Exception e) {
			
			result = false;
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	//	건물 등록 - 삭제 (ajax)
	@RequestMapping(value="/build/delProc")
	public ResponseEntity buildDelProc(@RequestBody List<AdminMgtBuildVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMgtBuildService.buildDelProc(list); 
			
		}catch(Exception e) {
			
			result = false;
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	//	건물 등록 - 코드 관리
	@RequestMapping("/build/codeList")
	public String buildCodeList(Model model) throws Exception {
		List<CommonApiVO> sidoList = commonApiService.getSido();
		
		model.addAttribute("sidoList", sidoList);
	
		return "admin/mgt/build/adminMgtBuildCodeList";
	}
	
	//	건물 등록 - 코드 관리 - 시도 생성
	@RequestMapping("/build/sidoWriteProc")
	public ResponseEntity sidoWriteProc(@RequestBody CommonApiVO commonApiVO) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			CommonApiVO vo = adminMgtBuildService.sidoWriteProc(commonApiVO);
			
			resultMap.put("sido_seq", vo.getSido_seq());
		}catch(Exception e) {
			
			result = false;
			log.error(commonApiVO.toString());
			
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	//	건물 등록 - 코드 관리 - 시도 수정
	@RequestMapping("/build/sidoModProc")
	public ResponseEntity sidoModProc(@RequestBody List<CommonApiVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMgtBuildService.sidoModProc(list); 
			
		}catch(Exception e) {
			
			result = false;
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	//	건물 등록 - 코드 관리 - 시도 삭제
	@RequestMapping("/build/sidoDelProc")
	public ResponseEntity sidoDelProc(@RequestBody List<CommonApiVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {

			adminMgtBuildService.sidoDelProc(list); 
			
		}catch(Exception e) {
			
			result = false;
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	

	//	건물 등록 - 코드 관리 - 시군구 생성
	@RequestMapping("/build/sigunguWriteProc")
	public ResponseEntity sigunguWriteProc(@RequestBody CommonApiVO commonApiVO) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			CommonApiVO vo = adminMgtBuildService.sigunguWriteProc(commonApiVO);
			
			resultMap.put("sigungu_seq", vo.getSigungu_seq());
		}catch(Exception e) {
			
			result = false;
			log.error(commonApiVO.toString());
			
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	
	//	건물 등록 - 코드 관리 - 시군구 수정
	@RequestMapping("/build/sigunguModProc")
	public ResponseEntity sigunguModProc(@RequestBody List<CommonApiVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMgtBuildService.sigunguModProc(list); 
			
		}catch(Exception e) {
			
			result = false;
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	
	//	건물 등록 - 코드 관리 - 시군구 삭제
	@RequestMapping("/build/sigunguDelProc")
	public ResponseEntity sigunguDelProc(@RequestBody List<CommonApiVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {

			adminMgtBuildService.sigunguDelProc(list); 
			
		}catch(Exception e) {
			
			result = false;
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	

	
	//	건물 등록 - 코드 관리 - 동 생성
	@RequestMapping("/build/dongWriteProc")
	public ResponseEntity dongWriteProc(@RequestBody CommonApiVO commonApiVO) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			CommonApiVO vo = adminMgtBuildService.dongWriteProc(commonApiVO);
			
			resultMap.put("dong_seq", vo.getDong_seq());
		}catch(Exception e) {
			
			result = false;
			log.error(commonApiVO.toString());
			
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}		
	
	//	건물 등록 - 코드 관리 - 동 수정
	@RequestMapping("/build/dongModProc")
	public ResponseEntity dongModProc(@RequestBody List<CommonApiVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMgtBuildService.dongModProc(list); 
			
		}catch(Exception e) {
			
			result = false;
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}		
	
	//	건물 등록 - 코드 관리 - 동 삭제
	@RequestMapping("/build/dongDelProc")
	public ResponseEntity dongDelProc(@RequestBody List<CommonApiVO> list) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {

			adminMgtBuildService.dongDelProc(list); 
			
		}catch(Exception e) {
			
			result = false;
			log.error(list.toString());
			log.error("list size : " + list.size());
			log.error(e.toString());
			
		}finally {
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}		
	
	//	건물 등록 - 건물 정보 관리 상세정보 작성 폼 (업종, 층)
	@PostMapping(value="/build/dtlWrite")
	public String buildDtlWrite(Model model, AdminMgtBuildVO adminMgtBuildVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "20")int listCount) throws Exception{
		
		//	최종 수정일 조회
		String mod_dt = adminMgtBuildService.getBuildLstModDt(adminMgtBuildVO);
		
		//	대분류 카테고리 조회
		List<AdminMgtBuildVO> lcateList = adminMgtBuildService.getLcateList(adminMgtBuildVO);
		
		//	소분류 카테고리 조회
		List<AdminMgtBuildVO> mcateList = adminMgtBuildService.getMcateList(adminMgtBuildVO);
		
		//	소분류 플래그
		List<AdminMgtBuildVO> mcateCount = adminMgtBuildService.getLcateCountOfMcate(adminMgtBuildVO);
		
		//	도면 조회
		List<AdminMgtBuildVO> floorList = adminMgtBuildService.getFloorList(adminMgtBuildVO);
				
		 Integer dtlType = 1; 
		 if(lcateList.size()!=0){
			 dtlType = 2; 
		}
		
		model.addAttribute("mcateCount", mcateCount);
		model.addAttribute("floorList",floorList);
		model.addAttribute("mcateList", mcateList);
		model.addAttribute("lcateList", lcateList);
		model.addAttribute("dtlType", dtlType);
		model.addAttribute("mod_dt", mod_dt);
		model.addAttribute("listCount", listCount);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("type", type);
		model.addAttribute("AdminMgtBuildVO", adminMgtBuildVO);
		return "admin/mgt/build/adminMgtBuildDtlWrite";
	}
	
	
	//	건물 등록 - 건물 정보 관리 상세정보 수정 폼
	@RequestMapping(value="/build/dtlWriteProc", method=RequestMethod.POST)
	public ResponseEntity upload(@RequestBody HashMap paramMap, HttpServletRequest req) throws Exception{
		
		Map resultMap = new HashMap();
		boolean result = true;
		String errorMsg = null;
		
		try {
			
			adminMgtBuildService.dtlWriteProc(paramMap, req);
		
		}catch(Exception e) {
			log.debug(e.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
		
	
}
