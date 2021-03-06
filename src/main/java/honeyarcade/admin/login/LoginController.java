package honeyarcade.admin.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import honeyarcade.admin.util.SessionUtil;
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
		Integer admin_grade = SessionUtil.getAdminGrade();
		String	url = null;
		
		if(admin_grade == 1) {
			url = "redirect:/admin/mgt/build/list"; 
		}else if(admin_grade == 2) {
			url = "redirect:/admin/mgt/store/list";
		}else {
			url = "redirect:/admin/mgt/store/list";
		}
		
		return url;
	}
	
	@RequestMapping("/admin")
	public String adminIndex() {
		Integer admin_grade = SessionUtil.getAdminGrade();
		String	url = null;
		
		if(admin_grade == 1) {
			url = "redirect:/admin/mgt/build/list"; 
		}else if(admin_grade == 2) {
			url = "redirect:/admin/mgt/store/list";
		}else {
			url = "redirect:/admin/mgt/store/list";
		}
		return url;
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
