package honeyarcade.admin.common.fcm;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonFcmService {

	private String firebaseSdkPath = "static/resources/google/fcm-test-e528a-firebase-adminsdk-f3i9e-546dfe384c.json";
	private String PROJECT_ID = "fcm-test-e528a";
	private String BASE_URL = "https://fcm.googleapis.com";
	private final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";
	private final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
	private final String[] SCOPES = { MESSAGING_SCOPE };
	private final String MESSAGE_KEY = "message";

	public HashMap<String, Integer> sendCommonMessage(ArrayList<BatchFcmVO> userList, String title, String body, HashMap dataMap) throws Exception {
		HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
		Integer success = 0;
		Integer fail = 0;
		Integer push_seq = 0;
		
		JsonObject notificationMessage = null;
		for(int i=0; i<userList.size(); i++) {
			BatchFcmVO vo = userList.get(i);
			notificationMessage = buildNotificationMessage(vo.getUser_device_token(), title, body, dataMap);
			HashMap<String, Integer> resMap = sendMessage(notificationMessage);
			Integer s = resMap.get("success");
			Integer f = resMap.get("fail");
			success = s+success;
			fail = f+fail;
			
			if(i==0) {
				push_seq = Integer.parseInt(String.valueOf(dataMap.get("pushId")));
			}
			prettyPrint(notificationMessage);
			//Thread.sleep(1000);
		}
		resultMap.put("fail", fail);
		resultMap.put("success", success);
		resultMap.put("push_seq", push_seq);
		return resultMap;
	}

	/**
	 * 데이터를 생성
	 * @param token
	 * @param title
	 * @param body
	 * @param dataMap
	 * @return
	 */
	private JsonObject buildNotificationMessage(String token, String title, String body, HashMap dataMap) {
		
		JsonObject data = null;
		if(dataMap != null) {
			data =  new JsonObject();
			Iterator<String> iter = dataMap.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				String value = String.valueOf(dataMap.get(key));
				data.addProperty(key, value);
			}
		}
		
		JsonObject jNotification = new JsonObject();
		jNotification.addProperty("title", title);
		jNotification.addProperty("body", body);

		JsonObject jMessage = new JsonObject();
		if(data!=null) {
			jMessage.add("data", data);
		}
		
		jMessage.add("notification", jNotification);		
		jMessage.addProperty("token", token);

		JsonObject jFcm = new JsonObject();
		jFcm.add(MESSAGE_KEY, jMessage);

		return jFcm;
	}

	/**
	 * 데이터 출력
	 * @param jsonObject
	 */
	private void prettyPrint(JsonObject jsonObject) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		log.info(gson.toJson(jsonObject) + "\n");
	}

	/**
	 * 단일건 전송
	 * @param fcmMessage
	 * @return
	 * 실패갯수, 성공갯수, 푸쉬번호 
	 * @throws Exception
	 */
	private HashMap<String, Integer> sendMessage(JsonObject fcmMessage) throws Exception {
		Integer success = 0;
		Integer fail = 0;
		HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
		HttpURLConnection connection = getConnection();
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		writer.write(fcmMessage.toString());
		writer.flush();
		writer.close();

		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			String response = inputstreamToString(connection.getInputStream());
			//System.out.println("Message sent to Firebase for delivery, response:");
			//System.out.println(response);
			success++;
		} else {
			log.debug("Unable to send message to Firebase:");
			String response = inputstreamToString(connection.getErrorStream());
			log.debug(response);
			fail++;
		}
		
		resultMap.put("success", success);
		resultMap.put("fail", fail);
		
		return resultMap;
	}

	/**
	 * 프린트. 로그
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	private String inputstreamToString(InputStream inputStream) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
		}
		return stringBuilder.toString();
	}

	/**
	 * HTTP 헤더 작성
	 * @return
	 * @throws Exception
	 */
	private HttpURLConnection getConnection() throws Exception { // [START use_access_token] 
		URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
	  HttpURLConnection httpURLConnection = (HttpURLConnection)
	  url.openConnection(); httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
	  httpURLConnection.setRequestProperty("Content-Type","application/json; UTF-8"); 
	  return httpURLConnection; // [END use_access_token] 
	}

	/**
	 * 토큰 발급
	 * @return
	 * @throws Exception
	 */
	@Bean
	private String getAccessToken() throws Exception {
		ClassPathResource resource = new ClassPathResource(firebaseSdkPath);
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(resource.getInputStream())
				.createScoped(Arrays.asList(SCOPES));
				//.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

		// GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
		// REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
		// googleCredentials.refreshAccessToken();
		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}

}
