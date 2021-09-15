package honeyarcade.admin.ad.coupon;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class AdminAdCouponVO {
	//	지역
	private Integer sido_seq;
	private String  sido_name;
	private Integer sigungu_seq;
	private String  sigungu_name;
	private Integer dong_seq;
	private String  dong_name;
	
	//	건물
	private Integer	build_seq;
	private String	build_name;
	
	//	페이징 변수
	private Integer start_num;
	private Integer end_num;
	private Integer listCount;
	private Integer nowPage;
	private Integer row_num;		//	순번
	
	//	검색 변수
	private String  type;			//	기본 : list, 검색:search 
	private String  search_text;	//	검색 내용
	private Integer search_type ; 	//	1 모든 광고, 2 현재 활성화 광고, 3 지난 광고, 4 예약 광고
	
	//	점주
	private String  owner_id;
	private String  store_name;
	private Integer	owner_sido_seq;
	private	Integer	owner_sigungu_seq;
	private	Integer	owner_dong_seq;
	
	//	관리자 저장 및 삭제
	private String  admin_id;
	
	//	쿠폰 광고
	private Integer	coupon_seq;
	private Integer	coupon_img;
	private String	coupon_start_day;
	private String	coupon_end_day;
	private Integer	coupon_sort;
	
	
	private Integer	coupon_dtl_seq;
	private String	coupon_dtl_text;
	
	//	쿠폰 파일 정보
	private	String	file_dtl_desc;
	private String	file_dtl_ext;
	private String	file_dtl_origin;
	private String	file_dtl_path;
	private String	file_dtl_url_path;
	private Integer	file_type;
	private Integer	file_seq;
	
}   