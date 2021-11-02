package honeyarcade.admin.member.event;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMemberEventMapper {

	/**
	 * 프로모션 전체 갯수 가져오기
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount(AdminMemberEventVO adminMemberEventVO) throws Exception;

	/**
	 * 프로모션 목록 가져오기
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberEventVO> getEventList(AdminMemberEventVO adminMemberEventVO) throws Exception;

	/**
	 * 유료 서비스 : 프로모션 데이터 가져오기
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	public AdminMemberEventVO getData(AdminMemberEventVO adminMemberEventVO) throws Exception;

	/**
	 * 유료 서비스 : 프로모션 삭제
	 * @param adminMemberEventVO
	 */
	public void delete(AdminMemberEventVO adminMemberEventVO) throws Exception;

	/**
	 * 유료 서비스 : 프로모션 등록(tb_event)
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	public Integer insertEvent(AdminMemberEventVO adminMemberEventVO) throws Exception;

	/**
	 * 유료 서비스 : 프로모션 상세 등록(tb_event_dtl)
	 * @param adminMemberEventVO
	 * @throws Exception
	 */
	public void insertEventDtl(AdminMemberEventVO adminMemberEventVO) throws Exception;

	/**
	 * 유료 서비스 : 프로모션 수정(tb_event)
	 * @param adminMemberEventVO
	 * @throws Exception
	 */
	public void updateEvent(AdminMemberEventVO adminMemberEventVO) throws Exception;

	/**
	 * 유료 서비스 : 프로모션 수정 - 상세 테이블 삭제
	 * @param adminMemberEventVO
	 * @throws Exception
	 */
	public void deleteEventDtl(AdminMemberEventVO adminMemberEventVO) throws Exception;

}
