package honeyarcade.admin.ad.area;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.ad.build.AdminAdBuildVO;
import honeyarcade.admin.ad.req.AdminAdReqService;
import honeyarcade.admin.ad.req.AdminAdReqVO;
import honeyarcade.admin.common.file.CommonFileService;
import honeyarcade.admin.common.file.CommonFileVO;
import honeyarcade.admin.util.SessionUtil;

@Service
public class AdminAdAreaService {

	@Autowired
	private AdminAdAreaMapper adminAdAreaMapper;
	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private CommonFileService commonFileService;
    
    @Autowired
    private AdminAdReqService adminAdReqService;
	
	public int getTotalCount(AdminAdAreaVO adminAdAreaVO) throws Exception{
		return adminAdAreaMapper.getTotalCount(adminAdAreaVO);
	}

	public List<AdminAdAreaVO> getAreaAdList(AdminAdAreaVO adminAdAreaVO) throws Exception{
		return adminAdAreaMapper.getAreaAdList(adminAdAreaVO);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void areaDelProc(List<AdminAdAreaVO> list) throws Exception{
		if(list.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		String admin_id = SessionUtil.getAdminId();
		for(int i=0; i<list.size(); i++) {
			AdminAdAreaVO vo = list.get(i);
			vo.setAdmin_id(admin_id);
			//	ad_owner_type == 1 제휴 점포, 2외부 점포
			if(1 == vo.getAd_owner_type()) {
				adminAdAreaMapper.delAreaBanner(vo);
			}else {
				adminAdAreaMapper.delAreaBannerOfExt(vo);
			}
		}
	}
	
	/**
	 * 지역광고 : 외부 점포 등록	//>> 트랜잭션 설정하면 file_seq 생성이 안됨
	 * @param dataMap
	 * @throws Exception
	 */
	public void areaWriteProc(HashMap dataMap) throws Exception{
		String admin_id = SessionUtil.getAdminId();
		
		String sido_seq			= String.valueOf(dataMap.get("sido_seq"));
		String sigungu_seq		= String.valueOf(dataMap.get("sigungu_seq")); 
		String dong_seq			= String.valueOf(dataMap.get("dong_seq")); 
		String owner_name		= String.valueOf(dataMap.get("owner_name"));	 
		String banner_url		= String.valueOf(dataMap.get("banner_url"));
		String banner_sort		= String.valueOf(dataMap.get("banner_sort"));
		String banner_start_day	= String.valueOf(dataMap.get("banner_start_day"));
		String banner_end_day	= String.valueOf(dataMap.get("banner_end_day"));
		String banner_type		= String.valueOf(dataMap.get("banner_type"));
		List file = (List) dataMap.get("file");
		List dongList = (List) dataMap.get("area");
		
		areaCheck(dataMap);
		
		Integer banner_img = null;
		Integer banner_main_img = null;
		
		for(int i=0; i<file.size(); i++) {
			
			HashMap bannerMap = (HashMap) file.get(i);
			
			CommonFileVO bannerVO = new CommonFileVO();
			
			bannerVO.setAdmin_id(admin_id);
			Integer file_seq = commonFileService.insertFileSeq(bannerVO);	//	파일 테이블 : 시퀀스 생성 > banner_img 키
			
			bannerVO.setFile_seq(file_seq);
			bannerVO.setFile_dtl_seq(0);
			bannerVO.setFile_dtl_desc(	  	String.valueOf(	bannerMap.get("file_dtl_desc")));
			bannerVO.setFile_dtl_ext(  	  	String.valueOf(	bannerMap.get("file_dtl_ext")));
			bannerVO.setFile_dtl_origin(  	String.valueOf(	bannerMap.get("file_dtl_origin")));
			bannerVO.setFile_dtl_path(	 	String.valueOf(	bannerMap.get("file_dtl_path")));
			bannerVO.setFile_dtl_url_path( 	String.valueOf(	bannerMap.get("file_dtl_url_path")));
			bannerVO.setFile_type(		  	Integer.parseInt(String.valueOf(bannerMap.get("file_type"))));
			
			commonFileService.insertFileDtl(bannerVO);
			
			if(i==0) {
				banner_img = file_seq;
			}else {
				banner_main_img = file_seq;
			}
		}
		
		AdminAdAreaVO vo = new AdminAdAreaVO();
		vo.setBanner_type(Integer.parseInt(banner_type));
		vo.setSido_seq(Integer.parseInt(sido_seq));
		vo.setSigungu_seq(Integer.parseInt(sigungu_seq));
		vo.setDong_seq(Integer.parseInt(dong_seq));
		vo.setOwner_name(owner_name);
		vo.setBanner_url(banner_url);
		vo.setBanner_img(banner_img);
		vo.setBanner_main_img(banner_main_img);
		vo.setBanner_start_day(banner_start_day);
		vo.setBanner_end_day(banner_end_day);
		vo.setBanner_sort(Integer.parseInt(banner_sort));
		vo.setAdmin_id(admin_id);
		
		Integer banner_seq = adminAdAreaMapper.insertExtBanner(vo);
		
		for(int i=0; i<dongList.size(); i++) {
			Integer ext_dong_seq = Integer.parseInt(String.valueOf(dongList.get(i)));
			AdminAdAreaVO dongExtBannerVO = new AdminAdAreaVO();
			dongExtBannerVO.setDong_seq(ext_dong_seq);
			dongExtBannerVO.setBanner_seq(banner_seq);
			adminAdAreaMapper.insertDongExtBanner(dongExtBannerVO);
		}
				
	}

	/**
	 * 외부점포 배너 광고 가져오기
	 * @param adminAdAreaVO
	 * @return
	 * @throws Exception
	 */
	public AdminAdAreaVO getAreaBannerOfExt(AdminAdAreaVO adminAdAreaVO) throws Exception{
		return adminAdAreaMapper.getAreaBannerOfExt(adminAdAreaVO);
	}

	public AdminAdAreaVO getAreaBanner(AdminAdAreaVO adminAdAreaVO) throws Exception{
		return adminAdAreaMapper.getAreaBanner(adminAdAreaVO);
	}

	/**
	 * 외부점포에 등록된 동 정보 가져오기
	 * @param banner_seq
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdAreaVO> getDongExtBanner(Integer banner_seq) throws Exception{
		return adminAdAreaMapper.getDongExtBanner(banner_seq);
	}
	
	/**
	 * 외부점포 등록 / 수정 체크
	 * @param dataMap
	 * @throws Exception
	 */
	private void areaCheck(HashMap dataMap) throws  Exception {
		String sido_seq			= String.valueOf(dataMap.get("sido_seq"));
		String sigungu_seq		= String.valueOf(dataMap.get("sigungu_seq")); 
		String dong_seq			= String.valueOf(dataMap.get("dong_seq")); 
		String owner_name		= String.valueOf(dataMap.get("owner_name"));	 
		String banner_url		= String.valueOf(dataMap.get("banner_url"));
		String banner_sort		= String.valueOf(dataMap.get("banner_sort"));
		String banner_start_day	= String.valueOf(dataMap.get("banner_start_day"));
		String banner_end_day	= String.valueOf(dataMap.get("banner_end_day"));
		String banner_type		= String.valueOf(dataMap.get("banner_type"));
		
		List file = (List) dataMap.get("file");
		List dongList = (List) dataMap.get("area");
		
		if("".equals(sido_seq)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(sigungu_seq)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(dong_seq)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}

		if("".equals(owner_name)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(banner_url)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(banner_sort)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(banner_start_day)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(banner_end_day)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
				
		if(dongList.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
	}

	/**
	 * 지역 광고 - 외부 점포 수정	
	 * >> 트랜잭션 설정하면 file_seq 생성이 안됨
	 * @param dataMap
	 * @throws Exception
	 */
	public void areaModProc(HashMap dataMap) throws Exception{
		areaCheck(dataMap);
		
		String admin_id = SessionUtil.getAdminId();
		
		String  sido_seq		= String.valueOf(dataMap.get("sido_seq"));
		String  sigungu_seq		= String.valueOf(dataMap.get("sigungu_seq")); 
		String  dong_seq		= String.valueOf(dataMap.get("dong_seq")); 
		String  owner_name		= String.valueOf(dataMap.get("owner_name"));	 
		String  banner_url		= String.valueOf(dataMap.get("banner_url"));
		String  banner_sort		= String.valueOf(dataMap.get("banner_sort"));
		String  banner_start_day= String.valueOf(dataMap.get("banner_start_day"));
		String  banner_end_day	= String.valueOf(dataMap.get("banner_end_day"));
		String  banner_type		= String.valueOf(dataMap.get("banner_type"));
		Integer banner_seq		= Integer.parseInt(String.valueOf(dataMap.get("banner_seq")));
		List file = (List) dataMap.get("file");
		List dongList = (List) dataMap.get("area");
		List delFile = (List) dataMap.get("del_file");
		
		if(delFile.get(0) != null) {
			//	delFile이 없으면 그대로 진행 
			for(int i=0; i<delFile.size(); i++) {
				Integer file_seq = Integer.parseInt(String.valueOf(delFile.get(i)));
				CommonFileVO fileVO = new CommonFileVO();
				fileVO.setFile_seq(file_seq);
				fileVO.setAdmin_id(admin_id);
				commonFileService.delFileSeq(fileVO);
			}
		}
		
		//	동 시퀀스 삭제
		adminAdAreaMapper.delDongExtBanner(banner_seq);
		Integer banner_img = null;
		Integer banner_main_img = null;
		
		for(int i=0; i<file.size(); i++) {
			HashMap bannerMap = (HashMap) file.get(i);
			CommonFileVO bannerVO = new CommonFileVO();
			String fileSeq = String.valueOf(bannerMap.get("file_seq"));
			Integer file_seq = null;
			
			if("".equals(fileSeq)) {
				bannerVO.setAdmin_id(admin_id);
				file_seq = commonFileService.insertFileSeq(bannerVO);	//	파일 테이블 : 시퀀스 생성 > banner_img 키
				bannerVO.setFile_seq(file_seq);
				bannerVO.setFile_dtl_seq(0);
				bannerVO.setFile_dtl_desc(	  	String.valueOf(	bannerMap.get("file_dtl_desc")));
				bannerVO.setFile_dtl_ext(  	  	String.valueOf(	bannerMap.get("file_dtl_ext")));
				bannerVO.setFile_dtl_origin(  	String.valueOf(	bannerMap.get("file_dtl_origin")));
				bannerVO.setFile_dtl_path(	 	String.valueOf(	bannerMap.get("file_dtl_path")));
				bannerVO.setFile_dtl_url_path( 	String.valueOf(	bannerMap.get("file_dtl_url_path")));
				bannerVO.setFile_type(		  	Integer.parseInt(String.valueOf(bannerMap.get("file_type"))));
				commonFileService.insertFileDtl(bannerVO);
			}else {
				file_seq = Integer.parseInt(fileSeq);
			}
			
			if(i==0) {
				banner_img = file_seq;
			}else if(i==1) {
				banner_main_img = file_seq;
			}
			
		}
		
		//	배너 업데이트
		AdminAdAreaVO vo = new AdminAdAreaVO();
		vo.setBanner_type(Integer.parseInt(banner_type));
		vo.setSido_seq(Integer.parseInt(sido_seq));
		vo.setSigungu_seq(Integer.parseInt(sigungu_seq));
		vo.setDong_seq(Integer.parseInt(dong_seq));
		vo.setOwner_name(owner_name);
		vo.setBanner_url(banner_url);
		vo.setBanner_img(banner_img);
		vo.setBanner_main_img(banner_main_img);
		vo.setBanner_start_day(banner_start_day);
		vo.setBanner_end_day(banner_end_day);
		vo.setBanner_sort(Integer.parseInt(banner_sort));
		vo.setAdmin_id(admin_id);
		vo.setBanner_seq(banner_seq);
		
		adminAdAreaMapper.updateExtBanner(vo);
		
		//	배너 - 동 테이블 인서트
		for(int i=0; i<dongList.size(); i++) {
			Integer ext_dong_seq = Integer.parseInt(String.valueOf(dongList.get(i)));
			AdminAdAreaVO dongExtBannerVO = new AdminAdAreaVO();
			dongExtBannerVO.setDong_seq(ext_dong_seq);
			dongExtBannerVO.setBanner_seq(banner_seq);
			adminAdAreaMapper.insertDongExtBanner(dongExtBannerVO);
		}
		
	}//

	/**
	 * 건물 광고 - 제휴점포 등록
	 * @param dataMap
	 * @throws Exception
	 */
	public void writeFormProc(HashMap dataMap) throws Exception{
		String	admin_id 		= SessionUtil.getAdminId();
		String  banner_sort		= String.valueOf(dataMap.get("banner_sort"));
		String  banner_start_day= String.valueOf(dataMap.get("banner_start_day"));
		String  banner_end_day	= String.valueOf(dataMap.get("banner_end_day"));
		Integer banner_type		= Integer.parseInt(String.valueOf(dataMap.get("ad_req_type")));
		Integer banner_seq		= Integer.parseInt(String.valueOf(dataMap.get("ad_seq")));
		String	owner_id		= String.valueOf(dataMap.get("ad_req_id"));
		
		List file 				= (List) dataMap.get("file");
		List dongList 			= (List) dataMap.get("area");
		
		//	데이터 체크
		checkAreaForm(dataMap);
		//	파일 등록
		Integer banner_img = null;
		Integer banner_main_img = null;
		
		for(int i=0; i<file.size(); i++) {
			
			HashMap bannerMap = (HashMap) file.get(i);
			
			CommonFileVO bannerVO = new CommonFileVO();
			
			bannerVO.setAdmin_id(admin_id);
			Integer file_seq = commonFileService.insertFileSeq(bannerVO);	//	파일 테이블 : 시퀀스 생성 > banner_img 키
			
			bannerVO.setFile_seq(file_seq);
			bannerVO.setFile_dtl_seq(0);
			bannerVO.setFile_dtl_desc(	  	String.valueOf(	bannerMap.get("file_dtl_desc")));
			bannerVO.setFile_dtl_ext(  	  	String.valueOf(	bannerMap.get("file_dtl_ext")));
			bannerVO.setFile_dtl_origin(  	String.valueOf(	bannerMap.get("file_dtl_origin")));
			bannerVO.setFile_dtl_path(	 	String.valueOf(	bannerMap.get("file_dtl_path")));
			bannerVO.setFile_dtl_url_path( 	String.valueOf(	bannerMap.get("file_dtl_url_path")));
			bannerVO.setFile_type(		  	Integer.parseInt(String.valueOf(bannerMap.get("file_type"))));
			
			commonFileService.insertFileDtl(bannerVO);
			
			if(i==0) {
				banner_img = file_seq;
			}else {
				banner_main_img = file_seq;
			}
		}
		
		//	배너 광고 등록
		AdminAdAreaVO vo = new AdminAdAreaVO();
		vo.setBanner_seq(banner_seq);
		vo.setBanner_type(banner_type);
		vo.setOwner_id(owner_id);
		vo.setBanner_img(banner_img);
		vo.setBanner_main_img(banner_main_img);
		vo.setBanner_start_day(banner_start_day);
		vo.setBanner_end_day(banner_end_day);
		vo.setBanner_sort(Integer.parseInt(banner_sort));
		vo.setAdmin_id(admin_id);
		
		adminAdAreaMapper.insertBanner(vo);
		
		//	배너에 설정되어있는 동 등록
		for(int i=0; i<dongList.size(); i++) {
			Integer dong_seq = Integer.parseInt(String.valueOf(dongList.get(i)));
			AdminAdAreaVO dongVO = new AdminAdAreaVO();
			dongVO.setDong_seq(dong_seq);
			dongVO.setBanner_seq(banner_seq);
			dongVO.setBanner_type(banner_type);
			dongVO.setOwner_id(owner_id);
			adminAdAreaMapper.insertDongBanner(dongVO);
		}
		
		//	광고 요청 상태 업데이트
		AdminAdReqVO adminAdReqVO = new AdminAdReqVO();
		adminAdReqVO.setAd_seq(banner_seq);			
		adminAdReqVO.setAd_req_type(banner_type);		//	1 건물, 2 지역,	3 쿠폰
		adminAdReqVO.setAd_req_status(4);				//	4 등록
		adminAdReqService.updateAdReqStatus(adminAdReqVO);
		
		//	점주 광고 사용여부 업데이트
		adminAdReqVO.setPay_dtl_use(5);					//	5 등록
		adminAdReqService.updatePayDtl(adminAdReqVO);
	}

	/**
	 * 건물 광고 - 제휴점포 등록/수정 데이터 체크
	 * @param dataMap
	 * @throws Exception
	 */
	private void checkAreaForm(HashMap dataMap) throws Exception{
		// TODO Auto-generated method stub
		String  banner_sort		= String.valueOf(dataMap.get("banner_sort"));
		String  banner_start_day= String.valueOf(dataMap.get("banner_start_day"));
		String  banner_end_day	= String.valueOf(dataMap.get("banner_end_day"));
		List file = (List) dataMap.get("file");
		List dongList = (List) dataMap.get("area");
		
		if("".equals(banner_sort)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(banner_start_day)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(banner_end_day)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
				
		if(dongList.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
	}
	
	/**
	 * 지역광고 - 제휴 점포 - 배너에 설정되어 있는 동 조회
	 * @param adminAdAreaVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdAreaVO> getDongBanner(AdminAdAreaVO adminAdAreaVO) throws Exception{
		return adminAdAreaMapper.getDongBanner(adminAdAreaVO);
	}

	/**
	 * 지역광고 - 제휴 점포 수정 프로세스
	 * @param dataMap
	 */
	public void modFormProc(HashMap dataMap) throws Exception{
		checkAreaForm(dataMap);
		
		String	admin_id 		= SessionUtil.getAdminId();
		String  banner_sort		= String.valueOf(dataMap.get("banner_sort"));
		String  banner_start_day= String.valueOf(dataMap.get("banner_start_day"));
		String  banner_end_day	= String.valueOf(dataMap.get("banner_end_day"));
		Integer banner_type		= Integer.parseInt(String.valueOf(dataMap.get("banner_type")));
		Integer banner_seq		= Integer.parseInt(String.valueOf(dataMap.get("banner_seq")));
		String	owner_id		= String.valueOf(dataMap.get("owner_id"));
		
		List file = (List) dataMap.get("file");
		List dongList = (List) dataMap.get("area");
		List delFile = (List) dataMap.get("del_file");
		
		//	삭제 파일이 없으면 그대로 진행
		if(delFile.get(0) != null) { 
			for(int i=0; i<delFile.size(); i++) {
				Integer file_seq = Integer.parseInt(String.valueOf(delFile.get(i)));
				CommonFileVO fileVO = new CommonFileVO();
				fileVO.setFile_seq(file_seq);
				fileVO.setAdmin_id(admin_id);
				commonFileService.delFileSeq(fileVO);
			}
		}
		//	파일 설정
		Integer banner_img = null;
		Integer banner_main_img = null;
		
		for(int i=0; i<file.size(); i++) {
			HashMap bannerMap = (HashMap) file.get(i);
			CommonFileVO bannerVO = new CommonFileVO();
			String fileSeq = String.valueOf(bannerMap.get("file_seq"));
			Integer file_seq = null;
			if("".equals(fileSeq)) {
				bannerVO.setAdmin_id(admin_id);
				file_seq = commonFileService.insertFileSeq(bannerVO);	//	파일 테이블 : 시퀀스 생성 > banner_img 키
				bannerVO.setFile_seq(file_seq);
				bannerVO.setFile_dtl_seq(0);
				bannerVO.setFile_dtl_desc(	  	String.valueOf(	bannerMap.get("file_dtl_desc")));
				bannerVO.setFile_dtl_ext(  	  	String.valueOf(	bannerMap.get("file_dtl_ext")));
				bannerVO.setFile_dtl_origin(  	String.valueOf(	bannerMap.get("file_dtl_origin")));
				bannerVO.setFile_dtl_path(	 	String.valueOf(	bannerMap.get("file_dtl_path")));
				bannerVO.setFile_dtl_url_path( 	String.valueOf(	bannerMap.get("file_dtl_url_path")));
				bannerVO.setFile_type(		  	Integer.parseInt(String.valueOf(bannerMap.get("file_type"))));
				commonFileService.insertFileDtl(bannerVO);
				
			}else {
				file_seq = Integer.parseInt(fileSeq);
			}
			if(i==0) {
				banner_img = file_seq;
			}else if(i==1) {
				banner_main_img = file_seq;
			}
		}
		AdminAdAreaVO vo = new AdminAdAreaVO();
		vo.setBanner_seq(banner_seq);
		vo.setBanner_type(banner_type);
		vo.setOwner_id(owner_id);
		vo.setBanner_img(banner_img);
		vo.setBanner_main_img(banner_main_img);
		vo.setBanner_start_day(banner_start_day);
		vo.setBanner_end_day(banner_end_day);
		vo.setBanner_sort(Integer.parseInt(banner_sort));
		vo.setAdmin_id(admin_id);
		
		//	배너에 설정되어 있는 동 삭제
		adminAdAreaMapper.deleteDongBanner(vo);
		
		//	제휴 점포 배너 광고 업데이트
		adminAdAreaMapper.updateBanner(vo);
		
		//	배너에 설정되어 있는 동 등록
		for(int i=0; i<dongList.size(); i++) {
			Integer dong_seq = Integer.parseInt(String.valueOf(dongList.get(i)));
			AdminAdAreaVO dongVO = new AdminAdAreaVO();
			dongVO.setDong_seq(dong_seq);
			dongVO.setBanner_seq(banner_seq);
			dongVO.setBanner_type(banner_type);
			dongVO.setOwner_id(owner_id);
			adminAdAreaMapper.insertDongBanner(dongVO);
		}
	}
	
	
}
