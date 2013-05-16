package team.top.activity;

import java.util.Calendar;

import team.top.dialog.SelectDialog;
import team.top.dialog.SetNameDialog;
import team.top.utils.ScreenCapturer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestContentActivity extends Activity {

	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testcontent);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.cameraBtn);
		btn6 = (Button) findViewById(R.id.screenshot);
		btn5.setOnClickListener(new BtnListener());
		btn4.setOnClickListener(new BtnListener());
		btn3.setOnClickListener(new BtnListener());
		btn6.setOnClickListener(new BtnListener());
	}

	class BtnListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.btn3:
				Intent intent = new Intent();
				intent.setClass(TestContentActivity.this, ImageToPdfActivity.class);
				startActivity(intent);
				break;
			case R.id.btn4:
				SelectDialog selectDialog = new SelectDialog(TestContentActivity.this);
				selectDialog.show();
				break;
			case R.id.cameraBtn:
				SetNameDialog setNameDialog = new SetNameDialog(TestContentActivity.this);
				setNameDialog.show();
			case R.id.screenshot:
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int mounth = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int munites = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);
				String name = "ScreenShot" + year + mounth + day + hour + munites + second;
				System.out.println(name);
				if(ScreenCapturer.TakeScreenShot(TestContentActivity.this, "/sdcard",name)){
					System.out.println("---------------------------");
				}
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
