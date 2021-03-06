package honeyarcade.admin.ad.build;

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

import honeyarcade.admin.ad.req.AdminAdReqVO;
import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 광고관리 - 건물광고
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/admin/ad")
@Slf4j
public class AdminAdBuildController {
	
	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private AdminAdBuildService adminAdBuildService;

	/**
	 * 건물광고 : 배너 건물 광고 목록 
	 * @param model
	 * @param adminAdBuildVO
	 * @param nowPage
	 * @param type
	 * @param listCount
	 * @param build_type
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/build/list")
	public String buildList(Model model, @ModelAttribute AdminAdBuildVO adminAdBuildVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "25")int listCount
			, @RequestParam(value="build_type", defaultValue = "1") int build_type) throws Exception{
		Integer admin_grade = SessionUtil.getAdminGrade();
		String 	admin_id	= SessionUtil.getAdminId();
	
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = null;
		List<CommonApiVO> dongList = null;
		
		adminAdBuildVO.setType(type);				//	type - list:목록, search:검색
		adminAdBuildVO.setBuild_type(build_type);	//	1 모든광고, 2 현재 활성화 광고, 3 지난 광고, 4 예약 광고
		adminAdBuildVO.setAdmin_grade(admin_grade);	//	관리자 등급
		adminAdBuildVO.setAdmin_id(admin_id);		//	관리자 아이디
			
		if(type.equals("search")) {	//	검색 조건 일때
			CommonApiVO commonApiVO = new CommonApiVO();
			if(adminAdBuildVO.getSido_seq() != null) {
				commonApiVO.setSido_seq(adminAdBuildVO.getSido_seq()); 
				sigunguList = commonApiService.getSigungu(commonApiVO); 
				model.addAttribute("sigunguList", sigunguList);
			}
			if(adminAdBuildVO.getSigungu_seq() != null) {
				commonApiVO.setSigungu_seq(adminAdBuildVO.getSigungu_seq());
				dongList = commonApiService.getDong(commonApiVO);
				model.addAttribute("dongList", dongList); 
			}
		}
		
		int totalCount = adminAdBuildService.getTotalCount(adminAdBuildVO);
		
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		
		adminAdBuildVO.setStart_num(pageUtil.getStartNum());
		adminAdBuildVO.setEnd_num(pageUtil.getEndNum());
		
		List<AdminAdBuildVO> adBuildList = adminAdBuildService.getBuildAdList(adminAdBuildVO);
		
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("adBuildList", adBuildList);
		model.addAttribute("listCount", listCount);
		model.addAttribute("adminAdBuildVO", adminAdBuildVO);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("type", type);
		
		return "admin/ad/build/adminAdBuildList";
	}
	
	/**
	 * 건물광고 : 배너광고 삭제
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/delProc")
	public ResponseEntity buildDelProc(@RequestBody List<AdminAdBuildVO> list) throws Exception{

		log.info("지역 광고 - 광고 삭제 : " + list.toString());
		log.info("지역 광고 - 광고 삭제 - 관리자 아이디 : " + SessionUtil.getAdminId());
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdBuildService.buildDelProc(list);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
		}finally {
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 건물광고 - 외부 점포 등록 폼
	 * @param model
	 * @param adminAdBuildVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/write")
	public String buildWrite (Model model, @ModelAttribute AdminAdBuildVO adminAdBuildVO) throws Exception{
		List<CommonApiVO> sidoList = commonApiService.getSido(); 
		List<CommonApiVO> allSidoList = commonApiService.getAllSido();
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("allSidoList", allSidoList);
		return "admin/ad/build/adminAdBuildWrite";
	}
	
	/**
	 * 건물광고 - 외부 점포 등록(저장) 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/writeProc")
	public ResponseEntity buildWriteProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {			
			adminAdBuildService.buildWriteProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("build writeProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 지역광고 - 수정 페이지
	 * @param model
	 * @param adminAdBuildVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/mod")
	public String buildModForm( Model model , @ModelAttribute AdminAdBuildVO adminAdBuildVO) throws Exception{
		String url = null;
		CommonApiVO commonApiVO = new CommonApiVO();
		commonApiVO.setSido_seq(adminAdBuildVO.getBanner_sido_seq());
		commonApiVO.setSigungu_seq(adminAdBuildVO.getBanner_sigungu_seq());
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> allSidoList = commonApiService.getAllSido();
		List<CommonApiVO> sigunguList = commonApiService.getSigungu(commonApiVO);
		List<CommonApiVO> dongList = commonApiService.getDong(commonApiVO);
		
		//	1 제휴 점포, 2 외부 점포
		Integer ad_owner_type = adminAdBuildVO.getAd_owner_type();
		
		AdminAdBuildVO bannerVO = null;						//	지역 광고 내용
		List<AdminAdBuildVO> bannerBuildList = null;		//	지역 광고의 지역
		if(ad_owner_type == 1) {	//	제휴점포
			commonApiVO.setDong_seq(adminAdBuildVO.getBanner_dong_seq());
			commonApiVO.setBuild_seq(adminAdBuildVO.getBanner_build_seq());
			
			List<CommonApiVO> buildList		= commonApiService.getBuild(commonApiVO);
			List<CommonApiVO> ownerList		= commonApiService.getOwnerOfBuild(commonApiVO);
			
			//	배너 정보
			bannerVO = adminAdBuildService.getBanner(adminAdBuildVO);
			
			//	건물 정보
			bannerBuildList = adminAdBuildService.getBuildBanner(adminAdBuildVO);		
			model.addAttribute("ownerList", ownerList);
			model.addAttribute("buildList", buildList);
			url =  "admin/ad/build/adminAdBuildModForm";
			
		}else {						//	외부점포
			bannerVO = adminAdBuildService.getBuildBannerOfExt(adminAdBuildVO);
			Integer banner_seq = bannerVO.getBanner_seq();
			bannerBuildList = adminAdBuildService.getBudilExtBanner(banner_seq);
			url = "admin/ad/build/adminAdBuildMod"; 
		}
		
		model.addAttribute("allSidoList", allSidoList);
		model.addAttribute("bannerBuildList", bannerBuildList);
		model.addAttribute("bannerVO", bannerVO);
		model.addAttribute("dongList", dongList);
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("adminAdBuildVO", adminAdBuildVO);
		return url;
	}
	
	/**
	 * 건물광고 - 외부점포 수정 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/modProc")
	public ResponseEntity buildModProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdBuildService.buildModProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("build modProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 건물광고 - 제휴점포 등록 폼
	 * @param adminAdReqVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/writeForm")
	public String buildWriteForm(@ModelAttribute AdminAdReqVO adminAdReqVO, Model model) throws Exception{
		
		CommonApiVO vo = new CommonApiVO();
		vo.setSido_seq(adminAdReqVO.getOwner_sido_seq());
		vo.setSigungu_seq(adminAdReqVO.getOwner_sigungu_seq());
		vo.setDong_seq(adminAdReqVO.getOwner_dong_seq());
		vo.setBuild_seq(adminAdReqVO.getOwner_build_seq());
				
		List<CommonApiVO> sidoList		= commonApiService.getSido();
		List<CommonApiVO> allSidoList = commonApiService.getAllSido();
		List<CommonApiVO> sigunguList	= commonApiService.getSigungu(vo);
		List<CommonApiVO> dongList		= commonApiService.getDong(vo);
		List<CommonApiVO> buildList		= commonApiService.getBuild(vo);
		List<CommonApiVO> ownerList		= commonApiService.getOwnerOfBuild(vo);
		
		model.addAttribute("allSidoList", allSidoList);
		model.addAttribute("adminAdReqVO", adminAdReqVO);
		model.addAttribute("ownerList", ownerList);
		model.addAttribute("buildList", buildList);
		model.addAttribute("dongList", dongList); 
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("sidoList", sidoList);
		return "admin/ad/build/adminAdBuildWriteForm";
	}
	
	/**
	 * 건물광고 - 제휴점포 등록 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/writeFormProc")
	public ResponseEntity writeFormProc(@RequestBody HashMap dataMap) throws Exception{
		
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			adminAdBuildService.writeFormProc(dataMap);
			
		}catch(Exception e) {
			
			result = false;
			log.error("build writeFormProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}finally {
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 건물 광고 - 제휴점포 - 수정 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/modFormProc")
	public ResponseEntity modFormProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdBuildService.modFormProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("build modFormProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}finally {
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
