package honeyarcade.admin.common.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import honeyarcade.admin.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/common/file")
@Slf4j
public class CommonFileController {
	
	@Autowired
	private CommonFileService commonFileService;
	
    @Autowired
    private MessageSource messageSource;
	
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity upload(@ModelAttribute CommonFileVO commonFileVO, HttpServletRequest req) throws Exception{
		Map resultMap = new HashMap();
		List fileList = new ArrayList();	// form name="files" 로 지정
		
		boolean result = true;
		
		
		String uri = req.getRequestURI();				//	/admin/set/web/list
		String url = req.getRequestURL().toString();	//	http://localhost:10001/admin/set/web/list
		url = url.replaceAll(uri, "");					//	http://localhost:10001
					
		String errorMsg = null;
		
		try {
			//	파일 서버에 저장 >> DB 생성은 안함
			
			
			MultipartFile[] files = commonFileVO.getFiles(); 
			
			for(MultipartFile f : files) {
				if(f.isEmpty()) {
					throw new Exception(messageSource.getMessage("data.null.file", null, Locale.KOREA));
				}
			}
			
			//	urlPath	: /upload/2021/08/19/1c8a6d94a15a4940a5038753d6599c68.jpg
			for(int i=0; i<files.length; i++) {
				MultipartFile f = files[i];
				
				Map fileMap = FileUtil.fileUpload(f);
				
				System.out.println(fileMap.toString());
				
				String file_dtl_url_path = url + String.valueOf(fileMap.get("urlPath"));
				String file_dtl_desc = String.valueOf(fileMap.get("descFileName"));		//	변경 후 이름
				String file_dtl_origin = String.valueOf(fileMap.get("originalFileName"));	//	변경 전 이름
				String file_dtl_path= String.valueOf(fileMap.get("realFullPath"));		//	파일 풀 패스
				String file_dtl_ext= String.valueOf(fileMap.get("ext"));					//	파일 확장자
				
				fileMap.clear();
				fileMap.put("file_dtl_url_path", file_dtl_url_path);
				fileMap.put("file_dtl_desc", file_dtl_desc);
				fileMap.put("file_dtl_origin", file_dtl_origin);
				fileMap.put("file_dtl_path", file_dtl_path);
				fileMap.put("file_dtl_ext", file_dtl_ext);
				
				fileList.add(fileMap);
			}
			
			
			
			
		}catch(Exception e) {
			log.debug(e.toString());
			log.debug(commonFileVO.toString());
			
			result = false;
			errorMsg = e.getMessage();
			
			resultMap.put("errorMsg", errorMsg);
			
		}finally {
		
			resultMap.put("result", result);
			resultMap.put("files", fileList);
		}
		
		
		
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/*
	 * @RequestMapping(value="/upload", method=RequestMethod.POST)
	 * 
	 * @ResponseBody public ResponseEntity upload(@ModelAttribute CommonFileVO
	 * commonFileVO, HttpServletRequest req) throws Exception{ Map resultMap = new
	 * HashMap();
	 * 
	 * boolean result = true;
	 * 
	 * 
	 * String uri = req.getRequestURI(); // /admin/set/web/list String url =
	 * req.getRequestURL().toString(); // http://localhost:10001/admin/set/web/list
	 * url = url.replaceAll(uri, ""); // http://localhost:10001
	 * 
	 * String errorMsg = null;
	 * 
	 * try {
	 * 
	 * commonFileVO.setPre_url(url);
	 * 
	 * 
	 * CommonFileVO fileVO = commonFileService.fileUploadProc(commonFileVO, req);
	 * 
	 * 
	 * 
	 * }catch(Exception e) { log.debug(e.toString());
	 * log.debug(commonFileVO.toString());
	 * 
	 * result = false; errorMsg = e.getMessage();
	 * 
	 * resultMap.put("errorMsg", errorMsg); }
	 * 
	 * resultMap.put("result", result);
	 * 
	 * return new ResponseEntity(resultMap, HttpStatus.OK); }
	 */

}
