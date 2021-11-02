package honeyarcade.admin.member.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMemberUserMapper {

	/**
	 * 일반회원 : 총 회원수
	 * @return
	 * @throws Exception
	 */
	public Integer getUserCount() throws Exception;

	/**
	 * 일반회원 - 회원 목록 가져오기
	 * @param adminMemberUserVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberUserVO> getUserList(AdminMemberUserVO adminMemberUserVO) throws Exception;

}
