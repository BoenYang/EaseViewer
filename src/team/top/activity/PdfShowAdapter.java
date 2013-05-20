package team.top.activity;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PdfShowAdapter extends BaseAdapter {

	private Context context;
	private List<Bitmap> bitmaps;
	private LayoutInflater layoutInflater;
	private int layoutId;
	private ViewHolder viewHolder;

	class ViewHolder {
		public ImageView pdfView;
	}

	/**
	 * 
	 * @param context
	 * @param data
	 * @param layoutId
	 */
	public PdfShowAdapter(Context context, List<Bitmap> data, int layoutId) {
		this.context = context;
		this.bitmaps = data;
		this.layoutId = layoutId;
		this.layoutInflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (bitmaps != null) {
			return bitmaps.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (bitmaps != null) {
			return bitmaps.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = (View) layoutInflater.inflate(layoutId, null);
			viewHolder  = new ViewHolder();
			viewHolder.pdfView = (ImageView) convertView.findViewById(R.id.pdfview);
			convertView.setTag(viewHolder);
		}
		viewHolder.pdfView.setImageBitmap(bitmaps.get(position));
		return convertView;
	}

}
