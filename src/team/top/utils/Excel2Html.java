package team.top.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

/**This is a class use for convert a excel document to a html file
 * 
 * @author ybw
 *
 */
public class Excel2Html {

	private String fileName;
	private HSSFWorkbook hssfWorkbook;
	private FileSystem fileUtil;
	private final static String HEAD = "<html><body>";
	private final static String TAIL = "</body></html>";
	
	/**
	 * 
	 * @param filepath 					the file path 
	 * @throws FileNotFoundException    thrown if the file not exist
	 * @throws IOException				thrown if open file error
	 * @throws SdCardNotFoud			thrown if sdcard not mounted
	 */
	public Excel2Html(String filepath) throws FileNotFoundException, IOException{
		String temp[] = filepath.split("/");
		String file = temp[temp.length-1];
		fileName = file.substring(0,file.lastIndexOf('.'));
		FileInputStream fileInputStream = new FileInputStream(filepath);
		hssfWorkbook = new HSSFWorkbook(fileInputStream);
		fileInputStream.close();
		fileUtil = new FileSystem();
	}
	
	/**use for check the merged col and raw in the sheet
	 * 
	 * 
	 * @param sheet 	the sheet to scan
	 * @return 			the merged information of the sheet
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {
		Map<String, String> map0 = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();
		int mergedNum = sheet.getNumMergedRegions();							//get the number of merged regions
		CellRangeAddress range = null;
		for (int i = 0; i < mergedNum; i++) {
			range = sheet.getMergedRegion(i);									//get a merged region
			int topRow = range.getFirstRow();									//get the number of top row and col of the region
			int topCol = range.getFirstColumn();							
			int bottomRow = range.getLastRow();									//get the number bottom row and col of the region
			int bottomCol = range.getLastColumn();
			map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);       //record the merged region
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
		Map[] map = { map0, map1 };
		return map;
	}

	/**convert excel document to html  file
	 * 
	 * @param hc 		convert the hssfcolot to normal color format
	 * @return  		the #.......  RGB color format 
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

	/**fill color string
	 * 
	 * @param str 		the color string uncompleted,such as #00
	 * @return 			the completed color string,such as #000000
	 */
	private String fillWithZero(String str) {
		if (str != null && str.length() < 2) {
			return "0" + str;
		}
		return str;
	}

	/**get the content in cell
	 * 
	 * @param cell  	the cell
	 * @return the 		content in cell
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

	/**convert the value of verticalAlignment to string (middle,bottom,center,top) 
	 * 
	 * @param verticalAlignment 		the verticalAligment value
	 * @return 							the VerticalAlignment in string
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

	/**convert the value of vAlignment to string (left,center,right) 
	 * 
	 * @param alignment  	the Alignment value
	 * @return 				the Alignment in string
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
	 * @return 				the absolute path of the html file
	 * @throws Exception	thrown if convert failed
	 */
	public String convert2Html() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(HEAD);
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);							//get sheet
		int lastRowNum = sheet.getLastRowNum();
		Map<String, String> map[] = getRowSpanColSpanMap(sheet);
		sb.append("<table border='1' cellspacing='0' width='100%'>");			//get the row number of this sheet 
		HSSFRow row = null;
		HSSFCell cell = null;

		for (int rowNum = sheet.getFirstRowNum(); 
			 rowNum < lastRowNum; 
		     rowNum++  ) {														//get every row data
			row = (HSSFRow) sheet.getRow(rowNum);								//get a raw
			if (row == null) {
				sb.append("<tr><td > &nbsp;</td></tr>");
				continue;
			}
			sb.append("<tr>");
			int lastColNum = row.getLastCellNum();								//get the cell number in a raw	
			for (int colNum = 0; colNum < lastColNum; colNum++) { 				//get every cell in a raw
				cell = row.getCell(colNum);
				if (cell == null) {
					sb.append("<td>&nbsp;</td>");
					continue;
				}
				String stringValue = getCellValue(cell);	                    //get cell value
				if (map[0].containsKey(rowNum + "," + colNum)) {               	//check the cell weather merged or not
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
				
				HSSFCellStyle cellStyle = cell.getCellStyle();					//get every cell style in a row
				if (cellStyle != null) {
					
					short alignment = cellStyle.getAlignment();                 //get horizon alignment
					sb.append("align='" + convertAlignToHtml(alignment) + "' ");  
					short verticalAlignment = cellStyle.getVerticalAlignment(); //get vertical alignment
					sb.append("valign='"
							+ convertVerticalAlignToHtml(verticalAlignment)
							+ "' ");
					HSSFFont hf = cellStyle.getFont(hssfWorkbook);   	        //get font style
					short boldWeight = hf.getBoldweight();             			//get font boldweight
					short fontColor = hf.getColor();       					    //get font color
					sb.append("style='");
					HSSFPalette palette = hssfWorkbook.getCustomPalette();   	//get the cell color in international standard
					HSSFColor hc = palette.getColor(fontColor);					//get the cell color
					sb.append("font-weight:" + boldWeight + ";"); 				//set font weight
					sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); 	//set font size
					String fontColorStr = convertToStardColor(hc);			    //convert hssfcolor the hex color
					if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
						sb.append("color:" + fontColorStr + ";"); 
					}
					short bgColor = cellStyle.getFillForegroundColor();         //get cell background color
					hc = palette.getColor(bgColor);
					String bgColorStr = convertToStardColor(hc);
					if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
						sb.append("background-color:" + bgColorStr + ";"); 
					}
					short borderColor = cellStyle.getBottomBorderColor(); 		//get cell border color 
					hc = palette.getColor(borderColor);
					String borderColorStr = convertToStardColor(hc);
					if (borderColorStr != null
							&& !"".equals(borderColorStr.trim())) {
						sb.append("border-color:" + borderColorStr + ";"); 
					}
					sb.append("' ");
				}

				sb.append(">");
				if (stringValue == null || "".equals(stringValue.trim())) {
					sb.append(" &nbsp; ");
				} else {
					sb.append(stringValue.replace(String.valueOf((char) 160), 	// transform space to html code
							"&nbsp;"));
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		sb.append(TAIL);
		fileUtil.WriteToAppDir(fileName + ".html", sb.toString().getBytes());
		return fileName + ".html";
	}
}



