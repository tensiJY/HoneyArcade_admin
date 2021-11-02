package honeyarcade.admin.common.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomMailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Value("${mail.sender}")
	private String mail_sender;
	
	/**
	 * 회원 가입 반려 메일
	 * @param member_id
	 * @param owner_email
	 * @param reject_text
	 * @throws Exception
	 */
	public void sendRejectMail(String member_id, String owner_email, String reject_text) throws Exception{
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		//	메일 제목 설정
		helper.setSubject("[허니아케이드] 가입 반려 건입니다 ");
		//	수신자 설정
		helper.setTo(owner_email);
		helper.setFrom(mail_sender);
		
		//	템플릿에 전달할 테이터 설정
		Context context = new Context();
		context.setVariable("owner_id", member_id);
		context.setVariable("reject_text", reject_text);
		//	메일 내용 설정 템플릿
		String html = templateEngine.process("admin/email/email-refuse", context);
		//System.out.println(html.toString());
		
		helper.setText(html, true);
		
		//	메일 보내기
		javaMailSender.send(message);
	}
	
	/**
	 * 광고 요청 반려 메일
	 * @param member_id
	 * @param owner_email
	 * @param ad_req_reject_reason
	 * @param ad_req_type_text
	 * @throws Exception
	 */
	public void sendReqRejectMail(String member_id, String owner_email, String ad_req_reject_reason, String ad_req_type_text, String ad_req_dt) throws Exception{
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		//		메일 제목 설정
		helper.setSubject("[허니아케이드] 광고 요청 반려 건입니다 ");
		//	수신자 설정
		helper.setTo(owner_email);
		helper.setFrom(mail_sender);
		
		//	템플릿에 전달할 테이터 설정
		Context context = new Context();
		context.setVariable("owner_id", member_id);
		context.setVariable("reject_text", ad_req_reject_reason);
		context.setVariable("ad_req_type_text", ad_req_type_text);
		context.setVariable("ad_req_dt", ad_req_dt);
		//	메일 내용 설정 템플릿
		String html = templateEngine.process("admin/email/email-req-reject", context);
		//System.out.println(html.toString());
		
		helper.setText(html, true);
		
		//	메일 보내기
		javaMailSender.send(message);
		
	}
	
}
