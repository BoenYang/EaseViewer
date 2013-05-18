package team.top.activity;

import java.util.ArrayList;
import java.util.List;

import team.top.data.FileInfo;
import team.top.utils.BitmapHelper;
import team.top.utils.FileListHelper;
import team.top.utils.FileSystem;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.GridView;

public class PdfReaderActivity extends Activity{
	
	private GridView gridView;
	private PdfShowAdapter pdfShowAdapter;
	private List<FileInfo> fileData;
	private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdfreader);
		gridView = (GridView)findViewById(R.id.pdfreader_gridview);
		FileListHelper fileListHelper = new FileListHelper(this);
		fileData = fileListHelper.GetAllFiles(FileSystem.APP_DIR, true);
		for(int i = 0; i < 3; i++)
		{
			bitmaps.add(BitmapHelper.createBitmap(fileData.get(i).absolutePath, 700, 700));
		}
		pdfShowAdapter = new PdfShowAdapter(this, bitmaps, R.layout.pdf_item);
		gridView.setAdapter(pdfShowAdapter);
	}
	
}
