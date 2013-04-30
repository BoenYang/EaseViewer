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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * 
 * @author ybw convert word to html files
 */
public class WordToHtml {

	private HWPFDocument document;
	//html tag constant
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
	
	private static String SDCARD_PATH;
	private static String APP_DIR;
	private String fileName;
	private List<Picture> pictures;
	private int presentPicture;
	private FileOutputStream htmlOutPut;

	/**
	 * 
	 * @param filepath the word documents absolute path
	 * @throws IOException  file not found or file can't open
	 */
	public WordToHtml(String filepath) throws IOException {
		//get file name exclude extension name
		String temp[] = filepath.split("/");
		String file = temp[temp.length-1];
		fileName = file.substring(0,file.lastIndexOf('.'));
		//get file input stream to read the word document
		FileInputStream inputStream = new FileInputStream(filepath);
		document = new HWPFDocument(inputStream);
		//set SDCARD_PATH
		WordToHtml.SDCARD_PATH = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		//set application directory
		WordToHtml.APP_DIR = WordToHtml.SDCARD_PATH + File.separator
				+ "AndroidReader";
		//get all the pictures in the document
		pictures = document.getPicturesTable().getAllPictures();
		//set the index of the start picture
		presentPicture = 0;
	}

	
	/**
	 * 
	 * @return the path of converted documents html file
	 */
	public String convertToHtml() {
		makeAppDir();
		//get the output stream of html file
		try {
			htmlOutPut = new FileOutputStream(WordToHtml.APP_DIR + File.separator 
					+ fileName + ".html");
		} catch (FileNotFoundException e) {
				try {
					if(htmlOutPut != null)
					htmlOutPut.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		}
		readAndWrite();
		if(htmlOutPut != null)
		{
			try {
				htmlOutPut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return WordToHtml.APP_DIR + File.separator 
				+ fileName + ".html";
	}

	private void makeAppDir() {
		File file = new File(WordToHtml.APP_DIR);
		if (!file.exists())
			file.mkdir();
	}


	/**
	 * read word documents translated it into html file and then write to sdcard 
	 */
	private void readAndWrite() {
		TableIterator tableIterator = null;
		try {
			htmlOutPut.write(HEAD.getBytes());
			Range range = document.getRange();
			tableIterator = new TableIterator(range);
			int numParagraphs = range.numParagraphs();
			for (int i = 0; i < numParagraphs; i++) {
				Paragraph p = range.getParagraph(i);
				if (p.isInTable()) {
					int temp = i;
					if (tableIterator.hasNext()) {

						//get each table
						Table table = tableIterator.next();
						//write the start tag of the table
						htmlOutPut.write(TABLE_BEGIN.getBytes());
						//get the rows of the table
						int rows = table./* 读取word中的内容写到sdcard上的.html文件中 */numRows();
						//get the content in each row
						for (int r = 0; r < rows; r++) {
							htmlOutPut.write(ROW_BEGIN.getBytes());
							TableRow row = table.getRow(r);
							int cols = row.numCells();
							int rowNumParagraphs = row.numParagraphs();
							int colsNumParagraphs = 0;
							for (int c = 0; c < cols; c++) {
								htmlOutPut.write(COL_BEGIN.getBytes());
								TableCell cell = row.getCell(c);
								int max = temp + cell.numParagraphs();
								colsNumParagraphs = colsNumParagraphs
										+ cell.numParagraphs();
								for (int cp = temp; cp < max; cp++) {
									Paragraph p1 = range.getParagraph(cp);
									htmlOutPut.write(BEGIN.getBytes());
									writeParagraphContent(p1);
									htmlOutPut.write(END.getBytes());
									temp++;
								}
								htmlOutPut.write(COL_END.getBytes());
							}
							int max1 = temp + rowNumParagraphs;
							for (int m = temp + colsNumParagraphs; m < max1; m++) {
								Paragraph p2 = range.getParagraph(m);
								temp++;
							}
							htmlOutPut.write(ROW_END.getBytes());
						}
						htmlOutPut.write(TABLE_END.getBytes());
					}
					i = temp;
				} else {
					htmlOutPut.write(BEGIN.getBytes());
					writeParagraphContent(p);
					htmlOutPut.write(END.getBytes());
				}
			}
			htmlOutPut.write(TAIL.getBytes());
		} catch (Exception e) {
			System.out.println("readAndWrite Exception");
		}
	}

	/**
	 * 
	 * @param paragraph paragrgaph to write
	 *  write the paragraph contents between the html tag
	 */
	private void writeParagraphContent(Paragraph paragraph) {
		Paragraph p = paragraph;
		int pnumCharacterRuns = p.numCharacterRuns();
		for (int j = 0; j < pnumCharacterRuns; j++) {
			CharacterRun run = p.getCharacterRun(j);
			if (run.getPicOffset() == 0 || run.getPicOffset() >= 1000) {
				if (presentPicture < pictures.size()) {
					writePicture();
				}
			} else {
				try {
					String text = run.text();
					if (text.length() >= 2 && pnumCharacterRuns < 2) {
						htmlOutPut.write(text.getBytes());
					} else {
						int size = run.getFontSize();
						int color = run.getColor();
						String fontSizeBegin = "<font size=\""
								+ decideSize(size) + "\">";
						String fontColorBegin = "<font color=\""
								+ decideColor(color) + "\">";
						String fontEnd = "</font>";
						String boldBegin = "<b>";
						String boldEnd = "</b>";
						String islaBegin = "<i>";
						String islaEnd = "</i>";
						htmlOutPut.write(fontSizeBegin.getBytes());
						htmlOutPut.write(fontColorBegin.getBytes());
						if (run.isBold()) {
							htmlOutPut.write(boldBegin.getBytes());
						}
						if (run.isItalic()) {
							htmlOutPut.write(islaBegin.getBytes());
						}
						htmlOutPut.write(text.getBytes());
						if (run.isBold()) {
							htmlOutPut.write(boldEnd.getBytes());
						}
						if (run.isItalic()) {
							htmlOutPut.write(islaEnd.getBytes());
						}
						htmlOutPut.write(fontEnd.getBytes());
						htmlOutPut.write(fontEnd.getBytes());
					}
				} catch (Exception e) {
					System.out.println("Write File Exception");
				}
			}
		}
	}

	/**
	 * write the picture in documents to sdcard
	 */
	private void writePicture() {
		Picture picture = (Picture) pictures.get(presentPicture);
		byte[] pictureBytes = picture.getContent();
		Bitmap bitmap = BitmapFactory.decodeByteArray(pictureBytes, 0,
				pictureBytes.length);
		presentPicture++;
		String picturePath = WordToHtml.APP_DIR + File.separator + picture.suggestFullFileName();
		File myPicture = new File(picturePath);
		if(!myPicture.exists())
		{
			try {
				myPicture.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream outputPicture = new FileOutputStream(myPicture);
			outputPicture.write(pictureBytes);
			outputPicture.close();
		} catch (Exception e) {
			System.out.println("outputPicture Exception");
		}
		String imageString = "<img src=\"" + picturePath + "\"";
		if (bitmap.getWidth() > 640) {
			imageString = imageString + " " + "width=\"" + 640 + "\"";
		}
		imageString = imageString + ">";
		try {
			htmlOutPut.write(imageString.getBytes());
		} catch (Exception e) {
			System.out.println("output Exception");
		}
	}

	/**
	 * 
	 * @param size  the font size in the word documents
	 * @return  the font size in html
	 */
	private int decideSize(int size) {

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
	 * 
	 * @param a the color's value in the word document
	 * @return the value of color in html
	 */
	private String decideColor(int a) {
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
