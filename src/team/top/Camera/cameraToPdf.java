package team.top.Camera;

import java.io.File;

import team.top.activity.R;
import team.top.utils.FileSystem;
import team.top.utils.PngToPdf;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author wzy
 *
 */
public class cameraToPdf extends Activity {
	FileSystem fileSystem;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoconfirm);
		Bitmap bitmap = getDiskBitmap(FileSystem.SDCARD_PATH
				+ "/myImage/111.png");
		Button confirmBtn = (Button) findViewById(R.id.confirm);
		Button cancelBtn = (Button) findViewById(R.id.cancel);
		Log.v("=", "done");
		((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);// 将图片显示在ImageView里

		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PngToPdf.Pdf(FileSystem.SDCARD_PATH + "/myImage/111.png",
						FileSystem.SDCARD_PATH + "/myImage/111.pdf");
				Toast.makeText(getApplicationContext(), "转换成功！",
						Toast.LENGTH_SHORT).show();
				cameraToPdf.this.finish();
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				cameraToPdf.this.finish();
			}
		});

	}

	/**
	 * 
	 * 获取图片文件
	 * 
	 * @param pathString
	 *            图片路径
	 * @return Bitmap
	 */

	private Bitmap getDiskBitmap(String pathString) {
		Bitmap bitmap = null;
		try {
			File file = new File(pathString);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(pathString);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return bitmap;
	}
}
