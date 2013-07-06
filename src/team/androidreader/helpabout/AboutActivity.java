package team.androidreader.helpabout;

import team.androidreader.mainview.IntroduceActivity;
import team.androidreader.theme.SetBackgroundImage;
import team.top.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AboutActivity extends Activity {

	private Button backButton;
	private LinearLayout title_about;
	private RelativeLayout preference_function_intoduce;
	private RelativeLayout preference_about_team;
	private RelativeLayout preference_advice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		backButton = (Button) findViewById(R.id.back_btn_about);
		title_about = (LinearLayout) findViewById(R.id.title_about);
		preference_function_intoduce = (RelativeLayout) findViewById(R.id.preference_function_intoduce);
		preference_about_team = (RelativeLayout) findViewById(R.id.preference_about_team);
		preference_advice = (RelativeLayout) findViewById(R.id.preference_advice);
		SetBackgroundImage.setBackGround(AboutActivity.this, title_about);
		initListener();
	}

	private void initListener() {
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		preference_function_intoduce
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(AboutActivity.this,
								IntroduceActivity.class);
						startActivity(intent);
					}
				});

		preference_advice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),
						R.string.advice_waitting, Toast.LENGTH_SHORT).show();
			}
		});

		preference_about_team.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri
						.parse("https://github.com/TopLittelbob/AndroidReader");
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			}
		});
	}

}
