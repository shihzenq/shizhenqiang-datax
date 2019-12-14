package com.wugui.dataxweb.export;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UserErrorExcel extends Excel {

	public void export(HttpServletRequest request, JSONArray array, OutputStream outputStream) throws IOException {
		// 设置生成路径
		String filepath = request.getSession().getServletContext().getRealPath("static") + File.separator
				+ "voucherupload" + File.separator;
		File file = new File(filepath);
		if (!file.exists()) {
			file.mkdirs();// 不存在该路径则自动生成
		}

		int startCellNumber = 0;
		int startRowNumber = 0;
		JSONObject msg;

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("导入用户错误信息");

		CellRangeAddress cra = new CellRangeAddress(startRowNumber, startRowNumber, startCellNumber, startCellNumber + 1); // 起始行, 终止行, 起始列, 终止列
		sheet.addMergedRegion(cra);

		// 居中主标题
		XSSFCell headCell1 = sheet.createRow(0).createCell(startCellNumber);
		headCell1.setCellStyle(getTitleCellStyle(workbook));
		headCell1.setCellValue("导入用户错误信息");

		setHeadTitle(workbook, sheet, 1, startCellNumber);
		int rowIndex = startRowNumber + 2;
		int arraySize = array.size();
		if (null != array && arraySize > 0) {
			for (int i = 0; i < arraySize; i++) {
				msg = JSONObject.parseObject(array.get(i).toString());
				XSSFRow row = sheet.createRow(rowIndex);

				Cell cell0 = row.createCell(startCellNumber);
				cell0.setCellStyle(getCenterCellStyle(workbook));
				cell0.setCellValue(i+1);

				Cell cell1 = row.createCell(startCellNumber+1);
				cell1.setCellStyle(getLeftCellStyle(workbook));
				cell1.setCellValue(msg.getString("msg"));
				rowIndex++;
			}
		}
		// 创建Excel文件对象
		file = new File(filepath, "导入用户错误信息.xlsx");
		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.flush();
		out.close();
	}

	private void setHeadTitle(XSSFWorkbook workbook, XSSFSheet sheet, int startRowNumber, int startCellNumber) {
		XSSFRow row = sheet.createRow(startRowNumber);
		// 序号
		Cell cell0 = row.createCell(startCellNumber);
		cell0.setCellStyle(getCenterCellStyle(workbook));
		cell0.setCellValue("序号");
		sheet.setColumnWidth((short)0, 2000);
		// 错误信息
		Cell cell1 = row.createCell(startCellNumber+1);
		cell1.setCellStyle(getCenterCellStyle(workbook));
		cell1.setCellValue("错误信息");
		sheet.setColumnWidth((short)1, 27000);
	}
}
