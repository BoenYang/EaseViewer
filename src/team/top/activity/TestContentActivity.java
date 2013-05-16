package team.top.activity;

import java.util.Calendar;

import team.top.dialog.SelectDialog;
import team.top.dialog.SetNameDialog;
import team.top.utils.ScreenCapturer;
import android.app.Activity;
import android.content.Intent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class TestContentActivity extends Activity {

	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;

	String FileName;
	public static String lastImagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testcontent);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.cameraBtn);
		btn6 = (Button) findViewById(R.id.screenshot);
		btn5.setOnClickListener(new BtnListener());
		btn4.setOnClickListener(new BtnListener());
		btn3.setOnClickListener(new BtnListener());
		btn6.setOnClickListener(new BtnListener());
	}

	class BtnListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.btn3:
				Intent intent = new Intent();
				intent.setClass(TestContentActivity.this, ImageToPdfActivity.class);
				startActivity(intent);
				break;
			case R.id.btn4:
				SelectDialog selectDialog = new SelectDialog(TestContentActivity.this);
				selectDialog.show();
				break;
			case R.id.cameraBtn:
				SetNameDialog setNameDialog = new SetNameDialog(TestContentActivity.this);
				setNameDialog.show();
			case R.id.screenshot:
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int mounth = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int munites = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);
				String name = "ScreenShot" + year + mounth + day + hour + munites + second;
				System.out.println(name);
				if(ScreenCapturer.TakeScreenShot(TestContentActivity.this, "/sdcard",name)){
					System.out.println("---------------------------");
				break;
			}
		}
	}

	/**
	 * 拍照获取图片
	 * 
	 */
	protected void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//photo directory
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidReader/Images/image.jpg");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		TestContentActivity.this.startActivityForResult(intent, 0);
	}

	/**
	 * 保存图片到本地(JPG)
	 * 
	 * @param bm
	 *            保存的图片
	 * @return 图片路径
	 */
	public String saveToLocal(Bitmap bm) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return null;
		}
		FileOutputStream fileOutputStream = null;
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidReader/ImagesCache/");
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = UUID.randomUUID().toString() + ".jpg";
		String filePath = Environment.getExternalStorageDirectory()
				+ "/AndroidReader/ImagesCache/" + fileName;
		File f = new File(filePath);
		if (!f.exists()) {
			try {
				f.createNewFile();
				fileOutputStream = new FileOutputStream(filePath);
				bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
			} catch (IOException e) {
				return null;
			} finally {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return filePath;
	}

	/**
	 * 创建一个缩放的图片
	 * 
	 * @param path
	 *            图片地址
	 * @param w
	 *            图片宽度
	 * @param h
	 *            图片高度
	 * @return 缩放后的图片
	 */
	public Bitmap createBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存。
			BitmapFactory.decodeFile(path, opts);
			int srcWidth = opts.outWidth;// 获取图片的原始宽度
			int srcHeight = opts.outHeight;// 获取图片原始高度
			int destWidth = 0;
			int destHeight = 0;
			// 缩放的比例
			double ratio = 0.0;
			if (srcWidth < w || srcHeight < h) {
				ratio = 0.0;
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长度
				ratio = (double) srcWidth / w;
				destWidth = w;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = (double) srcHeight / h;
				destHeight = h;
				destWidth = (int) (srcWidth / ratio);
			}
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			newOpts.inSampleSize = (int) ratio + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// 获取缩放后图片
			return BitmapFactory.decodeFile(path, newOpts);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	// **************************************************************//
	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	}
}
