package honeyarcade.admin.im.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.common.fcm.BatchFcmService;
import honeyarcade.admin.common.fcm.BatchFcmVO;
import honeyarcade.admin.common.fcm.CommonFcmService;
import honeyarcade.admin.util.SessionUtil;
import honeyarcade.admin.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminImPushService {
	
	@Autowired
	private AdminImPushMapper adminImPushMapper;
	
	@Autowired
	private CommonFcmService commonFcmService;
	
	@Autowired
	private BatchFcmService batchFcmService;

	/**
	 * 푸쉬 알림 총 갯수 구하기
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount() throws Exception{
		return adminImPushMapper.getTotalCount();
	}

	/**
	 * 푸쉬 알림 목록 구하기
	 * @param adminImPushVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminImPushVO> getPushList(AdminImPushVO adminImPushVO) throws Exception {
		// TODO Auto-generated method stub
		return adminImPushMapper.getPushList(adminImPushVO);
	}

	/**
	 * 푸쉬 알림 취소 프로세스
	 * @param paramMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void cancelProc(Map paramMap) throws Exception {
		if(paramMap.isEmpty()) {
			throw new Exception("처리하는데 문제가 생겼습니다\r\n데이터가 없습니다");
		}
		
		if(!paramMap.containsKey("push_seq")) {
			throw new Exception("처리하는데 문제가 생겼습니다\r\n데이터가 없습니다");
		}
		
		String pushSeq = String.valueOf(paramMap.get("push_seq"));
		if("".equals(pushSeq)) {
			throw new Exception("처리하는데 문제가 생겼습니다\r\n데이터가 없습니다");
		}
		
		Integer push_seq = Integer.parseInt(pushSeq);
		//	취소 프로세스 작성
		AdminImPushVO vo = new AdminImPushVO();
		vo.setPush_seq(push_seq);
		vo.setMod_id(SessionUtil.getAdminId());
		adminImPushMapper.cancelProc(vo);
	}

	/**
	 * 푸쉬 알림 저장 프로세스
	 * @param paramMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void savelProc(Map paramMap) throws Exception {
		if(paramMap.isEmpty()) {
			throw new Exception("처리하는데 문제가 생겼습니다\r\n데이터가 없습니다");
		}
				
		String	push_text	= String.valueOf(paramMap.get("push_text"));
		String	push_type	= String.valueOf(paramMap.get("push_type"));
		String	build_seq	= String.valueOf(paramMap.get("build_seq"));
		String	build_name	= String.valueOf(paramMap.get("build_name"));
		String	store_seq	= String.valueOf(paramMap.get("store_seq"));
		String	store_name	= String.valueOf(paramMap.get("store_name"));
		String	push_rez_dt	= String.valueOf(paramMap.get("psuh_rez_dt"));
		String	push_rez_time	= String.valueOf(paramMap.get("push_rez_time"));
		String	noti_type	= String.valueOf(paramMap.get("noti_type"));
		String 	push_title	= String.valueOf(paramMap.get("push_title"));
		
		if("".equals(push_title)) {
			throw new Exception("알림 제목을을 작성해주세요");
		}
		
		if("".equals(push_type)) {
			throw new Exception("유형을 선택해주세요");
		}
		
		if("".equals(push_text)) {
			throw new Exception("알림내용을 작성해주세요");
		}
		
		if("".equals(noti_type)) {
			throw new Exception("알림 예약 선택을 해주세요");
		}
		
		
		Integer pushType = Integer.parseInt(push_type);
		String andLink = "";
		String iosLink = "";
		
		if(pushType > 0) {
			andLink = build_name;
			iosLink = build_name;
			if(pushType == 5) {
				andLink = andLink + " " + store_name;
				iosLink = iosLink + " " + store_name;
			}
		}
		
		Integer notiType = Integer.parseInt(noti_type);
		String	current = StringUtil.getCurrentTime();
		
		AdminImPushVO adminImPushVO = new AdminImPushVO();
		adminImPushVO.setAdd_id(SessionUtil.getAdminId());
		adminImPushVO.setPush_rez_time(notiType == 1? current.substring(11, 16) : push_rez_time );
		adminImPushVO.setPush_rez_dt(notiType == 1? current.substring(0, 10) : push_rez_dt);
		adminImPushVO.setPush_text(push_text);
		adminImPushVO.setPush_type(pushType);
		adminImPushVO.setPush_ios_link(andLink);
		adminImPushVO.setPush_android_link(iosLink);
		adminImPushVO.setPush_status(notiType);
		adminImPushVO.setStore_seq(store_seq);
		adminImPushVO.setBuild_seq(build_seq);
		adminImPushVO.setPush_title(push_title);
		
		Integer key = adminImPushMapper.saveProc(adminImPushVO);
		
		if(notiType == 1) { //즉시전송
			ArrayList<BatchFcmVO> userList = batchFcmService.getUserList();
			HashMap dataMap = new HashMap();
			
				dataMap.put("type", pushType);
				dataMap.put("pushId", key);
				
			if(pushType>0) {
				dataMap.put("buildId", build_seq);
				if(pushType == 5) {
					dataMap.put("storeId", store_seq);
				}
			}
			HashMap resultMap = commonFcmService.sendCommonMessage(userList, push_title, push_text, dataMap);
			
			log.debug("푸쉬알림 결과 : " + resultMap.toString());
		}
	}
	
	/**
	 * 푸쉬 알림 데이터 가져오기
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public AdminImPushVO getData(Map paramMap) throws Exception {
		String pushSeq = String.valueOf(paramMap.get("push_seq"));
		return adminImPushMapper.getData(Integer.parseInt(pushSeq));
	}
	
}
