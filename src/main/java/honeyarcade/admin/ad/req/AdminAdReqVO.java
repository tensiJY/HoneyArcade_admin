package honeyarcade.admin.ad.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminAdReqVO {
		
	private Integer	ad_seq;					//	프로모션 결제 내용 시퀀스 : pay_seq	
	private Integer	ad_req_type; 			// 	1 : 건물광고, 2 : 지역광고(배너), 3쿠폰
	private String	ad_req_id;				//	owner_id 광고 요청 작성한 사람
	
	private String	member_id;				//	광고 작성자
		
	private String	ad_req_day;				//	광고 일자
	private String	ad_req_text;			//	광고 요청 내용
	private String	ad_req_total;			//	광고 요청 누적 횟수
	private String	ad_req_dt;				//	광고 요청 일자
	private String	ad_req_reject_reason;	//	광고 요청 반려 사유	
	private Integer	ad_req_status;			//	1 미승인, 2승인, 3요청반려	
	private String	ad_req_status_dt;		//	승인 또는 반려시 일자
	
	private String	ad_req_type_text;		//	건물/지역/쿠폰
	private String	ad_req_status_text;		//	미승인/승인/요청반려
	
	//	페이징 변수
	private Integer start_num;
	private Integer end_num;
	private Integer listCount;
	private Integer nowPage;
	private Integer row_num;		//	순번
	
	//	검색 변수
	private String  type;			//	기본 : list, 검색:search 
	private String  search_text;	//	검색 내용
	private Integer search_type ; 	//	1 미승인 요청 건, 2 승인 요청 건, 3 요청 반려 건
	
	//	지역 - 건물
	private Integer	sido_seq;
	private String	sido_name;
	private Integer sigungu_seq;
	private String	sigungu_name;
	private Integer dong_seq;
	private String	dong_name;
	private Integer build_seq;
	private String	build_name;
		
	//	지역
	private Integer	owner_sido_seq;
	private Integer owner_sigungu_seq;
	private Integer owner_dong_seq;
	private Integer owner_build_seq;
	private	String	owner_email;
	
	private String	store_name;
	
	//	관리자 저장 및 삭제
	private String  admin_id;
	
	//	프로모션
	private String	event_title;
	
	//	파일
	private Integer	file_seq;
	private Integer	file_dtl_seq;
	private Integer	file_type;
	private String	file_dtl_path;
	private String	file_dtl_desc;
	private String	file_dtl_ext;
	private String	file_dtl_url_path;
	
	//	프로모션 결제 세부내용
	private Integer	pay_dtl_use;
	
	//	관리자 등급
	private	Integer	admin_grade;	
	
	
}
