package honeyarcade.admin.common.fcm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BatchFcmVO {

	private String	user_device_token; 
	private String	user_device_os;
	private String	user_id;
	
	private Integer	push_seq;
	private String	push_text;
	private String	push_title;
	private Integer push_type;
	private Integer build_seq;
	private Integer store_seq;
	private Integer push_status;
	
	
}
