package honeyarcade.admin.login;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;



/**
 * @description : 로그인한 사용자의 정보 및 체크
 * @author Koreasoft kykim
 * @version : 1.0
 */
@Service
@Slf4j
public final class LoginUserDetailHelper {
	
	@Autowired
	private LoginMapper loginMapper;
    /**
     * 인증된 사용자객체를 VO형식으로 가져온다.
     * @return 사용자 ValueObject
     */
	public UserVO getAuthenticatedUser() {
		
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserVO userVO = new UserVO();
        
        if (authentication.getPrincipal() instanceof User) {
        	log.debug("1. : " );
        	UserDetails details = (UserDetails) authentication.getPrincipal();
        	
        	log.debug("## LoginUserDetailsHelper.getAuthenticatedUser : AuthenticatedUser is {}", details.getUsername());
        	
        	
        	userVO = loginMapper.findByLoginId(details.getUsername());
	        return userVO;
	        
        } else {
        	log.debug("2. : " );
        	
        	Collection<? extends GrantedAuthority> authRole= authentication.getAuthorities();
        	String userRole = authRole.toString();
        	userVO.setRole_id(userRole);
        	
        	
        	log.debug("## LoginUserDetailsHelper.getAuthenticatedUser : AuthenticatedUser is {}", userRole);
        	
        	return userVO;
        }
    }
}
