package team.androidreader.scanner;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import team.androidreader.mainview.MainActivity;
import team.androidreader.test.TestContentActivity;
import team.top.activity.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoPreviewActivity extends Activity {

	/**
	 * 手机SD卡图片缓存
	 */
	public static HashMap<String, SoftReference<Bitmap>> mPhoneAlbumCache = new HashMap<String, SoftReference<Bitmap>>();

	private Button backBtn;
	private Button saveBtn;
	private Button lRoateBtn;
	private Button rRoateBtn;
	private ImageView photoPreView;

	private String mPath;// 旧图片地址
	private Bitmap mBitmap;// 旧图片
	private Bitmap tempBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photopreview);
		findViewById();
		setListener();
		init();

	}

	private void findViewById() {
		backBtn = (Button) findViewById(R.id.back);
		saveBtn = (Button) findViewById(R.id.save);
		lRoateBtn = (Button) findViewById(R.id.leftRoate);
		rRoateBtn = (Button) findViewById(R.id.rightRoate);
		photoPreView = (ImageView) findViewById(R.id.photoPreView);
	}

	private void setListener() {
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
				reSave(mPath, TestContentActivity.curDegrees);
				//reSave(tempBitmap, TestContentActivity.curDegrees);
				break;
			case R.id.back:

				break;
			case R.id.leftRoate:
				tempBitmap = TestContentActivity.left(mBitmap);
				photoPreView.setImageBitmap(tempBitmap);
				break;
			case R.id.rightRoate:
				tempBitmap = TestContentActivity.right(mBitmap);
				photoPreView.setImageBitmap(tempBitmap);
				break;
			default:
				break;
			}
		}
	}

	private void init() {
		mPath = getIntent().getStringExtra("path");
		mBitmap = TestContentActivity.createBitmap(mPath,
				MainActivity.mScreenWidth, MainActivity.mScreenHeight);
		// 显示图片
		photoPreView.setImageBitmap(mBitmap);
	}

	public static void reSave(String path, float degrees) {
		Bitmap oldBitmap = BitmapFactory.decodeFile(path);
		System.out.println(path);
		Bitmap newBitmap = TestContentActivity.rotate(oldBitmap, degrees);
		SaveToLocal(newBitmap, path);
	}
	
//	public static void reSave(Bitmap bitmap, float degrees) {
//		Bitmap newBitmap = TestContentActivity.rotate(bitmap, degrees);
//		SaveToLocal(newBitmap, Environment.getExternalStorageDirectory() + "/AndroidReader/123.png");
//	}

	public static boolean SaveToLocal(Bitmap bitmap, String path) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return false;
		}
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据地址获取手机SD卡图片
	 */
	public static Bitmap getPhoneAlbum(String path) {
		Bitmap bitmap = null;
		if (mPhoneAlbumCache.containsKey(path)) {
			SoftReference<Bitmap> reference = mPhoneAlbumCache.get(path);
			bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}
		bitmap = BitmapFactory.decodeFile(path);
		mPhoneAlbumCache.put(path, new SoftReference<Bitmap>(bitmap));
		return bitmap;
	}

}
