package team.androidreader.scanner;

import java.util.List;

import team.androidreader.mainview.FileInfo;
import team.androidreader.mainview.FileListHelper;
import team.top.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class ImageToPdfActivity extends Activity {

	private ImageShowAdapter imageShowAdapter;
	private GridView fileGridView;
	private List<FileInfo> fileData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagetopdf);
		fileGridView = (GridView) findViewById(R.id.fileGridView);
		fileData = FileListHelper.GetAllFiles("/sdcard/" + "Camera", true);
		imageShowAdapter = new ImageShowAdapter(this, fileData,
				R.layout.image_item);
		fileGridView.setAdapter(imageShowAdapter);
	}
}
