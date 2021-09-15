package honeyarcade.admin.common.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class CommonFileService {

	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private CommonFileMapper commonFileMapper;

    //	파일 시퀀스 생성
	public Integer insertFileSeq(CommonFileVO vo) throws Exception {
		return commonFileMapper.insertFileSeq(vo);
	}

	//	디테일 저장
	public void insertFileDtl(CommonFileVO vo) throws Exception {
		commonFileMapper.insertFileDtl(vo);
	}

	//	파일 시퀀스 삭제(파일 삭제
	public void delFileSeq(CommonFileVO vo) throws Exception{
		commonFileMapper.delFileSeq(vo);
	}

	/**
	 * 파일 디테일 정보 가져오기
	 * @param commonFileVO
	 * @return
	 * @throws Exception
	 */
	public CommonFileVO getFileDtlInfo(CommonFileVO commonFileVO) throws Exception{
		// TODO Auto-generated method stub
		return commonFileMapper.getFileDtlInfo(commonFileVO);
	}
	

}
