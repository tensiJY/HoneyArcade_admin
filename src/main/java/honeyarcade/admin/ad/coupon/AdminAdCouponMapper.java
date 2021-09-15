package honeyarcade.admin.ad.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminAdCouponMapper {

	public int getTotalCount(AdminAdCouponVO adminAdCouponVO) throws Exception;

	public List<AdminAdCouponVO> getAdCouponList(AdminAdCouponVO adminAdCouponVO) throws Exception;

	public void couponDelProc(AdminAdCouponVO adminAdCouponVO) throws Exception;

	/**
	 * 쿠폰 등록
	 * @param adminAdCouponVO
	 * @throws Exception
	 */
	public void insertCoupon(AdminAdCouponVO adminAdCouponVO) throws Exception;

	/**
	 * 쿠폰 상세정보 등록
	 * @param adminAdCouponVO
	 * @throws Exception
	 */
	public void insertCouponDtl(AdminAdCouponVO adminAdCouponVO) throws Exception;

	/**
	 * 쿠폰 건물 등록
	 * @param buildVO
	 * @throws Exception
	 */
	public void insertBuildCoupon(AdminAdCouponVO buildVO) throws Exception;

	/**
	 * 쿠폰 정보 
	 * @param adminAdCouponVO
	 * @return
	 * @throws Exception
	 */
	public AdminAdCouponVO getAdCoupon(AdminAdCouponVO adminAdCouponVO) throws Exception;

	/**
	 * 쿠폰의 텍스트 정보 가져오기
	 * @param adminAdCouponVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdCouponVO> getCouponTextList(AdminAdCouponVO adminAdCouponVO) throws Exception;

	/**
	 * 쿠폰의 건물설정 정보 가져오기
	 * @param adminAdCouponVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdCouponVO> getCouponBuildList(AdminAdCouponVO adminAdCouponVO) throws Exception;

	/**
	 * 쿠폰 정보 업데이트
	 * @param adminAdCouponVO
	 * @throws Exception
	 */
	public void updateCoupon(AdminAdCouponVO adminAdCouponVO) throws Exception;

	/**
	 * 쿠폰 상세 정보 삭제
	 * @param adminAdCouponVO
	 */
	public void deleteCouponDtl(AdminAdCouponVO adminAdCouponVO) throws Exception;

	/**
	 * 쿠폰 건물 삭제
	 * @param adminAdCouponVO
	 * @throws Exception
	 */
	public void deleteBuildCoupon(AdminAdCouponVO adminAdCouponVO) throws Exception;

	
	
}
