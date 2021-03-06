package honeyarcade.admin.util;

import org.springframework.security.core.context.SecurityContextHolder;

import honeyarcade.admin.login.CustomUserDetails;

public class SessionUtil {
	
	/**
	 * 세션 여부 확인 : 세션이 없으면 false, 세션이 있으면 true
	 * @return
	 */
	public static boolean hasSession() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean result = principal instanceof CustomUserDetails;
		
		return result;
	}
	
	public static String getAdminId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		
		return user.getUsername();
	}
	
	public static Integer getAdminGrade() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails user = (CustomUserDetails)principal;
		
		return user.getAdmin_grade();
	}
	
}
