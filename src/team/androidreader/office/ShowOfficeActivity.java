package team.androidreader.office;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import team.androidreader.constant.Constant;
import team.androidreader.dialog.WaittingDialog;
import team.androidreader.exception.WriteHtmlExcpetion;
import team.androidreader.utils.FileSystem;
import team.androidreader.utils.OnProgressListener;
import team.androidreader.utils.ScreenCapturer;
import team.top.activity.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

@SuppressLint("HandlerLeak")
public class ShowOfficeActivity extends Activity implements OnProgressListener {

	private WebView webView;
	private String htmlPath = "";
	private String extension;
	private String path;
	private WaittingDialog waittingDialog;

	private Message msg = new Message();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showoffice);
		init();
		this.OnProgressStart();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init() {
		waittingDialog = new WaittingDialog(ShowOfficeActivity.this,
				R.style.MyDialog);
		webView = (WebView) findViewById(R.id.officeweb);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.getSettings().setDefaultTextEncodingName("gbk");
		setZoomControlGone(webView);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case Constant.PRASE_SUCCESSFUL:
				waittingDialog.dismiss();
				webView.loadUrl("file://" + htmlPath);
				break;
			case Constant.PRASE_FAILED:
				waittingDialog.dismiss();
				ShowOfficeActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		}

	};

	class PraseOfficeThread implements Runnable {
		@Override
		public void run() {
			String fileName = FileSystem.GetFileName(path);
			if (extension.equals("doc")) {
				WordToHtml word2Html = null;
				try {
					if (hasWordConverted(fileName)) {
						htmlPath = FileSystem.WORD_CACHE + File.separator
								+ fileName + File.separator + fileName
								+ ".html";
					} else {
						word2Html = new WordToHtml(path);
						htmlPath = word2Html.convertToHtml();
					}
				} catch (WriteHtmlExcpetion e) {
					msg.what = Constant.PRASE_FAILED;
					System.out.println("读取文件失败");
					e.printStackTrace();
				} catch (IOException e) {
					msg.what = Constant.PRASE_FAILED;
					System.out.println("文件不存在");
					e.printStackTrace();
				}
			} else if (extension.equals("xls")) {
				ExcelToHtml excel2Html;
				try {
					if (hasExcelConverted(fileName)) {
						htmlPath = FileSystem.EXCEL_CACHE + File.separator
								+ fileName + File.separator + fileName
								+ ".html";
					} else {
						excel2Html = new ExcelToHtml(path);
						htmlPath = excel2Html.convert2Html();
					}
				} catch (IOException e) {
					msg.what = Constant.PRASE_FAILED;
					System.out.println("文件不存在");
					e.printStackTrace();
				} catch (WriteHtmlExcpetion e) {
					msg.what = Constant.PRASE_FAILED;
					System.out.println("读取文件失败");
					e.printStackTrace();
				}
			}
			msg.what = Constant.PRASE_SUCCESSFUL;
			ShowOfficeActivity.this.OnProgressFinished();
		}
	}

	private void setZoomControlGone(View view) {
		Class<WebView> classType;
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

	private boolean hasWordConverted(String fileName) {
		File file = new File(FileSystem.WORD_CACHE + File.separator + fileName);
		if (file.exists()) {
			if (new File(FileSystem.WORD_CACHE + File.separator + fileName
					+ File.separator + fileName + ".html").exists()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean hasExcelConverted(String fileName) {
		File file = new File(FileSystem.EXCEL_CACHE + File.separator + fileName);
		if (file.exists()) {
			if (new File(FileSystem.EXCEL_CACHE + File.separator + fileName
					+ File.separator + fileName + ".html").exists()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void OnProgressStart() {
		Intent intent = getIntent();
		extension = intent.getStringExtra("extension");
		path = intent.getStringExtra("path");
		waittingDialog.show();
		waittingDialog.setText(R.string.dialog_waitting_loading);
		Thread thread = new Thread(new PraseOfficeThread());
		thread.start();
	}

	@Override
	public void OnProgressFinished() {
		handler.sendMessage(msg);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 5) {
			ScreenCapturer.TakeScreenShot(ShowOfficeActivity.this,
					FileSystem.PRTSCR_DIR,
					"PrtSc_" + FileSystem.GetTimeFileName() + ".jpg");
			Toast.makeText(getApplicationContext(),
					R.string.screen_shot_success, Toast.LENGTH_SHORT).show();
		}
		super.onKeyDown(keyCode, event);
		return true;
	}
}
