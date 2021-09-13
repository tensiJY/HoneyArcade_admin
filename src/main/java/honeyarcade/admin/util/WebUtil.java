package honeyarcade.admin.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	/**
	 * 브라우저 확인
	 * @param request
	 * @return
	 */
	public static String getBrowser(HttpServletRequest request) {
		
		String header = request.getHeader("User-Agent");
		//System.out.println(header);
		
		if (header != null) {
			
			if(header.indexOf("Edg") > -1) {
			
				return "Edg";
				
			} else if (header.indexOf("Trident") > -1) {
			
				return "MSIE";	//	ie
			} else if (header.indexOf("Chrome") > -1) {
				
				return "Chrome";
			} else if (header.indexOf("Opera") > -1) {
				
				return "Opera";
			} else if (header.indexOf("iPhone") > -1 && header.indexOf("Mobile") > -1) {
				
				return "iPhone";
			} else if (header.indexOf("Android") > -1 && header.indexOf("Mobile") > -1) {
				
				return "Android";
			} //else if (header.indexOf(""))
			
		}
		
		return "Firefox";
	}

}
