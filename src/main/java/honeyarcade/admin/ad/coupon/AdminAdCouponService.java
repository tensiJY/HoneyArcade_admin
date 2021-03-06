package honeyarcade.admin.ad.coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
public class AdminAdCouponService {

	@Autowired
	private AdminAdCouponMapper adminAdCouponMapper;
	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private CommonFileService commonFileService;
    
    @Autowired
    private AdminAdReqService adminAdReqService;
	
    /**
     * 쿠폰 관리 - 목록 : 쿠폰 전체 갯수 조회
     * @param adminAdAreaVO
     * @return
     * @throws Exception
     */
	public int getTotalCount(AdminAdCouponVO adminAdAreaVO) throws Exception{
		
		return adminAdCouponMapper.getTotalCount(adminAdAreaVO);
	}

	/**
	 * 쿠폰 관리 - 목록 : 쿠폰 목록 조회
	 * @param adminAdCouponVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdCouponVO> getAdCouponList(AdminAdCouponVO adminAdCouponVO) throws Exception{

		return adminAdCouponMapper.getAdCouponList(adminAdCouponVO);
	}
	
	/**
	 * 쿠폰 관리 - 삭제
	 * @param list
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void couponDelProc(List<AdminAdCouponVO> list) throws Exception {
		String admin_id = SessionUtil.getAdminId();
		
		if(list.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		for(int i=0; i<list.size(); i++) {
			AdminAdCouponVO adminAdCouponVO = list.get(i);
			adminAdCouponVO.setAdmin_id(admin_id);
			
			adminAdCouponMapper.couponDelProc(adminAdCouponVO);
		}
		
	}

	/**
	 * 쿠폰 등록 프로세스
	 * @param dataMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void couponWriteProc(HashMap dataMap) throws Exception{
		String admin_id = SessionUtil.getAdminId();
		couponCheck(dataMap);
		//String sido_seq			= String.valueOf(dataMap.get("sido_seq"));
		//String sigungu_seq		= String.valueOf(dataMap.get("sigungu_seq")); 
		//String dong_seq			= String.valueOf(dataMap.get("dong_seq")); 

		String coupon_sort		= String.valueOf(dataMap.get("coupon_sort"));
		String coupon_start_day	= String.valueOf(dataMap.get("coupon_start_day"));
		String coupon_end_day	= String.valueOf(dataMap.get("coupon_end_day"));
		
		String coupon_seq 		= String.valueOf(dataMap.get("ad_seq"));
		String owner_id			= String.valueOf(dataMap.get("ad_req_id"));
		
		List file = (List) dataMap.get("file");
		List buildList = (List) dataMap.get("build");
		List couponDtlText = (List) dataMap.get("coupon_dtl_txt");
		
		HashMap fileMap = (HashMap) file.get(0);
		CommonFileVO couponVO = new CommonFileVO();
		couponVO.setAdmin_id(admin_id);
		Integer file_seq = commonFileService.insertFileSeq(couponVO);	//	파일 테이블 : 시퀀스 생성 > banner_img 키
		
		couponVO.setFile_seq(file_seq);
		couponVO.setFile_dtl_seq(0);
		couponVO.setFile_dtl_desc(	  	String.valueOf(	fileMap.get("file_dtl_desc")));
		couponVO.setFile_dtl_ext(  	  	String.valueOf(	fileMap.get("file_dtl_ext")));
		couponVO.setFile_dtl_origin(  	String.valueOf(	fileMap.get("file_dtl_origin")));
		couponVO.setFile_dtl_path(	 	String.valueOf(	fileMap.get("file_dtl_path")));
		couponVO.setFile_dtl_url_path( 	String.valueOf(	fileMap.get("file_dtl_url_path")));
		couponVO.setFile_type(		  	Integer.parseInt(String.valueOf(fileMap.get("file_type"))));
		
		commonFileService.insertFileDtl(couponVO);
		
		AdminAdCouponVO adminAdCouponVO = new AdminAdCouponVO();
		adminAdCouponVO.setCoupon_seq(Integer.parseInt(coupon_seq));
		adminAdCouponVO.setOwner_id(owner_id);
		adminAdCouponVO.setCoupon_img(file_seq);
		adminAdCouponVO.setCoupon_sort(Integer.parseInt(coupon_sort));
		adminAdCouponVO.setCoupon_start_day(coupon_start_day);
		adminAdCouponVO.setCoupon_end_day(coupon_end_day);
		adminAdCouponVO.setAdmin_id(admin_id);
		
		
		//	쿠폰 등록
		adminAdCouponMapper.insertCoupon(adminAdCouponVO);
		
		//	쿠폰 세부내용 등록
		for(int i=0; i<couponDtlText.size(); i++) {
			AdminAdCouponVO dtlVO = new AdminAdCouponVO();
			dtlVO.setCoupon_seq(Integer.parseInt(coupon_seq));
			dtlVO.setCoupon_dtl_seq(i);
			dtlVO.setCoupon_dtl_text(String.valueOf(couponDtlText.get(i)));
			
			adminAdCouponMapper.insertCouponDtl(dtlVO);
		}
		
		//	쿠폰 건물 등록
		for(int i=0; i<buildList.size();i++) {
			AdminAdCouponVO buildVO = new AdminAdCouponVO();
			buildVO.setOwner_id(owner_id);
			buildVO.setBuild_seq(Integer.parseInt(String.valueOf(buildList.get(i))));
			buildVO.setCoupon_seq(Integer.parseInt(coupon_seq));
			
			adminAdCouponMapper.insertBuildCoupon(buildVO);
		}
		
		//	광고 요청 상태 업데이트
		AdminAdReqVO adminAdReqVO = new AdminAdReqVO();
		adminAdReqVO.setAd_seq(Integer.parseInt(coupon_seq));			
		adminAdReqVO.setAd_req_type(Integer.parseInt(String.valueOf(dataMap.get("ad_req_type"))));	//	3	쿠폰
		adminAdReqVO.setAd_req_status(4);		//	4	등록
		
		adminAdReqService.updateAdReqStatus(adminAdReqVO);
		
		//	점주 광고 사용여부 업데이트
		adminAdReqVO.setPay_dtl_use(5);			//	5	등록
		
		adminAdReqService.updatePayDtl(adminAdReqVO);
	}

	/**
	 * 쿠폰 발리데이션
	 * @param dataMap
	 * @throws Exception
	 */
	private void couponCheck(HashMap dataMap) throws Exception{
		//String sido_seq			= String.valueOf(dataMap.get("sido_seq"));
		//String sigungu_seq		= String.valueOf(dataMap.get("sigungu_seq")); 
		//String dong_seq			= String.valueOf(dataMap.get("dong_seq")); 

		String coupon_sort		= String.valueOf(dataMap.get("coupon_sort"));
		String coupon_start_day	= String.valueOf(dataMap.get("coupon_start_day"));
		String coupon_end_day	= String.valueOf(dataMap.get("coupon_end_day"));
		
		
		List file = (List) dataMap.get("file");
		List buildList = (List) dataMap.get("build");
		List couponDtlText = (List) dataMap.get("coupon_dtl_txt");
		/*
		if("".equals(sido_seq)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(sigungu_seq)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(dong_seq)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		 */
				
		if("".equals(coupon_sort)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(coupon_start_day)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(coupon_end_day)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
				
		if(buildList.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if(couponDtlText.size()==0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
	}

	/**
	 * 쿠폰 정보 가져오기
	 * coupon_seq
	 * @param adminAdCouponVO
	 * @return
	 * @throws Exception
	 */
	public AdminAdCouponVO getAdCoupon(AdminAdCouponVO adminAdCouponVO) throws Exception {
		return adminAdCouponMapper.getAdCoupon(adminAdCouponVO);
	}

	/**
	 * 쿠폰의 텍스트 정보 가져오기
	 * coupon_seq
	 * @param adminAdCouponVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdCouponVO> getCouponTextList(AdminAdCouponVO adminAdCouponVO) throws Exception{
		return adminAdCouponMapper.getCouponTextList(adminAdCouponVO);
	}

	/**
	 * 쿠폰의 건물설정 정보 가져오기
	 * @param adminAdCouponVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminAdCouponVO> getCouponBuildList(AdminAdCouponVO adminAdCouponVO) throws Exception{

		return adminAdCouponMapper.getCouponBuildList(adminAdCouponVO);
	}

	/**
	 * 쿠폰 관리 - 수정
	 * @param dataMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void couponModProc(HashMap dataMap) throws Exception {
		String admin_id = SessionUtil.getAdminId();
		couponCheck(dataMap);
		
		String coupon_sort		= String.valueOf(dataMap.get("coupon_sort"));
		String coupon_start_day	= String.valueOf(dataMap.get("coupon_start_day"));
		String coupon_end_day	= String.valueOf(dataMap.get("coupon_end_day"));
		String coupon_seq 		= String.valueOf(dataMap.get("coupon_seq"));
		String owner_id			= String.valueOf(dataMap.get("owner_id"));
		
		List file = (List) dataMap.get("file");
		List buildList = (List) dataMap.get("build");
		List couponDtlText = (List) dataMap.get("coupon_dtl_txt");
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
		
		HashMap fileMap = (HashMap) file.get(0);
		String  fileSeq = String.valueOf(fileMap.get("file_seq"));
		Integer file_seq = null;
		
		CommonFileVO couponVO = new CommonFileVO();
		
		if("".equals(fileSeq)) {
			couponVO.setAdmin_id(admin_id);
			file_seq = commonFileService.insertFileSeq(couponVO);	//	파일 테이블 : 시퀀스 생성 > banner_img 키
			
			couponVO.setFile_seq(file_seq);
			couponVO.setFile_dtl_seq(0);
			couponVO.setFile_dtl_desc(	  	String.valueOf(	fileMap.get("file_dtl_desc")));
			couponVO.setFile_dtl_ext(  	  	String.valueOf(	fileMap.get("file_dtl_ext")));
			couponVO.setFile_dtl_origin(  	String.valueOf(	fileMap.get("file_dtl_origin")));
			couponVO.setFile_dtl_path(	 	String.valueOf(	fileMap.get("file_dtl_path")));
			couponVO.setFile_dtl_url_path( 	String.valueOf(	fileMap.get("file_dtl_url_path")));
			couponVO.setFile_type(		  	Integer.parseInt(String.valueOf(fileMap.get("file_type"))));
			commonFileService.insertFileDtl(couponVO);
		}else {
			file_seq = Integer.parseInt(fileSeq);
		}
		
		//	쿠폰 업데이트
		AdminAdCouponVO adminAdCouponVO = new AdminAdCouponVO();
		adminAdCouponVO.setCoupon_seq(Integer.parseInt(coupon_seq));
		adminAdCouponVO.setOwner_id(owner_id);
		adminAdCouponVO.setCoupon_img(file_seq);
		adminAdCouponVO.setCoupon_sort(Integer.parseInt(coupon_sort));
		adminAdCouponVO.setCoupon_start_day(coupon_start_day);
		adminAdCouponVO.setCoupon_end_day(coupon_end_day);
		adminAdCouponVO.setAdmin_id(admin_id);
		adminAdCouponMapper.updateCoupon(adminAdCouponVO);
		//	쿠폰 세부 내용 데이터 삭제
		adminAdCouponMapper.deleteCouponDtl(adminAdCouponVO);
		//	쿠폰 세부 내용 등록
		for(int i=0; i<couponDtlText.size(); i++) {
			AdminAdCouponVO dtlVO = new AdminAdCouponVO();
			dtlVO.setCoupon_seq(Integer.parseInt(coupon_seq));
			dtlVO.setCoupon_dtl_seq(i);
			dtlVO.setCoupon_dtl_text(String.valueOf(couponDtlText.get(i)));
			adminAdCouponMapper.insertCouponDtl(dtlVO);
		}
		//	쿠폰 건물 삭제
		adminAdCouponMapper.deleteBuildCoupon(adminAdCouponVO);
			
		//	쿠폰 건물 등록
		for(int i=0; i<buildList.size();i++) {
			AdminAdCouponVO buildVO = new AdminAdCouponVO();
			buildVO.setOwner_id(owner_id);
			buildVO.setBuild_seq(Integer.parseInt(String.valueOf(buildList.get(i))));
			buildVO.setCoupon_seq(Integer.parseInt(coupon_seq));
			adminAdCouponMapper.insertBuildCoupon(buildVO);
		}
		
		//	사용자 쿠폰 기간 업데이트
		adminAdCouponMapper.updateUserCoupon(adminAdCouponVO);
	}
	
	
	
}
