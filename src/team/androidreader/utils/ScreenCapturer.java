package team.androidreader.utils;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.view.View;

public class ScreenCapturer {

	@SuppressWarnings("deprecation")
	public static boolean TakeScreenShot(Activity activity, String saveDir,
			String saveName) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();

		Bitmap bitmap = view.getDrawingCache();
		// Point size = new Point();
		// activity.getWindowManager().getDefaultDisplay().getSize(size);
		int w = activity.getWindowManager().getDefaultDisplay().getWidth();
		int h = activity.getWindowManager().getDefaultDisplay().getHeight();

		// int titlebarH
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h);
		return BitmapHelper.writeBitmapToSdcard(bitmap, saveDir + File.separator + saveName,
				CompressFormat.JPEG, 100);
	}

}
