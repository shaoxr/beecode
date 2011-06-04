package com.newland.beecode.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.ExcelService;
import com.newland.utils.CellDefine;
import com.newland.utils.Formatter;

/**
 * @author shaoxr:
 * @version 2011-5-8 下午05:20:19
 * 
 */
public class ExportExcelTemplateService implements ExcelService{
	final static Log log = LogFactory.getLog(XPathExcelTemplateService.class);

	public final static String STYLE_TEXT = "text";

	public final static String STYLE_DATE = "date";

	public final static String STYLE_DATETIME = "datetime";

	public final static String STYLE_NUMBER = "number";

	private String sheetName;

	private List cellList;

	private int maxRowIndex = 30000;

	/**
	 * @throws AppBizException 
	 * @see netbank.CoreSyst.file.ExcelTemplateService#generateExcelFile(java.lang.Object)
	 */
	public File generateExcelFile(Collection dataCollection, String beginTime,
			String endTime) throws AppException {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = null;
			HSSFRow row = null;
			int rowIndex = 0;
			sheet = wb.createSheet(this.sheetName);
			
			rowIndex = 0;
			Map bookStyleCache = new Hashtable();
			Iterator it = dataCollection.iterator();
			//创建首列
			row = sheet.createRow(rowIndex++);
			this.genFirstRow(wb, row);
			while (it.hasNext()) {
				row = sheet.createRow(rowIndex++);
				JXPathContext context = JXPathContext.newContext(it.next());
				for (short i = 0; i < cellList.size(); i++) {
					CellDefine cf = (CellDefine) cellList.get(i);
					// 单元格值处理
					Formatter formatter = cf.getFormatter();

					Object value = null;
					if (cf.getPath() != null) {
						try {
							value = context.getValue(cf.getPath());
						} catch (Throwable e) {
							log.error(cf.getPath() + "为空");
						}
						if (formatter != null) {
							value = formatter.format(value);

						}
					}
					if (cf.getDefaultValue() != null && value == null)
						value = cf.getDefaultValue();

					createCell(wb, row, i, value, cf, bookStyleCache);

				}
			}

			//	Write the output to a file
			File file = File.createTempFile("excelout", ".xls");
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
			return file;
		} catch (Exception e) {
			log.error("gen Excel error", e);
			throw new AppException(ErrorsCode.SYSTEM_ERR, "gen Excel error", e);
		}
	}



	public void createCell(HSSFWorkbook book, HSSFRow row, short col,
			Object value, CellDefine cd, Map bookStyleCache) {
		if (value == null)
			return;

		HSSFCell cell = row.createCell(col);
		// 确定样式
		HSSFCellStyle style = (HSSFCellStyle) bookStyleCache
				.get(new Short(col));

		if (style == null) {
			style = getCellStyle(book, value, cd);
			if (style != null)
				bookStyleCache.put(new Short(col), style);
		}

		if (style != null)
			cell.setCellStyle(style);
		// 确定值
		if (value instanceof String) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        //TODO 无此接口
			cell.setCellValue((String) value);

		} else if (value instanceof java.util.Date) {
			cell.setCellValue((Date) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue(((Boolean) value).booleanValue());
		} else if (value instanceof Number) {
			cell.setCellValue(((Number) value).doubleValue());
		} else {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        //TODO 无此接口
			cell.setCellValue(value.toString());

		}
	}
	public void genFirstRow(HSSFWorkbook wb, HSSFRow row) {
		//    	列头
		for (short i = 0; i < cellList.size(); i++) {
			CellDefine cellDefine = (CellDefine) cellList.get(i);
			HSSFCell cell = row.createCell(i);

			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			HSSFFont font = wb.createFont();
			font.setColor(HSSFColor.BLUE_GREY.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
			//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			cell.setCellStyle(style);
			setCellValue(cell, cellDefine.getName());

		}
	}

	public HSSFCellStyle getCellStyle(HSSFWorkbook book, Object value,
			CellDefine cd) {
		// 确定样式
		HSSFCellStyle style = book.createCellStyle();
		HSSFDataFormat format = book.createDataFormat();
		String cellFormat = null;
		if (cd.getStyleName() != null && cd.getCellFormat() == null) {
			if (STYLE_TEXT.equalsIgnoreCase(cd.getStyleName())) {
				cellFormat = "text";
			} else if (STYLE_NUMBER.equalsIgnoreCase(cd.getStyleName())) {
				cellFormat = "#,##0.00";
			} else if (STYLE_DATE.equalsIgnoreCase(cd.getStyleName())) {
				cellFormat = "yyyy-MM-dd";
			} else if (STYLE_DATETIME.equalsIgnoreCase(cd.getStyleName())) {
				cellFormat = "yyyy-MM-dd HH:mm:ss";
			}
		} else if (cd.getCellFormat() != null) {
			cellFormat = cd.getCellFormat();
		}

		// 未指定数据格式根据类型设置数据格式
		if (cellFormat == null) {
			if (value instanceof Date)
				cellFormat = "yyyy-MM-dd";
			else if (value instanceof String) {
				cellFormat = "text";
			}
		}

		if (cellFormat != null) {
			try {
				style.setDataFormat(format.getFormat(cellFormat));
			} catch (Exception e) {
				log.warn("Excel单元格样式格式设置错误   ", e);
			}
		}

		// 设置对齐方式   
		if (cd.getAlign() != null) {
			if ("left".equalsIgnoreCase(cd.getAlign())) {
				style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			} else if ("center".equalsIgnoreCase(cd.getAlign())) {
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			} else if ("right".equalsIgnoreCase(cd.getAlign())) {
				style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			}
		}
		return style;
	}

	public void setCellValue(HSSFCell cell, Object value) {
		// 确定值
		if (value instanceof String) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        //TODO 无此接口
			cell.setCellValue((String) value);

		} else if (value instanceof java.util.Date) {
			cell.setCellValue((Date) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue(((Boolean) value).booleanValue());
		} else if (value instanceof Number) {
			cell.setCellValue(((Number) value).doubleValue());
		} else {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        //TODO 无此接口
			cell.setCellValue(value.toString());

		}
	}

	/**
	 * @param dataFieldList The dataFieldList to set.
	 */
	public void setCellList(List dataFieldList) {
		this.cellList = dataFieldList;
	}

	/**
	 * @param sheetName The sheetName to set.
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}


	public int getMaxRowIndex() {
		return maxRowIndex;
	}

	public void setMaxRowIndex(int maxRowIndex) {
		this.maxRowIndex = maxRowIndex;
	}
}

