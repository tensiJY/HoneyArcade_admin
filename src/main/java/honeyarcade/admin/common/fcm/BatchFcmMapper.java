package honeyarcade.admin.common.fcm;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchFcmMapper {

	/**
	 * 회원 사용자 조회 : 가입자
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BatchFcmVO> getUserList() throws Exception;

	/**
	 * 푸시 목록 조회
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BatchFcmVO> getPushList() throws Exception;

	/**
	 * 푸시 완료 업데이트
	 * @param batchFcmVO
	 * @throws Exception
	 */
	public void updatePush(BatchFcmVO batchFcmVO) throws Exception;

}
