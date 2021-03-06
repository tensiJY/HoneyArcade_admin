package honeyarcade.admin.member.owner;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.common.file.CommonFileService;
import honeyarcade.admin.common.file.CommonFileVO;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminMemberOwnerService {
	
	@Autowired
	private AdminMemberOwnerMapper adminMemberOwnerMapper;
	
    @Autowired
    private MessageSource messageSource;
	
	@Autowired
	private CommonFileService commonFileService;

	/**
	 * 회원 관리 : 점주 회원 - 분류별 회원 수 조회
	 * @param adminMemberOwnerVO 
	 * @return
	 * @throws Exception
	 */
	public AdminMemberOwnerVO getOwnerTypeCount(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception{
		// TODO Auto-generated method stub
		return adminMemberOwnerMapper.getOwnerTypeCount(adminMemberOwnerVO);
	}

	/**
	 * 회원 관리 : 점주 회원 - 전체 수 조회
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public int getTotalCount(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception{
		return adminMemberOwnerMapper.getTotalCount(adminMemberOwnerVO);
	}

	/**
	 * 회원 관리 : 점주 회목 - 목록 조회
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getOwnerList(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception {

		return adminMemberOwnerMapper.getOwnerList(adminMemberOwnerVO);
	}

	/**
	 * 회원 관리 : 상세 보기 - 회원 정보 가져오기
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public AdminMemberOwnerVO getOwnerInfo(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMemberOwnerMapper.getOwnerInfo(adminMemberOwnerVO);
	}

	/**
	 * 회원 관리 : 상세 보기 - 상품 목록
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getProductList(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMemberOwnerMapper.getProductList(adminMemberOwnerVO);
	}

	/**
	 * 회원 관리 : 상세 보기 - 오픈 시간
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getOpenDay(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception{
		// TODO Auto-generated method stub
		return adminMemberOwnerMapper.getOpenDay(adminMemberOwnerVO);
	}

	/**
	 * 회원 관리 : 상세 보기 - 휴게 시간
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getBreakDay(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMemberOwnerMapper.getBreakDay(adminMemberOwnerVO);
	}

	/**
	 * 회원관리 : 점주회원 - 승인
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void acceptProc(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception{
		// TODO Auto-generated method stub
		adminMemberOwnerMapper.acceptProc(adminMemberOwnerVO);
	}

	/**
	 * 회원관리 : 상세 보기 - 점포 휴무일
	 * @param adminMemberOwnerVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMemberOwnerVO> getDayOffList(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMemberOwnerMapper.getDayOffList(adminMemberOwnerVO);
	}

	//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void saveProc(Map dataMap) throws Exception{
		String 	admin_id		= SessionUtil.getAdminId();
		String	owner_id		= String.valueOf(dataMap.get("owner_id"));
		String	owner_email 	= String.valueOf(dataMap.get("owner_email"));
		String	owner_phone		= String.valueOf(dataMap.get("owner_phone"));
		String	build_seq		= String.valueOf(dataMap.get("build_seq"));
		String	store_name		= String.valueOf(dataMap.get("store_name"));
		String	store_link		= String.valueOf(dataMap.get("store_link"));
		String	lcate_seq		= String.valueOf(dataMap.get("lcate_seq"));
		String	mcate_seq		= String.valueOf(dataMap.get("mcate_seq"));
		String	store_text		= String.valueOf(dataMap.get("store_text"));
		String	store_keyword	= String.valueOf(dataMap.get("store_keyword"));
		String	store_sort		= String.valueOf(dataMap.get("store_sort"));
		String	main_file_seq	= String.valueOf(dataMap.get("main_file_seq"));
		
		if("".equals(owner_id)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(owner_email)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(owner_phone)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(store_name)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(store_text)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}

		if("".equals(store_keyword)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(store_sort)) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}		
		
		List	del_file	= (List) dataMap.get("del_file");
		List	openList	= (List) dataMap.get("open");
		List	breakList	= (List) dataMap.get("break");
		List	dayList		= (List) dataMap.get("day");
		List	productList	= (List) dataMap.get("product");
		List	main_img	= (List) dataMap.get("main_img");
		
		if(main_img.size()== 0) {
			throw new Exception("메인 이미지가 없습니다");
		}
		
		Map	proImg		= (Map) dataMap.get("proImg");
		Map	busiImg		= (Map) dataMap.get("busiImg");
		Integer pro_file_seq 		= proImg.isEmpty()? 0 : getFileSeq(proImg);		//	매장 프로필
		Integer busi_reg_file_seq 	= busiImg.isEmpty()? 0 : getFileSeq(busiImg);	//	사업자 등록번호
		Integer main_seq			= "".equals(main_file_seq) ? 0 : Integer.parseInt(main_file_seq);
		
		commonFileService.delDtlFileOfFileSeq(main_seq);
		for(int i=0; i<main_img.size(); i++) {
			HashMap imgMap = (HashMap) main_img.get(i);
			CommonFileVO imgVO = new CommonFileVO();
			imgVO.setFile_seq(main_seq);
			imgVO.setFile_dtl_seq(i);
			imgVO.setFile_type(Integer.parseInt(String.valueOf(imgMap.get("file_type"))));
			imgVO.setFile_dtl_path(String.valueOf(imgMap.get("file_dtl_path")));
			imgVO.setFile_dtl_desc(String.valueOf(imgMap.get("file_dtl_desc")));
			imgVO.setFile_dtl_origin(String.valueOf(imgMap.get("file_dtl_origin")));
			imgVO.setFile_dtl_ext(String.valueOf(imgMap.get("file_dtl_ext")));
			imgVO.setFile_dtl_url_path(String.valueOf(imgMap.get("file_dtl_url_path")));
			commonFileService.insertFileDtl(imgVO);
		}
		
		AdminMemberOwnerVO adminMemberOwnerVO = new AdminMemberOwnerVO();
		adminMemberOwnerVO.setOwner_id(owner_id);
		adminMemberOwnerVO.setAdmin_id(admin_id);
		adminMemberOwnerVO.setBuild_seq(Integer.parseInt(build_seq));
		adminMemberOwnerVO.setOwner_email(owner_email);
		adminMemberOwnerVO.setOwner_phone(owner_phone);
		adminMemberOwnerVO.setBusi_reg_file_seq(busi_reg_file_seq);
		adminMemberOwnerVO.setStore_name(store_name);
		adminMemberOwnerVO.setStore_sort(Integer.parseInt(store_sort));
		adminMemberOwnerVO.setStore_keyword(store_keyword);
		adminMemberOwnerVO.setStore_text(store_text);
		adminMemberOwnerVO.setLcate_seq(Integer.parseInt(lcate_seq));
		adminMemberOwnerVO.setMcate_seq(Integer.parseInt(mcate_seq));
		adminMemberOwnerVO.setStore_link(store_link);
		adminMemberOwnerVO.setMain_file_seq(main_seq);
		adminMemberOwnerVO.setPro_file_seq(pro_file_seq);
		
		//	점주 업데이트
		adminMemberOwnerMapper.updateOwner(adminMemberOwnerVO);
		
		//	파일 삭제
		for(int i=0; i<del_file.size(); i++) {
			Integer file_seq = Integer.parseInt(String.valueOf(del_file.get(i)));
			CommonFileVO vo = new CommonFileVO();
			vo.setFile_seq(file_seq);
			vo.setAdmin_id(admin_id);
			commonFileService.delFileSeq(vo);
			log.info(" 회원 관리 - 점주 회원 - del file_seq : " + file_seq);
		}
		adminMemberOwnerMapper.deleteOpen(adminMemberOwnerVO);		//	영업시간 삭제
		adminMemberOwnerMapper.deleteBreak(adminMemberOwnerVO);		//	휴게시간 삭제
		adminMemberOwnerMapper.deleteDayOff(adminMemberOwnerVO);	//	휴무일 삭제
		adminMemberOwnerMapper.deleteProduct(adminMemberOwnerVO);	//	상품 삭제
		
		//	영업시간 등록
		for(int i=0; i<openList.size();i++) {
			Map openMap = (Map) openList.get(i);
			AdminMemberOwnerVO vo = new AdminMemberOwnerVO();
			vo.setOwner_id(owner_id);
			vo.setOpen_day(Integer.parseInt(String.valueOf(openMap.get("open_day"))));
			vo.setOpen_time(String.valueOf(openMap.get("open_time")));
			vo.setClose_time(String.valueOf(openMap.get("close_time")));
			adminMemberOwnerMapper.insertOpen(vo);
		}
				
		//	휴게시간 등록
		for(int i=0; i<breakList.size();i++) {
			Map map = (Map) breakList.get(i);
			AdminMemberOwnerVO vo = new AdminMemberOwnerVO();
			vo.setOwner_id(owner_id);
			vo.setBreak_day(Integer.parseInt(String.valueOf(map.get("break_day"))));
			vo.setOpen_time(String.valueOf(map.get("open_time")));
			vo.setClose_time(String.valueOf(map.get("close_time")));	
			adminMemberOwnerMapper.insertBreak(vo);
		}
		
		//	휴무일 등록
		for(int i=0; i<dayList.size(); i++) {
			Map map = (Map) dayList.get(i);
			AdminMemberOwnerVO vo = new AdminMemberOwnerVO();
			vo.setOwner_id(owner_id);
			vo.setDay_off_week(String.valueOf(map.get("day_off_week")));	
			vo.setDay_off_day(String.valueOf(map.get("day_off_day")));
			adminMemberOwnerMapper.insertDayOff(vo);
		}
		
		//	상품 등록
		for(int i=0; i<productList.size();i++) {
			Map map = (Map) productList.get(i);
			Integer file_seq	 	= getFileSeq(map);
			String	product_name	= String.valueOf(map.get("product_name"));
			String	product_price	= String.valueOf(map.get("product_price"));
			
			if("".equals(product_name)) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			}
			
			if("".equals(product_price)) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			}
			
			AdminMemberOwnerVO vo = new AdminMemberOwnerVO();
			vo.setFile_seq(file_seq);
			vo.setProduct_name(product_name);
			vo.setProduct_price(Integer.parseInt(product_price));
			vo.setOwner_id(owner_id);
			vo.setProduct_seq(i);
			adminMemberOwnerMapper.insertProduct(vo);
		}
	}

	private Integer getFileSeq(Map fileMap) throws Exception{
		String 	admin_id	= SessionUtil.getAdminId();
		String	fileSeq		= (String) fileMap.get("file_seq");
		Integer file_seq 	= null;
		if("".equals(fileSeq)) {
			CommonFileVO vo = new CommonFileVO();
			vo.setAdmin_id(admin_id);
			file_seq = commonFileService.insertFileSeq(vo);
			vo.setFile_seq(file_seq);
			vo.setFile_type(Integer.parseInt(String.valueOf(fileMap.get("file_type"))));
			vo.setFile_dtl_path(String.valueOf(fileMap.get("file_dtl_path")));
			vo.setFile_dtl_desc(String.valueOf(fileMap.get("file_dtl_desc")));
			vo.setFile_dtl_origin(String.valueOf(fileMap.get("file_dtl_origin")));
			vo.setFile_dtl_ext(String.valueOf(fileMap.get("file_dtl_ext")));
			vo.setFile_dtl_url_path(String.valueOf(fileMap.get("file_dtl_url_path")));
			vo.setFile_dtl_seq(0);
			commonFileService.insertFileDtl(vo);
		}else {
			file_seq = Integer.parseInt(fileSeq);
		}
		return file_seq;
	}

	/**
	 * 회원관리 : 점주회원 - 점포상태 변경
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void statusProc(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception {
		// TODO Auto-generated method stub
		adminMemberOwnerMapper.statusProc(adminMemberOwnerVO);
		adminMemberOwnerMapper.updateMemberFlag(adminMemberOwnerVO);
	}

	/**
	 * 회원관리 : 점주회원 - 반려
	 * @param adminMemberOwnerVO
	 * @throws Exception
	 */
	public void rejectProc(AdminMemberOwnerVO adminMemberOwnerVO) throws Exception{
		// TODO Auto-generated method stub
		adminMemberOwnerMapper.rejectProc(adminMemberOwnerVO);
	}

	
}
