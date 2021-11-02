package honeyarcade.admin.im.data;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminImDataMapper {

	/**
	 * 점포 데이터 총 갯수
	 * @param adminImDataVO
	 * @return
	 * @throws Exception
	 */
	public Integer getStoreTotalCount(AdminImDataVO adminImDataVO) throws Exception;

	/**
	 * 점포 목록
	 * @param adminImDataVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminImDataVO> getStoreList(AdminImDataVO adminImDataVO) throws Exception;

	/**
	 * 쿠폰 사용 수
	 * @param adminImDataVO
	 * @return
	 * @throws Exception
	 */
	public Integer getCouponTotalCount(AdminImDataVO adminImDataVO) throws Exception;

	/**
	 * 쿠폰 목록
	 * @param adminImDataVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminImDataVO> getCouponList(AdminImDataVO adminImDataVO) throws Exception;

}
