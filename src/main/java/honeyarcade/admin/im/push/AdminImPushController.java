package honeyarcade.admin.im.push;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/im")

public class AdminImPushController {

	@Autowired
	private AdminImPushService adminImPushService;
	
	@Autowired
	private CommonApiService commonApiService;

	/**
	 * 푸쉬 알림 리스트
	 * @param model
	 * @param listCount
	 * @param nowPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/push/list")
	public String pushList(Model model
			, @RequestParam(value = "listCount", defaultValue = "10")Integer listCount
			, @RequestParam(value = "nowPage", defaultValue= "1")Integer nowPage )throws Exception{
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		
		Integer totalCount = adminImPushService.getTotalCount();
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		
		AdminImPushVO searchVO = new AdminImPushVO();
		searchVO.setStart_num(pageUtil.getStartNum());
		searchVO.setEnd_num(pageUtil.getEndNum());
		
		List<AdminImPushVO> pushList = adminImPushService.getPushList(searchVO);
		
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("pushList", pushList);
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("nowPage", nowPage);
		return "admin/im/push/adminImPushList";
	}
	
	/**
	 * 푸쉬 알림 취소 프로세스
	 * push_seq
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/push/cancelProc")
	public ResponseEntity cancelProc(@RequestBody Map paramMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		
		try {
			adminImPushService.cancelProc(paramMap);
			
			msg = "저장에 성공하였습니다\r\n푸쉬 알림을 취소하였습니다";
		}catch(Exception e) {
			log.error("푸쉬 알림 취소 프로세스 에러");
			log.error(paramMap.toString());
			log.error(e.toString());
			result = false;
			msg = "처리하는데 문제가 생겼습니다 관리자에게 문의 바랍니다";
		}finally {
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 푸쉬 알림 데이터 가져오기
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/push/getData")
	public ResponseEntity getData(@RequestBody Map paramMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		AdminImPushVO pushVO = null;
		
		try {
			pushVO =  adminImPushService.getData(paramMap);
			
		}catch(Exception e) {
			log.error("푸쉬 알림 데이터 가져오기 프로세스 에러");
			log.error(paramMap.toString());
			log.error(e.toString());
			result = false;
			msg = "처리하는데 문제가 생겼습니다 관리자에게 문의 바랍니다";
		}finally {
			resultMap.put("pushVO", pushVO);
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	
	/**
	 * 푸쉬 알림 저장 프로세스
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/push/saveProc")
	public ResponseEntity saveProc(@RequestBody Map paramMap) throws Exception{
		boolean result = true;
		String msg = null;
		Map resultMap = new HashMap();
		
		try {
			adminImPushService.savelProc(paramMap);
			msg = "저장에 성공하였습니다";
		}catch(Exception e) {
			log.error("푸쉬 알림 저장 프로세스 에러");
			log.error(paramMap.toString());
			log.error(e.toString());
			result = false;
			msg = e.getMessage();
		}finally {
			resultMap.put("msg", msg);
			resultMap.put("result", result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
