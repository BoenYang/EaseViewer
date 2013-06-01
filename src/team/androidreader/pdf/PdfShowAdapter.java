package team.androidreader.pdf;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import team.androidreader.pdf.AsyncImageLoader.CallBack;
import team.androidreader.utils.FileSystem;
import team.top.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class PdfShowAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private int layoutId;
	private ViewHolder viewHolder;
	public static List<HashMap<String, SoftReference<Bitmap>>> bitmaps;
	private String fileName;
	Toast toast;
	public static Integer threadCount = 0;

	class ViewHolder {
		public ImageView pdfView;
	}

	/**
	 * 
	 * @param context
	 * @param data
	 * @param layoutId
	 */
	public PdfShowAdapter(Context context,
			List<HashMap<String, SoftReference<Bitmap>>> data, String fileName,
			int layoutId) {
		this.context = context;
		PdfShowAdapter.bitmaps = data;
		this.layoutId = layoutId;
		this.layoutInflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.fileName = fileName;
		toast = Toast.makeText(context, " ", Toast.LENGTH_SHORT);
	}

	@Override
	public int getCount() {
		if (bitmaps != null) {
			return bitmaps.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (bitmaps != null) {
			return bitmaps.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		toast.setText("" + position);
		toast.show();
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = (View) layoutInflater.inflate(layoutId, null);
			viewHolder = new ViewHolder();
			viewHolder.pdfView = (ImageView) convertView
					.findViewById(R.id.pdfview);
			convertView.setTag(viewHolder);
		}

		if (threadCount < 1) {
			Bitmap bitmap = null;
			AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
					bitmaps.get(position), position);
			threadCount++;
			bitmap = asyncImageLoader.loadBitmap(FileSystem.PDF_CACHE
					+ File.separator + fileName + File.separator + fileName
					+ position + ".jpg", new CallBack() {

				@Override
				public Bitmap setBitmap(ImageView imageView, Bitmap bitmap) {
					if (bitmap != null) {
						imageView.setImageBitmap(bitmap);
					}
					return bitmap;
				}
			}, viewHolder.pdfView);

			if (bitmap != null) {
				viewHolder.pdfView.setImageBitmap(bitmap);
			}
		}

		return convertView;
	}

}
