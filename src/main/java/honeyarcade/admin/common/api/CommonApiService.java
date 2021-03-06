package honeyarcade.admin.common.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import honeyarcade.admin.util.SessionUtil;

@Service
public class CommonApiService {
	
	@Autowired
	private CommonApiMapper commonApiMapper;
	
	/**
	 * 시도 목록	admin_grade, admin_id
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getSido() throws Exception{
		// TODO Auto-generated method stub
		CommonApiVO paramVO = new CommonApiVO();
		paramVO.setAdmin_grade(SessionUtil.getAdminGrade());
		paramVO.setAdmin_id(SessionUtil.getAdminId());
		return commonApiMapper.getSido(paramVO);
	}

	/**
	 * 시군구 목록	admin_grade, admin_id
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getSigungu(CommonApiVO commonApiVO) throws Exception{
		commonApiVO.setAdmin_grade(SessionUtil.getAdminGrade());
		commonApiVO.setAdmin_id(SessionUtil.getAdminId());
		return commonApiMapper.getSigungu(commonApiVO);
	}

	/**
	 * 동 목록		admin_grade, admin_id
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getDong(CommonApiVO commonApiVO) throws Exception{
		commonApiVO.setAdmin_grade(SessionUtil.getAdminGrade());
		commonApiVO.setAdmin_id(SessionUtil.getAdminId());
		return commonApiMapper.getDong(commonApiVO);
	}

	/**
	 * 건물 목록	admin_grade, admin_id
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getBuild(CommonApiVO commonApiVO) throws Exception{
		commonApiVO.setAdmin_grade(SessionUtil.getAdminGrade());
		commonApiVO.setAdmin_id(SessionUtil.getAdminId());
		return commonApiMapper.getBuild(commonApiVO);
	}
	
	/**
	 * 층 목록
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getFloor(CommonApiVO commonApiVO) throws Exception{
		// TODO Auto-generated method stub
		return commonApiMapper.getFloor(commonApiVO);
	}

	public List<CommonApiVO> getOwnerOfBuild(CommonApiVO commonApiVO) throws Exception{
		// TODO Auto-generated method stub
		return commonApiMapper.getOwnerOfBuild(commonApiVO);
	}
	
	/**
	 * 대 카테고리 가져오기(build_seq) 
	 * @param commonApiVO 
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getLcateOfBuild(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getLcateOfBuild(commonApiVO);
	}

	/**
	 * 소 카테고리 가져오기(build_seq, lcate_seq)
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getMcateOfBuild(CommonApiVO commonApiVO) throws Exception{
		return commonApiMapper.getMcateOfBuild(commonApiVO);
	}

	/**
	 * 전체 시도 목록 조회
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllSido() throws Exception {
		return commonApiMapper.getAllSido();
	}

	/**
	 * 전체 시군구 목록 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllSigungu(CommonApiVO commonApiVO) throws Exception {
		return commonApiMapper.getAllSigungu(commonApiVO);
	}

	/**
	 * 전체 동 목록 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllDong(CommonApiVO commonApiVO) throws Exception {
		return commonApiMapper.getAllDong(commonApiVO);
	}

	/**
	 * 전체 건물 목록 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllBuild(CommonApiVO commonApiVO) throws Exception {
		return commonApiMapper.getAllBuild(commonApiVO);
	}

	/**
	 * 가입 승인되어있는 점포 조회
	 * @param commonApiVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonApiVO> getAllStore(CommonApiVO commonApiVO) throws Exception {
		// TODO Auto-generated method stub
		return commonApiMapper.getAllStore(commonApiVO);
	}

	
}
