package honeyarcade.admin.member.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Slf4j
public class AdminMemberEventVO {

	//	페이징 변수
	private Integer start_num;
	private Integer end_num;
	private Integer listCount;
	private Integer nowPage;
	
	//	검색 변수
	private String  type;			//	기본 : list, 검색:search 
	private String  search_text;	//	검색 내용
	
	private Integer row_num;		//	순번
	private Integer event_seq; 		//	프로모션 키
	private String	event_title; 	//	프로모션 제목
	private Integer	event_price; 	//	프로모션 가격
	private Integer	event_sort; 	//	프로모션 정렬 순위
	private Integer	build_day;		//	건물
	private Integer	area_day;		//	지역
	private Integer	coupon_day;		//	쿠폰
	private String	add_date;		//	작성일
	private String	event_text;		//	내용
	
	private String	admin_id;		//	관리자 아이디
	
	private Integer event_dtl_type;	//	1건물, 2지역, 3쿠폰
	private Integer event_dtl_day;
}
