package honeyarcade.admin.im.sales;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminImSalesMapper {
	
	/**
	 * 수익 관리 총 갯수 및 총 금액 구하기
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public AdminImSalesVO getTotalCount(AdminImSalesVO searchVO) throws Exception;

	/**
	 * 수익 관리 목록 데이터 가져오기
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminImSalesVO> getStatList(AdminImSalesVO searchVO) throws Exception;

}
