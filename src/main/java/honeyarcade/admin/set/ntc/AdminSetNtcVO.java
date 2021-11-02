package honeyarcade.admin.set.ntc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class AdminSetNtcVO {

	private Integer	ntc_seq;
	private String	ntc_title;
	private String 	ntc_text;
	private Integer ntc_type;	//	1 APP전체, 2 사장님홈페이지 공지사항, 3사장님회원 공지사항
	private String	add_id;
	private String	mod_id;
	private String 	ntc_date;
	
	
}
