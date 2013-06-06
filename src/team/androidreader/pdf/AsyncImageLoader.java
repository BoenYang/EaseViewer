package team.androidreader.pdf;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import org.vudroid.pdfdroid.codec.PdfPage;

import team.androidreader.utils.BitmapHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncImageLoader {
	private HashMap<String, SoftReference<Bitmap>> imageCache;
	private int position;
	private Bitmap bitmap;

	public AsyncImageLoader(HashMap<String, SoftReference<Bitmap>> imageCache,
			int position) {
		this.imageCache = imageCache;
		this.position = position;
	}

	/***
	 * 下载图片
	 * 
	 * @param imageUrl
	 *            图片地址
	 * @param imageCallback
	 *            回调接口
	 * @return
	 */
	public Bitmap loadBitmap(final String imagePath, final CallBack callBack,
			final ImageView imageView) {
		if (imageCache.containsKey(imagePath)) {
			SoftReference<Bitmap> softReference = imageCache.get(imagePath);
			bitmap = softReference.get();
			if (bitmap != null) {
				PdfShowAdapter.threadCount--;
				return bitmap;
			}
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				callBack.setBitmap(imageView, bitmap);
			}
		};

		new Thread() {
			@Override
			public void run() {
				synchronized (PdfShowAdapter.threadCount) {
					File file = new File(imagePath);
					if (!file.exists()) {
						PdfPage page = (PdfPage) PdfReaderActivity.document
								.getPage(position);
						Bitmap b = page
								.renderBitmap(page.getWidth(),
										page.getHeight(), new RectF(0.0f, 0.0f,
												1.0f, 1.0f));
						BitmapHelper.writeBitmapToSdcard(b, imagePath,
								CompressFormat.JPEG, 100);
						bitmap = b;
					} else {
						bitmap = loadBitmapFromSdcrad(imagePath);
						
					}
					imageCache.put(imagePath, new SoftReference<Bitmap>(
							bitmap));
					PdfShowAdapter.bitmaps.set(position, imageCache);
					Message msg = handler.obtainMessage(0, bitmap);
					handler.sendMessage(msg);
					PdfShowAdapter.threadCount--;
				}
			}
		}.start();
		return null;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap loadBitmapFromSdcrad(String path) {
		return BitmapFactory.decodeFile(path);
	}

	public interface CallBack {
		public Bitmap setBitmap(ImageView imageView, Bitmap bitmap);
	}
}
