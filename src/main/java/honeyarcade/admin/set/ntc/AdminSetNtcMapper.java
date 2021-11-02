package honeyarcade.admin.set.ntc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminSetNtcMapper {

	/**
	 * APP 내 공지사항 목록 가져오기
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetNtcVO> getAppList() throws Exception;

	/**
	 * 사장님 페이지 공지사항
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetNtcVO> getNtcList() throws Exception;

	/**
	 * 사장님 페이지 점포회원 전용 공지사항
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetNtcVO> getOwnerNtcList() throws Exception;

	/**
	 * 공지사항 삭제 프로세스
	 * @param adminSetNtcVO
	 * @throws Exception
	 */
	public void ntcDelProc(AdminSetNtcVO adminSetNtcVO) throws Exception;

	/**
	 * 공지사항 내용 가져오기
	 * @param adminSetNtcVO
	 * @return
	 * @throws Exception
	 */
	public AdminSetNtcVO getNtc(AdminSetNtcVO adminSetNtcVO) throws Exception;
	
	/**
	 * 공지사항 수정 프로세스
	 * @param adminSetNtcVO
	 * @throws Exception
	 */
	public void ntcModProc(AdminSetNtcVO adminSetNtcVO) throws Exception;

	/**
	 * 공지사항 저장 프로세스
	 * @param adminSetNtcVO
	 * @throws Exception
	 */
	public void ntcWriteProc(AdminSetNtcVO adminSetNtcVO) throws Exception;

}
