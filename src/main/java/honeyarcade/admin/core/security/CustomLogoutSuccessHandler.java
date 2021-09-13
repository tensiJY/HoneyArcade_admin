package honeyarcade.admin.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import honeyarcade.admin.login.CustomUserDetails;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		//	1. 세션 지워주기
		//	2. 인덱스 페이지로 이동
		
		
        if (authentication != null && authentication.getDetails() != null) {
            try {
            	
            	
            	request.getSession().invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
   
		
	}
	
	

}
