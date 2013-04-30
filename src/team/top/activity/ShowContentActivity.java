package team.top.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShowContentActivity extends Activity {

	WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcontent_activity);
		webView = (WebView) findViewById(R.id.wordWeb);
		Intent intent = getIntent();
		String url = intent.getStringExtra("path");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.requestFocus();
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
		System.out.println("content://com.android.htmlfileprovider" + url);
		webView.loadUrl("content://com.android.htmlfileprovider" + url);
		// File file = new File(url);
		//
		// byte[] data = null;
		// try {
		// data = new byte[(int)file.length()];
		// FileInputStream fileInputStream = new FileInputStream(file);
		// fileInputStream.read(data);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// String string = new String(data);
		// webView.loadData(string, "text/html", "UTF-8");
		// System.out.println(string);

	}

}
