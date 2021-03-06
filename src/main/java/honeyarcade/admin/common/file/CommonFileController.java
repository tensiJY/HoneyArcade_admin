package honeyarcade.admin.common.file;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
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
	
	/**
	 * 파일 업로드
	 * @param commonFileVO
	 * @param req
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 파일 다운로드 :  file_seq
	 * @param commonFileVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/download")
	@ResponseBody
	public ResponseEntity<Resource> download (@ModelAttribute CommonFileVO commonFileVO, @RequestHeader("User-Agent")String agent) throws Exception{
		log.info(commonFileVO.toString());
		CommonFileVO cvo = commonFileService.getFileDtlInfo(commonFileVO);
		File target = new File(cvo.getFile_dtl_path());
		HttpHeaders header = new HttpHeaders();
		Resource rs = null;
		if(target.exists()) {
			try {
				String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
				
				if(mimeType == null) {
					mimeType ="octet-stream";
				}
				rs = new UrlResource(target.toURI());
				String fileName =  cvo.getFile_dtl_origin();
				if(agent.contains("Trident")) {	// 익스
					fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
				}else if(agent.contains("Edge")) { // Edge
					fileName = URLEncoder.encode(fileName, "UTF-8");
				}else { // 크롬
					fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				}
				
				header.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ fileName +"\"");
				header.setCacheControl("no-cache");
				header.setContentType(MediaType.parseMediaType(mimeType));
				
			}catch(Exception e) {
				log.error("download error");
				log.error("param : " + commonFileVO.toString());
				log.error("result : " + cvo.toString());
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<Resource>(rs, header, HttpStatus.OK);
	}
	

}
