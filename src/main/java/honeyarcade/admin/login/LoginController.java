package honeyarcade.admin.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	
	/**
	 * 인덱스
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index(Model model)throws Exception {
		
		return "redirect:/admin/mgt/build/list";
	}
	
	@RequestMapping("/admin")
	public String adminIndex() {
		String url = null;
		
		
		return "redirect:/admin/mgt/build/list";
	}
	
	
	
	/**
	 * 로그인
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/admin/login/form")
	public String loginForm(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		return "admin/login/adminLoginForm";
	}
	

	
}