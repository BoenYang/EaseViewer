package team.androidreader.scanner;

import java.util.ArrayList;

import team.androidreader.utils.ImageManager;
import team.top.activity.R;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Adapter_GridImage extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> dataList;
	private DisplayMetrics dm;

	public Adapter_GridImage(Context c, ArrayList<String> dataList) {

		mContext = c;
		this.dataList = dataList;
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
			imageView.setAdjustViewBounds(false);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		} else
			imageView = (ImageView) convertView;
		
		String path;
		if (dataList != null && position < dataList.size())
			path = dataList.get(position);
		else
			path = "camera_default";
		
		Log.i("path", "path:" + path + "::position" + position);
		
		if (path.contains("default"))
			imageView.setImageResource(R.drawable.pic_add_btn);
		else {
			ImageManager.from(mContext).displayImage(imageView, path,
					R.drawable.camera_default, 100, 100);
		}
		
		return imageView;
	}


}
