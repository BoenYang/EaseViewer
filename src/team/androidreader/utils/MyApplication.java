package team.androidreader.utils;

import team.androidreader.mainview.RightCategoryFragment;
import team.top.activity.R;
import android.app.Application;

public class MyApplication extends Application {

	public boolean isLogin = false;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}


	public static void setChoosed(int choosed) {
		switch (choosed) {
		case 0:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.drawable.button_category);
			break;
		case 1:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.drawable.button_category);
			break;
		case 2:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.drawable.button_category);
			break;
		case 3:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.drawable.button_category);
			break;
		case 4:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.drawable.button_category);
			break;
		case 5:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.color.brown_pressed);
			break;
		default:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.drawable.button_category);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.drawable.button_category);
			break;
		}
	}
}
