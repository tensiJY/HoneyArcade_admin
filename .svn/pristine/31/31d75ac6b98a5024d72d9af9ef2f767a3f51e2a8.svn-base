package honeyarcade.admin.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import honeyarcade.admin.login.CustomUserDetails;
import honeyarcade.admin.login.CustomUserDetailsService;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		//	로그인 파라미터로 인증 된 값
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		

		CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
		
		
		//	비밀번호 일치하는지 확인
		if(!passwordEncoder().matches(password, customUserDetails.getPassword())){
			throw new BadCredentialsException(username);

		}
		
		//	계정 잠겨 있는경우 에러 
		if(!customUserDetails.isEnabled()) {
			
			throw new DisabledException(username);
		}

			

		return new UsernamePasswordAuthenticationToken(customUserDetails, customUserDetails.getPassword(), customUserDetails.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
