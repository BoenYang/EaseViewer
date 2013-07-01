package team.androidreader.txt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import team.androidreader.pdf.PdfReaderActivity;
import team.androidreader.utils.FileSystem;
import team.androidreader.utils.ScreenCapturer;
import team.top.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

public class TextReaderActivity extends Activity {

	private EditText textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_textreader);
		textView = (EditText) findViewById(R.id.textreader_text);
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		File file = new File(path);
		String content = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fileInputStream.read(data);
			content = new String(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		textView.setText(content);
		textView.setFocusable(false);
		textView.setFocusableInTouchMode(false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 5) {
			ScreenCapturer.TakeScreenShot(TextReaderActivity.this,
					FileSystem.PRTSCR_DIR,
					"PrtSc_" + FileSystem.GetTimeFileName() + ".jpg");
			Toast.makeText(getApplicationContext(),
					R.string.screen_shot_success, Toast.LENGTH_SHORT).show();
		}
		super.onKeyDown(keyCode, event);
		return true;
	}
}
