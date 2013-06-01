package team.androidreader.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import team.androidreader.exception.WriteHtmlExcpetion;
import team.androidreader.utils.FileSystem;

/**
 * note: exception handle need improved
 */

/**
 * This is a class use for convert a excel document to a html file
 * 
 * @author ybw
 * 
 */
public class ExcelToHtml {

	private String fileName;
	private HSSFWorkbook hssfWorkbook;
	private final static String HEAD = "<html><body>";
	private final static String TAIL = "</body></html>";
	private String saveDir;

	/**
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public ExcelToHtml(String filepath) throws IOException {
		fileName = FileSystem.GetFileNameByPath(filepath);
		FileInputStream fileInputStream = new FileInputStream(filepath);
		hssfWorkbook = new HSSFWorkbook(fileInputStream);
		fileInputStream.close();
		saveDir = FileSystem.EXCEL_CACHE + File.separator + fileName;
	}

	/**
	 * use for check the merged col and raw in the sheet
	 * 
	 * 
	 * @param sheet
	 *            the sheet to scan
	 * @return the merged information of the sheet
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {
		Map<String, String> map0 = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();
		int mergedNum = sheet.getNumMergedRegions(); // get the number of merged
														// regions
		CellRangeAddress range = null;
		for (int i = 0; i < mergedNum; i++) {
			range = sheet.getMergedRegion(i); // get a merged region
			int topRow = range.getFirstRow(); // get the number of top row and
												// col of the region
			int topCol = range.getFirstColumn();
			int bottomRow = range.getLastRow(); // get the number bottom row and
												// col of the region
			int bottomCol = range.getLastColumn();
			map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol); // record
																			// the
																			// merged
																			// region
			int tempRow = topRow;
			while (tempRow <= bottomRow) {
				int tempCol = topCol;
				while (tempCol <= bottomCol) {
					map1.put(tempRow + "," + tempCol, "");
					tempCol++;
				}
				tempRow++;
			}
			map1.remove(topRow + "," + topCol);
		}
		@SuppressWarnings("rawtypes")
		Map[] map = { map0, map1 };
		return map;
	}

	/**
	 * convert excel document to html file
	 * 
	 * @param hc
	 *            convert the hssfcolot to normal color format
	 * @return the #....... RGB color format
	 */
	private String convertToStardColor(HSSFColor hc) {
		StringBuffer sb = new StringBuffer("");
		if (hc != null) {
			if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
				return null;
			}
			sb.append("#");
			for (int i = 0; i < hc.getTriplet().length; i++) {
				sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
			}
		}
		return sb.toString();
	}

	/**
	 * fill color string
	 * 
	 * @param str
	 *            the color string uncompleted,such as #00
	 * @return the completed color string,such as #000000
	 */
	private String fillWithZero(String str) {
		if (str != null && str.length() < 2) {
			return "0" + str;
		}
		return str;
	}

	/**
	 * get the content in cell
	 * 
	 * @param cell
	 *            the cell
	 * @return the content in cell
	 */
	private String getCellValue(HSSFCell cell) {
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			DecimalFormat format = new DecimalFormat("#0.##");
			return format.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return "";
		}
	}

	/**
	 * convert the value of verticalAlignment to string
	 * (middle,bottom,center,top)
	 * 
	 * @param verticalAlignment
	 *            the verticalAligment value
	 * @return the VerticalAlignment in string
	 */
	private String convertVerticalAlignToHtml(short verticalAlignment) {
		String valign = "middle";
		switch (verticalAlignment) {
		case HSSFCellStyle.VERTICAL_BOTTOM:
			valign = "bottom";
			break;
		case HSSFCellStyle.VERTICAL_CENTER:
			valign = "center";
			break;
		case HSSFCellStyle.VERTICAL_TOP:
			valign = "top";
			break;
		default:
			break;
		}
		return valign;
	}

	/**
	 * convert the value of vAlignment to string (left,center,right)
	 * 
	 * @param alignment
	 *            the Alignment value
	 * @return the Alignment in string
	 */
	private String convertAlignToHtml(short alignment) {
		String align = "left";
		switch (alignment) {
		case HSSFCellStyle.ALIGN_LEFT:
			align = "left";
			break;
		case HSSFCellStyle.ALIGN_CENTER:
			align = "center";
			break;
		case HSSFCellStyle.ALIGN_RIGHT:
			align = "right";
			break;
		default:
			break;
		}
		return align;
	}

	/**
	 * 
	 * @return the absolute path of the html file
	 * @throws WriteHtmlExcpetion
	 * @throws UnsupportedEncodingException
	 * 
	 */
	public String convert2Html() throws WriteHtmlExcpetion,
			UnsupportedEncodingException {
		File file = new File(saveDir);
		if (!file.exists()) {
			file.mkdir();
		} 
		StringBuffer sb = new StringBuffer();
		sb.append(HEAD);
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0); // get sheet
		int lastRowNum = sheet.getLastRowNum();
		Map<String, String> map[] = getRowSpanColSpanMap(sheet);
		sb.append("<table border='1' cellspacing='0' width='100%'>");
		HSSFRow row = null;
		HSSFCell cell = null;

		for (int rowNum = sheet.getFirstRowNum(); rowNum < lastRowNum; rowNum++) {
			row = (HSSFRow) sheet.getRow(rowNum);
			if (row == null) {
				sb.append("<tr><td > &nbsp;</td></tr>");
				continue;
			}
			sb.append("<tr>");
			int lastColNum = row.getLastCellNum();
			for (int colNum = 0; colNum < lastColNum; colNum++) {
				cell = row.getCell(colNum);
				if (cell == null) {
					sb.append("<td>&nbsp;</td>");
					continue;
				}
				String stringValue = getCellValue(cell);
				if (map[0].containsKey(rowNum + "," + colNum)) {
					String pointString = map[0].get(rowNum + "," + colNum);
					map[0].remove(rowNum + "," + colNum);
					int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
					int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
					int rowSpan = bottomeRow - rowNum + 1;
					int colSpan = bottomeCol - colNum + 1;
					sb.append("<td rowspan= '" + rowSpan + "' colspan= '"
							+ colSpan + "' ");
				} else if (map[1].containsKey(rowNum + "," + colNum)) {
					map[1].remove(rowNum + "," + colNum);
					continue;
				} else {
					sb.append("<td ");
				}
				HSSFCellStyle cellStyle = cell.getCellStyle();
				if (cellStyle != null) {
					short alignment = cellStyle.getAlignment();
					sb.append("align='" + convertAlignToHtml(alignment) + "' ");
					short verticalAlignment = cellStyle.getVerticalAlignment();
					sb.append("valign='"
							+ convertVerticalAlignToHtml(verticalAlignment)
							+ "' ");
					HSSFFont hf = cellStyle.getFont(hssfWorkbook);
					short boldWeight = hf.getBoldweight();
					short fontColor = hf.getColor();
					sb.append("style='");
					HSSFPalette palette = hssfWorkbook.getCustomPalette();
					HSSFColor hc = palette.getColor(fontColor);
					sb.append("font-weight:" + boldWeight + ";");
					sb.append("font-size: " + hf.getFontHeight() / 2 + "%;");
					String fontColorStr = convertToStardColor(hc);
					if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
						sb.append("color:" + fontColorStr + ";");
					}
					short bgColor = cellStyle.getFillForegroundColor();
					hc = palette.getColor(bgColor);
					String bgColorStr = convertToStardColor(hc);
					if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
						sb.append("background-color:" + bgColorStr + ";");
					}
					short borderColor = cellStyle.getBottomBorderColor();
					hc = palette.getColor(borderColor);
					String borderColorStr = convertToStardColor(hc);
					if (borderColorStr != null
							&& !"".equals(borderColorStr.trim())) {
						sb.append("border-color:" + borderColorStr + ";");
					}
					sb.append("' ");
				}
				sb.append(">");
				if (stringValue == null || " ".equals(stringValue.trim())) {
					sb.append(" &nbsp; ");
				} else {
					sb.append(stringValue.replace(String.valueOf((char) 160),
							"&nbsp;"));
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		sb.append(TAIL);
		if (!FileSystem.Write(saveDir + File.separator + fileName + ".html", sb
				.toString().getBytes("gbk"))) {
			throw new WriteHtmlExcpetion();
		}
		return saveDir + File.separator + fileName + ".html";
	}
}
