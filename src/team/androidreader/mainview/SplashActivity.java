package team.androidreader.mainview;

import team.top.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	Handler handler = new Handler();
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		preferences = getSharedPreferences("FIRST_TIME", MODE_WORLD_READABLE);

		boolean FIRST_TIME = preferences.getBoolean("FIRST_TIME", true);

		if (FIRST_TIME) {
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this,
							IntroduceActivity.class);
					startActivity(intent);
					SplashActivity.this.finish();

				}
			}, 1000);
		} else {
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this, MainActivity.class);
					startActivity(intent);
					SplashActivity.this.finish();

				}
			}, 1000);
		}

	}

}
