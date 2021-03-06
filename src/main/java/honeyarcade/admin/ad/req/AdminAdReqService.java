package honeyarcade.admin.ad.req;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.ad.coupon.AdminAdCouponVO;
import honeyarcade.admin.util.SessionUtil;

@Service
public class AdminAdReqService {

	@Autowired
	private AdminAdReqMapper adminAdReqMapper; 
	
    @Autowired
    private MessageSource messageSource;
	
	/**
	 * 광고 요청 목록 - 전체 갯수 조회
	 * @param adminAdReqVO
	 * @return
	 * @throws Exception
	 */
	public int getTotalCount(AdminAdReqVO adminAdReqVO) throws Exception{
		return adminAdReqMapper.getTotalCount(adminAdReqVO);
	}

	/**
	 * 광고 요청 목록 - 전체 목록 
	 * @param adminAdReqVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdReqVO> getAdReqList(AdminAdReqVO adminAdReqVO) throws Exception {
		// TODO Auto-generated method stub
		return adminAdReqMapper.getAdReqList(adminAdReqVO);
	}

	/**
	 * 광고 요청 삭제 프로세스
	 * @param list
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void reqDelProc(List<AdminAdReqVO> list) throws Exception {
		String admin_id = SessionUtil.getAdminId();
		
		if(list.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		for(int i=0; i<list.size(); i++) {
			AdminAdReqVO adminAdReqVO = list.get(i);
			adminAdReqVO.setAdmin_id(admin_id);
			
			adminAdReqMapper.reqDelProc(adminAdReqVO);
		}
		
	}

	/**
	 * 광고요청 - 상세보기 - 데이터 가져오기
	 * @param adminAdReqVO
	 * @return
	 */
	public AdminAdReqVO getReqView(AdminAdReqVO adminAdReqVO) throws Exception {
		// TODO Auto-generated method stub
		return adminAdReqMapper.getReqView(adminAdReqVO);
	}

	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void reqAccept(AdminAdReqVO adminAdReqVO) throws Exception{
		//	광고 요청 승인 처리
		adminAdReqVO.setAd_req_status(2);
		adminAdReqMapper.reqAccept(adminAdReqVO);
		
		//	프로모션 결제 세부내용 / 광고 사용여부 업데이트
		adminAdReqVO.setPay_dtl_use(3);
		adminAdReqMapper.payDtlProc(adminAdReqVO);
		
	}

	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void reqReject(AdminAdReqVO adminAdReqVO) throws Exception {
		// TODO Auto-generated method stub
		adminAdReqVO.setAd_req_status(3);
		adminAdReqMapper.reqReject(adminAdReqVO);
		
		adminAdReqVO.setPay_dtl_use(4);
		adminAdReqMapper.payDtlProc(adminAdReqVO);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void updateAdReqStatus(AdminAdReqVO adminAdReqVO) throws Exception{
		adminAdReqMapper.updateAdReqStatus(adminAdReqVO);
		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void updatePayDtl(AdminAdReqVO adminAdReqVO) throws Exception{
		adminAdReqMapper.payDtlProc(adminAdReqVO);
	}

}
