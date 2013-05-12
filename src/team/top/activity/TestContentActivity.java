package team.top.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class TestContentActivity extends Activity {

	private Button btn3;
	private Button btn4;
	private Button btn5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testcontent);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.cameraBtn);
		btn5.setOnClickListener(new BtnListener());
		btn4.setOnClickListener(new BtnListener());
		btn3.setOnClickListener(new BtnListener());
	}

	class BtnListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.btn3:
				break;
			case R.id.btn4:
				
				break;
			case R.id.cameraBtn:
				Intent intent_camera = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent_camera, 1);
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
