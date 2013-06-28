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

public class HelpActivity extends Activity {

	Button backButton;
	LinearLayout title_help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		backButton = (Button) findViewById(R.id.back_btn_help);
		title_help = (LinearLayout) findViewById(R.id.title_help);
		SetBackgroundImage.setBackGround(HelpActivity.this, title_help);
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
