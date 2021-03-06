package honeyarcade.admin.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomUserDetails extends User{

	private static final long serialVersionUID = 1L;

	private String  admin_name;
	private Integer admin_grade;
		
    public CustomUserDetails(UserVO userVO, Collection<? extends GrantedAuthority> authorities) {
    	
        super(
            userVO.getAdmin_id(),	//	username
            userVO.getAdmin_pwd(),	//	password
            true,					//	enabled
            true,					//	accountNonExpired
            true,					//	credentialsNonExpired;
            true,
            authorities
        );
        
        this.admin_name		= userVO.getAdmin_name();
        this.admin_grade	= userVO.getAdmin_grade();
             
    }
    
    


}
