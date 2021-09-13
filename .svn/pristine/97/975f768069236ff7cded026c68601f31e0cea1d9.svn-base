package honeyarcade.admin.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
/**
 * 에러페이지 503
 * @author 
 *
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	private String errorPage = "/error/503";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		
		
		if(accessDeniedException instanceof AccessDeniedException) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			
		}
		
		
		request.getRequestDispatcher(errorPage).forward(request, response);
	}
	
	

}
