package honeyarcade.admin.member.event;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.util.SessionUtil;

@Service
public class AdminMemberEventService {

	@Autowired
	private AdminMemberEventMapper adminMemberEventMapper;
	
    @Autowired
    private MessageSource messageSource;

	/**
	 * 프로모션 전체 갯수 가져오기
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount(AdminMemberEventVO adminMemberEventVO) throws Exception{
		return adminMemberEventMapper.getTotalCount(adminMemberEventVO);
	}

	/**
	 * 프로모션 목록 가져오기
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberEventVO> getEventList(AdminMemberEventVO adminMemberEventVO) throws Exception {
		return adminMemberEventMapper.getEventList(adminMemberEventVO);
	}

	/**
	 * 유료 서비스 : 프로모션 데이터 가져오기
	 * @param adminMemberEventVO
	 * @return
	 * @throws Exception
	 */
	public AdminMemberEventVO getData(AdminMemberEventVO adminMemberEventVO) throws Exception {
		return adminMemberEventMapper.getData(adminMemberEventVO);
	}

	/**
	 * 유료 서비스 : 프로모션 삭제
	 * @param adminMemberEventVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void delete(AdminMemberEventVO adminMemberEventVO) throws Exception {
		adminMemberEventMapper.delete(adminMemberEventVO);		
	}

	/**
	 * 유료 서비스 : 프로모션 저장 프로세스
	 * @param dataMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void saveProc(HashMap dataMap) throws Exception {
		checkData(dataMap);
		
		String admin_id 	= SessionUtil.getAdminId();
		String event_title	= String.valueOf(dataMap.get("event_title"));
		String event_text	= String.valueOf(dataMap.get("event_text"));
		String event_price	= String.valueOf(dataMap.get("event_price"));
		String event_sort	= String.valueOf(dataMap.get("event_sort"));
		
		AdminMemberEventVO adminMemberEventVO = new AdminMemberEventVO();
		adminMemberEventVO.setAdmin_id(admin_id);
		adminMemberEventVO.setEvent_title(event_title);
		adminMemberEventVO.setEvent_text(event_text);
		adminMemberEventVO.setEvent_price(Integer.parseInt(event_price));
		adminMemberEventVO.setEvent_sort(Integer.parseInt(event_sort));
		
		Integer event_seq = adminMemberEventMapper.insertEvent(adminMemberEventVO);
		
		List event_dtl		= (List) dataMap.get("event_dtl");
	
		for(int i=0; i<event_dtl.size(); i++) {
			HashMap dtlMap = (HashMap) event_dtl.get(i);
			Integer event_dtl_type	= Integer.parseInt(String.valueOf(dtlMap.get("event_dtl_type")));
			Integer event_dtl_day	= Integer.parseInt(String.valueOf(dtlMap.get("event_dtl_day")));
			
			AdminMemberEventVO dtlVO = new AdminMemberEventVO();
			dtlVO.setEvent_seq(event_seq);
			dtlVO.setEvent_dtl_type(event_dtl_type);
			dtlVO.setEvent_dtl_day(event_dtl_day);
			
			adminMemberEventMapper.insertEventDtl(dtlVO);
		}
	}

	private void checkData(HashMap dataMap) throws Exception {
		
		String event_title	= String.valueOf(dataMap.get("event_title"));
		String event_text	= String.valueOf(dataMap.get("event_text"));
		String event_price	= String.valueOf(dataMap.get("event_price"));
		String event_sort	= String.valueOf(dataMap.get("event_sort"));
		
		List event_dtl		= (List) dataMap.get("event_dtl");
		
		if("".equals(event_title)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(event_text)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(event_price)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(event_sort)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if(event_dtl.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
	}
	
	/**
	 *  유료 서비스 : 프로모션 수정 프로세스
	 * @param dataMap
	 * @throws Exception
	 */
	public void modProc(HashMap dataMap) throws Exception {
		checkData(dataMap);
		
		String admin_id 	= SessionUtil.getAdminId();
		String event_title	= String.valueOf(dataMap.get("event_title"));
		String event_text	= String.valueOf(dataMap.get("event_text"));
		String event_price	= String.valueOf(dataMap.get("event_price"));
		String event_sort	= String.valueOf(dataMap.get("event_sort"));
		Integer event_seq	= Integer.parseInt(String.valueOf(dataMap.get("event_seq")));
		
		AdminMemberEventVO adminMemberEventVO = new AdminMemberEventVO();
		adminMemberEventVO.setAdmin_id(admin_id);
		adminMemberEventVO.setEvent_title(event_title);
		adminMemberEventVO.setEvent_text(event_text);
		adminMemberEventVO.setEvent_price(Integer.parseInt(event_price));
		adminMemberEventVO.setEvent_sort(Integer.parseInt(event_sort));
		adminMemberEventVO.setEvent_seq(event_seq);
		
		//	헤더 테이블 업데이트
		adminMemberEventMapper.updateEvent(adminMemberEventVO);
		
		//	상세 테이블 삭제
		adminMemberEventMapper.deleteEventDtl(adminMemberEventVO);
		
		//	상세 테이블 등록
		List event_dtl		= (List) dataMap.get("event_dtl");
		for(int i=0; i<event_dtl.size(); i++) {
			HashMap dtlMap = (HashMap) event_dtl.get(i);
			Integer event_dtl_type	= Integer.parseInt(String.valueOf(dtlMap.get("event_dtl_type")));
			Integer event_dtl_day	= Integer.parseInt(String.valueOf(dtlMap.get("event_dtl_day")));
			
			AdminMemberEventVO dtlVO = new AdminMemberEventVO();
			dtlVO.setEvent_seq(event_seq);
			dtlVO.setEvent_dtl_type(event_dtl_type);
			dtlVO.setEvent_dtl_day(event_dtl_day);
			
			adminMemberEventMapper.insertEventDtl(dtlVO);
		}
	}
}
