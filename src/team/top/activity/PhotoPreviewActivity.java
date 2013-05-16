package team.top.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PhotoPreviewActivity extends Activity {

	private Button backBtn;
	private Button saveBtn;
	private Button lRoateBtn;
	private Button rRoateBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photopreview);
		backBtn = (Button) findViewById(R.id.back);
		saveBtn = (Button) findViewById(R.id.save);
		lRoateBtn = (Button) findViewById(R.id.leftRoate);
		rRoateBtn = (Button) findViewById(R.id.rightRoate);
		backBtn.setOnClickListener(new PhotoBtnClickListener());
		saveBtn.setOnClickListener(new PhotoBtnClickListener());
		lRoateBtn.setOnClickListener(new PhotoBtnClickListener());
		rRoateBtn.setOnClickListener(new PhotoBtnClickListener());
	}

	class PhotoBtnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.save:

				break;
			case R.id.back:

				break;
			case R.id.leftRoate:

				break;
			case R.id.rightRoate:

				break;
			default:
				break;
			}
		}

	}

}
