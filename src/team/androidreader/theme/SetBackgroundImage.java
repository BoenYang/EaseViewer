package team.androidreader.theme;

import team.top.activity.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

/**
 * 设置默认背景和更换背景
 * 
 * 
 */
public class SetBackgroundImage {
	
	public static void changeBackgroundImage(int i) {

	}
	
	/**
	 * 设置背景图片
	 * @param activity
	 * @param bg_title 在这里传的是LinearLayout
	 */
	public static void setBackGround(Activity activity, View bg_title, View bg_activity) {
		String bg = getBG(activity);
		if (bg != null) {
			if ("white".equals(bg)) {
				bg_title.setBackgroundResource(R.color.holo_blue_dark);
				bg_activity.setBackgroundColor(Color.WHITE);
			}
			if ("black".equals(bg)) {
				bg_title.setBackgroundResource(R.color.black_title);
				bg_activity.setBackgroundResource(R.color.bg_black);
			}
		}else{
			saveBackground(activity,"white");
			bg_title.setBackgroundResource(R.color.holo_blue_dark);
			bg_activity.setBackgroundColor(Color.WHITE);
		}
	}
	
	/**
	 * 更改默认背景图片
	 * @param activity
	 * @param imageTag	
	 */
	public static void saveBackground(Activity activity, String imageTag) {
		SharedPreferences preferences = activity.getSharedPreferences("theme",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("background", imageTag);
		editor.commit();
	}
	
	/**
	 * 得到默认图片标识
	 * @param activity
	 * @return 
	 */
	private static String getBG(Activity activity) {
		SharedPreferences preferences = activity.getSharedPreferences("theme",Activity.MODE_PRIVATE);
		String bg = preferences.getString("background", null);
		return bg;
	}
}
