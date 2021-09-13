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

	public int getTotalCount(AdminMgtStoreVO adminMgtStoreVO) throws Exception {
		// TODO Auto-generated method stub
		return adminMgtStoreMapper.getTotalCount(adminMgtStoreVO);
	}

	public List<AdminMgtStoreVO> getStoreList(AdminMgtStoreVO adminMgtStoreVO) throws Exception{
		// TODO Auto-generated method stub
		return adminMgtStoreMapper.getStoreList(adminMgtStoreVO);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void viewProc(Map paramMap) throws Exception{
		//	type : 2 노출, 1 숨김
		List list = (ArrayList) paramMap.get("data");
		if(list.size()==0) {
			throw new Exception( messageSource.getMessage("data.null", null, Locale.KOREA));
		}
		
		List<AdminMgtStoreVO> dataList = new ArrayList<AdminMgtStoreVO>();
		String admin_id = SessionUtil.getAdminId();
		
		Integer store_status = Integer.parseInt(String.valueOf(paramMap.get("type")));
		
		for(int i=0; i<list.size(); i++) {
			String owner_id = String.valueOf(list.get(i));
			
			
			AdminMgtStoreVO vo = new AdminMgtStoreVO();
			vo.setAdmin_id(admin_id);
			vo.setOwner_id(owner_id);
			vo.setStore_status(store_status);
			dataList.add(vo);
		}
		
		adminMgtStoreMapper.viewProc(dataList);
	}

	public List<AdminMgtStoreVO> getStoreExcelList(AdminMgtStoreVO adminMgtStoreVO) throws Exception{
		// TODO Auto-generated method stub
		return adminMgtStoreMapper.getStoreExcelList(adminMgtStoreVO);
	}

}
