package honeyarcade.admin.common.file;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface CommonFileMapper {

	/**
	 * 파일 시퀀스 생성
	 * @param commonFileVO
	 * @return
	 * @throws Exception
	 */
	public Integer insertFileSeq(CommonFileVO commonFileVO) throws Exception;

	/**
	 * 파일 디테일 저장
	 * @param commonFileVO
	 * @throws Exception
	 */
	public void insertFileDtl(CommonFileVO commonFileVO) throws Exception;

	/**
	 * 파일 시퀀스 삭제
	 * @param commonFileVO
	 * @throws Exception
	 */
	public void delFileSeq(CommonFileVO commonFileVO) throws Exception;

	/**
	 * 파일 디테일 정보 가져오기
	 * @param commonFileVO
	 * @return
	 */
	public CommonFileVO getFileDtlInfo(CommonFileVO commonFileVO);

	/**
	 * 파일 디테일 정보 가져오기
	 * @param commonFileVO
	 * @return
	 * @throws Exception
	 */
	public List<CommonFileVO> getFileDtlList(CommonFileVO commonFileVO) throws Exception;

	/**
	 * 파일 시퀀스로 파일 디테일 테이블 삭제하기
	 * @param file_seq
	 * @throws Exception
	 */
	public void delDtlFileOfFileSeq(Integer file_seq) throws Exception;
	
	
}
