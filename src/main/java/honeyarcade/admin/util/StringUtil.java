package honeyarcade.admin.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class StringUtil {
	
	/**
	 * - (하이픈) 있음
	 * RandomUUID 생성
	 * @return
	 */
	public static String getRandomUUID() {
		
		return UUID.randomUUID().toString();
	}

	
	/**
	 * - 하이픈 제거
	 * RadndomUUID()
	 * @return
	 */
	public static String randomUUID() {
		String str = getRandomUUID();
		
		return str.replaceAll("-", "");
	}
	
	/**
	 * 비밀번호 암호화
	 * @param S
	 * @return
	 */
	public static String getPasswordEncode(String password) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		return bc.encode(password);
	}
	
	/**
	 * 현재년도를 구하는 함수	String
	 * ex) 2020
	 * @return
	 */
	public static String getFullYearYYYY() {
		return String.valueOf(getFullYearInt()); 
	}
	
	/**
	 * 현재년도를 구하는 함수	int
	 * @return
	 */
	public static int getFullYearInt() {
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.YEAR);
	}
	
	
	/**
	 * 현재 달을 구하는 함수	
	 * ex) 2자리이며, 1~9월일경우 0을 붙임 09
	 * @return
	 */
	public static String getMonthMM() {
		int month = getMonthInt();
		String temp = "";
		
		if(month<10) {
			temp = "0"+month;
		}else {
			temp = ""+month;
		}
		
		return temp;
	}
	
	
	/**
	 * 현재 달을 구하는 함수
	 * ex) 1월~9월은 1자리 // 10~12월은 2자리	
	 * @return
	 */
	public static String getMonthString() {
		
		return String.valueOf(getMonthInt());
	}
	
	/**
	 * 현재 달을 구하는 함수
	 * ex) 1월~9월은 1자리 // 10~12월은 2자리
	 * @return
	 */
	public static int getMonthInt() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get (cal.MONTH) + 1 ;
		return month;
	}
	
	/**
	 * 현재 일을 구하는 함수
	 * 1~9는 1자리
	 * @return
	 */
	public static int getDayInt() {
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.DATE);
	}
	
	/**
	 * 현재 일을 구하는 함수
	 * 1~9는 0을 붙여서 01~09 이며, 나머지는 2자리
	 * @return
	 */
	public static String getDayDD() {
		int date = getDayInt();
		String temp = "";
		if(date<10) {
			temp = "0"+date;
		}else {
			temp = ""+date;
		}
		return temp;
	}
	
	/**
	 * 해당하는 월의 마지막 일자를 구하는 함수
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getLastDayOfMonth(Integer year, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 01);	//월은 -1해줘야 해당월로 인식
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 현재 시간을 구하는 함수
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime() {
		LocalDateTime dateTime = LocalDateTime.now();
	    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
	}
	
}
