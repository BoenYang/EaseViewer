
package team.top.activity;

import java.util.List;

import team.top.data.FileInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author ybw
 * 
 */
public class FileListAdapter extends BaseAdapter {

	private Context context;
	private List<FileInfo> fileList;
	private int layoutId;
	private LayoutInflater layoutInflater;
	private ViewHolder viewHolder;

	class ViewHolder {
		public TextView fileNameTextView;
		public TextView fileSizeTextView;
	}

	public FileListAdapter(Context context, List<FileInfo> fileList,
			int layoutId) {
		this.context = context;
		this.fileList = fileList;
		this.layoutId = layoutId;
		layoutInflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/*
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (fileList != null) {
			return fileList.size();
		} else {
			return 0;
		}
	}

	/*
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (fileList != null && fileList.size() != 0) {
			return fileList.get(position);
		} else {
			return null;
		}
	}

	/*
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("get view" + position);
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			System.out.println("new view" + position);
			convertView = layoutInflater.inflate(layoutId, null);
			viewHolder = new ViewHolder();
			viewHolder.fileNameTextView = (TextView) convertView
					.findViewById(R.id.filetext);
			viewHolder.fileSizeTextView = (TextView) convertView
					.findViewById(R.id.fileinfo);
			convertView.setTag(viewHolder);
		}
		if (fileList != null && fileList.size() != 0) {
			FileInfo file = fileList.get(position);
			viewHolder.fileNameTextView.setText(file.fileName);

			if (!file.isDirectory) {
				viewHolder.fileSizeTextView.setText(file.fileSize + "");
			} else {
				viewHolder.fileSizeTextView.setText("");
			}

		}
		return convertView;
	}

}
