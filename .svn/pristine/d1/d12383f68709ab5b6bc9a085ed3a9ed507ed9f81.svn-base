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

	public AdminAdAreaVO getAreaBannerOfExt(AdminAdAreaVO adminAdAreaVO) throws Exception;
	
	//	지역광고 - 외부 점포 인서트
	public Integer insertExtBanner(AdminAdAreaVO vo) throws Exception;

	//	TB_DONG_EXT_BANNER 인서트
	public void insertDongExtBanner(AdminAdAreaVO dongExtBannerVO) throws Exception;

	//	지역광고 - 외부 점포 조회 (TB_DONG_EXT_BANNER) 
	public List<AdminAdAreaVO> getDongExtBanner(Integer banner_seq) throws Exception;

	//	TB_DONG_EXT_BANNER 삭제
	public void delDongExtBanner(Integer banner_seq) throws Exception;

	//	지역 광고 - 외부 점포 수정
	public void updateExtBanner(AdminAdAreaVO adminAdAreaVO)throws Exception;
	
}
