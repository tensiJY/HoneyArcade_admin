package honeyarcade.admin.im.push;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminImPushVO {

	private Integer start_num;
	private Integer end_num;
	private Integer	push_seq;
	private Integer row_num;
	private String	push_text;
	private String	push_rez_dt;
	private	String	push_rez_time;
	private String	push_status_text;
	private String	add_dt;
	private Integer	push_status;
	private Integer	and_cnt;
	private Integer ios_cnt;
	private String	push_android_link;
	private String	push_ios_link;
	private String	mod_id;
	private String	add_id;
	private Integer	push_type;
	private String	build_seq;
	private String	store_seq;
	private String	push_title;
	
}
