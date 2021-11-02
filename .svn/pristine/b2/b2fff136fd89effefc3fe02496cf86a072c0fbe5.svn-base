package honeyarcade.admin.set.dir;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import honeyarcade.admin.common.api.CommonApiVO;

@Mapper
public interface AdminSetDirMapper {
	/**
	 * 관리자 총 수 구하기
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount() throws Exception;

	/**
	 * 관리자 목록
	 * @param paramVO
	 * @return
	 */
	public List<AdminSetDirVO> getAdminList(AdminSetDirVO paramVO);

	/**
	 * 관리자 아이디 삭제
	 * @param paramVO
	 * @throws Exception
	 */
	public void deleteAdminId(AdminSetDirVO paramVO) throws Exception;

	/**
	 * 전체 동목록 가져오기 type: 1 가나다 순, 2 다나가 순
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetDirVO> getDongList(Integer type) throws Exception;

	/**
	 * 건물 목록 가져오기 가나다 순
	 * @param dong_seq
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetDirVO> getBuildList(Integer dong_seq) throws Exception;

	/**
	 * 아이디가 존재하는지 체크
	 * 존재할경우 0보다 큼
	 * @param admin_id
	 * @return
	 * @throws Exception
	 */
	public Integer isAdminId(String admin_id) throws Exception;

	/**
	 * 관리자 아이디 등록
	 * @param adminVO
	 * @throws Exception
	 */
	public void insertAdminId(AdminSetDirVO adminVO) throws Exception;

	/**
	 * 관리자 접근 관리페이지 - 건물 등록
	 * @param adminVO
	 * @throws Exception
	 */
	public void insertAdminGrade(AdminSetDirVO adminVO) throws Exception;

	/**
	 * 관리자 접근 관리 페이지 삭제
	 * @param paramVO
	 * @throws Exception
	 */
	public void deleteAdminGrade(AdminSetDirVO paramVO) throws Exception;

	/**
	 * 관리자 수정
	 * @param adminVO
	 * @throws Exception
	 */
	public void updateAdminId(AdminSetDirVO adminVO) throws Exception;

}
