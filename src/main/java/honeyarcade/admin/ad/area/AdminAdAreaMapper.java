package honeyarcade.admin.ad.area;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminAdAreaMapper {

	public int getTotalCount(AdminAdAreaVO adminAdAreaVO) throws Exception;

	public List<AdminAdAreaVO> getAreaAdList(AdminAdAreaVO adminAdAreaVO) throws Exception;

	public void delAreaBanner(AdminAdAreaVO vo) throws Exception;

	public void delAreaBannerOfExt(AdminAdAreaVO vo) throws Exception;

	public AdminAdAreaVO getAreaBanner(AdminAdAreaVO adminAdAreaVO) throws Exception;

	/**
	 * 지역광고 - 외부 점포 배너 정보 
	 * @param adminAdAreaVO
	 * @return
	 * @throws Exception
	 */
	public AdminAdAreaVO getAreaBannerOfExt(AdminAdAreaVO adminAdAreaVO) throws Exception;
	
	/**
	 * 지역광고 - 외부 점포 인서트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Integer insertExtBanner(AdminAdAreaVO vo) throws Exception;

	/**
	 * TB_DONG_EXT_BANNER 인서트
	 * @param dongExtBannerVO
	 * @throws Exception
	 */
	public void insertDongExtBanner(AdminAdAreaVO dongExtBannerVO) throws Exception;

	/**
	 * 지역광고 - 외부 점포 조회 (TB_DONG_EXT_BANNER) 
	 * @param banner_seq
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdAreaVO> getDongExtBanner(Integer banner_seq) throws Exception;

	/**
	 * TB_DONG_EXT_BANNER 삭제
	 * @param banner_seq
	 * @throws Exception
	 */
	public void delDongExtBanner(Integer banner_seq) throws Exception;

	/**
	 * 지역광고 - 외부 점포 수정
	 * @param adminAdAreaVO
	 * @throws Exception
	 */
	public void updateExtBanner(AdminAdAreaVO adminAdAreaVO) throws Exception;

	/**
	 * 지역광고 - 제휴 점포 배너 광고 등록
	 * @param adminAdAreaVO
	 * @throws Exception
	 */
	public void insertBanner(AdminAdAreaVO adminAdAreaVO) throws Exception;

	/**
	 * 지역광고 - 제휴 점포 - 배너에 설정되어 있는 동 등록 
	 * @param adminAdAreaVO
	 * @throws Exception
	 */
	public void insertDongBanner(AdminAdAreaVO adminAdAreaVO) throws Exception;

	/**
	 * 지역광고 - 제휴 점포 - 배너에 설정되어 있는 동 조회
	 * @param adminAdAreaVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdAreaVO> getDongBanner(AdminAdAreaVO adminAdAreaVO) throws Exception;

	/**
	 * 지역광고 - 제휴 점포 - 배너에 설정되어 있는 동 삭제
	 * @param adminAdAreaVO
	 * @throws Exception
	 */
	public void deleteDongBanner(AdminAdAreaVO adminAdAreaVO) throws Exception;

	/**
	 * 지역광고 - 제휴 점포 - 배너 업데이트
	 * @param adminAdAreaVO
	 * @throws Exception
	 */
	public void updateBanner(AdminAdAreaVO adminAdAreaVO) throws Exception;
	
}
