package team.androidreader.helpabout;

import team.androidreader.theme.SetBackgroundImage;
import team.top.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AboutActivity extends Activity {

	private Button backButton;
	LinearLayout title_about;
	RelativeLayout layout_about;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		backButton = (Button) findViewById(R.id.back_btn_about);
		title_about = (LinearLayout)findViewById(R.id.title_about);
		layout_about = (RelativeLayout)findViewById(R.id.layout_about);
		SetBackgroundImage.setBackGround(AboutActivity.this, title_about, layout_about);
		initListener();
	}

	private void initListener() {
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
