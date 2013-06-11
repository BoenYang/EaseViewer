package team.androidreader.test;

import team.androidreader.utils.FileOperationHelper;
import team.androidreader.utils.OnProgressListener;
import team.top.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class TestContentActivity extends Activity implements OnProgressListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testcontent);
		init();
	}

	private void init() {
	}

	class BtnListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			}
		}
	}
	
	class MoveThread implements Runnable{

		@Override
		public void run() {
			FileOperationHelper.MoveFile("/sdcard/AndroidReader", "/sdcard/Photo");
			TestContentActivity.this.OnProgressFinished();
		}
		
	}

	class DeleteThread implements Runnable {

		@Override
		public void run() {
			FileOperationHelper.DeleteFile("/sdcard/AndroidReader");
			TestContentActivity.this.OnProgressFinished();
		}

	}

	class CopyTread implements Runnable {

		@Override
		public void run() {
			FileOperationHelper.copyFile("/sdcard/AndroidReader", "/sdcard");
			TestContentActivity.this.OnProgressFinished();
		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}

	};

	@Override
	public void OnProgressStart() {
	}

	@Override
	public void OnProgressFinished() {
		handler.sendEmptyMessage(0);
	}

}
