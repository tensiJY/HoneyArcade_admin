package honeyarcade.admin.mgt.store;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import lombok.extern.slf4j.Slf4j;

@Component("storeListDown")
@Slf4j
public class StoreExcelView extends AbstractXlsView{
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List sheetList = (List) model.get("sheetList");
		
		String[] sheetHeader = {"No.", "층", "상점명", "대분류", "소분류", "소개글", "숨김 키워드", "노출 순위", "전화번호", "상태"};
		
		for(int i=0; i<sheetList.size(); i++) {
			Sheet sheet = workbook.createSheet((i+1) + " 페이지");
			
			List<AdminMgtStoreVO> dataList = (List<AdminMgtStoreVO>) sheetList.get(i);
			
			int rowCnt = 0;
			
			for(int j=0; j<dataList.size();j++) {
				
				int cellCnt = 0;
				
				Row row = null;
				
				if(j==0 ) {
					
					row = sheet.createRow(rowCnt++);
					
					for(int k=0; k<sheetHeader.length; k++) {
						Cell cell = row.createCell(cellCnt++);
						cell.setCellValue(sheetHeader[k]);
					}
					
					cellCnt = 0;	//	헤더 작성후 셀 초기화
				}
				
				AdminMgtStoreVO vo = dataList.get(j);
				
				row = sheet.createRow(rowCnt++);
				
				Cell no = row.createCell(cellCnt++);
				no.setCellValue(vo.getRow_num());
				
				Cell floor = row.createCell(cellCnt++);
				floor.setCellValue(vo.getFloor_name());
				
				Cell store = row.createCell(cellCnt++);
				store.setCellValue(vo.getStore_name());
				
				Cell lcate = row.createCell(cellCnt++);
				lcate.setCellValue(vo.getLcate_name());
				
				Cell mcate = row.createCell(cellCnt++);
				mcate.setCellValue(vo.getMcate_name());

				Cell store_text = row.createCell(cellCnt++);
				store_text.setCellValue(vo.getStore_text());
				
				Cell keword = row.createCell(cellCnt++);
				keword.setCellValue(vo.getStore_keyword());
				
				Cell sort = row.createCell(cellCnt++);
				sort.setCellValue(vo.getStore_sort());
				
				Cell tel = row.createCell(cellCnt++);
				tel.setCellValue(vo.getStore_tel());
				
				Cell view = row.createCell(cellCnt++);
				view.setCellValue(vo.getStore_status_text());
				
			}//
		}
		
		
		String fileName = String.valueOf(System.currentTimeMillis()) +".xls";
		
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		//response.setHeader("Content-Disposition", "attachment; filename=\"sample.xls\"");
	}


	

}
