package honeyarcade.admin.mgt.store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMgtStoreVO {
	
	//	지역
	private Integer sido_seq;
	private Integer sigungu_seq;
	private Integer dong_seq;
	//	건물
	private Integer build_seq;
	//	층
	private Integer floor_seq;
	private Integer floor_type;
	private String	floor_name;
	//	검색 조건
	private String type;
	private String search_text;
	//	업종 대분류
	private Integer lcate_seq;
	private String lcate_name;
	//	업종 소분류
	private Integer mcate_seq;
	private String mcate_name;
	//	점주
	private String	owner_id;		//	점주 아이디
	private String	store_name;		//	상점 명
	private Integer store_sort;		//	상점 노출 순위
	private String	store_keyword;	//	
	private Integer	store_status;	//	점포 상태 default 2
	private Integer store_floor;	//	점포 층수
	private String	store_tel;		//	점포 전화번호
	private String	store_text;		//	점포 소개글
	private Integer	owner_status;	//	5숨김(폐업)
	private String	del_flag;
	//	페이징 변수
	private Integer start_num;
	private Integer end_num;
	private Integer listCount;
	//	목록 변수
	private Integer row_num;			//	순번
	private String	store_status_text;	//	상태 변수
	private Integer	member_flag;	//	회원 가입 / 미가입 (점포 새로 등록한 상태)
	private String	admin_id;
	private String	mod_id;
	private Integer	admin_grade;		
	
}
