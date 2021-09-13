package honeyarcade.admin.util;

import org.springframework.security.core.context.SecurityContextHolder;

import honeyarcade.admin.login.CustomUserDetails;

public class SessionUtil {
	
	
	public static String getAdminId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		
		return user.getUsername();
	}
	
}
