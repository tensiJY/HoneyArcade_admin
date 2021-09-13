package honeyarcade.admin.core.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import honeyarcade.admin.login.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 성공시 핸들러
 * @author wndud
 *
 */
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private CustomUserDetailsService loginService; 

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		/*
		// IP, 세션 ID
		WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
		System.out.println("IP : " + web.getRemoteAddress());
		System.out.println("Session ID : " + web.getSessionId());
		
		// 인증 ID
		System.out.println("name : " + authentication.getName());
		
		// 권한 리스트
		List<GrantedAuthority> authList = (List<GrantedAuthority>) authentication.getAuthorities();
		System.out.print("권한 : ");
		for(int i = 0; i< authList.size(); i++) {
			System.out.print(authList.get(i).getAuthority() + " ");
		}
		System.out.println();
		*/

		//	로그인 성공시 부가작업
		//	1. 에러 세션 지우기
		//	2. 실패 초기화
		//	3. URL 리다이렉트
		
		//	에러 세션 삭제
		clearAuthenticationAttributes(request);

		//	실패 초기화
//		String username = request.getParameter("username");
//		loginService.resetFailureCount(username);

		// 디폴트 URI
		String uri = "/admin";
//		/* 강제 인터셉트 당했을 경우의 데이터 get */
//		RequestCache requestCache = new HttpSessionRequestCache();
//		SavedRequest savedRequest = requestCache.getRequest(request, response);
//		
//		
//		//	방문하려던 페이지가 있는경우, 방문하려던 페이지로 이동
//		if (savedRequest != null) {
//			
//			uri = savedRequest.getRedirectUrl();
//		}
		
		//	디폴트 페이지로 이동
		response.sendRedirect(uri);
	}
	
	
	/**
	 * 에러 세션 삭제
	 * @param request
	 */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }


}

