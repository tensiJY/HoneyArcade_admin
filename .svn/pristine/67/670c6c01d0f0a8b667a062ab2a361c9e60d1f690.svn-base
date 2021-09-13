package honeyarcade.admin.ad.build;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.common.file.CommonFileService;
import honeyarcade.admin.common.file.CommonFileVO;
import honeyarcade.admin.util.SessionUtil;

@Service
public class AdminAdBuildService {

	@Autowired
	private AdminAdBuildMapper adminAdBuildMapper;
	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private CommonFileService commonFileService;
	
    /**
     * 건물광고 - 배너 광고 총 갯수 
     * @param adminAdBuildVO
     * @return
     * @throws Exception
     */
	public int getTotalCount(AdminAdBuildVO adminAdBuildVO) throws Exception{
		
		return adminAdBuildMapper.getTotalCount(adminAdBuildVO);
	}

	/**
	 * 건물광고 - 배너 광고 목록
	 * @param adminAdBuildVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdBuildVO> getBuildAdList(AdminAdBuildVO adminAdBuildVO) throws Exception{

		return adminAdBuildMapper.getBuildAdList(adminAdBuildVO);
	}

	/**
	 * 건물광고 - 배너 광고 삭제
	 * @param list
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void buildDelProc(List<AdminAdBuildVO> list) throws Exception{
		if(list.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		String admin_id = SessionUtil.getAdminId();
		for(int i=0; i<list.size(); i++) {
			AdminAdBuildVO vo = list.get(i);
			vo.setAdmin_id(admin_id);
			//	ad_owner_type == 1 제휴 점포, 2외부 점포
			if(1 == vo.getAd_owner_type()) {
				adminAdBuildMapper.delBuildBanner(vo);
			}else {
				adminAdBuildMapper.delBuildBannerOfExt(vo);
			}
			
		}
		
	}

	/**
	 * 건물광보 - 외부 점포 건물 광고 등록
	 * >>> 트랜잭션 설정하면 file_seq 생성이 안됨
	 * @param dataMap
	 * @throws Exception
	 */
	public void buildWriteProc(HashMap dataMap) throws Exception{
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
		List buildList = (List) dataMap.get("build");
		
		buildCheck(dataMap);
		
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
		
		AdminAdBuildVO vo = new AdminAdBuildVO();
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
		
		Integer banner_seq = adminAdBuildMapper.insertExtBanner(vo);
		
		for(int i=0; i<buildList.size(); i++) {
			Integer ext_build_seq = Integer.parseInt(String.valueOf(buildList.get(i)));
			AdminAdBuildVO buildExtBannerVO = new AdminAdBuildVO();
			buildExtBannerVO.setBuild_seq(ext_build_seq);
			buildExtBannerVO.setBanner_seq(banner_seq);
			adminAdBuildMapper.insertBuildExtBanner(buildExtBannerVO);
		}
				
	}//

	/**
	 * 건물광고 - 편집 : 외부점포 배너 광고 가져오기
	 * @param adminAdBuildVO
	 * @return
	 * @throws Exception
	 */
	public AdminAdBuildVO getBuildBannerOfExt(AdminAdBuildVO adminAdBuildVO) throws Exception{
		return adminAdBuildMapper.getBuildBannerOfExt(adminAdBuildVO);
	}

	public AdminAdBuildVO getAreaBanner(AdminAdBuildVO adminAdAreaVO) throws Exception{
		return adminAdBuildMapper.getAreaBanner(adminAdAreaVO);
	}

	/**
	 * 건물광고 - 편집 : 외부점포 배너에 포함된 건물 목록 가져오기
	 * @param banner_seq
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdBuildVO> getBudilExtBanner(Integer banner_seq) throws Exception{
		return adminAdBuildMapper.getBudilExtBanner(banner_seq);
	}
	
	//	외부점포 등록 / 수정 체크
	private void buildCheck(HashMap dataMap) throws  Exception {
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
		List buildList = (List) dataMap.get("build");
		
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
				
		if(buildList.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
	}

	/**
	 * 	건물광고 - 외부점포 수정 프로세스
	 * @param dataMap
	 * @throws Exception
	 */
	public void buildModProc(HashMap dataMap) throws Exception{
		buildCheck(dataMap);
		
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
		List buildList = (List) dataMap.get("build");
		List delFile = (List) dataMap.get("del_file");

		//	delFile이 없으면 그대로 진행 
		if(delFile.get(0) != null) {
			for(int i=0; i<delFile.size(); i++) {
				Integer file_seq = Integer.parseInt(String.valueOf(delFile.get(i)));
				CommonFileVO fileVO = new CommonFileVO();
				fileVO.setFile_seq(file_seq);
				fileVO.setAdmin_id(admin_id);
				commonFileService.delFileSeq(fileVO);
			}
		}
		
		//	동 시퀀스 삭제
		adminAdBuildMapper.delBuildExtBanner(banner_seq);
		
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
		AdminAdBuildVO vo = new AdminAdBuildVO();
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
		
		adminAdBuildMapper.updateExtBanner(vo);
		
		
		//	배너 - 동 테이블 인서트
		for(int i=0; i<buildList.size(); i++) {
			Integer ext_build_seq = Integer.parseInt(String.valueOf(buildList.get(i)));
			AdminAdBuildVO buildExtBannerVO = new AdminAdBuildVO();
			buildExtBannerVO.setBuild_seq(ext_build_seq);
			buildExtBannerVO.setBanner_seq(banner_seq);
			adminAdBuildMapper.insertBuildExtBanner(buildExtBannerVO);
		}
		
	}//


		
}
