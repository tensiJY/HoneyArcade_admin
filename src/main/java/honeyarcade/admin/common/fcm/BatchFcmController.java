package honeyarcade.admin.common.fcm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 푸쉬 메시지 배치 컴퍼넌트
 * 간격 : 매일 1시간 마다
 * 
 */
@Component
@Slf4j
public class BatchFcmController {
	//private String cron = "0 0/2 * * * *";
	
	private ThreadPoolTaskScheduler scheduler;
	private String cron = "0 0 0/1 * * *";
	
	@Autowired
	private BatchFcmService batchFcmService;
	
	@Autowired
	private CommonFcmService commonFcmService;

	/**
	 * 스케줄러 실행
	 */
	public void startScheduler() {
		scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize(); 
		scheduler.schedule(getRunnable(),getTrigger());
	}

	/**
	 * 스케줄러 중단
	 */
	private void stopScheduler() {
		scheduler.shutdown();
	}
	
	/**
	 * 스케줄러 시간 변경
	 * @param cron
	 */
	public void changeCronSet(String cron) {
		this.cron = cron;
	}

	private Runnable getRunnable() {
		return () -> {
			ArrayList<HashMap<String, Integer>> resultList = new ArrayList<HashMap<String, Integer>>();
			
			try {
				log.info("===== 배치 시작 : " + LocalDateTime.now().toString() + " =====");
				
				ArrayList<BatchFcmVO> pushList = batchFcmService.getPushList();
				ArrayList<BatchFcmVO> userList = batchFcmService.getUserList();
				
				for(int i=0; i<pushList.size(); i++) {
					BatchFcmVO vo =  pushList.get(i);
					Integer push_type = vo.getPush_type();
					HashMap dataMap = new HashMap();
					
					dataMap.put("type", push_type);
					dataMap.put("pushId", vo.getPush_seq());
					if(push_type>0) {
						dataMap.put("buildId", vo.getBuild_seq());
						if(push_type == 5) {
							dataMap.put("storeId", vo.getStore_seq());
						}
					}
					
					HashMap<String, Integer> resultMap = commonFcmService.sendCommonMessage(userList, vo.getPush_title(), vo.getPush_text(), dataMap);
					log.info(resultMap.toString());
					resultList.add(resultMap);
					
				}
				
				if(resultList.size() != 0) {
					for(int i=0; i<resultList.size(); i++) {
						HashMap<String, Integer> resMap = resultList.get(i); 
						log.info("===== 배치 결과 : " + i + " " + resMap.toString() + " =====");
						BatchFcmVO pushVO = new BatchFcmVO();
						pushVO.setPush_seq(resMap.get("push_seq"));
						pushVO.setPush_status(1);
						batchFcmService.updatePush(pushVO);
					}
				}else {
					log.info("===== 배치 결과 : PUSH 알림 데이터가 없습니다  =====");
				}
				
			}catch(Exception e) {
				log.debug("===== 배치 에러 : " + LocalDateTime.now().toString() + " =====");
				log.debug(e.getMessage());
			}finally {
				log.info("===== 배치 종료 : " + LocalDateTime.now().toString() + " =====");
			}
			
			
		};
	}

	/**
	 * 크론 트리거 생성
	 * @return
	 */
	private Trigger getTrigger() {
		// cronSetting
		return new CronTrigger(cron);
	}

	/**
	 * 서버 가동시 실행
	 */
	@PostConstruct
	public void init() {
		startScheduler();
	}

	/**
	 * 스케줄러 중단
	 */
	@PreDestroy
	public void destroy() {
		stopScheduler();
	}
}
