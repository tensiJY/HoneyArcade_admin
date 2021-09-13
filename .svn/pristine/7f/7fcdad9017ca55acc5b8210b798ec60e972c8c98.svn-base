package honeyarcade.admin.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

	public UserVO findByLoginId(String username);

	public void resetFailureCount(String username);

	public void addFailureCount(String username);

	public int getFailureCount(String username);

	public void disabledUsername(String username);
	
	

	
}
