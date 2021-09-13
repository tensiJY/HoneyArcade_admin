package honeyarcade.admin.ad.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/ad")
@Slf4j
public class AdminAdCouponController {

	@GetMapping("/coupon/list")
	public String couponList() {
		log.info("call coupon list");
		return "admin/ad/coupon/adminAdCouponList";
	}
}
