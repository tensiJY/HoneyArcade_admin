package honeyarcade.admin.ad.req;

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
import org.springframework.web.bind.annotation.ResponseBody;

import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.common.file.CommonFileService;
import honeyarcade.admin.common.file.CommonFileVO;
import honeyarcade.admin.common.mail.CustomMailService;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 광고관리 - 광고요청
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/admin/ad")
@Slf4j
public class AdminAdReqController {
	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private AdminAdReqService adminAdReqService;
	
	@Autowired
	private CommonFileService commonFileService;
	
	@Autowired
	private CustomMailService customMailService;
	
	/**
	 * 광고 요청 목록
	 * @param model
	 * @param adminAdReqVO
	 * @param nowPage
	 * @param type
	 * @param listCount
	 * @param search_type
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/req/list")
	public String reqList(Model model, @ModelAttribute AdminAdReqVO adminAdReqVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "25")int listCount
			, @RequestParam(value="search_type", defaultValue = "1") int search_type) throws Exception{
		Integer admin_grade = SessionUtil.getAdminGrade();
		String 	admin_id	= SessionUtil.getAdminId();
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = null;
		List<CommonApiVO> dongList = null;
		
		adminAdReqVO.setType(type);					//	type - list:목록, search:검색
		adminAdReqVO.setSearch_type(search_type);	//	1 전체보기, 2 미승인 요청 건, 3 승인 요청 건, 4 요청 반려 건
		adminAdReqVO.setAdmin_grade(admin_grade);	//	관리자 등급
		adminAdReqVO.setAdmin_id(admin_id);			//	관리자 아이디
			
		if(type.equals("search")) {	//	검색 조건 일때
			CommonApiVO commonApiVO = new CommonApiVO();
			
			  if(adminAdReqVO.getSido_seq() != null) {
				  commonApiVO.setSido_seq(adminAdReqVO.getSido_seq()); 
				  sigunguList = commonApiService.getSigungu(commonApiVO); 
				  model.addAttribute("sigunguList", sigunguList);
			  }
			  if(adminAdReqVO.getSigungu_seq() != null) {
				  commonApiVO.setSigungu_seq(adminAdReqVO.getSigungu_seq());
				  dongList = commonApiService.getDong(commonApiVO);
				  model.addAttribute("dongList", dongList); 
			  }
		}
		int totalCount = adminAdReqService.getTotalCount(adminAdReqVO);
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		
		adminAdReqVO.setStart_num(pageUtil.getStartNum());
		adminAdReqVO.setEnd_num(pageUtil.getEndNum());
		List<AdminAdReqVO> adReqList = adminAdReqService.getAdReqList(adminAdReqVO);
				
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("adReqList", adReqList);
		model.addAttribute("listCount", listCount);
		model.addAttribute("adminAdReqVO", adminAdReqVO);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("type", type);
		
		return "admin/ad/req/adminAdReqList";
	}
	
	/**
	 * 광고 요청 삭제 프로세스
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/req/delProc")
	public ResponseEntity reqDelProc(@RequestBody List<AdminAdReqVO> list) throws Exception{

		log.info("광고 요청 - 삭제 : " + list.toString());
		log.info("광고 요청 - 삭제 - 관리자 아이디 : " + SessionUtil.getAdminId());
		
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			adminAdReqService.reqDelProc(list);
		}catch(Exception e) {
			result = false;
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}	
	
	/**
	 * 광고요청 - 상세보기
	 * @param adminAdReqVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/req/view")
	public String reqView(@ModelAttribute AdminAdReqVO adminAdReqVO
			, Model model) throws Exception{
		
		AdminAdReqVO viewVO = adminAdReqService.getReqView(adminAdReqVO);
		CommonFileVO cvo = new CommonFileVO();
		cvo.setFile_seq(viewVO.getFile_seq());
		List<CommonFileVO> file_dtl_list = commonFileService.getFileDtlList(cvo);
		
		model.addAttribute("file_dtl_list",file_dtl_list);
		model.addAttribute("viewVO", viewVO);
		model.addAttribute("adminAdReqVO", adminAdReqVO);
		return "admin/ad/req/adminAdReqView";
	}
	
	/**
	 * 광고요청 - 승인 프로세스
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/req/accept")
	@ResponseBody
	public ResponseEntity reqAccept(@RequestBody AdminAdReqVO adminAdReqVO) throws Exception{
		
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			adminAdReqService.reqAccept(adminAdReqVO);
		}catch(Exception e) {
			
			result = false;
			log.error("req accept : " + SessionUtil.getAdminId());
			log.error(adminAdReqVO.toString());
			log.error(e.toString());
		}
		
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 광고요청 - 반려 프로세스
	 * @param adminAdReqVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/req/reject")
	@ResponseBody
	public ResponseEntity reqReject(@RequestBody AdminAdReqVO adminAdReqVO) throws Exception{
		
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			adminAdReqService.reqReject(adminAdReqVO);
			customMailService.sendReqRejectMail(adminAdReqVO.getMember_id(), adminAdReqVO.getOwner_email(), adminAdReqVO.getAd_req_reject_reason(), adminAdReqVO.getAd_req_type_text(), adminAdReqVO.getAd_req_dt());
			
		}catch(Exception e) {
			result = false;
			log.error("req reject : " + SessionUtil.getAdminId());
			log.error(adminAdReqVO.toString());
			log.error(e.toString());
		}
		resultMap.put("result", result);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
}
