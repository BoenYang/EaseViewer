package team.top.activity;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.poi.hssf.record.ScenarioProtectRecord;

import team.top.exception.WriteHtmlExcpetion;
import team.top.utils.ExcelToHtml;
import team.top.utils.ScreenCapturer;
import team.top.utils.WordToHtml;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

public class ShowContentActivity extends Activity {

	private WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		webView = new WebView(this);
		setContentView(webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.getSettings().setDefaultTextEncodingName("gbk");
		setZoomControlGone(webView);
		Intent intent = getIntent();
		String extension = intent.getStringExtra("extension");
		String path = intent.getStringExtra("path");
		String htmlPath = "";
		if (extension.equals("doc")) {
			WordToHtml word2Html = null;
				try {
					word2Html = new WordToHtml(path);
					htmlPath = word2Html.convertToHtml();
				} catch (WriteHtmlExcpetion e) {
					System.out.println("读取文件失败");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("文件不存在");
					e.printStackTrace();
				}
		
			
		} else if (extension.equals("xls")) {
			ExcelToHtml excel2Html;
				try {
					excel2Html = new ExcelToHtml(path);
					htmlPath = excel2Html.convert2Html();
				} catch (IOException e) {
					System.out.println("文件不存在");
					e.printStackTrace();
				} catch (WriteHtmlExcpetion e) {
					System.out.println("读取文件失败");
					e.printStackTrace();
				}
		
		}
		webView.loadUrl("file://" + htmlPath);
	}

	public void setZoomControlGone(View view) {
		Class classType;
		Field field;
		try {
			classType = WebView.class;
			field = classType.getDeclaredField("mZoomButtonsController");
			field.setAccessible(true);
			ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
					view);
			mZoomButtonsController.getZoomControls().setVisibility(
					View.INVISIBLE);
			try {
				field.set(view, mZoomButtonsController);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

}
