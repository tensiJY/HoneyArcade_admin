package honeyarcade.admin.mgt.store;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMgtStoreMapper {
	
	/**
	 * 점포 관리 - 목록 총 갯수 구하기
	 * @param adminMgtStoreVO
	 * @return
	 * @throws Exception
	 */
	public int getTotalCount(AdminMgtStoreVO adminMgtStoreVO) throws Exception;

	/**
	 * 점포 관리 - 목록 구하기
	 * @param adminMgtStoreVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMgtStoreVO> getStoreList(AdminMgtStoreVO adminMgtStoreVO) throws Exception;

	/**
	 * 점포 상태 업데이트
	 * @param dataList
	 * @throws Exception
	 */
	public void viewProc(List<AdminMgtStoreVO> dataList) throws Exception;
	
	/**
	 * 점주 상태 업데이트
	 * @param dataList
	 * @throws Exception
	 */
	public void updateMember(List<AdminMgtStoreVO> dataList) throws Exception;

	/**
	 * 엑셀다운로드
	 * @param adminMgtStoreVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMgtStoreVO> getStoreExcelList(AdminMgtStoreVO adminMgtStoreVO) throws Exception;

	/**
	 * 점포 테이블(TB_OWNER) 데이터 삭제
	 * @param dataList
	 * @throws Exception
	 */
	public void delProc(List<AdminMgtStoreVO> dataList) throws Exception;

	/**
	 * 점주 테이블(TB_MEMBER) 데이터 삭제
	 * @param dataList
	 * @throws Exception
	 */
	public void delMember(List<AdminMgtStoreVO> dataList) throws Exception;
	

}
