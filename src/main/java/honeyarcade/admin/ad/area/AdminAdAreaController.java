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

import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 광고 관리 - 지역 광고
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

	//	지역광고 - 목록
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
	
	//	지역광고 - 광고 삭제
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
	
	//	지역광고 - 작성 - 외부 점포
	@RequestMapping("/area/write")
	public String areaWrite (Model model, @ModelAttribute AdminAdAreaVO adminAdAreaVO) throws Exception{
		
		log.info(adminAdAreaVO.toString());
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		
		
		model.addAttribute("sidoList", sidoList);
		return "admin/ad/area/adminAdAreaWrite";
	}
	
	//	지역광고 - 작성 프로세스 - 외부점포
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
	
	//	지역광고 - 수정 페이지
	@RequestMapping("/area/mod")
	public String areaModForm( Model model , @ModelAttribute AdminAdAreaVO adminAdAreaVO) throws Exception{
		String url = null;
		
		//	시도 리스트
		//	시군구 리스트
		//	지역 리스트
		List<CommonApiVO> sidoList = commonApiService.getSido();
		
		CommonApiVO commonApiVO = new CommonApiVO();
		commonApiVO.setSido_seq(adminAdAreaVO.getSido_seq()); 
		List<CommonApiVO>  sigunguList = commonApiService.getSigungu(commonApiVO); 
		commonApiVO.setSigungu_seq(adminAdAreaVO.getSigungu_seq());
		List<CommonApiVO> dongList = commonApiService.getDong(commonApiVO);
		
		//	1 제휴 점포, 2 외부 점포
		Integer ad_owner_type = adminAdAreaVO.getAd_owner_type();
		
		AdminAdAreaVO bannerVO = null;					//	지역 광고 내용
		List<AdminAdAreaVO> bannerAreaList = null;		//	지역 광고의 지역
		if(ad_owner_type == 1) {
			//	작성 해야함
			//bannerVO = adminAdAreaService.getAreaBanner(adminAdAreaVO);
			//Integer banner_seq = bannerVO.getBanner_seq();
			
			url =  "";
			
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
	
	//	지역광고 - 수정 프로세스
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
	 
	
}