package honeyarcade.admin.im.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/im")
@Slf4j
public class AdminImSalesController {
	
	@Autowired
	private AdminImSalesService adminSalesService;
	
	@RequestMapping("/sales/list")
	public String salesList(@RequestParam(defaultValue = "1")Integer dateType
			, @RequestParam(defaultValue="")String year
			, @RequestParam(defaultValue ="")String month
			, @RequestParam(defaultValue = "1") Integer nowPage
			, @RequestParam(defaultValue = "25") Integer listCount
			, Model model) throws Exception{
		
		if("".equals(year)) {
			year = StringUtil.getFullYearYYYY();
		}
		
		if("".equals(month)) {
			month = StringUtil.getMonthMM();
		}
		
		AdminImSalesVO searchVO = new AdminImSalesVO();
		searchVO.setDateType(dateType);
		searchVO.setYear(year);
		searchVO.setMonth(month);
		
		AdminImSalesVO resultVO = adminSalesService.getTotalCount(searchVO);
		
		PageUtil pageUtil = new PageUtil(nowPage, resultVO.getTotalCount(), listCount);
		searchVO.setStart_num(pageUtil.getStartNum());
		searchVO.setEnd_num(pageUtil.getEndNum());
		
		List<AdminImSalesVO> statList = adminSalesService.getStatList(searchVO); 
		
		model.addAttribute("dateType", dateType);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("resultVO", resultVO);
		model.addAttribute("statList", statList);
		model.addAttribute("pageUtil", pageUtil);
		return "admin/im/sales/adminImSalesList";
	}

}
