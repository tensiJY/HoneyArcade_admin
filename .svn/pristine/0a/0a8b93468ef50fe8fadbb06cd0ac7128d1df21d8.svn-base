package honeyarcade.admin.set.web;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminSetWebMapper {

	public AdminSetWebVO getHomepageData();

	//	파일 시퀀스 생성
	public int insertFileSeq(AdminSetWebVO adminSetWebVO) throws Exception;
	
	//	파일 시퀀스 삭제
	public void delFile(AdminSetWebVO adminSetWebVO) throws Exception;
	
	//	파일 디테일 시퀀스 삭제
	public void delDtlFile(AdminSetWebVO adminSetWebVO) throws Exception;

	//	파일 디테일 인서트
	public void insertFileDtl(AdminSetWebVO adminSetWebVO) throws Exception;
	
	//	점주용 홈페이지 - 회사소개 이미지 저장(이미지)
	public void insertHoneyArcadeImg(AdminSetWebVO adminSetWebVO) throws Exception;

	//	점주용 홈페이지 - 회사소개 이미지 가져오기(이미지)
	public AdminSetWebVO getHoneyArcadeImg() throws Exception;
	
	//	점주용 홈페이지 - 회사소개 이미지 삭제(del_flag = 'Y')
	public void delHoneyArcadeImg(AdminSetWebVO adminSetWebVO) throws Exception;

	//	점주용 홈페이지 - 유료 상품 헤택 소개 데이터 가져오기(이미지)
	public AdminSetWebVO getBenefitImg() throws Exception;
	
	//	점주용 홈페이지 - 유료 상품 혜택 소개 이미지 저장
	public void insertBenefit(AdminSetWebVO adminSetWebVO) throws Exception;

	//	점주용 홈페이지 - 유료 상품 혜택 소개 이미지 삭제 (del_flag = 'Y')
	public void delBenefitImg(AdminSetWebVO adminSetWebVO) throws Exception;

	





	
}
