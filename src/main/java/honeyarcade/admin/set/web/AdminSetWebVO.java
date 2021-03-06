package honeyarcade.admin.set.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminSetWebVO {
	
	//	점주 웹 이미지
	private Integer	honeyarcade_seq;
	private Integer benefit_seq;
	private Integer file_seq;
	
	//	파일
	private String  file_dtl_url_path;
	
	//	관리자 아이디
	private String	admin_id;
	 
}
