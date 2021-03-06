package honeyarcade.admin.set.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import honeyarcade.admin.common.file.CommonFileService;
import honeyarcade.admin.common.file.CommonFileVO;
import honeyarcade.admin.util.FileUtil;
import honeyarcade.admin.util.SessionUtil;

@Service
public class AdminSetWebService {

	@Autowired
	private AdminSetWebMapper adminSetWebMapper;
	
    @Autowired
    private CommonFileService commonFileService;
	
	/**
	 * 회사소개 이미지 가져오기
	 * @return
	 * @throws Exception
	 */
	public AdminSetWebVO getHoneyArcadeImg() throws Exception { 
		return adminSetWebMapper.getHoneyArcadeImg(); 
	}
	 
	/**
	 * 혜택사항 이미지 가져오기
	 * @return
	 * @throws Exception
	 */
	public AdminSetWebVO getBenefitImg() throws Exception {
		return adminSetWebMapper.getBenefitImg();
	}
	
	/**
	 * 점주 웹 이미지 저장 프로세스
	 * @param dataMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void writeProc(HashMap dataMap) throws Exception {
		String admin_id = SessionUtil.getAdminId();
		
		CommonFileVO fileVO = new CommonFileVO();
		fileVO.setAdmin_id(admin_id);
		Integer file_seq = commonFileService.insertFileSeq(fileVO);
		
		fileVO.setFile_seq(file_seq);
		fileVO.setFile_dtl_seq(0);
		fileVO.setFile_dtl_desc(	  	String.valueOf(	dataMap.get("file_dtl_desc")));
		fileVO.setFile_dtl_ext(  	  	String.valueOf(	dataMap.get("file_dtl_ext")));
		fileVO.setFile_dtl_origin(  	String.valueOf(	dataMap.get("file_dtl_origin")));
		fileVO.setFile_dtl_path(	 	String.valueOf(	dataMap.get("file_dtl_path")));
		fileVO.setFile_dtl_url_path( 	String.valueOf(	dataMap.get("file_dtl_url_path")));
		fileVO.setFile_type(		  	Integer.parseInt(String.valueOf(dataMap.get("file_type"))));
		
		commonFileService.insertFileDtl(fileVO);
		
		Integer type = Integer.parseInt(String.valueOf(dataMap.get("type")));
		boolean old_has_file = (boolean) dataMap.get("old_has_file");
		
		if(type == 1) {
			//	회사소개
			if(old_has_file) {
				//	파일 있는 경우 시퀀스 삭제
				//	1. 파일 삭제
				HashMap oldFileMap = (HashMap) dataMap.get("old_file");
				Integer old_file_seq = Integer.parseInt(String.valueOf(oldFileMap.get("file_seq")));
				CommonFileVO delFileVO = new CommonFileVO();
				delFileVO.setFile_seq(old_file_seq);
				delFileVO.setAdmin_id(admin_id);
				commonFileService.delFileSeq(delFileVO);
				//	2. 회사소개 삭제
				Integer img_seq = Integer.parseInt(String.valueOf(oldFileMap.get("img_seq")));
				AdminSetWebVO delVO = new AdminSetWebVO();
				delVO.setHoneyarcade_seq(img_seq);
				delVO.setAdmin_id(admin_id);
				adminSetWebMapper.delHoneyArcadeImg(delVO);
			}
			//	이미지 저장
			AdminSetWebVO insertVO = new AdminSetWebVO();
			insertVO.setAdmin_id(admin_id);
			insertVO.setFile_seq(file_seq);
			adminSetWebMapper.insertHoneyArcadeImg(insertVO);
		}else {
			//	유료상품 혜택 소개
			if(old_has_file) {
				//	파일 있는 경우 시퀀스 삭제
				//	1. 파일 삭제
				HashMap oldFileMap = (HashMap) dataMap.get("old_file");
				Integer old_file_seq = Integer.parseInt(String.valueOf(oldFileMap.get("file_seq")));
				CommonFileVO delFileVO = new CommonFileVO();
				delFileVO.setFile_seq(old_file_seq);
				delFileVO.setAdmin_id(admin_id);
				commonFileService.delFileSeq(delFileVO);
				//	2. 상품 삭제
				Integer img_seq = Integer.parseInt(String.valueOf(oldFileMap.get("img_seq")));
				AdminSetWebVO delVO = new AdminSetWebVO();
				delVO.setBenefit_seq(img_seq);
				delVO.setAdmin_id(admin_id);
				adminSetWebMapper.delBenefitImg(delVO);
			}
			//	이미지 저장
			AdminSetWebVO insertVO = new AdminSetWebVO();
			insertVO.setAdmin_id(admin_id);
			insertVO.setFile_seq(file_seq);
			adminSetWebMapper.insertBenefit(insertVO);
		}
	}

}
