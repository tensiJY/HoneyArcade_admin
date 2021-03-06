package honeyarcade.admin.mgt.build;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.common.api.CommonApiVO;
import honeyarcade.admin.common.file.CommonFileService;
import honeyarcade.admin.common.file.CommonFileVO;
import honeyarcade.admin.util.FileUtil;
import honeyarcade.admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminMgtBuildService {
	
    @Autowired
    private MessageSource messageSource;
	
	@Autowired
	private AdminMgtBuildMapper adminMgtBuildMapper;
	
	@Autowired
	private CommonFileService commonFileService;

	//	건물 목록 가져오기
	public List<AdminMgtBuildVO> getBuildList(AdminMgtBuildVO adminMgtBuildVO) throws Exception {
		return adminMgtBuildMapper.getBuildList(adminMgtBuildVO);
	}

	//	건물 등록 - 저장
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void buildInsert(AdminMgtBuildVO adminMgtBuildVO) throws Exception{
		//	시도
		if(adminMgtBuildVO.getSido_seq() == null) {
			throw new Exception( messageSource.getMessage("data.null.sido", null, Locale.KOREA));
		}
		
		//	군구
		if(adminMgtBuildVO.getSigungu_seq() == null) {
			throw new Exception( messageSource.getMessage("data.null.sigungu", null, Locale.KOREA));
		}
		
		//	지역명
		if(adminMgtBuildVO.getDong_seq() == null) {
			throw new Exception( messageSource.getMessage("data.null.dong", null, Locale.KOREA));
		}
		
		//	건물이름
		if(adminMgtBuildVO.getBuild_name() == null) {
			throw new Exception( messageSource.getMessage("data.null.build.name", null, Locale.KOREA));
		}
		
		//	주소좌표 - 경도
		if(adminMgtBuildVO.getBuild_x() == null) {
			throw new Exception( messageSource.getMessage("data.null.build.x", null, Locale.KOREA));
		}
		
		//	주소좌표 - 위도
		if(adminMgtBuildVO.getBuild_y() == null) {
			throw new Exception( messageSource.getMessage("data.null.build.y", null, Locale.KOREA));
		}
		
		adminMgtBuildVO.setAdmin_id(SessionUtil.getAdminId());
		
		adminMgtBuildMapper.buildInsert(adminMgtBuildVO);
		
	}
	
	//	건물 목록 - 건물의 총 갯수 가져오기
	public int getBuildTotalCount(AdminMgtBuildVO adminMgtBuildVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMgtBuildMapper.getBuildTotalCount(adminMgtBuildVO);
	}

	//	건물 목록 - 건물 활성화
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void enableProc(List<AdminMgtBuildVO> list) throws Exception{
		// TODO Auto-generated method stub
		
		if(list.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		String admin_id = SessionUtil.getAdminId();
		
		for(AdminMgtBuildVO vo: list) {
			vo.setAdmin_id(admin_id);
		}
		
		adminMgtBuildMapper.enableProc(list);
		
		
	}

	//	건물 목록 - 건물 비활성화
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void disableProc(List<AdminMgtBuildVO> list) throws Exception{
		// TODO Auto-generated method stub
		if(list.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		String admin_id = SessionUtil.getAdminId();
		
		for(AdminMgtBuildVO vo: list) {
			vo.setAdmin_id(admin_id);
		}
		
		adminMgtBuildMapper.disableProc(list);
		
	}

	//	건물 목록 - 건물 삭제
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void buildDelProc(List<AdminMgtBuildVO> list) throws Exception {
		// TODO Auto-generated method stub
		if(list.size() == 0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		String admin_id = SessionUtil.getAdminId();
		
		for(AdminMgtBuildVO vo: list) {
			vo.setAdmin_id(admin_id);
		}
		
		adminMgtBuildMapper.buildDelProc(list);
		
	}

	//	건물 목록 - 코드 관리 - 시도 삭제
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sidoDelProc(List<CommonApiVO> list) throws Exception{
					
		String admin_id = SessionUtil.getAdminId();
		
		for(CommonApiVO commonApiVO: list) {
			
			if(commonApiVO.getSido_seq() == null) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			}
			
			commonApiVO.setAdmin_id(admin_id);
			//	지역 삭제
			adminMgtBuildMapper.dongDelProcSidoSeq(commonApiVO);
			//	시군구 삭제
			adminMgtBuildMapper.sigunguDelProcSidoSeq(commonApiVO);
			//	시도 삭제
			adminMgtBuildMapper.sidoDelProcSidoSeq(commonApiVO);
			
		}
		
		
	}
	
	//	건물 등록 - 코드 관리 - 시도 수정
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sidoModProc(List<CommonApiVO> list) throws Exception{
				
		String admin_id = SessionUtil.getAdminId();
		
		for(CommonApiVO commonApiVO: list) {
			if("".equals(commonApiVO.getSido_name())) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			};
			
			commonApiVO.setAdmin_id(admin_id);
		}
		
		adminMgtBuildMapper.sidoModProc(list);
		
	}

	//	건물 등록 - 코드 관리 - 시군구 삭제
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sigunguDelProc(List<CommonApiVO> list) throws  Exception {
		
		String admin_id = SessionUtil.getAdminId();
		
		for(CommonApiVO commonApiVO: list) {
			commonApiVO.setAdmin_id(admin_id);
			
			if(commonApiVO.getSigungu_seq() == null) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			}
			
			//	지역 삭제
			adminMgtBuildMapper.dongDelProcSigunguSeq(commonApiVO);
			//	시군구 삭제
			adminMgtBuildMapper.sigunguDelProcSigunguSeq(commonApiVO);
		}
		
	}

	//	건물 등록 - 코드 관리 - 시군구 수정
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void sigunguModProc(List<CommonApiVO> list) throws  Exception {
		// TODO Auto-generated method stub
		
		String admin_id = SessionUtil.getAdminId();
		
		for(CommonApiVO commonApiVO: list) {
			if("".equals(commonApiVO.getSigungu_name())) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			};
			
			if(commonApiVO.getSigungu_seq() == null) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			}
			commonApiVO.setAdmin_id(admin_id);
		}
		
		
		adminMgtBuildMapper.sigunguModProc(list);
	}

	//	건물 등록 - 코드 관리 - 동 삭제
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void dongDelProc(List<CommonApiVO> list) throws Exception {
				
		String admin_id = SessionUtil.getAdminId();
		
		for(CommonApiVO commonApiVO: list) {
			commonApiVO.setAdmin_id(admin_id);
			
			if(commonApiVO.getDong_seq() == null) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			}
			
			//	지역 삭제
			adminMgtBuildMapper.dongDelProcDongSeq(commonApiVO);
		}
	}

	//	건물 등록 - 코드 관리 - 동 수정	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void dongModProc(List<CommonApiVO> list) throws Exception{
		String admin_id = SessionUtil.getAdminId();
		
		for(CommonApiVO commonApiVO: list) {
			if("".equals(commonApiVO.getDong_name())) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			};
			
			if(commonApiVO.getDong_seq() == null) {
				throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
			}
			
			commonApiVO.setAdmin_id(admin_id);
		}
		
		adminMgtBuildMapper.dongModProc(list);
		
	}

	//	건물 등록 - 코드 관리 - 시도 생성	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public CommonApiVO sidoWriteProc(CommonApiVO commonApiVO) throws Exception {
		String admin_id = SessionUtil.getAdminId();
		if("".equals(commonApiVO.getSido_name())){
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}  
		commonApiVO.setAdmin_id(admin_id);
		return adminMgtBuildMapper.sidoWriteProc(commonApiVO);
	}

	//	건물 등록 - 코드 관리 - 시군구 생성	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public CommonApiVO sigunguWriteProc(CommonApiVO commonApiVO) throws Exception{
		String admin_id = SessionUtil.getAdminId();
		if("".equals(commonApiVO.getSigungu_name())){
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		if(commonApiVO.getSido_seq() == null) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		commonApiVO.setAdmin_id(admin_id);
		return adminMgtBuildMapper.sigunguWriteProc(commonApiVO);
	}

	//	건물 등록 - 코드 관리 - 동 생성	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})	
	public CommonApiVO dongWriteProc(CommonApiVO commonApiVO) throws Exception{
		String admin_id = SessionUtil.getAdminId();
		if("".equals(commonApiVO.getDong_name())){
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		if(commonApiVO.getSigungu_seq() == null) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		commonApiVO.setAdmin_id(admin_id);
		return adminMgtBuildMapper.dongWriteProc(commonApiVO);
	}

	//	건물 등록 - 업종 대분류 조회
	public List<AdminMgtBuildVO> getLcateList(AdminMgtBuildVO adminMgtBuildVO) throws Exception{
		return adminMgtBuildMapper.getLcateList(adminMgtBuildVO);
	}

	//	건물 등록 - 업종 소분류 조회
	public List<AdminMgtBuildVO> getMcateList(AdminMgtBuildVO adminMgtBuildVO) throws Exception {
		return adminMgtBuildMapper.getMcateList(adminMgtBuildVO);
	}

	//	건물 등록 - 업종 도면 조회	
	public List<AdminMgtBuildVO> getFloorList(AdminMgtBuildVO adminMgtBuildVO) throws Exception{
		return adminMgtBuildMapper.getFloorList(adminMgtBuildVO);
	}

	//	최종 수정일 조회
	public String getBuildLstModDt(AdminMgtBuildVO adminMgtBuildVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMgtBuildMapper.getBuildLstModDt(adminMgtBuildVO);
	}
	
	//	건물 등록 - 대카테고리, 소카테고리, 도면 등 저장 및 수정
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void dtlWriteProc(HashMap paramMap, HttpServletRequest req) throws Exception {
		Integer dtlType = Integer.parseInt(String.valueOf(paramMap.get("dtlType")));
		Integer build_seq =Integer.parseInt(String.valueOf(paramMap.get("build_seq")));
		String login_id = SessionUtil.getAdminId();
		List lcateList = (List) paramMap.get("lcate_list");	//	대분류
		List floorList = (List) paramMap.get("floor_list");	//	층 목록
		List delFileList = null;	//	파일 삭제
		
		int cnt = 0;
		//	대분류 갯수 체크
		for(int i=0; i<lcateList.size(); i++) {
			HashMap lcateMap = (HashMap) lcateList.get(i);
			if(lcateMap.get("lcate_name") == null || "".equals(String.valueOf(lcateMap.get("lcate_name")))) {
				cnt++;
			}
		}
		
		if(cnt == 3) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if(dtlType == 2) {
			//	파일 시퀀스 삭제	(디테일X, 파일 시퀀스만 플래그 설정)
			delFileList = (List) paramMap.get("del_file_seq");
			for(int i=0; i<delFileList.size(); i++) {
				CommonFileVO vo = new CommonFileVO();
				vo.setAdmin_id(login_id);
				vo.setFile_seq(Integer.parseInt(String.valueOf(delFileList.get(i))));
				log.info("파일 시퀀스를 삭제합니다");
				
				commonFileService.delFileSeq(vo);
			}
			//	소 카테고리 삭제
			adminMgtBuildMapper.delMcate(build_seq);
			
			//	대 카테고리 삭제
			adminMgtBuildMapper.delLcate(build_seq);

			//	층 삭제
			adminMgtBuildMapper.delFloor(build_seq);
		}

		for(int i=0; i<lcateList.size(); i++) {
			HashMap lcateMap = (HashMap) lcateList.get(i);
			if(lcateMap.get("lcate_name") == null || "".equals(String.valueOf(lcateMap.get("lcate_name")))) {
				continue;
			}
			String lcate_name = String.valueOf(lcateMap.get("lcate_name"));
			
			AdminMgtBuildVO lcateVO = new AdminMgtBuildVO();
			lcateVO.setAdmin_id(login_id);
			lcateVO.setLcate_name(lcate_name);
			lcateVO.setBuild_seq(build_seq);
			
			HashMap fileMap = (HashMap) lcateMap.get("file");
			HashMap fileObjMap = null;
			
			boolean hasFile = (boolean) fileMap.get("hasFile");
			if(!hasFile) {
				lcateVO.setFile_seq(null);
			}else {
 
				 fileObjMap = (HashMap) fileMap.get("fileObj");
				 CommonFileVO vo = new CommonFileVO();
				 vo.setAdmin_id(login_id);
				 
				 Integer file_seq = null;
				 //	시퀀스가 없는 경우 >>> 이미지 업로드
				 if(String.valueOf(fileObjMap.get("file_seq")) == "") {	//	파일 시퀀스가 없는 경우
					 
					 log.info("file_seq : "+ file_seq );
					 
					 vo.setFile_dtl_desc(	  String.valueOf(fileObjMap.get("file_dtl_desc")));
					 vo.setFile_dtl_ext(  	  String.valueOf(fileObjMap.get("file_dtl_ext")));
					 vo.setFile_dtl_origin(	  String.valueOf(fileObjMap.get("file_dtl_origin")));
					 vo.setFile_dtl_path(	  String.valueOf(fileObjMap.get("file_dtl_path")));
					 vo.setFile_dtl_url_path( String.valueOf(fileObjMap.get("file_dtl_url_path")));
					 vo.setFile_type(		  Integer.parseInt(String.valueOf(fileObjMap.get("file_type"))));
					 
					 file_seq = commonFileService.insertFileSeq(vo);
					 vo.setFile_seq(file_seq);
					 vo.setFile_dtl_seq(0);
					 commonFileService.insertFileDtl(vo);
				//	시퀀스가 있는 경우 >> 그대로	 
				 }else {
					 
					 file_seq = Integer.parseInt(String.valueOf(fileObjMap.get("file_seq")));
				 }
				 
				 lcateVO.setFile_seq(file_seq); 		
			}
			lcateVO.setLcate_seq(i);
			
			adminMgtBuildMapper.insertLcate(lcateVO);
			
			List mcateList = (List) lcateMap.get("mcate_list");		//	소 분류
			
			for(int j=0; j<mcateList.size();j++) {
				HashMap mcateMap = (HashMap) mcateList.get(j);
				String mcate_name = String.valueOf(mcateMap.get("mcate_name"));
				if("".equals(mcate_name) || mcate_name == null) {
					continue;
				}
				AdminMgtBuildVO mcateVO = new AdminMgtBuildVO();
				mcateVO.setAdmin_id(login_id);
				mcateVO.setBuild_seq(build_seq);
				mcateVO.setLcate_seq(i);
				mcateVO.setMcate_seq(j);
				mcateVO.setMcate_name(mcate_name);
				adminMgtBuildMapper.insertMcate(mcateVO);
			}
			
		}
		
		for(int i=0; i<floorList.size();i++) {
			HashMap floorMap = (HashMap) floorList.get(i);
			
			String floorName = String.valueOf(floorMap.get("floor_name"));
			
			AdminMgtBuildVO floorVO = new AdminMgtBuildVO();
			floorVO.setBuild_seq(build_seq);
			floorVO.setFloor_type(Integer.parseInt(String.valueOf(floorMap.get("floor_type"))));
			floorVO.setFloor_seq(Integer.parseInt(String.valueOf(floorMap.get("floor_seq"))));
			floorVO.setFloor_name(String.valueOf(floorMap.get("floor_name")));

			HashMap floorFileMap = (HashMap) floorMap.get("floor_file");
			boolean hasFile = (boolean) floorFileMap.get("hasFile");
			
			
			if(!hasFile) {
				floorVO.setFile_seq(null);
			}else {
				HashMap fileObjMap = (HashMap) floorFileMap.get("fileObj");
				CommonFileVO vo = new CommonFileVO();
				vo.setAdmin_id(login_id);
				 
				Integer file_seq = null;
				//	시퀀스가 없는 경우 >>> 이미지 업로드
				if(String.valueOf(fileObjMap.get("file_seq")) == "") {	//	파일 시퀀스가 없는 경우
					
					log.info("file_seq : "+ file_seq );
					 
					vo.setFile_dtl_desc(	  String.valueOf(fileObjMap.get("file_dtl_desc")));
					vo.setFile_dtl_ext(  	  String.valueOf(fileObjMap.get("file_dtl_ext")));
					vo.setFile_dtl_origin(	  String.valueOf(fileObjMap.get("file_dtl_origin")));
					vo.setFile_dtl_path(	  String.valueOf(fileObjMap.get("file_dtl_path")));
					vo.setFile_dtl_url_path( String.valueOf(fileObjMap.get("file_dtl_url_path")));
					vo.setFile_type(		  Integer.parseInt(String.valueOf(fileObjMap.get("file_type"))));
					 
					file_seq = commonFileService.insertFileSeq(vo);
					vo.setFile_seq(file_seq);
					vo.setFile_dtl_seq(0);
					commonFileService.insertFileDtl(vo);
				//	시퀀스가 있는 경우 >> 그대로	 
				}else {
					 
					 file_seq = Integer.parseInt(String.valueOf(fileObjMap.get("file_seq")));
				 }
				 floorVO.setFile_seq(file_seq);
				 
				 HashMap<String, Integer> imgMap = FileUtil.getImgXY(String.valueOf(fileObjMap.get("file_dtl_path")));
				 floorVO.setImgX(imgMap.get("imgX"));
				 floorVO.setImgY(imgMap.get("imgY"));
			}
			adminMgtBuildMapper.insertFloor(floorVO);
		}
		
		AdminMgtBuildVO updateVO = new AdminMgtBuildVO();
		updateVO.setAdmin_id(login_id);
		updateVO.setBuild_seq(build_seq);
		adminMgtBuildMapper.buildModDate(updateVO);
	}//	함수 종료

	public List<AdminMgtBuildVO> getLcateCountOfMcate(AdminMgtBuildVO adminMgtBuildVO) {
		
		return adminMgtBuildMapper.getLcateCountOfMcate(adminMgtBuildVO);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void insertOwner(List<AdminMgtBuildVO> ownerList) throws Exception {
		adminMgtBuildMapper.insertOwner(ownerList);
	}


}
