package honeyarcade.admin.member.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import honeyarcade.admin.member.user.AdminMemberUserVO;

@Mapper
public interface AdminMemberPaymentMapper {

	/**
	 * 결제 현황 총 갯수 가져오기
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount() throws Exception;

	/**
	 * 결제 현황 목록
	 * @param adminMemberPaymentVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberUserVO> getPaymentList(AdminMemberPaymentVO adminMemberPaymentVO) throws Exception;

}
