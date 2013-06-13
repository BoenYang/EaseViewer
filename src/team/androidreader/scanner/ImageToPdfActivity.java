package team.androidreader.scanner;

import java.util.ArrayList;
import java.util.List;

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
		fileData = new ArrayList<Bitmap>();
		//BitmapFactory.decodeResource(getResources(), R.id.)
		//imageShowAdapter = new ImageShowAdapter(this, fileData,
		//		R.layout.item_image);
		//fileGridView.setAdapter(imageShowAdapter);
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
