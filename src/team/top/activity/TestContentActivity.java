package team.top.activity;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import team.top.dialog.SelectDialog;
import team.top.dialog.SetNameDialog;
import team.top.dialog.WaittingDialog;
import team.top.utils.FileSystem;
import team.top.utils.ScreenCapturer;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class TestContentActivity extends Activity {

	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button pdfshowBtn;
	public Button testWaittingBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testcontent);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.cameraBtn);
		btn6 = (Button) findViewById(R.id.screenshot);
		pdfshowBtn = (Button) findViewById(R.id.pdfshow);
		testWaittingBtn = (Button)findViewById(R.id.testWattingDialog);
		btn5.setOnClickListener(new BtnListener());
		btn4.setOnClickListener(new BtnListener());
		btn3.setOnClickListener(new BtnListener());
		btn6.setOnClickListener(new BtnListener());
		pdfshowBtn.setOnClickListener(new BtnListener());
		testWaittingBtn.setOnClickListener(new BtnListener());
	}

	class BtnListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.btn3:
				Intent intent = new Intent();
				intent.setClass(TestContentActivity.this,
						ImageToPdfActivity.class);
				startActivity(intent);
				break;
			case R.id.btn4:
				SelectDialog selectDialog = new SelectDialog(
						TestContentActivity.this);
				selectDialog.show();
				break;
			case R.id.cameraBtn:
				SetNameDialog setNameDialog = new SetNameDialog(
						TestContentActivity.this);
				setNameDialog.show();
			case R.id.screenshot:
		
				String name = "ScreenShot" + FileSystem.GetNameByTime();
				if (ScreenCapturer.TakeScreenShot(TestContentActivity.this,
						FileSystem.PRTSCR_DIR, name)) {
					System.out.println("---------------------------");
					break;
				}
			case R.id.pdfshow:
				Intent intent2 = new Intent();
				intent2.setClass(TestContentActivity.this, PdfReaderActivity.class);
				startActivity(intent2);
				break;
			case R.id.testWattingDialog:
				File file = new File("/sdcard/test.pdf");
				System.out.println(file.hashCode());
				break;
			}
		}
	}

	/**
	 * 拍照获取图片
	 * 
	 */
	protected void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// photo directory
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidReader/Images/image.jpg");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		TestContentActivity.this.startActivityForResult(intent, 0);
	}
}
