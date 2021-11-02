package honeyarcade.admin.mgt.build;

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

/**
 * 건물 서식 다운로드
 *
 */
@Component("buildFormDown")
@Slf4j
public class BuildExcelDown extends AbstractXlsView {@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//AdminMgtBuildVO adminMgtBuildVO = (AdminMgtBuildVO) model.get("adminMgtBuildVO");
		//Integer build_seq = adminMgtBuildVO.getBuild_seq();
		List<AdminMgtBuildVO> floorList = (List<AdminMgtBuildVO>) model.get("floorList");
		
		//	sheet 1
		String[] cellArr = {"build_seq", "floor_seq" ,"store_ho"};
		
		Sheet sheet = workbook.createSheet("입력");
		Row row = sheet.createRow(0);
		
		for(int i=0; i<cellArr.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(cellArr[i]);
		}
		
		//	sheet 2
		Sheet sheet2 = workbook.createSheet("코드표");
		String[] sheet2Header = {"build_seq", "floor_seq", "floor_name"};
		Integer sheet2RowCnt = 0;
		if(floorList.size() == 0) {
			row = sheet2.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("해당하는 건물의 층정보가 없습니다. 층 입력후 이용하시기 바랍니다");
		}else {
			Row sheet2Row = sheet2.createRow(sheet2RowCnt++);
			for(int i=0; i<sheet2Header.length; i++) {
				Cell cell = sheet2Row.createCell(i);
				cell.setCellValue(sheet2Header[i]);	
			}
			
			for(int i=0; i<floorList.size();i++) {
				AdminMgtBuildVO vo = floorList.get(i);
				sheet2Row = sheet2.createRow(sheet2RowCnt++);
				Integer cellCnt = 0;
				Cell build_seq_cell = sheet2Row.createCell(cellCnt++);
				build_seq_cell.setCellValue(vo.getBuild_seq());
				
				Cell floor_seq = sheet2Row.createCell(cellCnt++);
				floor_seq.setCellValue(vo.getFloor_seq());
				
				Cell floor_name = sheet2Row.createCell(cellCnt++);
				floor_name.setCellValue(vo.getFloor_name());
			}
		}
		
		String fileName = String.valueOf(System.currentTimeMillis()) +".xls";
		
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	}

}
