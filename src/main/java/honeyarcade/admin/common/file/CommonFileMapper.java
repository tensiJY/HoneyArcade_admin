package honeyarcade.admin.common.file;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface CommonFileMapper {

	//	파일 시퀀스 생성
	public Integer insertFileSeq(CommonFileVO vo) throws Exception;

	//	파일 디테일 저장
	public void insertFileDtl(CommonFileVO vo) throws Exception;

	//	파일 시퀀스 삭제
	public void delFileSeq(CommonFileVO vo) throws Exception;
	
	/*
	//	파일 시퀀스 생성
	public int insertFileSeq(CommonFileVO commonFileVO) throws Exception;
	
	//	파일 시퀀스 삭제
	public void delFile(CommonFileVO commonFileVO) throws Exception;
	
	//	파일 디테일 시퀀스 삭제
	public void delDtlFile(CommonFileVO commonFileVO) throws Exception;

	//	파일 디테일 인서트
	public int insertFileDtl(CommonFileVO commonFileVO) throws Exception;
	*/	
}
