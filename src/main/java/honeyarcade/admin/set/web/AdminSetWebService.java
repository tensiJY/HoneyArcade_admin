package honeyarcade.admin.set.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import honeyarcade.admin.util.FileUtil;
import honeyarcade.admin.util.SessionUtil;

@Service
public class AdminSetWebService{
	
	
	@Autowired
	private AdminSetWebMapper adminSetWebMapper;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public AdminSetWebVO getHoneyArcadeImg() throws Exception {
		// TODO Auto-generated method stub
		return adminSetWebMapper.getHoneyArcadeImg();
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public AdminSetWebVO getBenefitImg() throws Exception {
		// TODO Auto-generated method stub
		return adminSetWebMapper.getBenefitImg();
	}
	

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void saveImg(AdminSetWebVO adminSetWebVO) throws Exception {

	
		MultipartFile file = adminSetWebVO.getFile();
		
		//	파일 확인
		if(file.getOriginalFilename() == "") {
			throw new Exception("첨부된 파일이 없습니다");
		}
		
		//	파일 형식 확인
		if(!FileUtil.isImage(file.getOriginalFilename())) {
			throw new Exception("이미지만 등록해주시기 바랍니다");
		}
		
		
		//	파일 저장
		Map resultMap = FileUtil.fileUpload(adminSetWebVO.getFile());
		
		
		adminSetWebVO.setAdmin_id(SessionUtil.getAdminId());									//	작성자 아이디
		
		
		int new_file_seq = adminSetWebMapper.insertFileSeq(adminSetWebVO);
		
		adminSetWebVO.setNew_file_seq(new_file_seq);											//	파일 시퀀스
		adminSetWebVO.setFile_dtl_desc(String.valueOf(resultMap.get("descFileName")));			//	변경 후 이름
		adminSetWebVO.setFile_dtl_origin(String.valueOf(resultMap.get("originalFileName")));	//	변경 전 이름
		adminSetWebVO.setFile_dtl_path(String.valueOf(resultMap.get("realFullPath")));			//	파일 풀 패스
		adminSetWebVO.setFile_dtl_ext(String.valueOf(resultMap.get("ext")));					//	파일 확장자
		
		
		//	adminSetWebVO.setFile_dtl_url_path(String.valueOf(resultMap.get("urlPath")));		//	/upload/2021/08/19/1c8a6d94a15a4940a5038753d6599c68.jpg
		
		String url_path = adminSetWebVO.getPre_url()+String.valueOf(resultMap.get("urlPath"));
		adminSetWebVO.setFile_dtl_url_path(url_path);											//	파일 URL PATH : http://localhost:10001/upload/2021/08/19/bc6fc5956e094c8ba53d50cd4690ed8e.jpg
		
		//	1 관리자->점주 허니아케이드 소개페이지
		//	2 관리자->점주 상품 혜택사항
		if(adminSetWebVO.getFile_type() == 1) {
			//	이전 이미지 파일이 있는 경우
			if(adminSetWebVO.getOld_file_seq() != 0) {
				adminSetWebMapper.delHoneyArcadeImg(adminSetWebVO);		
				adminSetWebMapper.delFile(adminSetWebVO);
				adminSetWebMapper.delDtlFile(adminSetWebVO);
			}
			
			
			adminSetWebMapper.insertHoneyArcadeImg(adminSetWebVO);
			
			
		}else {
			if(adminSetWebVO.getOld_file_seq() != 0) {
				adminSetWebMapper.delBenefitImg(adminSetWebVO);
				adminSetWebMapper.delFile(adminSetWebVO);
				adminSetWebMapper.delDtlFile(adminSetWebVO);
			}
			
			
			adminSetWebMapper.insertBenefit(adminSetWebVO);
		}
		
		//	공통 : 1 관리자->점주 허니아케이드 소개페이지, 2 관리자->점주 상품 혜택사항
		adminSetWebMapper.insertFileDtl(adminSetWebVO);
		
		
	}


	




}
