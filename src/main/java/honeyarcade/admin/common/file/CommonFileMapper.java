package honeyarcade.admin.common.file;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface CommonFileMapper {

	//	파일 시퀀스 생성
	public Integer insertFileSeq(CommonFileVO vo) throws Exception;

	//	파일 디테일 저장
	public void insertFileDtl(CommonFileVO vo) throws Exception;

	//	파일 시퀀스 삭제
	public void delFileSeq(CommonFileVO vo) throws Exception;

	//	파일 디테일 정보 가져오기
	public CommonFileVO getFileDtlInfo(CommonFileVO commonFileVO);
	
	
}
