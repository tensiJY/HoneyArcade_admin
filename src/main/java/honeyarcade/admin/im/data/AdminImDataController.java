package honeyarcade.admin.im.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import honeyarcade.admin.common.api.CommonApiService;
import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.util.PageUtil;
import honeyarcade.admin.util.SessionUtil;
import honeyarcade.admin.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/im")
public class AdminImDataController {
	
	@Autowired
	private CommonApiService commonApiService;
	
	@Autowired
	private AdminImDataService adminImDataService;
	
	@RequestMapping("/data/list")
	public String dataList( Model modl
			, @ModelAttribute AdminImDataVO adminImDataVO
			, @RequestParam(value="tapType" , defaultValue="1")Integer tapType 
			, @RequestParam(value="type", defaultValue="list") String type
			, @RequestParam(value="dateType", defaultValue = "1") Integer dateType
			, @RequestParam(value="year", defaultValue = "") String year
			, @RequestParam(value="month", defaultValue = "") String month
			, @RequestParam(value="listCount", defaultValue = "25") Integer listCount
			, @RequestParam(value="nowPage", defaultValue= "1") Integer nowPage
			, Model model) throws Exception{
				
		if("".equals(year)) {
			year = StringUtil.getFullYearYYYY();
		}
		
		if("".equals(month)) {
			month = StringUtil.getMonthMM();
		}
		
		List<CommonApiVO> sidoList = commonApiService.getSido();
		List<CommonApiVO> sigunguList = null;
		List<CommonApiVO> dongList = null;
		
		if(type.equals("search")) {
			CommonApiVO commonApiVO = new CommonApiVO();
			if(adminImDataVO.getSido_seq() != null) {
				commonApiVO.setSido_seq(adminImDataVO.getSido_seq()); 
				sigunguList = commonApiService.getSigungu(commonApiVO);
			}
			
			if(adminImDataVO.getSigungu_seq() != null) {
				commonApiVO.setSigungu_seq(adminImDataVO.getSigungu_seq());
				dongList = commonApiService.getDong(commonApiVO); 
			}
		}
		
		String start_date = null;
		String end_date = null;
		
		if(dateType == 1) {
			start_date = year + "-"+"01"+"-"+"01";
			end_date = year + "-"+"12"+"-"+ StringUtil.getLastDayOfMonth(Integer.parseInt(year), 12);
		}else {
			start_date = year + "-"+month+"-"+"01";
			end_date = year + "-"+month+"-"+ StringUtil.getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
		}
		
		adminImDataVO.setStart_date(start_date);
		adminImDataVO.setEnd_date(end_date);
		adminImDataVO.setType(type);
		adminImDataVO.setDateType(dateType);
		adminImDataVO.setAdmin_grade(SessionUtil.getAdminGrade());
		adminImDataVO.setAdmin_id(SessionUtil.getAdminId());
		
		List<AdminImDataVO> storeList = null;
		List<AdminImDataVO> couponList = null;
		
		Integer  totalCount = null;
		PageUtil pageUtil = null;
		
		if(tapType == 1) {
			totalCount = adminImDataService.getStoreTotalCount(adminImDataVO);
			pageUtil = new PageUtil(nowPage, totalCount, listCount);
			adminImDataVO.setStart_num(pageUtil.getStartNum());
			adminImDataVO.setEnd_num(pageUtil.getEndNum());
			storeList = adminImDataService.getStoreList(adminImDataVO);
		}else {
			totalCount = adminImDataService.getCouponTotalCount(adminImDataVO);
			pageUtil = new PageUtil(nowPage, totalCount, listCount);
			adminImDataVO.setStart_num(pageUtil.getStartNum());
			adminImDataVO.setEnd_num(pageUtil.getEndNum());
			couponList = adminImDataService.getCouponList(adminImDataVO);
		}
		
		model.addAttribute("tapType", tapType);		//	1??????, 2??????
		model.addAttribute("type", type);			//	list : ?????? | search : ??????
		model.addAttribute("dateType", dateType);	//	1???, 2???
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("sigunguList", sigunguList);
		model.addAttribute("dongList", dongList);
		model.addAttribute("adminImDataVO", adminImDataVO);
		model.addAttribute("pageUtil", pageUtil);
		model.addAttribute("storeList", storeList);
		model.addAttribute("couponList", couponList);
		return "admin/im/data/adminImDataList";
	}
	

}
