package team.top.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * 
 * @author ybw
 * main activity for show file list
 */
public class MainActivity extends Activity {

	private Button testpoiBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		testpoiBtn = (Button)findViewById(R.id.testPoi);
		testpoiBtn.setOnClickListener(new BtnOnClickListener());
		
	}
	
	class BtnOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();
			switch (id) {
			case R.id.testPoi:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, TestContentActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
