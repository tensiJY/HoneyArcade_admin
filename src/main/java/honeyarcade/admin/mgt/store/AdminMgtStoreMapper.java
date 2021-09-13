package honeyarcade.admin.mgt.store;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMgtStoreMapper {

	int getTotalCount(AdminMgtStoreVO adminMgtStoreVO) throws Exception;

	List<AdminMgtStoreVO> getStoreList(AdminMgtStoreVO adminMgtStoreVO) throws Exception;

	void viewProc(List<AdminMgtStoreVO> dataList) throws Exception;

	List<AdminMgtStoreVO> getStoreExcelList(AdminMgtStoreVO adminMgtStoreVO) throws Exception;

}
