package honeyarcade.admin.set.dir;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.util.SessionUtil;
import honeyarcade.admin.util.StringUtil;

@Service
public class AdminSetDirService {

	@Autowired
	private AdminSetDirMapper adminSetDirMapper;
	
	/**
	 * 관리자 총 수 구하기
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount() throws Exception{
		return 	adminSetDirMapper.getTotalCount();
	}

	/**
	 * 관리자 목록
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetDirVO> getAdminList(AdminSetDirVO paramVO) throws Exception{
		return adminSetDirMapper.getAdminList(paramVO);
	}

	/**
	 * 관리자 아이디 삭제
	 * @param paramVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void deleteAdminId(AdminSetDirVO paramVO) throws Exception {
		adminSetDirMapper.deleteAdminId(paramVO);
	}
	
	/**
	 * 관리자 접근 관리 페이지 삭제
	 * @param paramVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void deleteAdminGrade(AdminSetDirVO paramVO) throws Exception {
		adminSetDirMapper.deleteAdminGrade(paramVO);
	}	

	/**
	 * 전체 동목록 가져오기 type: 1 가나다 순, 2 다나가 순
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetDirVO> getDongList(Integer type) throws Exception {
		return adminSetDirMapper.getDongList(type);
	}

	/**
	 * 건물 목록 가져오기 가나다 순
	 * @param dong_seq
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetDirVO> getBuildList(Integer dong_seq) throws Exception {
		return adminSetDirMapper.getBuildList(dong_seq);
	}

	/**
	 * 관리자 저장 프로세스
	 * @param paramMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void saveProc(HashMap paramMap) throws Exception {
		//String 	type		= String.valueOf(paramMap.get("type"));
		String	admin_grade = String.valueOf(paramMap.get("admin_grade"));
		String	admin_pwd 	= String.valueOf(paramMap.get("admin_pwd"));
		String	build_seq 	= String.valueOf(paramMap.get("build_seq"));
		String	admin_id	= String.valueOf(paramMap.get("admin_id"));
		String	admin_name	= String.valueOf(paramMap.get("admin_name"));
		
		if("".equals(admin_id)) {
			throw new Exception("관리자 아이디를 입력해주세요");
		}
		if("".equals(admin_pwd)) {
			throw new Exception("관리자 비밀번호를 작성해주세요");
		}
		if("".equals(admin_grade)) {
			throw new Exception("관리자 권한을 작성해주세요");
		}
		if("".equals(admin_name)) {
			throw new Exception("관리자 이름을 작성해주세요");
		}
		
		Integer count = adminSetDirMapper.isAdminId(admin_id);
		if(count>0) {
			throw new Exception("관리자 아이디가 존재합니다 다른 아이디를 입력해주세요");
		}
		
		AdminSetDirVO adminVO = new AdminSetDirVO();
		adminVO.setAdd_id(SessionUtil.getAdminId());
		adminVO.setAdmin_grade(Integer.parseInt(admin_grade));
		adminVO.setAdmin_id(admin_id);
		adminVO.setAdmin_name(admin_name);
		adminVO.setAdmin_pwd(StringUtil.getPasswordEncode(admin_pwd));
		
		adminSetDirMapper.insertAdminId(adminVO);
		
		if(Integer.parseInt(admin_grade) == 3) {
			adminVO.setBuild_seq(Integer.parseInt(build_seq));
			adminSetDirMapper.insertAdminGrade(adminVO);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void modProc(HashMap paramMap) throws Exception{
		String	admin_grade = String.valueOf(paramMap.get("admin_grade"));
		String	build_seq 	= String.valueOf(paramMap.get("build_seq"));
		String	admin_id	= String.valueOf(paramMap.get("admin_id"));
		String	admin_name	= String.valueOf(paramMap.get("admin_name"));
		
		if("".equals(admin_id)) {
			throw new Exception("관리자 아이디를 입력해주세요");
		}
		if("".equals(admin_grade)) {
			throw new Exception("관리자 권한을 작성해주세요");
		}
		if("".equals(admin_name)) {
			throw new Exception("관리자 이름을 작성해주세요");
		}
		
		AdminSetDirVO adminVO = new AdminSetDirVO();
		adminVO.setMod_id(SessionUtil.getAdminId());
		adminVO.setAdmin_grade(Integer.parseInt(admin_grade));
		adminVO.setAdmin_id(admin_id);
		adminVO.setAdmin_name(admin_name);
		
		adminSetDirMapper.deleteAdminGrade(adminVO);
		adminSetDirMapper.updateAdminId(adminVO);
		if(Integer.parseInt(admin_grade) == 3) {
			adminVO.setBuild_seq(Integer.parseInt(build_seq));
			adminVO.setAdd_id(SessionUtil.getAdminId());
			adminSetDirMapper.insertAdminGrade(adminVO);
		}
	}

	
}
