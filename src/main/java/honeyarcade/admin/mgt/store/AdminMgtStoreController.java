package honeyarcade.admin.mgt.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;

import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.mgt.build.AdminMgtBuildVO;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 건물관리 - 점포 관리
 * @author Koreasoft
 *
 */
@Controller
@RequestMapping("/admin/mgt")
@Slf4j
public class AdminMgtStoreController {

	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private AdminMgtStoreService adminMgtStoreService;
	
	/**
	 * 점포 관리 - 목록
	 * @param model
	 * @param adminMgtStoreVO
	 * @param nowPage
	 * @param type
	 * @param listCount
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/store/list")
	public String storeList(Model model, @ModelAttribute AdminMgtStoreVO adminMgtStoreVO
			, @RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="listCount", defaultValue = "50")int listCount) throws Exception{
		Integer admin_grade = SessionUtil.getAdminGrade();
		String 	admin_id	= SessionUtil.getAdminId();
			
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = null;
		List<CommonApiVO> dongList = null;
		List<CommonApiVO> buildList = null;
		List<CommonApiVO> floorList = null;
		List<AdminMgtStoreVO> storeList = null;
			
		if(type.equals("search")) {	//	검색 조건 일때
			CommonApiVO commonApiVO = new CommonApiVO();
			
			if(adminMgtStoreVO.getSido_seq() != null) {
				commonApiVO.setSido_seq(adminMgtStoreVO.getSido_seq());
				sigunguList = commonApiService.getSigungu(commonApiVO);
				model.addAttribute("sigunguList", sigunguList);

			}
			
			if(adminMgtStoreVO.getSigungu_seq() != null) {
				commonApiVO.setSigungu_seq(adminMgtStoreVO.getSigungu_seq());
				dongList = commonApiService.getDong(commonApiVO);
				model.addAttribute("dongList", dongList);
			}
			
			if(adminMgtStoreVO.getDong_seq() != null) {
				commonApiVO.setDong_seq(adminMgtStoreVO.getDong_seq());
				buildList = commonApiService.getBuild(commonApiVO);
				model.addAttribute("buildList", buildList);
			}
			
			if(adminMgtStoreVO.getBuild_seq() != null) {
				commonApiVO.setBuild_seq(adminMgtStoreVO.getBuild_seq());
				floorList = commonApiService.getFloor(commonApiVO);
				model.addAttribute("floorList", floorList);
			}
						
			adminMgtStoreVO.setType(type);
			adminMgtStoreVO.setAdmin_grade(admin_grade);	//	관리자 등급
			adminMgtStoreVO.setAdmin_id(admin_id);			//	관리자 아이디
			
			int totalCount = adminMgtStoreService.getTotalCount(adminMgtStoreVO);
			PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
			adminMgtStoreVO.setStart_num(pageUtil.getStartNum());
			adminMgtStoreVO.setEnd_num(pageUtil.getEndNum());
			
			storeList = adminMgtStoreService.getStoreList(adminMgtStoreVO);
			model.addAttribute("storeList", storeList);
			model.addAttribute("pageUtil", pageUtil);
		}
		
		model.addAttribute("listCount", listCount);
		model.addAttribute("adminMgtStoreVO", adminMgtStoreVO);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("type", type);
		
		return "admin/mgt/store/adminMgtStoreList";
	}
	
	/**
	 * 점포관리 - 노출, 숨김
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/store/viewProc")
	public ResponseEntity viewProc(@RequestBody Map paramMap ) throws Exception{
		boolean result = true;
		String msg = null;
		
		Map resultMap = new HashMap();
		
		try {
			
			adminMgtStoreService.viewProc(paramMap);
			
		}catch(Exception e) {
			log.error("점포 관리 노출, 숨김 에러");
			log.error(paramMap.toString());
			log.error(e.toString());
			result = false;
			
		}finally {
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="/store/delProc")
	public ResponseEntity delProc(@RequestBody Map paramMap ) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		try {
			adminMgtStoreService.delProc(paramMap);
		}catch(Exception e) {
			log.error("삭제");
			log.error(paramMap.toString());
			log.error(e.toString());
			result = false;
		}finally {
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
	@GetMapping(path="/store/excelDown", produces = "application/vnd.ms-excel")
	public String excelDown(Model model, AdminMgtStoreVO adminMgtStoreVO)throws Exception {
		adminMgtStoreVO.setAdmin_grade(SessionUtil.getAdminGrade());
		adminMgtStoreVO.setAdmin_id(SessionUtil.getAdminId());
		List<AdminMgtStoreVO> storeList = adminMgtStoreService.getStoreExcelList(adminMgtStoreVO);
		int totalCnt = storeList.size();
		int listCount = adminMgtStoreVO.getListCount() == null ? 50 : adminMgtStoreVO.getListCount();
		PageUtil util = new PageUtil(1, totalCnt, listCount);
		int totalPage = util.getTotalPage();	
		
		List dataList = new ArrayList();
		
		for(int i=1; i<=totalPage; i++) {
			List sheet = new ArrayList();
			util = new PageUtil(i, totalCnt, listCount);
			int start = util.getStartNum();
			int end =  totalPage == i? totalCnt : util.getEndNum();
			for(int j=start; j<=end; j++) {	// list의 인덱스는 0부터 시작이므로 j에서 -1을 시킨다
				sheet.add(storeList.get(j-1));
			}
			dataList.add(sheet);
		}
		model.addAttribute("sheetList", dataList);
		return "storeListDown";
	}
	

	
}
