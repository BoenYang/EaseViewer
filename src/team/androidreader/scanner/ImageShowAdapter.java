package team.androidreader.scanner;

import java.util.ArrayList;
import java.util.List;

import team.androidreader.mainview.FileInfo;
import team.androidreader.utils.BitmapHelper;
import team.top.activity.R;
import team.top.activity.R.id;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageShowAdapter extends BaseAdapter {

	private List<FileInfo> data;
	private int layoutId;
	private Context context;
	private LayoutInflater layoutInflater;
	private ViewHolder viewHolder;
	private List<Bitmap> bitmaps = new ArrayList<Bitmap>();

	class ViewHolder {
		public ImageView imageView;
	}

	public ImageShowAdapter(Context context, List<FileInfo> data, int layoutId) {
		this.context = context;
		this.data = data;
		this.layoutId = layoutId;
		this.layoutInflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (FileInfo fileInfo : data) {
			bitmaps.add(BitmapHelper.createBitmap(fileInfo.absolutePath, 100, 100));
		}
	}

	@Override
	public int getCount() {
		if (data != null) {
			return data.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (data != null) {
			return data.get(position);
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
		System.out.println("--------------get view " + position);
		if(convertView != null){
			viewHolder = (ViewHolder) convertView.getTag();
		}else{
			convertView = layoutInflater.inflate(layoutId, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		}
		viewHolder.imageView.setImageBitmap(bitmaps.get(position));
		return convertView;
	}
	


}
