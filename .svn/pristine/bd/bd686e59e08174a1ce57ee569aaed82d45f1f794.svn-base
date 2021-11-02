package honeyarcade.admin.common.fcm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchFcmService {

	@Autowired
	private BatchFcmMapper batchFcmMapper;

	/**
	 * 회원 사용자 조회 : 가입자
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BatchFcmVO> getUserList() throws Exception {
		return batchFcmMapper.getUserList();
	}

	/**
	 * 푸시 목록 조회
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BatchFcmVO> getPushList() throws Exception {
		return batchFcmMapper.getPushList();
	}

	/**
	 * 푸시 완료 업데이트
	 * @param batchFcmVO
	 * @throws Exception
	 */
	public void updatePush(BatchFcmVO batchFcmVO) throws Exception {
		// TODO Auto-generated method stub
		batchFcmMapper.updatePush(batchFcmVO);
	}
	
	
	
}
