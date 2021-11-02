package honeyarcade.admin.im.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminImSalesService {

	@Autowired
	private AdminImSalesMapper adminImSalesMapper;

	/**
	 * 수익 관리 총 갯수 및 총 금액 구하기
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public AdminImSalesVO getTotalCount(AdminImSalesVO searchVO) throws Exception{
		return adminImSalesMapper.getTotalCount(searchVO);
	}

	/**
	 * 수익 관리 목록 데이터 가져오기
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminImSalesVO> getStatList(AdminImSalesVO searchVO) throws Exception {
		return adminImSalesMapper.getStatList(searchVO);
	}
	
	
}
