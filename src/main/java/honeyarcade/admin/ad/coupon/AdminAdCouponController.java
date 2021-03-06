package honeyarcade.admin.ad.coupon;

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

import honeyarcade.admin.ad.area.AdminAdAreaService;
import honeyarcade.admin.ad.area.AdminAdAreaVO;
import honeyarcade.admin.ad.build.AdminAdBuildVO;
import honeyarcade.admin.ad.req.AdminAdReqVO;
import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 광고관리 - 쿠폰관리
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/admin/ad")
@Slf4j
public class AdminAdCouponController {
	
	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private AdminAdCouponService adminAdCouponService;

	/**
	 * 쿠폰 관리 - 목록
	 * @param model
	 * @param adminAdCouponVO
	 * @param nowPage
	 * @param type
	 * @param listCount
	 * @param search_type
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/coupon/list")
	public String areaList(Model model, @ModelAttribute AdminAdCouponVO adminAdCouponVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "25")int listCount
			, @RequestParam(value="search_type", defaultValue = "1") int search_type) throws Exception{
		Integer admin_grade = SessionUtil.getAdminGrade();
		String 	admin_id	= SessionUtil.getAdminId();
	
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = null;
		List<CommonApiVO> dongList = null;
		
		adminAdCouponVO.setType(type);					//	type - list:목록, search:검색
		adminAdCouponVO.setSearch_type(search_type);	//	1 모든광고, 2 현재 활성화 광고, 3 지난 광고, 4 예약 광고
		adminAdCouponVO.setAdmin_grade(admin_grade);	//	관리자 등급
		adminAdCouponVO.setAdmin_id(admin_id);			//	관리자 아이디
			
		if(type.equals("search")) {	//	검색 조건 일때
			CommonApiVO commonApiVO = new CommonApiVO();
			
			  if(adminAdCouponVO.getSido_seq() != null) {
				  commonApiVO.setSido_seq(adminAdCouponVO.getSido_seq()); 
				  sigunguList = commonApiService.getSigungu(commonApiVO); 
				  model.addAttribute("sigunguList", sigunguList);
			  }
			  if(adminAdCouponVO.getSigungu_seq() != null) {
				  commonApiVO.setSigungu_seq(adminAdCouponVO.getSigungu_seq());
				  dongList = commonApiService.getDong(commonApiVO);
				  model.addAttribute("dongList", dongList); 
			  }
		}
		
		int totalCount = adminAdCouponService.getTotalCount(adminAdCouponVO);
		
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		
		adminAdCouponVO.setStart_num(pageUtil.getStartNum());
		adminAdCouponVO.setEnd_num(pageUtil.getEndNum());
		
		List<AdminAdCouponVO> couponList = adminAdCouponService.getAdCouponList(adminAdCouponVO);
		
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("couponList", couponList);
		model.addAttribute("listCount", listCount);
		model.addAttribute("adminAdCouponVO", adminAdCouponVO);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("type", type);
		
		return "admin/ad/coupon/adminAdCouponList";
	}
	
	/**
	 * 쿠폰 관리 - 쿠폰 광고 삭제 프로세스
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/coupon/delProc")
	public ResponseEntity couponDelProc(@RequestBody List<AdminAdCouponVO> list) throws Exception{
		log.info("쿠폰 관리 - 쿠폰 광고 삭제 : " + list.toString());
		log.info("쿠폰 관리 - 쿠폰 광고 삭제 - 관리자 아이디 : " + SessionUtil.getAdminId());
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdCouponService.couponDelProc(list);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 쿠폰 관리 - 제휴 점포 쿠폰 광고 등록	
	 * @param adminAdReqVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/coupon/write")
	public String couponWrite(@ModelAttribute AdminAdReqVO adminAdReqVO, Model model) throws Exception{
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
		return "admin/ad/coupon/adminAdCouponWrite";
	}
	
	/**
	 * 쿠폰 관리 - 등록 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/coupon/writeProc")	
	public ResponseEntity couponWriteProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {			
			adminAdCouponService.couponWriteProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("coupon writeProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}finally {
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 쿠폰 관리 - 수정 페이지
	 * @param model
	 * @param adminAdCouponVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/coupon/mod")
	public String couponMod( Model model , @ModelAttribute AdminAdCouponVO adminAdCouponVO) throws Exception{
		CommonApiVO commonApiVO = new CommonApiVO();
		commonApiVO.setSido_seq(adminAdCouponVO.getOwner_sido_seq());
		commonApiVO.setSigungu_seq(adminAdCouponVO.getOwner_sigungu_seq());
		commonApiVO.setDong_seq(adminAdCouponVO.getOwner_dong_seq());
		commonApiVO.setBuild_seq(adminAdCouponVO.getBuild_seq());
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> allSidoList = commonApiService.getAllSido();
		List<CommonApiVO> sigunguList = commonApiService.getSigungu(commonApiVO);	 
		List<CommonApiVO> dongList = commonApiService.getDong(commonApiVO);
		List<CommonApiVO> buildList = commonApiService.getBuild(commonApiVO);
		List<CommonApiVO> ownerList = commonApiService.getOwnerOfBuild(commonApiVO);
				
		//	쿠폰 정보
		AdminAdCouponVO coupon = adminAdCouponService.getAdCoupon(adminAdCouponVO);
		//	텍스트 정보
		List<AdminAdCouponVO> couponTextList = adminAdCouponService.getCouponTextList(adminAdCouponVO);
		//	건물 정보
		List<AdminAdCouponVO> couponBuildList = adminAdCouponService.getCouponBuildList(adminAdCouponVO);

		model.addAttribute("allSidoList", allSidoList);
		model.addAttribute("couponTextList", couponTextList);
		model.addAttribute("couponBuildList", couponBuildList);
		model.addAttribute("coupon", coupon);					//	데이터
		model.addAttribute("adminAdCouponVO", adminAdCouponVO);	//	파라미터
		model.addAttribute("ownerList", ownerList);
		model.addAttribute("buildList", buildList);
		model.addAttribute("dongList", dongList);
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("adminAdCouponVO", adminAdCouponVO);
		return "admin/ad/coupon/adminAdCouponMod";
	}
	
	/**
	 * 쿠폰 관리 - 수정 프로세스
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/coupon/modProc")
	public ResponseEntity couponModProc(@RequestBody HashMap dataMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminAdCouponService.couponModProc(dataMap);
		}catch(Exception e) {
			result = false;
			log.error("coupon modProc : " + SessionUtil.getAdminId());
			log.error(dataMap.toString());
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
}
