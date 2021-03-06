package honeyarcade.admin.ad.area;

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
 * 광고관리 - 지역광고
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/admin/ad")
@Slf4j
public class AdminAdAreaController {
	
	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private AdminAdAreaService adminAdAreaService;

	/**
	 * 지역광고 - 목록
	 * @param model
	 * @param adminAdAreaVO
	 * @param nowPage
	 * @param type
	 * @param listCount
	 * @param area_type
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/area/list")
	public String areaList(Model model, @ModelAttribute AdminAdAreaVO adminAdAreaVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "25")int listCount
			, @RequestParam(value="area_type", defaultValue = "1") int area_type) throws Exception{
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = null;
		List<CommonApiVO> dongList = null;
		adminAdAreaVO.setType(type);			//	type - list:목록, search:검색
		adminAdAreaVO.setArea_type(area_type);	//	1 모든광고, 2 현재 활성화 광고, 3 지난 광고, 4 예약 광고
			
		if(type.equals("search")) {	//	검색 조건 일때
			CommonApiVO commonApiVO = new CommonApiVO();
			  if(adminAdAreaVO.getSido_seq() != null) {
				  commonApiVO.setSido_seq(adminAdAreaVO.getSido_seq()); 
				  sigunguList = commonApiService.getSigungu(commonApiVO); 
				  model.addAttribute("sigunguList", sigunguList);
			  }
			  if(adminAdAreaVO.getSigungu_seq() != null) {
				  commonApiVO.setSigungu_seq(adminAdAreaVO.getSigungu_seq());
				  dongList = commonApiService.getDong(commonApiVO);
				  model.addAttribute("dongList", dongList); 
			  }
		}
		
		int totalCount = adminAdAreaService.getTotalCount(adminAdAreaVO);
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		adminAdAreaVO.setStart_num(pageUtil.getStartNum());
		adminAdAreaVO.setEnd_num(pageUtil.getEndNum());
		List<AdminAdAreaVO> adAreaList = adminAdAreaService.getAreaAdList(adminAdAreaVO);
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("adAreaList", adAreaList);
		model.addAttribute("listCount", listCount);
		model.addAttribute("adminAdAreaVO", adminAdAreaVO);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("type", type);
		
		return "admin/ad/area/adminAdAreaList";
	}
	
	/**
	 * 지역광고 - 광고 삭제
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area/delProc")
	public ResponseEntity areaDelProc(@RequestBody List<AdminAdAreaVO> list) throws Exception{
		log.info("지역 광고 - 광고 삭제 : " + list.toString());
		log.info("지역 광고 - 광고 삭제 - 관리자 아이디 : " + SessionUtil.getAdminId());
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdAreaService.areaDelProc(list);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 지역광고 - 작성 - 외부 점포
	 * @param model
	 * @param adminAdAreaVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area/write")
	public String areaWrite (Model model, @ModelAttribute AdminAdAreaVO adminAdAreaVO) throws Exception{
		List<CommonApiVO> sidoList = commonApiService.getSido();
		model.addAttribute("sidoList", sidoList);
		return "admin/ad/area/adminAdAreaWrite";
	}
	
	/**
	 * 지역광고 - 작성 프로세스 - 외부점포
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area/writeProc")
	public ResponseEntity areaWriteProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdAreaService.areaWriteProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("area writeProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 지역광고 - 수정 페이지
	 * @param model
	 * @param adminAdAreaVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area/mod")
	public String areaModForm( Model model , @ModelAttribute AdminAdAreaVO adminAdAreaVO) throws Exception{
		String url = null;
		
		CommonApiVO commonApiVO = new CommonApiVO();
		commonApiVO.setSido_seq(adminAdAreaVO.getBanner_sido_seq());
		commonApiVO.setSigungu_seq(adminAdAreaVO.getBanner_sigungu_seq());
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = commonApiService.getSigungu(commonApiVO);
		List<CommonApiVO> dongList = commonApiService.getDong(commonApiVO);
		
		//	1 제휴 점포, 2 외부 점포
		Integer ad_owner_type = adminAdAreaVO.getAd_owner_type();
		
		AdminAdAreaVO bannerVO = null;					//	지역 광고 내용
		List<AdminAdAreaVO> bannerAreaList = null;		//	지역 광고의 지역
		if(ad_owner_type == 1) {
			commonApiVO.setDong_seq(adminAdAreaVO.getBanner_dong_seq());
			commonApiVO.setBuild_seq(adminAdAreaVO.getBanner_build_seq());
			
			List<CommonApiVO> buildList		= commonApiService.getBuild(commonApiVO);
			List<CommonApiVO> ownerList		= commonApiService.getOwnerOfBuild(commonApiVO);
			
			//	배너 정보
			bannerVO = adminAdAreaService.getAreaBanner(adminAdAreaVO);
			
			//	배너에 속한 지역 정보
			bannerAreaList = adminAdAreaService.getDongBanner(adminAdAreaVO);
			
			model.addAttribute("ownerList", ownerList);
			model.addAttribute("buildList", buildList);
			
			url = "admin/ad/area/adminAdAreaModForm";
			
		}else {	//	외부점포
			bannerVO = adminAdAreaService.getAreaBannerOfExt(adminAdAreaVO);
			Integer banner_seq = bannerVO.getBanner_seq();
			bannerAreaList = adminAdAreaService.getDongExtBanner(banner_seq);
			url = "admin/ad/area/adminAdAreaMod"; 
		}
		
		model.addAttribute("bannerAreaList", bannerAreaList);
		model.addAttribute("bannerVO", bannerVO);
		model.addAttribute("dongList", dongList);
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("adminAdAreaVO", adminAdAreaVO);
		return url;
	}
	
	/**
	 * 지역광고 - 외부 점포 - 수정 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area/modProc")
	public ResponseEntity areaModProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdAreaService.areaModProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("area modProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 지역광고 - 제휴점포 등록
	 * @param adminAdReqVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area/writeForm")
	public String areaWriteForm(@ModelAttribute AdminAdReqVO adminAdReqVO, Model model) throws Exception{
		
		CommonApiVO vo = new CommonApiVO();
		vo.setSido_seq(adminAdReqVO.getOwner_sido_seq());
		vo.setSigungu_seq(adminAdReqVO.getOwner_sigungu_seq());
		vo.setDong_seq(adminAdReqVO.getOwner_dong_seq());
		vo.setBuild_seq(adminAdReqVO.getOwner_build_seq());
				
		List<CommonApiVO> sidoList		= commonApiService.getSido();
		List<CommonApiVO> sigunguList	= commonApiService.getSigungu(vo);
		List<CommonApiVO> dongList		= commonApiService.getDong(vo);
		List<CommonApiVO> buildList		= commonApiService.getBuild(vo);
		List<CommonApiVO> ownerList		= commonApiService.getOwnerOfBuild(vo);
		
		model.addAttribute("adminAdReqVO", adminAdReqVO);
		model.addAttribute("ownerList", ownerList);
		model.addAttribute("buildList", buildList);
		model.addAttribute("dongList", dongList); 
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("sidoList", sidoList);
		return "admin/ad/area/adminAdAreaWriteForm";
	}
	
	/**
	 * 지역광고 - 제휴점포 등록 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area/writeFormProc")
	public ResponseEntity writeFormProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdAreaService.writeFormProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("area writeFormProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 지역광고 - 제휴점포 - 수정 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/area/modFormProc")
	public ResponseEntity modFormProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdAreaService.modFormProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("area modFormProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
}
