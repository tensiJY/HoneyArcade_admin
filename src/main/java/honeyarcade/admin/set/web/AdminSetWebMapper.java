package honeyarcade.admin.set.web;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminSetWebMapper {
	
	/**
	 * 점주용 홈페이지 - 회사소개 이미지 가져오기(이미지)
	 * @return
	 * @throws Exception
	 */
	public AdminSetWebVO getHoneyArcadeImg() throws Exception;
	
	/**
	 * 점주용 홈페이지 - 유료 상품 헤택 소개 데이터 가져오기(이미지)
	 * @return
	 * @throws Exception
	 */
	public AdminSetWebVO getBenefitImg() throws Exception;

	/**
	 * 점주용 홈페이지 - 회사소개 이미지 삭제(del_flag = 'Y')
	 * @param adminSetWebVO
	 * @throws Exception
	 */
	public void delHoneyArcadeImg(AdminSetWebVO adminSetWebVO) throws Exception;
	
	/**
	 * 점주용 홈페이지 - 회사소개 이미지 저장(이미지)
	 * @param adminSetWebVO
	 * @throws Exception
	 */
	public void insertHoneyArcadeImg(AdminSetWebVO adminSetWebVO) throws Exception;
	
	/**
	 * 점주용 홈페이지 - 유료 상품 혜택 소개 이미지 삭제 (del_flag = 'Y')
	 * @param adminSetWebVO
	 * @throws Exception
	 */
	public void delBenefitImg(AdminSetWebVO adminSetWebVO) throws Exception;
		
	/**
	 * 점주용 홈페이지 - 유료 상품 혜택 소개 이미지 저장
	 * @param adminSetWebVO
	 * @throws Exception
	 */
	public void insertBenefit(AdminSetWebVO adminSetWebVO) throws Exception;
	
}
