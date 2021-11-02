package honeyarcade.admin.im.sales;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class AdminImSalesVO {

	private Integer	dateType;	//	조회 방식	1:년, 2:년-월
	private String	year;		
	private String	month;
	
	private Integer start_num;
	private Integer end_num;
	
	private Integer	totalCount;	//	총 갯수
	private long	totalPrice;	//	총 금액
	
	private	String	member_id;
	private	Integer	row_num;
	private long	sum_money;
	private String	pay_ym;
	private String	pay_day;
	private String	build_name;
	private String	dong_name;
	private String	sigungu_name;
	private String	sido_name;
	private String	store_name;
	
	
	
}
