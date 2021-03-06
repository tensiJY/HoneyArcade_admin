package honeyarcade.admin.util;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;



@Slf4j
public class FileUtil {
	
	private static final String fileTempPath = PropertyUtil.getProperty("file.temp.path");
	
	//private static final String fileRealPath = PropertyUtil.getProperty("file.real.path");
	
	private static final String fileUrlPath = PropertyUtil.getProperty("file.url.path");
	
	private static final String[] imageExt = {"png", "jpg", "jpeg", "gif", "bmp"};
	private static final String[] excelExt = {"xlsx", "xls"};
	private static final String pdfExt = "pdf";
	
	/**
	 * 엑셀 파일 여부
	 * @param excelFile
	 * @return
	 */
	public static boolean isExcel (String excelFile) {
		boolean result = false;
		String temp = excelFile.substring(excelFile.lastIndexOf(".")+1);
		for(int i=0; i<excelExt.length; i++) {
			if(excelExt[i].toUpperCase().equals(temp.toUpperCase())) {
				result =true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 이미지의 가로 세로 구함
	 * @param fileFullPath
	 * @return
	 */
	public static HashMap<String, Integer> getImgXY(String realFullPath) {
		
		HashMap<String, Integer> returnMap = new HashMap<String, Integer>();
		
		Image img = new ImageIcon(realFullPath).getImage();
		
		//	이미지 파일이 아닌경우 -1
		Integer imgX = img.getWidth(null);
		Integer imgY = img.getHeight(null);
					
		
		returnMap.put("imgX", imgX);
		returnMap.put("imgY", imgY);
		
		return returnMap;
	}
	
	/**
	 * 이미지 파일인지 체크하는 함수		
	 * 이미지이면 true 아니면 false
	 * @param imgFile
	 * @return
	 */
	public static boolean isImage (String imgFile) {
		boolean result = false;
		
		String temp = imgFile.substring(imgFile.lastIndexOf(".")+1);
		//System.out.println(temp);
		for(int i=0; i<imageExt.length; i++) {
			if(imageExt[i].toUpperCase().equals(temp.toUpperCase())) {
				result =true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 이미지 파일의 확장자를 리턴해주는 함수
	 * ex) png, jpg, jpeg, gif
	 * @param imgFile
	 * @return
	 */
	public static String getImageExt(String imgFile) {
		String temp = imgFile.substring(imgFile.lastIndexOf(".")+1);
		String ext = "";
		
		for(int i=0; i<imageExt.length; i++) {
			if(imageExt[i].toUpperCase().equals(temp.toUpperCase())) {
				ext = imageExt[i];
				break;
			}
		}
		return ext;
	}
	
	/**
	 * pdf 파일인지 확인
	 * @param pdfFile
	 * @return
	 */
	public static boolean isPdf(String pdfFile) {
		boolean result = false;
		String temp = pdfFile.substring(pdfFile.lastIndexOf(".")+1);
		if(pdfExt.toUpperCase().equals(temp.toUpperCase())) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 파일 확장자를 가져오는 함수
	 * @param file
	 * @return
	 */
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}

	
	/**
	 * 
	 * @param multipartFile	array
	 * @return
	 */
	public static List fileUpload(MultipartFile[] multipartFile){
		List list = fileUpload(multipartFile, fileTempPath);
		return list;
	}
	
	/**
	 * 
	 * @param multipartFile		array
	 * @param filePath			ex) c:/web_logs/HoneyArcade_admin/file/
	 * @return
	 */
	public static List fileUpload(MultipartFile[] multipartFile, String filePath) {
		List returnList = new ArrayList();
		Map returnMap = null;
		for(int i=0; i<multipartFile.length; i++) {
			returnMap = fileUpload(multipartFile[i], filePath);
			returnList.add(returnMap);
		}
		return returnList;
	}


	public static Map fileUpload(MultipartFile multipartFile) {
		return fileUpload(multipartFile, fileTempPath);
	}

	
	public static Map fileUpload(MultipartFile multipartFile, String filePath) {
		Map returnMap = new HashMap();
		InputStream in = null;
		OutputStream out = null;

		// 파일명
		String originalFileName = multipartFile.getOriginalFilename();
		// 파일확장자
		String ext = getExt(originalFileName);
		// UUID4
		String descFileName = null;//StringUtil.randomUUID();
		String tempPath = fileTempPath;
		// /year/month/day 폴더를 만듬
		String datePath = StringUtil.getFullYearYYYY() + "/" + StringUtil.getMonthMM() + "/" + StringUtil.getDayDD() +"/";
		String tempFullPath = null; //	tempPath + datePath + descFileName;
		
		try {
			File tempFilePath = new File(tempPath + datePath);
			if (!tempFilePath.exists()) {
				tempFilePath.mkdirs();
			}
			
			File tempFileFullPath =  null; 
			while(true) {
				descFileName = StringUtil.randomUUID()+"."+ext;
				tempFullPath = tempPath + datePath + descFileName ;
				
				tempFileFullPath = new File(tempFullPath);
				if(!tempFileFullPath.exists()) {
					break;
				}
			}

			byte[] bytes = multipartFile.getBytes();
			out = new FileOutputStream(tempFullPath);
			out.write(bytes);
			out.close();

			returnMap.put("ext", ext);
			returnMap.put("originalFileName", originalFileName);//
			returnMap.put("descFileName", descFileName); //
			returnMap.put("realFullPath", tempFullPath); //
			returnMap.put("realPath", tempFilePath); //
			returnMap.put("urlPath", fileUrlPath+datePath+descFileName);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}

				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
						
		return returnMap;
	}	
	
	
	

}
