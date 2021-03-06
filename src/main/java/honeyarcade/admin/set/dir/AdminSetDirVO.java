package honeyarcade.admin.set.dir;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
@ToString
public class AdminSetDirVO {
	
	private Integer start_num;
	private Integer end_num;
	private Integer row_num;
	private String	admin_grade_text;
	private String	admin_id;
	private String	admin_name;
	private String	add_dt;
	private Integer	admin_grade;
	private String	mod_id;
	private String	add_id;
	private	String	dong_seq;
	private	String	dong_name;
	private String	build_name;
	private	Integer build_seq;
	private String	admin_pwd;

}
