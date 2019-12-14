package com.wugui.dataxweb.export;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

abstract class Excel {

    private CellStyle baseCellStyle = null;
    private CellStyle rightCellStyle = null;
    private CellStyle leftCellStyle = null;
    private CellStyle centerCellStyle = null;
    private CellStyle titleCellStyle = null;
    private CellStyle titleCellForReportStyle = null;

    private CellStyle baseCellStyleForReport = null;
    private CellStyle baseCellStyleNoBorder = null;
    private CellStyle rightCellStyleNoBorder = null;
    private CellStyle leftCellStyleNoBorder = null;

    CellStyle getRightCellStyle(XSSFWorkbook workbook) {
        if(null != rightCellStyle) return rightCellStyle;
        CellStyle baseCellStyle = getBaseCellStyle(workbook);
        rightCellStyle = workbook.createCellStyle();
        rightCellStyle.cloneStyleFrom(baseCellStyle);
        rightCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 水平
        return rightCellStyle;
    }

    CellStyle getCenterCellStyle(XSSFWorkbook workbook) {
        if(null != centerCellStyle) return centerCellStyle;
        CellStyle baseCellStyle = getBaseCellStyle(workbook);
        centerCellStyle = workbook.createCellStyle();
        centerCellStyle.cloneStyleFrom(baseCellStyle);
        centerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        return centerCellStyle;
    }

    CellStyle getLeftCellStyle(XSSFWorkbook workbook) {
        if(null != leftCellStyle) return leftCellStyle;
        CellStyle baseCellStyle = getBaseCellStyle(workbook);
        leftCellStyle = workbook.createCellStyle();
        leftCellStyle.cloneStyleFrom(baseCellStyle);
        leftCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平
        return leftCellStyle;
    }

    CellStyle getRightCellStyleNoBorder(XSSFWorkbook workbook){
        if(null != rightCellStyleNoBorder) return rightCellStyleNoBorder;
        CellStyle baseCellStyle = getBaseCellStyle(workbook);
        rightCellStyleNoBorder = workbook.createCellStyle();
        rightCellStyleNoBorder.cloneStyleFrom(baseCellStyle);
        rightCellStyleNoBorder.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 水平
        rightCellStyleNoBorder.setBorderBottom((short) 0);
        rightCellStyleNoBorder.setBorderLeft((short) 0);
        rightCellStyleNoBorder.setBorderRight((short) 0);
        rightCellStyleNoBorder.setBorderTop((short) 0);
        return rightCellStyleNoBorder;
    }
    
    CellStyle getLeftCellStyleNoBorder(XSSFWorkbook workbook){
        if(null != leftCellStyleNoBorder) return leftCellStyleNoBorder;
        CellStyle baseCellStyle = getBaseCellStyle(workbook);
        leftCellStyleNoBorder = workbook.createCellStyle();
        leftCellStyleNoBorder.cloneStyleFrom(baseCellStyle);
        leftCellStyleNoBorder.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 水平
        leftCellStyleNoBorder.setBorderBottom((short) 0);
        leftCellStyleNoBorder.setBorderLeft((short) 0);
        leftCellStyleNoBorder.setBorderRight((short) 0);
        leftCellStyleNoBorder.setBorderTop((short) 0);
        return leftCellStyleNoBorder;
    }

    CellStyle getTitleCellStyle(XSSFWorkbook workbook) {
        if(null != titleCellStyle) return titleCellStyle;
        CellStyle baseCellStyle = getBaseCellStyle(workbook);
        titleCellStyle = workbook.createCellStyle();
        titleCellStyle.cloneStyleFrom(baseCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        XSSFFont cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short) 18);
        cellFont.setFontName("宋体");
        cellFont.setBold(true);
        titleCellStyle.setFont(cellFont);
        titleCellStyle.setBorderBottom((short) 0);
        titleCellStyle.setBorderLeft((short) 0);
        titleCellStyle.setBorderRight((short) 0);
        titleCellStyle.setBorderTop((short) 0);
        return titleCellStyle;
    }

    CellStyle getTitleForReportCellStyle(XSSFWorkbook workbook) {
        if(null != titleCellForReportStyle) return titleCellForReportStyle;
        CellStyle baseCellStyle = getBaseCellStyle(workbook);
        titleCellForReportStyle = workbook.createCellStyle();
        titleCellForReportStyle.cloneStyleFrom(baseCellStyle);
        titleCellForReportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        XSSFFont cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short) 20);
        cellFont.setFontName("宋体");
        cellFont.setBold(true);
        titleCellForReportStyle.setFont(cellFont);
        titleCellForReportStyle.setBorderBottom((short) 1);
        titleCellForReportStyle.setBorderLeft((short) 1);
        titleCellForReportStyle.setBorderRight((short) 1);
        titleCellForReportStyle.setBorderTop((short) 1);
        return titleCellForReportStyle;
    }

    CellStyle getBaseCellStyleForReport(XSSFWorkbook workbook) {
        if(null != baseCellStyleForReport) return baseCellStyleForReport;
        CellStyle baseCellStyle = getBaseCellStyle(workbook);
        baseCellStyleForReport = workbook.createCellStyle();
        baseCellStyleForReport.cloneStyleFrom(baseCellStyle);
        baseCellStyleForReport.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平
        XSSFFont cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short) 10);
        cellFont.setFontName("宋体");
        baseCellStyleForReport.setFont(cellFont);
        baseCellStyleForReport.setBorderBottom((short) 1);
        baseCellStyleForReport.setBorderLeft((short) 1);
        baseCellStyleForReport.setBorderRight((short) 1);
        baseCellStyleForReport.setBorderTop((short) 1);
        return baseCellStyleForReport;
    }

    CellStyle getBaseCellStyle(XSSFWorkbook workbook) {
        if(null != baseCellStyle) return baseCellStyle;
        Font cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short) 10);
        cellFont.setFontName("宋体");
        baseCellStyle = workbook.createCellStyle();
        baseCellStyle.setFont(cellFont);
        baseCellStyle.setBorderBottom((short) 1);
        baseCellStyle.setBorderLeft((short) 1);
        baseCellStyle.setBorderRight((short) 1);
        baseCellStyle.setBorderTop((short) 1);
        baseCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        baseCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        return baseCellStyle;
    }

    CellStyle getBaseCellStyle(XSSFWorkbook workbook, boolean hasBorder) {
        if(null != baseCellStyle && hasBorder) return baseCellStyle;
        baseCellStyle = getBaseCellStyle(workbook);
        baseCellStyleNoBorder = workbook.createCellStyle();
        if(hasBorder) {
            return baseCellStyle;
        }
        baseCellStyleNoBorder.cloneStyleFrom(baseCellStyle);
        baseCellStyleNoBorder.setBorderBottom((short)0);
        baseCellStyleNoBorder.setBorderLeft((short)0);
        baseCellStyleNoBorder.setBorderRight((short)0);
        baseCellStyleNoBorder.setBorderTop((short)0);
        return this.baseCellStyleNoBorder;
    }

    void setCellRangeAddressBorder(CellRangeAddress cra, XSSFSheet sheet, XSSFWorkbook workbook) {
        RegionUtil.setBorderBottom(1, cra, sheet, workbook); // 下边框
        RegionUtil.setBorderLeft(1, cra, sheet, workbook); // 左边框
        RegionUtil.setBorderRight(1, cra, sheet, workbook); // 有边框
        RegionUtil.setBorderTop(1, cra, sheet, workbook); // 上边框
    }
}
