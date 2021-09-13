package honeyarcade.admin.im.sales;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/im")
@Slf4j
public class AdminImSalesController {
	
	@GetMapping("/sales/list")
	public String salesList() throws Exception{
		log.info("call sales list");
		
		return "admin/im/sales/adminImSalesList";
	}

}
