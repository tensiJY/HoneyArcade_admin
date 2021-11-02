package honeyarcade.admin.set.ntc;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminSetNtcService {
	
	@Autowired
	private AdminSetNtcMapper adminSetNtcMapper;
	
    @Autowired
    private MessageSource messageSource;
	
	/**
	 * APP 내 공지사항 목록 가져오기
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetNtcVO> getAppList() throws Exception {
		return adminSetNtcMapper.getAppList();
	}

	/**
	 * 사장님 페이지 공지사항
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetNtcVO> getNtcList() throws Exception{
		return adminSetNtcMapper.getNtcList();
	}

	/**
	 * 사장님 페이지 점포회원 전용 공지사항
	 * @return
	 * @throws Exception
	 */
	public List<AdminSetNtcVO> getOwnerNtcList() throws Exception {
		return adminSetNtcMapper.getOwnerNtcList();
	}

	/**
	 * 공지사항 삭제 프로세스
	 * @param adminSetNtcVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void ntcDelProc(AdminSetNtcVO adminSetNtcVO) throws Exception{
		adminSetNtcMapper.ntcDelProc(adminSetNtcVO);
		
	}

	/**
	 * 공지사항 내용 가져오기
	 * @param adminSetNtcVO
	 * @return
	 * @throws Exception
	 */
	public AdminSetNtcVO getNtc(AdminSetNtcVO adminSetNtcVO) throws Exception{
		return adminSetNtcMapper.getNtc(adminSetNtcVO);
	}

	/**
	 * 공지사항 수정 프로세스
	 * @param adminSetNtcVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void ntcModProc(AdminSetNtcVO adminSetNtcVO) throws Exception {
		checkData(adminSetNtcVO);
		adminSetNtcMapper.ntcModProc(adminSetNtcVO);
	}

	/**
	 * 데이터 체크
	 * @param adminSetNtcVO
	 * @throws Exception
	 */
	private void checkData(AdminSetNtcVO adminSetNtcVO) throws Exception{
		if("".equals(adminSetNtcVO.getNtc_text())) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		if("".equals(adminSetNtcVO.getNtc_text())) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
	}

	/**
	 * 공지사항 저장 프로세스
	 * @param adminSetNtcVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void writeProc(AdminSetNtcVO adminSetNtcVO) throws Exception {
		checkData(adminSetNtcVO);
		adminSetNtcMapper.ntcWriteProc(adminSetNtcVO);
	}
	
}
