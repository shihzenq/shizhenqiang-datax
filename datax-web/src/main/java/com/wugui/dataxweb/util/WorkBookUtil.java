package com.wugui.dataxweb.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.text.SimpleDateFormat;

public class WorkBookUtil {
    public static String getValue(Cell cell) {
        HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
        String temp = null;
        if (null ==cell) {
            temp = "";
        } else {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    temp = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        temp = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    } else {
                        // 可以区分整数和小数
                        String cellFormatted = dataFormatter.formatCellValue(cell);
                        temp = cellFormatted;
                    }
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    temp = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    temp = cell.getBooleanCellValue() + "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    temp = cell.getErrorCellValue() + "";
                    break;
                case Cell.CELL_TYPE_BLANK:
                    temp = "";
                    break;
                default:
                    temp = "";
                    break;
            }
        }
        return temp.trim();
    }
}
