package honeyarcade.admin.member.owner;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.common.file.CommonFileService;
import honeyarcade.admin.common.file.CommonFileVO;
import honeyarcade.admin.common.mail.CustomMailService;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/member")
@Slf4j
public class AdminMemberOwnerController {
	
	@Autowired
	private AdminMemberOwnerService adminMemberOwnerService;
	
	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private CommonFileService commonFileService;
	
	@Autowired
	private CustomMailService customMailService;

	/**
	 * 회원관리 - 점주 회원 : 목록
	 * @param model
	 * @param adminMemberOwnerVO
	 * @param nowPage
	 * @param listCount
	 * @param type
	 * @param search_text
	 * @param owner_type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/owner/list")
	public String ownerList(Model model, @ModelAttribute AdminMemberOwnerVO adminMemberOwnerVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="listCount", defaultValue = "25") int listCount
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="search_text", defaultValue="") String search_text 
			, @RequestParam(value="owner_type", defaultValue="0") int owner_type
			, @RequestParam(value="sort_date_type", defaultValue="1") Integer sort_date_type
			) throws Exception {
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = null;
		List<CommonApiVO> dongList = null;
		List<CommonApiVO> buildList = null;
		
		adminMemberOwnerVO.setType(type);				//	type - list:목록, search:검색
		adminMemberOwnerVO.setOwner_type(owner_type);	//	0 전체회원, 1 무료 회원, 2 유료 회원, 3 만료 회원
		adminMemberOwnerVO.setSort_date_type(sort_date_type);
			
		if(type.equals("search")) {	//	검색 조건 일때
			CommonApiVO commonApiVO = new CommonApiVO();
			
			if(adminMemberOwnerVO.getSido_seq() != null) {
				commonApiVO.setSido_seq(adminMemberOwnerVO.getSido_seq()); 
				sigunguList = commonApiService.getSigungu(commonApiVO); 
				model.addAttribute("sigunguList", sigunguList);
			}
			
			if(adminMemberOwnerVO.getSigungu_seq() != null) {
				commonApiVO.setSigungu_seq(adminMemberOwnerVO.getSigungu_seq());
				dongList = commonApiService.getDong(commonApiVO);
				model.addAttribute("dongList", dongList); 
			}
			
			if(adminMemberOwnerVO.getDong_seq() != null) {
				commonApiVO.setDong_seq(adminMemberOwnerVO.getDong_seq());
				buildList = commonApiService.getBuild(commonApiVO);
				model.addAttribute("buildList", buildList);
			}
		}
		
		//	점주 회원 분류별 회원 수 조회
		AdminMemberOwnerVO countVO = adminMemberOwnerService.getOwnerTypeCount(adminMemberOwnerVO); 
		int totalCount = adminMemberOwnerService.getTotalCount(adminMemberOwnerVO);
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		adminMemberOwnerVO.setStart_num(pageUtil.getStartNum());
		adminMemberOwnerVO.setEnd_num(pageUtil.getEndNum());
		List<AdminMemberOwnerVO> ownerList = adminMemberOwnerService.getOwnerList(adminMemberOwnerVO);
		model.addAttribute("sort_date_type", sort_date_type);
		model.addAttribute("countVO", countVO);
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("ownerList", ownerList);
		model.addAttribute("listCount", listCount);
		model.addAttribute("adminMemberOwnerVO", adminMemberOwnerVO);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("type", type);
		model.addAttribute("owner_type", owner_type);
		model.addAttribute("search_text", search_text);
		return "admin/member/owner/adminMemberOwnerList";
	}
	
	/**
	 * 점주회원 : 상세보기 
	 * @param model
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/owner/view")
	public String owneView(Model model, @ModelAttribute AdminMemberOwnerVO adminMemberOwnerVO)  throws Exception{
		AdminMemberOwnerVO ownerInfo = adminMemberOwnerService.getOwnerInfo(adminMemberOwnerVO);
		CommonApiVO commonApiVO = new CommonApiVO();
		commonApiVO.setSido_seq(ownerInfo.getSido_seq());
		commonApiVO.setSigungu_seq(ownerInfo.getSigungu_seq());
		commonApiVO.setDong_seq(ownerInfo.getDong_seq());
		commonApiVO.setBuild_seq(ownerInfo.getBuild_seq());
		commonApiVO.setLcate_seq(ownerInfo.getLcate_seq());

		List<CommonApiVO> sidoList 		= commonApiService.getSido();						//	시도
		List<CommonApiVO> sigunguList	= commonApiService.getSigungu(commonApiVO);			//	시군구
		List<CommonApiVO> dongList 		= commonApiService.getDong(commonApiVO);			//	지역
		List<CommonApiVO> buildList		= commonApiService.getBuild(commonApiVO);			//	건물
		List<CommonApiVO> floorList		= commonApiService.getFloor(commonApiVO);			//	층
		List<CommonApiVO> lcateList		= commonApiService.getLcateOfBuild(commonApiVO);	//	대분류
		List<CommonApiVO> mcateList		= commonApiService.getMcateOfBuild(commonApiVO);	//	소분류
		List<AdminMemberOwnerVO> productList = adminMemberOwnerService.getProductList(adminMemberOwnerVO);//	상품
		
		CommonFileVO fileVO = new CommonFileVO();
		fileVO.setFile_seq(ownerInfo.getBusi_reg_file_seq());
		CommonFileVO busiVO = commonFileService.getFileDtlInfo(fileVO);	//	사업자등록증
		
		fileVO.setFile_seq(ownerInfo.getMain_file_seq());
		List<CommonFileVO> mainList = commonFileService.getFileDtlList(fileVO);	//	메인사진
		
		fileVO.setFile_seq(ownerInfo.getPro_file_seq());
		CommonFileVO proVO = commonFileService.getFileDtlInfo(fileVO);	//	프로필사진
		
		List<AdminMemberOwnerVO> openList  = adminMemberOwnerService.getOpenDay(adminMemberOwnerVO);//	오픈시간
		List<AdminMemberOwnerVO> breakList = adminMemberOwnerService.getBreakDay(adminMemberOwnerVO);//	휴게시간
		List<AdminMemberOwnerVO> dayOffList = adminMemberOwnerService.getDayOffList(adminMemberOwnerVO);//휴무일
		
		model.addAttribute("dayOffList", dayOffList);
		model.addAttribute("breakList", breakList);
		model.addAttribute("openList", openList);
		model.addAttribute("proVO", proVO);
		model.addAttribute("mainList", mainList);
		model.addAttribute("busiVO", busiVO);
		model.addAttribute("productList", productList);
		model.addAttribute("mcateList", mcateList);
		model.addAttribute("lcateList", lcateList);
		model.addAttribute("floorList", floorList);
		model.addAttribute("buildList", buildList);
		model.addAttribute("dongList", dongList);
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("ownerInfo", ownerInfo);
		model.addAttribute("adminMemberOwnerVO", adminMemberOwnerVO);	//	파라미터
		return "admin/member/owner/adminMemberOwnerView";
	}
	
	/**
	 * 회원관리 : 상세보기 - 저장 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/owner/saveProc", method=RequestMethod.POST)
	public ResponseEntity saveProc(@RequestBody Map dataMap) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;			
		String errorMsg = null;
		try {
			log.info("회원 관리 - 상세 보기 - 저장 프로세스 : " + dataMap.toString());
			adminMemberOwnerService.saveProc(dataMap);
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(dataMap.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원관리 : 점주회원 - 승인
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/owner/acceptProc", method=RequestMethod.POST)
	public ResponseEntity acceptProc(@RequestBody AdminMemberOwnerVO adminMemberOwnerVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;			
		String errorMsg = null;
		try {
			adminMemberOwnerService.acceptProc(adminMemberOwnerVO);
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(adminMemberOwnerVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원관리 : 점주회원 - 점포상태 변경
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/owner/statusProc", method=RequestMethod.POST)
	public ResponseEntity statusProc(@RequestBody AdminMemberOwnerVO adminMemberOwnerVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;			
		String errorMsg = null;
		try {
			adminMemberOwnerVO.setMod_id(SessionUtil.getAdminId());
			adminMemberOwnerService.statusProc(adminMemberOwnerVO);
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(adminMemberOwnerVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 거절 프로세스
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/owner/rejectProc", method=RequestMethod.POST)
	public ResponseEntity rejectProc(@RequestBody AdminMemberOwnerVO adminMemberOwnerVO) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;			
		String errorMsg = null;
		try {
			adminMemberOwnerVO.setAdmin_id(SessionUtil.getAdminId());
			log.info("반려 : " + adminMemberOwnerVO.toString());
			adminMemberOwnerService.rejectProc(adminMemberOwnerVO);
			customMailService.sendRejectMail(adminMemberOwnerVO.getMember_id(), adminMemberOwnerVO.getOwner_email(), adminMemberOwnerVO.getReject_text());
			
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(adminMemberOwnerVO.toString());
			result = false;
			errorMsg = e.getMessage();
			resultMap.put("errorMsg", errorMsg);
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
