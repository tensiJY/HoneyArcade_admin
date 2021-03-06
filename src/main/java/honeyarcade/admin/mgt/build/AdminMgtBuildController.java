package honeyarcade.admin.mgt.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import honeyarcade.admin.common.file.CommonFileVO;
import honeyarcade.admin.util.FileUtil;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
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
	
	/**
	 * 건물 등록 - 목록
	 * @param model
	 * @param adminMgtBuildVO
	 * @param nowPage
	 * @param type
	 * @param listCount
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 추가
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/build/write")
	public String buildWrite(Model model) throws Exception{
		List<CommonApiVO> sidoList = commonApiService.getSido();
		
		model.addAttribute("sidoList", sidoList);
		return "admin/mgt/build/adminMgtBuildWrite";
	}
	
	/**
	 * 건물 등록 - 저장
	 * @param model
	 * @param adminMgtBuildVO
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 활성화 (ajax)
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 비활성화 (ajax)
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 삭제 (ajax)
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 코드 관리
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/build/codeList")
	public String buildCodeList(Model model) throws Exception {
		List<CommonApiVO> sidoList = commonApiService.getSido();
		
		model.addAttribute("sidoList", sidoList);
	
		return "admin/mgt/build/adminMgtBuildCodeList";
	}
	
	/**
	 * 건물 등록 - 코드 관리 - 시도 생성
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 코드 관리 - 시도 수정
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 코드 관리 - 시도 삭제
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	

	/**
	 * 건물 등록 - 코드 관리 - 시군구 생성
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
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
	
	
	/**
	 * 건물 등록 - 코드 관리 - 시군구 수정
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	
	
	/**
	 * 건물 등록 - 코드 관리 - 시군구 삭제
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 코드 관리 - 동 생성
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 코드 관리 - 동 수정
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 코드 관리 - 동 삭제
	 * @param list
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 건물 정보 관리 상세정보 작성 폼 (업종, 층)
	 * @param model
	 * @param adminMgtBuildVO
	 * @param nowPage
	 * @param type
	 * @param listCount
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 건물 등록 - 건물 정보 관리 상세정보 수정 폼
	 * @param paramMap
	 * @param req
	 * @return
	 * @throws Exception
	 */
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
		
	@GetMapping(path="/build/excelDown", produces = "application/vnd.ms-excel")
	public String excelDown(Model model, AdminMgtBuildVO adminMgtBuildVO)throws Exception {
		List<AdminMgtBuildVO> floorList = adminMgtBuildService.getFloorList(adminMgtBuildVO);
		model.addAttribute("floorList", floorList);
		model.addAttribute("adminMgtBuildVO", adminMgtBuildVO);
		return "buildFormDown";
	}
	
	@RequestMapping(value="/build/excelUpload", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity upload(@ModelAttribute CommonFileVO commonFileVO, HttpServletRequest req) throws Exception{
		Map resultMap = new HashMap();
		boolean result = true;
		String msg = "";
		FileInputStream fis = null;
		
		try {
			List<AdminMgtBuildVO> ownerList = new ArrayList<AdminMgtBuildVO>();
			//	파일 서버에 저장 >> DB 생성은 안함
			MultipartFile file = commonFileVO.getFile(); 
			
			if(file.isEmpty()) {
				throw new Exception(messageSource.getMessage("data.null.file", null, Locale.KOREA));
			}
			if(!FileUtil.isExcel(file.getOriginalFilename())) {
				throw new Exception("엑셀 파일만 업로드 가능합니다");
			}
			
			String extension = FileUtil.getExt(file.getOriginalFilename());
			
			Map fileMap = FileUtil.fileUpload(file);
			String file_dtl_path= String.valueOf(fileMap.get("realFullPath"));
			
			Workbook workbook = null;
			fis = new FileInputStream(file_dtl_path);
						
		    if (extension.toLowerCase().equals("xlsx")) {
		    	
		      workbook = new XSSFWorkbook(fis);
		    } else if (extension.toLowerCase().equals("xls")) {
		      workbook = new HSSFWorkbook(fis);
		    }
			
		    Sheet sheet = workbook.getSheetAt(0);
		    
		    //System.out.println("sheet 이름 : " + workbook.getSheetName(0));
		    int numOfRows = sheet.getPhysicalNumberOfRows();
		    
		    for(int i=1; i<numOfRows; i++) {
		    	Row row = sheet.getRow(i);
		    	 
		    	if(row.getCell(0) == null || row.getCell(0).getCellType().toString().equals("BLANK")) {	//	build_seq;
		    		msg += ""+ (i+1) + "행의 build_seq 값이 없습니다\r\n";
		    		result = false;
		    		continue;
		    	}
		    			    	
		    	if(row.getCell(1) == null || row.getCell(1).getCellType().toString().equals("BLANK")) {
		    		msg += ""+ (i+1) + "행의 floor_seq 값이 없습니다\r\n";
		    		result = false;
		    		continue;
		    	}
		    	//System.out.println(row.getCell(2).getCellType());
		    	
		    	if(row.getCell(2) == null || row.getCell(2).getCellType().toString().equals("BLANK")) {
		    		msg += ""+ (i+1) + "행의 store_ho 값이 없습니다\r\n";
		    		result = false;
		    		continue;
		    	}
		    			    	 
		    	Integer build_seq = (int) row.getCell(0).getNumericCellValue();
		    	Integer floor_seq = (int) row.getCell(1).getNumericCellValue();;
		    	String  store_ho = row.getCell(2).getCellType().toString().equals("STRING") ? row.getCell(2).getStringCellValue() : String.valueOf( (int) row.getCell(2).getNumericCellValue());    
		    	
		    	AdminMgtBuildVO ownerVO = new AdminMgtBuildVO();
		    	ownerVO.setBuild_seq(build_seq);
		    	ownerVO.setFloor_seq(floor_seq);
		    	ownerVO.setStore_ho(store_ho);
		    	ownerVO.setMod_id(SessionUtil.getAdminId());
		    	ownerList.add(ownerVO);
		    }
		    
		    if(result) {
		    	adminMgtBuildService.insertOwner(ownerList);
		    	msg = "저장에 성공하였습니다\r\n";
		    	msg += ownerList.size() + " 행을 저장하였습니다";
		    }else {
		    	msg = "저장에 실패하였습니다\r\n" + msg;
		    }
		    
		
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(commonFileVO.toString());
			result = false;
			msg = e.getMessage();
			
		}finally {
			if(fis != null) {
				fis.close();
			}
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
