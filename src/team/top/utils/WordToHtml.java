package team.top.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

import team.top.exception.WriteHtmlExcpetion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * This class use for convert a word document to a html file
 * 
 * @author ybw
 */
public class WordToHtml {

	private HWPFDocument document;
	// html tag constant
	private final static String HEAD = "<html><body>";
	private final static String TAIL = "</body></html>";
	private final static String BEGIN = "<p>";
	private final static String END = "</p>";
	private final static String TABLE_BEGIN = "<table style=\"border-collapse:collapse\" border=1 bordercolor=\"black\">";
	private final static String TABLE_END = "</table>";
	private final static String ROW_BEGIN = "<tr>";
	private final static String ROW_END = "</tr>";
	private final static String COL_BEGIN = "<td>";
	private final static String COL_END = "</td>";
	private final static String FONT_END = "</font>";
	private final static String BOLDB_EGIN = "<b>";
	private final static String BOLD_END = "</b>";
	private final static String ISLA_BEGIN = "<i>";
	private final static String ISLA_END = "</i>";

	private String fileName;
	private List<Picture> pictures;
	private int presentPicture;
	private StringBuilder html;

	/**
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public WordToHtml(String filepath) throws IOException{
		fileName = FileSystem.GetFileNameByPath(filepath);
		FileInputStream inputStream = new FileInputStream(filepath);
		document = new HWPFDocument(inputStream);
		pictures = document.getPicturesTable().getAllPictures();
		presentPicture = 0;
		html = new StringBuilder();
		inputStream.close();
	}

	/**
	 * 
	 * @return
	 * @throws WriteHtmlExcpetion
	 */
	public String convertToHtml() throws WriteHtmlExcpetion {
		readDoc();
		
		if(!FileSystem.WriteToAppDir(fileName + ".html",html.toString().getBytes())){
			throw new WriteHtmlExcpetion();
		}
		return FileSystem.APP_DIR + File.separator + fileName + ".html";
	}

	/**
	 * 
	 */
	private void readDoc() {
		TableIterator tableIterator = null;
			html.append(HEAD);
			Range range = document.getRange();
			tableIterator = new TableIterator(range);
			int numParagraphs = range.numParagraphs();
			for (int i = 0; i < numParagraphs; i++) {
				Paragraph p = range.getParagraph(i);
				if (p.isInTable()) {
					int temp = i;
					if (tableIterator.hasNext()) {
						Table table = tableIterator.next();
						html.append(TABLE_BEGIN);
						int rows = table.numRows();
						for (int r = 0; r < rows; r++) {
							html.append(ROW_BEGIN);
							TableRow row = table.getRow(r);
							int cols = row.numCells();
							int rowNumParagraphs = row.numParagraphs();
							int colsNumParagraphs = 0;
							for (int c = 0; c < cols; c++) {
								html.append(COL_BEGIN);
								TableCell cell = row.getCell(c);
								int max = temp + cell.numParagraphs();
								colsNumParagraphs = colsNumParagraphs
										+ cell.numParagraphs();
								for (int cp = temp; cp < max; cp++) {
									Paragraph p1 = range.getParagraph(cp);
									html.append(BEGIN);
									readParagrahp(p1);
									html.append(END);
									temp++;
								}
								html.append(COL_END);
							}
							int max1 = temp + rowNumParagraphs;
							for (int m = temp + colsNumParagraphs; m < max1; m++) {
								Paragraph p2 = range.getParagraph(m);
								temp++;
							}
							html.append(ROW_END);
						}
						html.append(TABLE_END);
					}
					i = temp;
				} else {
					html.append(BEGIN);
					readParagrahp(p);
					html.append(END);
				}
			}
			html.append(TAIL);
	}

	/**
	 * 
	 * @param paragraph
	 */
	private void readParagrahp(Paragraph paragraph) {
		Paragraph p = paragraph;
		int pnumCharacterRuns = p.numCharacterRuns();
		for (int j = 0; j < pnumCharacterRuns; j++) {
			CharacterRun run = p.getCharacterRun(j);
			if (run.getPicOffset() == 0 || run.getPicOffset() >= 1000) {
				if (presentPicture < pictures.size()) {
					writePicture();
				}
			} else {
				String text = run.text();
				if (text.length() >= 2 && pnumCharacterRuns < 2) {
					html.append(text);
				} else {
					int size = run.getFontSize();
					int color = run.getColor();
					String fontSizeBegin = "<font size=\""
							+ convertFontSize(size) + "\">";
					String fontColorBegin = "<font color=\""
							+ convertFontColor(color) + "\">";
					html.append(fontSizeBegin);
					html.append(fontColorBegin);
					if (run.isBold()) {
						html.append(BOLDB_EGIN);
					}
					if (run.isItalic()) {
						html.append(ISLA_BEGIN);
					}
					html.append(text);
					if (run.isBold()) {
						html.append(BOLD_END);
					}
					if (run.isItalic()) {
						html.append(ISLA_END);
					}
					html.append(FONT_END);
				}
			}
		}
	}

	/**
	 * write the picture in documents to sdcard
	 * 
	 */
	private void writePicture() {
		Picture picture = (Picture) pictures.get(presentPicture);
		byte[] pictureBytes = picture.getContent();
		Bitmap bitmap = BitmapFactory.decodeByteArray(pictureBytes, 0,
				pictureBytes.length);
		presentPicture++;
		FileSystem.WriteToAppDir(picture.suggestFullFileName(), pictureBytes);
		String imageString = "<img src=\"" + picture.suggestFullFileName()
				+ "\"";
		if (bitmap.getWidth() > 640) {
			imageString += " " + "width=\"" + 640 + "\"";
		}
		imageString += ">";
		html.append(imageString);
	}

	/**
	 * convert font size in word to html font size
	 * 
	 * @param size
	 *            the font size in the word documents
	 * @return the font size in html
	 */
	private int convertFontSize(int size) {

		if (size >= 1 && size <= 8) {
			return 1;
		}
		if (size >= 9 && size <= 11) {
			return 2;
		}
		if (size >= 12 && size <= 14) {
			return 3;
		}
		if (size >= 15 && size <= 19) {
			return 4;
		}
		if (size >= 20 && size <= 29) {
			return 5;
		}
		if (size >= 30 && size <= 39) {
			return 6;
		}
		if (size >= 40) {
			return 7;
		}
		return 3;
	}

	/**
	 * convert the font color in word to html font color
	 * 
	 * @param a
	 *            the color's value in the word document
	 * @return the value of color in html
	 */
	private String convertFontColor(int a) {
		int color = a;
		switch (color) {
		case 1:
			return "#000000";
		case 2:
			return "#0000FF";
		case 3:
		case 4:
			return "#00FF00";
		case 5:
		case 6:
			return "#FF0000";
		case 7:
			return "#FFFF00";
		case 8:
			return "#FFFFFF";
		case 9:
			return "#CCCCCC";
		case 10:
		case 11:
			return "#00FF00";
		case 12:
			return "#080808";
		case 13:
		case 14:
			return "#FFFF00";
		case 15:
			return "#CCCCCC";
		case 16:
			return "#080808";
		default:
			return "#000000";
		}
	}

}
