package honeyarcade.admin.ad.build;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminAdBuildMapper {

	public int getTotalCount(AdminAdBuildVO adminAdBuildVO) throws Exception;

	public List<AdminAdBuildVO> getBuildAdList(AdminAdBuildVO adminAdBuildVO) throws Exception;

	public void delBuildBanner(AdminAdBuildVO vo) throws Exception;

	public void delBuildBannerOfExt(AdminAdBuildVO vo) throws Exception;

	public AdminAdBuildVO getBuildBannerOfExt(AdminAdBuildVO adminAdBuildVO) throws Exception;
	
	/**
	 * 건물 광고 - 외부 점포 배너 광고 저장
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Integer insertExtBanner(AdminAdBuildVO vo) throws Exception;

	/**
	 * 건물 광고 - 외부 점포 배너 광고와 건물을 연결 설정 저장
	 * @param dongExtBannerVO
	 * @throws Exception
	 */
	public void insertBuildExtBanner(AdminAdBuildVO dongExtBannerVO) throws Exception;

	/**
	 * 건물광고 - 외부 점포 : 외부점포 배너에 포함된 건물 목록 가져오기
	 * @param banner_seq
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdBuildVO> getBudilExtBanner(Integer banner_seq) throws Exception;

	/**
	 * 건물광고 - 외부 점포 삭제 TB_DONG_EXT_BANNER
	 * @param banner_seq
	 * @throws Exception
	 */
	public void delBuildExtBanner(Integer banner_seq) throws Exception;

	/**
	 * 건물광고 - 외부 점포 편집 저장
	 * @param adminAdBuildVO
	 * @throws Exception
	 */
	public void updateExtBanner(AdminAdBuildVO adminAdBuildVO)throws Exception;

	/**
	 * 건물광고 - 제휴 점포 등록
	 * @param adminAdBuildVO
	 */
	public void insertBanner(AdminAdBuildVO adminAdBuildVO) throws Exception;
	
	/**
	 * 건물광고 - 제휴 점포 등록 - 배너에 설정되어있는 건물
	 * @param buildVO
	 * @throws Exception
	 */
	public void insertBuildBanner(AdminAdBuildVO buildVO) throws Exception;

	/**
	 * 건물 광고 - 제휴점포 - 배너 정보 가져오기
	 * @param adminAdBuildVO
	 * @return
	 * @throws Exception
	 */
	public AdminAdBuildVO getBanner(AdminAdBuildVO adminAdBuildVO) throws Exception;

	/**
	 * 건물 광고 - 제휴점포 - 배너에 설정되어 있는 건물
	 * @param adminAdBuildVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdBuildVO> getBuildBanner(AdminAdBuildVO adminAdBuildVO) throws Exception;

	/**
	 * 건물 광고 - 제휴점포 - 배너 정보 업데이트
	 * @param adminAdBuildVO
	 * @throws Exception
	 */
	public void updateBanner(AdminAdBuildVO adminAdBuildVO) throws Exception;

	/**
	 * 건물 광고 - 제휴점포 - 배너에 설정되어있는 건물 삭제
	 * @param adminAdBuildVO
	 * @throws Exception
	 */
	public void deleteBuildBanner(AdminAdBuildVO adminAdBuildVO) throws Exception;
	
}
