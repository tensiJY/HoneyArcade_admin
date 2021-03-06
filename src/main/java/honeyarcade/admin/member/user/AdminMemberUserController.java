package honeyarcade.admin.member.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * μΌλ°νμ 
 * @author 
 *
 */
@Controller
@RequestMapping("/admin/member")
@Slf4j
public class AdminMemberUserController {
	
	@Autowired
	private AdminMemberUserService adminMemberUserService;
	
	@GetMapping("/user/list")
	public String userList(@RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="listCount", defaultValue = "25")int listCount
			, Model model) throws Exception{
		
		Integer totalCount = adminMemberUserService.getUserCount();
		
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
		
		AdminMemberUserVO vo = new AdminMemberUserVO();
		vo.setStart_num(pageUtil.getStartNum());
		vo.setEnd_num(pageUtil.getEndNum());
		
		List<AdminMemberUserVO> userList = adminMemberUserService.getUserList(vo);
		
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("userList", userList);
		model.addAttribute("listCount", listCount);
		model.addAttribute("totalCount", totalCount);
		return "admin/member/user/adminMemberUserList";
	}

}
