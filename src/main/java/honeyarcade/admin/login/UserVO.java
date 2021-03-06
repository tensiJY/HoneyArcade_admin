package honeyarcade.admin.login;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String	admin_id;	// 아이디
	private String	admin_pwd;	// 로그인 암호
	private String	role_id;	// 권한	
	private String	admin_name;
	private Integer admin_grade;
	
	
}
