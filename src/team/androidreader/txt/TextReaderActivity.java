package team.androidreader.txt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import team.top.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;

public class TextReaderActivity extends Activity {

	private EditText textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_textreader);
		textView = (EditText)findViewById(R.id.textreader_text);
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
}
