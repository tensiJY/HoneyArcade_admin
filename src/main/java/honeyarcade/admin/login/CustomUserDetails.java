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
	
	//	스프링 시큐리티는 UserDetails 라는 인터페이스를 구현한 객체를 로그인과정에서 사용합니다.
	//	하지만 요즘에는 이 인터페이스를 어느정도 구현하고 있는 User 라는 클래스를 제공하고 있기 때문에, 이 User 클래스를 상속받아서 생성자를 UserVO 에 맞게 설정해 주면 편리하게 사용할 수 있습니다.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String admin_name;
	
	private int admin_garde;
	
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
        
        this.admin_name = userVO.getAdmin_name();
        
        this.admin_garde = userVO.getAdmin_grade();
      
    }
    
    


}
