package team.androidreader.theme;

import team.top.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ChangeThemeActivity extends Activity {
	Button black_btn, white_btn,back_btn;
	LinearLayout title_theme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changetheme);
		back_btn = (Button)findViewById(R.id.back_btn_theme);
		black_btn = (Button) findViewById(R.id.black_btn);
		white_btn = (Button) findViewById(R.id.white_btn);
		title_theme = (LinearLayout) findViewById(R.id.title_theme);
		SetBackgroundImage.setBackGround(ChangeThemeActivity.this, title_theme);
		initListener();
	}

	private void initListener() {
		back_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		black_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SetBackgroundImage.saveBackground(ChangeThemeActivity.this,
						"black");
				SetBackgroundImage.setBackGround(ChangeThemeActivity.this,
						title_theme);

			}
		});

		white_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SetBackgroundImage.saveBackground(ChangeThemeActivity.this,
						"white");
				SetBackgroundImage.setBackGround(ChangeThemeActivity.this,
						title_theme);

			}
		});
	}
}
