package honeyarcade.admin.common.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonApiService {
	
	@Autowired
	private CommonApiMapper commonApiMapper;
	
	public List<CommonApiVO> getSido() throws Exception{
		// TODO Auto-generated method stub
		return commonApiMapper.getSido();
	}

	public List<CommonApiVO> getSigungu(CommonApiVO commonApiVO) throws Exception{
		// TODO Auto-generated method stub
		return commonApiMapper.getSigungu(commonApiVO);
	}

	public List<CommonApiVO> getDong(CommonApiVO commonApiVO) throws Exception{
		// TODO Auto-generated method stub
		return commonApiMapper.getDong(commonApiVO);
	}

	public List<CommonApiVO> getBuild(CommonApiVO commonApiVO) throws Exception{
		// TODO Auto-generated method stub
		return commonApiMapper.getBuild(commonApiVO);
	}

	public List<CommonApiVO> getFloor(CommonApiVO commonApiVO) throws Exception{
		// TODO Auto-generated method stub
		return commonApiMapper.getFloor(commonApiVO);
	}

	

}