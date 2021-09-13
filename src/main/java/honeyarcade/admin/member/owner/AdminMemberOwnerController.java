package honeyarcade.admin.member.owner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/member")
@Slf4j
public class AdminMemberOwnerController {

	@GetMapping("/owner/list")
	public String ownerList() {
		return "admin/member/owner/adminMemberOwnerList";
	}
}