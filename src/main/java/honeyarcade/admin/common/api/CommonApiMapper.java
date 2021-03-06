package honeyarcade.admin.common.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonApiMapper {
	
	/**
	 * 시도 조회	admin_grade, admin_id
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getSido(CommonApiVO paramVO) throws Exception;

	/**
	 * 시군구 조회	admin_grade, admin_id
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getSigungu(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 동 조회		admin_grade, admin_id
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getDong(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 건물 조회	admin_grade, admin_id	
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getBuild(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 층 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getFloor(CommonApiVO commonApiVO) throws Exception;

	public List<CommonApiVO> getOwnerOfBuild(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 대 카테고리 가져오기(build_seq) 
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getLcateOfBuild(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 소 카테고리 가져오기(build_seq, lcate_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getMcateOfBuild(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 전체 시도 목록 조회
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllSido() throws Exception;

	/**
	 * 전체 시군구 목록 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllSigungu(CommonApiVO commonApiVO) throws Exception;
	
	/**
	 * 전체 동 목록 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllDong(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 전체 건물 목록 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllBuild(CommonApiVO commonApiVO) throws Exception;

	/**
	 * 가입 승인되어있는 점포 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllStore(CommonApiVO commonApiVO) throws Exception;

}
