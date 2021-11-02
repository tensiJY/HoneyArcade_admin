package honeyarcade.admin.member.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberUserService {

	@Autowired
	private AdminMemberUserMapper adminMemberUserMapper;
	
	/**
	 * 일반회원 - 총 회원수
	 * @return
	 * @throws Exception
	 */
	public Integer getUserCount() throws Exception {
		// TODO Auto-generated method stub
		return adminMemberUserMapper.getUserCount();
	}
	
	/**
	 * 일반회원 - 회원 목록 가져오기
	 * @param adminMemberUserVO
	 * @return
	 */
	public List<AdminMemberUserVO> getUserList(AdminMemberUserVO adminMemberUserVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMemberUserMapper.getUserList(adminMemberUserVO);
	}

}
