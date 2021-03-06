package honeyarcade.admin.ad.req;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminAdReqMapper {

	public int getTotalCount(AdminAdReqVO adminAdReqVO) throws Exception;

	public List<AdminAdReqVO> getAdReqList(AdminAdReqVO adminAdReqVO) throws Exception;

	public void reqDelProc(AdminAdReqVO adminAdReqVO) throws Exception;

	public AdminAdReqVO getReqView(AdminAdReqVO adminAdReqVO) throws Exception;

	public void reqAccept(AdminAdReqVO adminAdReqVO) throws Exception;

	public void payDtlProc(AdminAdReqVO adminAdReqVO) throws Exception;

	public void reqReject(AdminAdReqVO adminAdReqVO) throws Exception;

	public void updateAdReqStatus(AdminAdReqVO adminAdReqVO) throws Exception;

}
