package team.top.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class ShowContentActivity extends Activity {

	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcontent_activity);
		webView = (WebView)findViewById(R.id.wordWeb);
		Intent intent = getIntent();
		String url = intent.getStringExtra("path");
		webView.loadUrl("content://com.android.htmlfileprovider/sdcard/AndroidReader/TestWord.html");
		System.out.println("file:///mnt" + url );
	}

}
