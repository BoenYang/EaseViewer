package team.top.activity;

import java.util.ArrayList;
import java.util.List;

import org.vudroid.pdfdroid.codec.PdfContext;
import org.vudroid.pdfdroid.codec.PdfDocument;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.GridView;


public class PdfReaderActivity extends Activity{
	
	private GridView gridView;
	private PdfShowAdapter pdfShowAdapter;
	private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdfreader);
		gridView = (GridView)findViewById(R.id.pdfreader_gridview);
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		PdfContext pdfContext = new PdfContext();
		PdfDocument document = (PdfDocument) pdfContext.openDocument(path);
		int num = document.getPageCount();
		System.out.println(num);
		pdfShowAdapter = new PdfShowAdapter(this, bitmaps, R.layout.pdf_item);
		gridView.setAdapter(pdfShowAdapter);
	}
	
}
