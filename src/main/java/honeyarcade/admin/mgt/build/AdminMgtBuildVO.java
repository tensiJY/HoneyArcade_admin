package honeyarcade.admin.mgt.build;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMgtBuildVO {
	
	private Integer sido_seq;		//	시도 시퀀스
	
	private String sido_name;		//	시도 명
		
	private Integer sigungu_seq;	//	시군구 시퀀스
	
	private String sigungu_name;	//	시군구 명
	
	private Integer dong_seq;		//	동읍면 시퀀스
	
	private String dong_name;		//	동명
	
	private Integer build_seq;		//	건물 시퀀스
	
	private Integer build_status;	//	건물 상태	default 0 : 0은 비활성화, 1화설화
	
	private String build_x;			//	건물 경도
	
	private String build_y; 		//	건물 위도
	
	private String build_name;		//	건물 명
	
	private String search_text;		//	검색어
	
	private String admin_id;		//	관리자 아이디
	
	private Integer top_floor;		//	최고 층수
		
	////	페이징 변수
	private int start_num;			//	시작 번호
	
	private int end_num;			//	마지막 번호
	
	private int row_num;			//	조회 번호
	
	private String type;			//	검색인지 목록인지 : default 목록(list)
	
	////	업종 대분류  
	private int lcate_seq;			//	대분류 시퀀스
	
	private String lcate_name;		//	대분류 명
	
	private Integer lcate_count; 
	
	private Integer file_seq;			//	파일 시퀀스
	
	////	업종 소분류 카테고리
	private int mcate_seq;			//	소분류 시퀀스
	
	
	private String mcate_name;		//	업종 소분류명
	
	////	층
	private Integer floor_name;		//	층명
	
	private Integer floor_seq;		//	층 시퀀스
	
	private Integer floor_type;		//	층 유형 1지상, 2지하
	
	////	이미지
	private String file_dtl_url_path;
	
	////	공통
	private String mod_dt;			//	수정일
	
	private String mod_id;			//	수정자

	
	
	
	////////	업종 대분류
	/*
	private MultipartFile[] lcate_file;			//	업종 대분류 이미지 아이콘
	private Integer[] lcate_file_seq;			//	업종 대분류 파일 시퀀스
	private Integer[] lcate_seqs;					//	업종 대분류 시퀀스
	private String[] lcate_names;						//	업종 대분류
	
	////////	소 카테고리
	private String[] mcate_a;					//	업종 세부분류 1-1 ~ N
	private String[] mcate_b;					//	업종 세부분류 2-1 ~ N
	private String[] mcate_c;					//	업종 세부분류 3-1 ~ N
	
	////////	층수정보
	private String[] floor_name_arr;			//	층수 정보
	private MultipartFile[] floor_file;			//	층수 파일
	private Integer[] floor_file_seq;			//	층수 파일 정보 시퀀스

	private Integer dtlType;					//	1 : new , 2 mod
	
	private String file_dtl_url_path;
	 */
}