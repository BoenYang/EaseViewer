package team.top.activity;

import java.io.IOException;
import java.lang.reflect.Field;

import com.itextpdf.text.xml.simpleparser.NewLineHandler;

import team.top.dialog.WaittingDialog;
import team.top.exception.WriteHtmlExcpetion;
import team.top.utils.ExcelToHtml;
import team.top.utils.WordToHtml;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

public class ShowOfficeActivity extends Activity {

	private WebView webView;
	private String htmlPath = "";
	private String extension;
	private String path;
	private final static int PRASE_SUCCESSFUL = 0;
	private final static int FILE_NOT_FOUND = 1;
	private final static int PRASE_FAILED = -1;
	private WaittingDialog waittingDialog;
	
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
		extension = intent.getStringExtra("extension");
		path = intent.getStringExtra("path");
		waittingDialog = new WaittingDialog(ShowOfficeActivity.this);
		waittingDialog.show();
		Thread thread = new Thread(new PraseOfficeThread());
		thread.start();
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case PRASE_SUCCESSFUL:
				waittingDialog.dismiss();
				webView.loadUrl("file://" + htmlPath);
				break;
			case PRASE_FAILED:
				waittingDialog.dismiss();
				ShowOfficeActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	class PraseOfficeThread implements Runnable{

		@Override
		public void run() {
			Message msg = new Message();
			if (extension.equals("doc")) {
				WordToHtml word2Html = null;
					try {
						word2Html = new WordToHtml(path);
						htmlPath = word2Html.convertToHtml();
					} catch (WriteHtmlExcpetion e) {
						msg.what = PRASE_FAILED;
						System.out.println("读取文件失败");
						e.printStackTrace();
					} catch (IOException e) {
						msg.what = PRASE_FAILED;
						System.out.println("文件不存在");
						e.printStackTrace();
					}
			
				
			} else if (extension.equals("xls")) {
				ExcelToHtml excel2Html;
					try {
						excel2Html = new ExcelToHtml(path);
						htmlPath = excel2Html.convert2Html();
					} catch (IOException e) {
						msg.what = PRASE_FAILED;
						System.out.println("文件不存在");
						e.printStackTrace();
					} catch (WriteHtmlExcpetion e) {
						msg.what = PRASE_FAILED;
						System.out.println("读取文件失败");
						e.printStackTrace();
					}
			
			}
			msg.what = PRASE_SUCCESSFUL;
			handler.sendMessage(msg);
		}
		
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
