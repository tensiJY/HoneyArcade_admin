package honeyarcade.admin.common.file;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import honeyarcade.admin.util.FileUtil;
import honeyarcade.admin.util.SessionUtil;

@Service
public class CommonFileService {

	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private CommonFileMapper commonFileMapper;

    //	파일 시퀀스 생성
	public Integer insertFileSeq(CommonFileVO vo) throws Exception {
		return commonFileMapper.insertFileSeq(vo);
	}

	//	디테일 저장
	public void insertFileDtl(CommonFileVO vo) throws Exception {
		commonFileMapper.insertFileDtl(vo);
	}

	//	파일 시퀀스 삭제(파일 삭제
	public void delFileSeq(CommonFileVO vo) throws Exception{
		commonFileMapper.delFileSeq(vo);
	}
	
	//	파일 업로드 프로세서 : 단일
    /**
     * 타입, multipartFile 필요
     * @param commonFileVO
     * @param req
     * @return
     * @throws Exception
     */
    /*
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public CommonFileVO fileUploadProc(CommonFileVO commonFileVO, HttpServletRequest req) throws Exception {
		
		MultipartFile file = commonFileVO.getFile();
		
		//	파일 확인
		if(file.isEmpty() == true) {
			throw new Exception(messageSource.getMessage("data.null.file", null, Locale.KOREA));
		}
		
		if(commonFileVO.getPre_url() == "" || commonFileVO.getPre_url() == null) {
			String uri = req.getRequestURI();				//	/admin/set/web/list
			String url = req.getRequestURL().toString();	//	http://localhost:10001/admin/set/web/list
			url = url.replaceAll(uri, "");					//	http://localhost:10001
			
			commonFileVO.setPre_url(url);
		}
		
						
		//	파일 저장
		Map resultMap = FileUtil.fileUpload(file);
		
		commonFileVO.setAdmin_id(SessionUtil.getAdminId());
		
		Integer file_seq =  commonFileMapper.insertFileSeq(commonFileVO);
		
		commonFileVO.setFile_seq(file_seq);													//	파일 시퀀스
		commonFileVO.setFile_dtl_desc(String.valueOf(resultMap.get("descFileName")));		//	변경 후 이름
		commonFileVO.setFile_dtl_origin(String.valueOf(resultMap.get("originalFileName")));	//	변경 전 이름
		commonFileVO.setFile_dtl_path(String.valueOf(resultMap.get("realFullPath")));		//	파일 풀 패스
		commonFileVO.setFile_dtl_ext(String.valueOf(resultMap.get("ext")));					//	파일 확장자
		
		//	urlPath	: /upload/2021/08/19/1c8a6d94a15a4940a5038753d6599c68.jpg
		
		String url_path = commonFileVO.getPre_url()+String.valueOf(resultMap.get("urlPath"));
		commonFileVO.setFile_dtl_url_path(url_path);											//	파일 URL PATH : http://localhost:10001/upload/2021/08/19/bc6fc5956e094c8ba53d50cd4690ed8e.jpg
		
		Integer file_dtl_seq = commonFileMapper.insertFileDtl(commonFileVO);
		
		commonFileVO.setFile_dtl_seq(file_dtl_seq);
		 
		
		
		return commonFileVO;
	}
	*/
}
