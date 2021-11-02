package honeyarcade.admin.member.owner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMemberOwnerMapper {

	/**
	 * 회원 관리 : 점주 회원 - 분류별 회원 수 조회
	 * @param adminMemberOwnerVO 
	 * @return
	 * @throws Exception
	 */
	public AdminMemberOwnerVO getOwnerTypeCount(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원 관리 : 점주 회원 - 전체 수 조회
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public int getTotalCount(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원 관리 : 점주 회목 - 목록 조회
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getOwnerList(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원 관리 : 상세 보기 - 회원 정보 가져오기
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public AdminMemberOwnerVO getOwnerInfo(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원 관리 : 상세 보기 - 상품 목록
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getProductList(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원 관리 : 상세 보기 - 오픈 시간
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getOpenDay(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원 관리 : 상세 보기 - 휴게 시간
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getBreakDay(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원관리 : 점주회원 - 승인
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void acceptProc(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원관리 : 상세 보기 - 점포 휴무일
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getDayOffList(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 영업시간 삭제
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void deleteOpen(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 휴게시간 삭제
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void deleteBreak(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 점포 휴무일 삭제
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void deleteDayOff(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 상품 삭제
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void deleteProduct(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 영업시간 등록
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void insertOpen(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 휴게시간 등록
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void insertBreak(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 휴무일 등록
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void insertDayOff(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 상품등록
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void insertProduct(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 점주 업데이트
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void updateOwner(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원관리 : 점주회원 - 점포상태 변경
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void statusProc(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 회원관리 : 점주회원 - 반려
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void rejectProc(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception;

	/**
	 * 점주 상태 변경
	 * @param adminMemberOwnerVO
	 */
	public void updateMemberFlag(AdminMemberOwnerVO adminMemberOwnerVO)throws Exception;

}
