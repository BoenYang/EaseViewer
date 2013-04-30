package team.top.activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Range;

import team.top.utils.WordToHtml;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

/**
 * 
 * @author ybw activity for select which to show
 */
public class TestContentActivity extends Activity {

	private Button getWordDataBtn;
	private Button getPptDataBtn;
	private Button getExcelDataBtn;
	private static final short ENTER_ASCII = 13;
	/**
	 * 空格符ASCII码
	 */
	private static final short SPACE_ASCII = 32;
	/**
	 * 水平制表符ASCII码
	 */
	private static final short TABULATION_ASCII = 9;
	private String htmlText = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testcontent_activity);
		getWordDataBtn = (Button) findViewById(R.id.getWorldData);
		getPptDataBtn = (Button) findViewById(R.id.getPptData);
		getExcelDataBtn = (Button) findViewById(R.id.getExcelData);
		getWordDataBtn.setOnClickListener(new BtnOnClickListener());
		getPptDataBtn.setOnClickListener(new BtnOnClickListener());
		getExcelDataBtn.setOnClickListener(new BtnOnClickListener());
	}

	@SuppressLint("NewApi")
	class BtnOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();
			switch (id) {
			case R.id.getWorldData:
				String filepath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/" + "TestWord.doc";
				String path = "";
				try {
					WordToHtml wordToHtml = new WordToHtml(filepath);
					path = wordToHtml.convertToHtml();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent();
				intent.putExtra("path", path);
				intent.setClass(TestContentActivity.this, ShowContentActivity.class);
				startActivity(intent);
				break;
			case R.id.getPptData:
				break;
			case R.id.getExcelData:
				break;
			default:
				break;
			}
		}

	}

	public void getWordAndStyle(String fileName) throws Exception {
		FileInputStream in = new FileInputStream(new File(fileName));
		HWPFDocument doc = new HWPFDocument(in);
		// 取得文档中字符的总数
		int length = doc.characterLength();
		// 创建图片容器
		PicturesTable pTable = doc.getPicturesTable();
		htmlText = "<html><head><title>"
				+ doc.getSummaryInformation().getTitle()
				+ "</title></head><body>";
		// 创建临时字符串,好加以判断一串字符是否存在相同格式
		String tempString = "";
		for (int i = 0; i < length - 1; i++) {
			// 整篇文章的字符通过一个个字符的来判断,range为得到文档的范围
			Range range = new Range(i, i + 1, doc);
			CharacterRun cr = range.getCharacterRun(0);
			if (pTable.hasPicture(cr)) {
				// 读写图片
				this.readPicture(pTable, cr);
			} else {
				Range range2 = new Range(i + 1, i + 2, doc);
				// 第二个字符
				CharacterRun cr2 = range2.getCharacterRun(0);
				// 当前字符
				char currentChar = cr.text().charAt(0);
				// 判断是否为回车符
				if (currentChar == ENTER_ASCII)
					tempString += "<br/>";
				// 判断是否为空格符
				else if (currentChar == SPACE_ASCII)
					tempString += "&nbsp;";
				// 判断是否为水平制表符
				else if (currentChar == TABULATION_ASCII)
					tempString += " &nbsp;&nbsp;&nbsp;";
				// 比较前后2个字符是否具有相同的格式
				boolean flag = compareCharStyle(cr, cr2);
				String fontStyle = "<span style='font-family:"
						+ cr.getFontName() + ";font-size:" + cr.getFontSize()
						/ 2 + "pt;";
				if (cr.isBold())
					fontStyle += "font-weight:bold;";
				if (cr.isItalic())
					fontStyle += "font-style:italic;";
				if (flag && i != length - 2)
					tempString += currentChar;
				else if (!flag) {
					htmlText += fontStyle + "'>" + tempString + currentChar
							+ "</span>";
					tempString = "";
				} else
					htmlText += fontStyle + "'>" + tempString + currentChar
							+ "</span>";
			}
		}
		htmlText += "</body></html>";
		this.writeFile(htmlText);
	}

	private void readPicture(PicturesTable pTable, CharacterRun cr)
			throws Exception {
		// 提取图片
		org.apache.poi.hwpf.usermodel.Picture pic = pTable.extractPicture(cr,
				false);
		// 返回POI建议的图片文件名
		String afileName = pic.suggestFullFileName();
		OutputStream out = new FileOutputStream(new File(Environment
				.getExternalStorageDirectory().getAbsoluteFile()
				+ File.separator + afileName));
		pic.writeImageContent(out);
		htmlText += "<img src='"
				+ Environment.getExternalStorageDirectory().getAbsoluteFile()
				+ File.separator + afileName + "'/>";
	}

	private boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) {
		boolean flag = false;
		if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic()
				&& cr1.getFontName().equals(cr2.getFontName())
				&& cr1.getFontSize() == cr2.getFontSize()) {
			flag = true;
		}
		return flag;
	}

	private void writeFile(String s) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/" + "TestWord.html");
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(s);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}

	public static Intent getWordFileIntent(String param)

	{

		Intent intent = new Intent("android.intent.action.VIEW");

		intent.addCategory("android.intent.category.DEFAULT");

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/msword");

		return intent;

	}
}
