package honeyarcade.admin.member.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import honeyarcade.admin.member.user.AdminMemberUserVO;

@Service
public class AdminMemberPaymentService {
	
	@Autowired
	private AdminMemberPaymentMapper adminMemberPaymentMapper; 
	
	/**
	 * 결제 현황 총 갯수 가져오기
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount() throws Exception {
		// TODO Auto-generated method stub
		return adminMemberPaymentMapper.getTotalCount();
	}

	/**
	 * 결제 현황 목록
	 * @param vo
	 * @return
	 */
	public List<AdminMemberUserVO> getPaymentList(AdminMemberPaymentVO adminMemberPaymentVO) throws Exception{
		// TODO Auto-generated method stub
		return adminMemberPaymentMapper.getPaymentList(adminMemberPaymentVO);
	}

}
