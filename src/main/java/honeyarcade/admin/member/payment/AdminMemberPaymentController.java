package honeyarcade.admin.member.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.member.user.AdminMemberUserVO;
import honeyarcade.admin.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원관리 - 결제 현황
 * @author 
 *
 */
@Controller
@RequestMapping("/admin/member")
@Slf4j
public class AdminMemberPaymentController {
	
	@Autowired
	private AdminMemberPaymentService adminMemberPaymentService;

	@GetMapping("/payment/list")
	public String paymentList(@RequestParam(value="nowPage", defaultValue="1")int nowPage
			, @RequestParam(value="listCount", defaultValue = "25")int listCount
			, Model model) throws Exception {
		
		Integer totalCount = adminMemberPaymentService.getTotalCount();
		PageUtil pageUtil = new PageUtil(nowPage, totalCount, listCount);
				
		AdminMemberPaymentVO vo = new AdminMemberPaymentVO();
		vo.setStart_num(pageUtil.getStartNum());
		vo.setEnd_num(pageUtil.getEndNum());
		
		List<AdminMemberUserVO> paymentList = adminMemberPaymentService.getPaymentList(vo);
		
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("paymentList", paymentList);
		model.addAttribute("listCount", listCount);
		
		return "admin/member/payment/adminMemberPaymentList";
	}
		
}
