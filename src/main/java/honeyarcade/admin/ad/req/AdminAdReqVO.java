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
	private Integer	file_seq;				//	광고 요청 파일	
	private String	ad_req_day;				//	광고 일자
	private String	ad_req_text;			//	광고 요청 내용
	private String	ad_req_total;			//	광고 요청 누적 횟수?
	private String	ad_req_dt;				//	광고 요청 일자
	private String	ad_req_reject_reason;	//	광고 요청 반려 사유	
	private Integer	ad_req_status;			//	1 미승인, 2승인, 3요청반려	
	private String	ad_req_status_dt;		//	승인 또는 반려시 일자
	
	private Integer	sido_seq;
	private Integer sigungu_seq;
	private Integer dong_seq;
	private Integer build_seq;
	
}
