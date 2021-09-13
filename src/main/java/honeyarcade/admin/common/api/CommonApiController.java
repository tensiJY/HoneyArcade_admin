package honeyarcade.admin.common.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/common/api")
@Slf4j
public class CommonApiController {
	
	@Autowired
	private CommonApiService commonApiService;
	
	@RequestMapping(value="/getSido", method=RequestMethod.POST)
	public ResponseEntity getSido() throws Exception{
		Map resultMap = new HashMap();
		
		boolean result = true;			
		
		String errorMsg = null;
		
		
		try {
			
			List<CommonApiVO> sidoList = commonApiService.getSido();
			resultMap.put("sidoList", sidoList);
			
			
			
		}catch(Exception e) {
			log.debug(e.toString());
			
			result = false;
			errorMsg = e.getMessage();
			
			resultMap.put("errorMsg", errorMsg);
		}
		
		resultMap.put("result", result);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	
	@RequestMapping(value="/getSigungu", method=RequestMethod.POST)
	public ResponseEntity getSigungu(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		
		boolean result = true;			
		
		String errorMsg = null;
		
		
		try {
			
			List<CommonApiVO> sigunguList = commonApiService.getSigungu(commonApiVO);
			resultMap.put("sigunguList", sigunguList);
			resultMap.put("sido_seq", commonApiVO.getSido_seq());
			
			
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			
			result = false;
			errorMsg = e.getMessage();
			
			resultMap.put("errorMsg", errorMsg);
		}
		
		resultMap.put("result", result);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getDong", method=RequestMethod.POST)
	public ResponseEntity getDong(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		
		boolean result = true;		
		
		String errorMsg = null;
	
		
		try {
			
			List<CommonApiVO> dongList = commonApiService.getDong(commonApiVO);
			resultMap.put("dongList", dongList);
			resultMap.put("sigungu_seq", commonApiVO.getSigungu_seq());
			
			
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			
			result = false;
			errorMsg = e.getMessage();
			
			resultMap.put("errorMsg", errorMsg);
		}
		
		resultMap.put("result", result);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getBuild", method=RequestMethod.POST)
	public ResponseEntity getBuild(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		
		boolean result = true;		
		String errorMsg = null;
	
		try {
			
			List<CommonApiVO> buildList = commonApiService.getBuild(commonApiVO);
			resultMap.put("buildList", buildList);
			
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			
			result = false;
			errorMsg = e.getMessage();
			
			resultMap.put("errorMsg", errorMsg);
		}
		
		resultMap.put("result", result);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getFloor", method=RequestMethod.POST)
	public ResponseEntity getFloor(@RequestBody CommonApiVO commonApiVO) throws Exception{
		Map resultMap = new HashMap();
		
		boolean result = true;		
		String errorMsg = null;
	
		try {
			log.info(commonApiVO.toString());
			List<CommonApiVO> floorList = commonApiService.getFloor(commonApiVO);
			resultMap.put("floorList", floorList);
			
		}catch(Exception e) {
			log.debug("/getFloor");
			log.debug(e.toString());
			log.debug(commonApiVO.toString());
			
			result = false;
			errorMsg = e.getMessage();
			
			resultMap.put("errorMsg", errorMsg);
		}
		
		resultMap.put("result", result);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
