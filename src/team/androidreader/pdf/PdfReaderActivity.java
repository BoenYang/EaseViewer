package team.androidreader.pdf;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.vudroid.pdfdroid.codec.PdfContext;
import org.vudroid.pdfdroid.codec.PdfDocument;
import org.vudroid.pdfdroid.codec.PdfPage;

import team.androidreader.constant.Constant;
import team.androidreader.dialog.WaittingDialog;
import team.androidreader.utils.BitmapHelper;
import team.androidreader.utils.FileSystem;
import team.androidreader.utils.OnProgressListener;
import team.androidreader.utils.ScreenCapturer;
import team.top.activity.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class PdfReaderActivity extends Activity implements OnProgressListener {

	private ListView listview;
	private PdfShowAdapter pdfShowAdapter;
	private String path;
	private String filename;
	private WaittingDialog waittingDialog;
	private List<HashMap<String, SoftReference<Bitmap>>> bitmaps = new ArrayList<HashMap<String, SoftReference<Bitmap>>>();
	public static PdfDocument document;
	Message msg = new Message();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdfreader);
		init();
		this.OnProgressStart();
	}

	private void init() {
		listview = (ListView) findViewById(R.id.pdfreader_listview);
		listview.setOnTouchListener(new ZoomListener(listview));
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case Constant.PRASE_SUCCESSFUL:
				waittingDialog.dismiss();
				pdfShowAdapter = new PdfShowAdapter(PdfReaderActivity.this,
						bitmaps, filename, R.layout.item_pdf);
				listview.setAdapter(pdfShowAdapter);
				break;
			case Constant.PRASE_FAILED:
				break;
			}
			super.handleMessage(msg);
		}

	};

	class ParseThread implements Runnable {
		@Override
		public void run() {
			String saveDir = FileSystem.PDF_CACHE + File.separator + filename;
			PdfContext pdfContext = new PdfContext();
			document = (PdfDocument) pdfContext.openDocument(path);

			int num = document.getPageCount();

			for (int i = 0; i < num; i++) {
				HashMap<String, SoftReference<Bitmap>> item = new HashMap<String, SoftReference<Bitmap>>();
				item.put(saveDir + File.separator + filename + i + ".jpg",
						new SoftReference<Bitmap>(null));
				bitmaps.add(item);
			}

			if (num > 20) {
				num = 20;
			}

			File dir = new File(saveDir);
			if (!dir.exists()) {
				dir.mkdir();
			}

			PdfPage page = null;
			String saveImageName = "";
			for (int i = 0; i < num; i++) {
				saveImageName = saveDir + File.separator + filename + i
						+ ".jpg";
				File file = new File(saveImageName);
				if (!file.exists()) {
					page = (PdfPage) document.getPage(i);
					Bitmap bitmap = page
							.renderBitmap(page.getWidth(), page.getHeight(),
									new RectF(0.0f, 0.0f, 1.0f, 1.0f));
					if (BitmapHelper.writeBitmapToSdcard(bitmap, saveImageName,
							CompressFormat.JPEG, 100)) {
						HashMap<String, SoftReference<Bitmap>> item = bitmaps
								.get(i);
						item.put(saveImageName, new SoftReference<Bitmap>(
								bitmap));
						bitmaps.set(i, item);
						msg.what = Constant.PRASE_SUCCESSFUL;
					} else {
						msg.what = Constant.PRASE_FAILED;
					}
				}
			}
			PdfReaderActivity.this.OnProgressFinished();
		}
	}

	@Override
	public void OnProgressStart() {
		Intent intent = getIntent();
		path = intent.getStringExtra("path");
		filename = FileSystem.GetFileName(path);
		waittingDialog = new WaittingDialog(PdfReaderActivity.this,
				R.style.MyDialog);
		waittingDialog.show();
		waittingDialog.setText(R.string.dialog_waitting_loading);
		Thread thread = new Thread(new ParseThread());
		thread.start();
	}

	@Override
	public void OnProgressFinished() {
		handler.sendMessage(msg);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 5) {
			ScreenCapturer.TakeScreenShot(PdfReaderActivity.this,
					FileSystem.PRTSCR_DIR,
					"PrtSc_" + FileSystem.GetTimeFileName() + ".jpg");
			Toast.makeText(getApplicationContext(),
					R.string.screen_shot_success, Toast.LENGTH_SHORT).show();
		}
		super.onKeyDown(keyCode, event);
		return true;
	}
}
