package honeyarcade.admin.ad.build;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class AdminAdBuildVO {
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
	
	//	검색 변수
	private String  type;			//	기본 : list, 검색:search 
	private String  search_text;	//	검색 내용
	private Integer build_type ; 	//	1 모든 광고, 2 현재 활성화 광고, 3 지난 광고, 4 예약 광고
	
	//	점주
	private String  owner_id;
	private String  store_name;
		
	//	외부
	private String  owner_name;		//	외부점포
	
	//	목록 변수
	private Integer row_num;		//	순번
	
	//	배너
	private Integer banner_seq;
	private Integer banner_type;
	private String  banner_start_day;
	private String  banner_end_day;
	private String  banner_url;
	private Integer banner_sort;
	private Integer banner_img;
	private Integer banner_main_img;
	
	private Integer banner_sido_seq;
	private Integer banner_sigungu_seq;
	private Integer banner_dong_seq;
	private Integer banner_build_seq;
		
	//	광고 외부 제휴 구분
	private String  ad_owner_type_text;	//
	private Integer ad_owner_type;		//	1 제휴 점포, 2 외부 점포
	
	//	관리자 저장 및 삭제
	private String  admin_id;
	private	String	member_id;
	private	Integer	admin_grade;
	
	//	파일 이미지
	private String	banner_file_dtl_ext;
	private String	banner_file_dtl_origin;
	private String	banner_file_dtl_path;
	private String	banner_dtl_url_path;	
	private String	banner_file_dtl_desc;	//	배너
	private Integer banner_file_type;		
	private Integer	banner_dtl_seq;			//	배너

	private String	main_file_dtl_ext;
	private String	main_file_dtl_origin;
	private String	main_file_dtl_path;
	private String	main_dtl_url_path;	
	private String	main_file_dtl_desc;		
	private Integer main_file_type;		
	private Integer	main_dtl_seq;			
	
}   