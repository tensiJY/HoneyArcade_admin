package honeyarcade.admin.mgt.build;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import honeyarcade.admin.common.api.CommonApiVO;

@Mapper
public interface AdminMgtBuildMapper {


	public List<AdminMgtBuildVO> getBuildList(AdminMgtBuildVO adminMgtBuildVO) throws Exception;

	public void buildInsert(AdminMgtBuildVO adminMgtBuildVO) throws Exception;

	public int getBuildTotalCount(AdminMgtBuildVO adminMgtBuildVO) throws Exception;

	public void enableProc(List<AdminMgtBuildVO> list) throws Exception;

	public void disableProc(List<AdminMgtBuildVO> list) throws Exception;

	public void buildDelProc(List<AdminMgtBuildVO> list) throws Exception;

	public void dongDelProcSidoSeq(CommonApiVO commonApiVO) throws Exception;

	public void sigunguDelProcSidoSeq(CommonApiVO commonApiVO) throws Exception;

	public void sidoDelProcSidoSeq(CommonApiVO commonApiVO) throws Exception;

	public void sidoModProc(List<CommonApiVO> list) throws Exception;

	public void dongDelProcSigunguSeq(CommonApiVO commonApiVO) throws Exception;

	public void sigunguDelProcSigunguSeq(CommonApiVO commonApiVO) throws Exception;

	public void sigunguModProc(List<CommonApiVO> list) throws Exception;

	public void dongDelProcDongSeq(CommonApiVO commonApiVO) throws Exception;

	public void dongModProc(List<CommonApiVO> list) throws Exception;

	public CommonApiVO sidoWriteProc(CommonApiVO commonApiVO) throws Exception;

	public CommonApiVO sigunguWriteProc(CommonApiVO commonApiVO) throws Exception;

	public CommonApiVO dongWriteProc(CommonApiVO commonApiVO) throws Exception;

	public List<AdminMgtBuildVO> getLcateList(AdminMgtBuildVO adminMgtBuildVO) throws Exception;

	public List<AdminMgtBuildVO> getMcateList(AdminMgtBuildVO adminMgtBuildVO) throws Exception;

	public List<AdminMgtBuildVO> getFloorList(AdminMgtBuildVO adminMgtBuildVO) throws Exception;

	//	대카테고리 등록
	public void insertLcate(AdminMgtBuildVO lcateVO) throws Exception;

	//	소카테고리 등록
	public void insertMcate(AdminMgtBuildVO lcateVO) throws Exception;
	
	//	층 도면 등록
	public void insertFloor(AdminMgtBuildVO floorVO) throws Exception;

	//	건물 수정일자 업데이트
	public void buildModDate(AdminMgtBuildVO adminMgtBuildVO) throws Exception;

	//	건물 수정일자 불러옴
	public String getBuildLstModDt(AdminMgtBuildVO adminMgtBuildVO) throws Exception;

	//	소분류 삭제
	public void delMcate(Integer build_seq) throws Exception;

	//	대분류 삭제
	public void delLcate(Integer build_seq) throws Exception;

	//	층 (도면) 삭제
	public void delFloor(Integer build_seq) throws Exception;

	public List<AdminMgtBuildVO> getLcateCountOfMcate(AdminMgtBuildVO adminMgtBuildVO);

	public void insertOwner(List<AdminMgtBuildVO> ownerList) throws Exception;

		

}
