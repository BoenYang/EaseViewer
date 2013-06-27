package team.androidreader.scanner;

import java.util.List;

import team.androidreader.mainview.FileInfo;
import team.androidreader.mainview.FileListHelper;
import team.top.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ImageToPdfActivity extends Activity {

	private ImageShowAdapter imageShowAdapter;
	private GridView fileGridView;
	private List<Bitmap> fileData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagetopdf);
		fileGridView = (GridView)findViewById(R.id.fileGridView);
		List<FileInfo> fileInfos = FileListHelper.GetFiles("/sdcard/Camera", false);
		System.out.println(fileInfos.size());
		imageShowAdapter = new ImageShowAdapter(this, fileInfos,
				R.layout.item_image);
		fileGridView.setAdapter(imageShowAdapter);
	}

	class OnImageClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

		}
	}

	class SelectDialog extends Dialog {

		public SelectDialog(Context context) {
			super(context);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_select);
			setTitle("please select photo");
		}
	}
}
