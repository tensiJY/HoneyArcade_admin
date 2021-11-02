package honeyarcade.admin.im.push;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminImPushMapper {

	/**
	 * 푸쉬 알림 총 갯수 구하기
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount() throws Exception;

	/**
	 * 푸쉬 알림 목록 구하기
	 * @param adminImPushVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminImPushVO> getPushList(AdminImPushVO adminImPushVO) throws Exception;

	/**
	 * 푸쉬 알림 취소
	 * @param adminImPushVO
	 * @throws Exception
	 */
	public void cancelProc(AdminImPushVO adminImPushVO) throws Exception;

	/**
	 * 푸쉬 알림 데이터 가져오기
	 * @param push_seq
	 * @return
	 * @throws Exception
	 */
	public AdminImPushVO getData(Integer push_seq) throws Exception;

	/**
	 * 푸쉬 알림 데이터 저장
	 * @param adminImPushVO
	 * @return 
	 * @throws Exception
	 */
	public Integer saveProc(AdminImPushVO adminImPushVO) throws Exception;

}
