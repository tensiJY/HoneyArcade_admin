package honeyarcade.admin.im.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminImDataService {

	@Autowired
	private AdminImDataMapper adminImDataMapper;
	
	/**
	 * 점포 데이터 총 갯수
	 * @param adminImDataVO
	 * @return
	 * @throws Exception
	 */
	public Integer getStoreTotalCount(AdminImDataVO adminImDataVO) throws Exception {
		// TODO Auto-generated method stub
		return adminImDataMapper.getStoreTotalCount(adminImDataVO);
	}

	/**
	 * 점포 목록
	 * @param adminImDataVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminImDataVO> getStoreList(AdminImDataVO adminImDataVO) throws Exception {
		// TODO Auto-generated method stub
		return adminImDataMapper.getStoreList(adminImDataVO);
	}

	/**
	 * 쿠폰 사용 수
	 * @param adminImDataVO
	 * @return
	 * @throws Exception
	 */
	public Integer getCouponTotalCount(AdminImDataVO adminImDataVO) throws Exception {
		return adminImDataMapper.getCouponTotalCount(adminImDataVO);
	}

	/**
	 * 쿠폰 목록
	 * @param adminImDataVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminImDataVO> getCouponList(AdminImDataVO adminImDataVO) throws Exception {
		return adminImDataMapper.getCouponList(adminImDataVO);
	}


}
