package honeyarcade.admin.mgt.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import honeyarcade.admin.util.SessionUtil;

@Service
public class AdminMgtStoreService {
	
    @Autowired
    private MessageSource messageSource;
	
	@Autowired
	private AdminMgtStoreMapper adminMgtStoreMapper;

	/**
	 * 점포 관리 - 목록 총 갯수 구하기
	 * @param adminMgtStoreVO
	 * @return
	 * @throws Exception
	 */
	public int getTotalCount(AdminMgtStoreVO adminMgtStoreVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMgtStoreMapper.getTotalCount(adminMgtStoreVO);
	}

	/**
	 * 점포 관리 - 목록 구하기
	 * @param adminMgtStoreVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMgtStoreVO> getStoreList(AdminMgtStoreVO adminMgtStoreVO) throws Exception{
		// TODO Auto-generated method stub
		return adminMgtStoreMapper.getStoreList(adminMgtStoreVO);
	}

	/**
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void viewProc(Map paramMap) throws Exception{
		//	type : 1 숨김, 2 노출 
		List list = (ArrayList) paramMap.get("data");
		if(list.size()==0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		List<AdminMgtStoreVO> dataList = new ArrayList<AdminMgtStoreVO>();
		String admin_id = SessionUtil.getAdminId();
		Integer type = Integer.parseInt(String.valueOf(paramMap.get("type")));
		for(int i=0; i<list.size(); i++) {
			String owner_id = String.valueOf(list.get(i));
			AdminMgtStoreVO vo = new AdminMgtStoreVO();
			vo.setMod_id(admin_id);
			vo.setOwner_id(owner_id);
			if(type == 1) {
				vo.setDel_flag("Y");
			}else {
				vo.setDel_flag("N");
			}
			dataList.add(vo);
		}
		adminMgtStoreMapper.viewProc(dataList);
		adminMgtStoreMapper.updateMember(dataList);
	}

	/**
	 * 엑셀다운로드
	 * @param adminMgtStoreVO
	 * @return
	 * @throws Exception
	 */
	public List<AdminMgtStoreVO> getStoreExcelList(AdminMgtStoreVO adminMgtStoreVO) throws Exception{
		// TODO Auto-generated method stub
		return adminMgtStoreMapper.getStoreExcelList(adminMgtStoreVO);
	}
	
	/**
	 * 점포 테이블(TB_OWNER) 데이터 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void delProc(Map paramMap) throws Exception{
		List list = (ArrayList) paramMap.get("data");
		if(list.size()==0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		List<AdminMgtStoreVO> dataList = new ArrayList<AdminMgtStoreVO>();
		String admin_id = SessionUtil.getAdminId();
		for(int i=0; i<list.size(); i++) {
			String owner_id = String.valueOf(list.get(i));
			AdminMgtStoreVO vo = new AdminMgtStoreVO();
			vo.setMod_id(admin_id);
			vo.setOwner_id(owner_id);
			dataList.add(vo);
		}
		//	점포 삭제
		adminMgtStoreMapper.delProc(dataList);
		
		//	점주 아이디 삭제
		adminMgtStoreMapper.delMember(dataList);
	}

}
