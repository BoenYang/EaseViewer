
package team.androidreader.mainview;

import java.util.List;

import team.top.activity.R;
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

	@Override
	public int getCount() {
		if (fileList != null) {
			return fileList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (fileList != null && fileList.size() != 0) {
			return fileList.get(position);
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
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = layoutInflater.inflate(layoutId, null);
			viewHolder = new ViewHolder();
			viewHolder.fileNameTextView = (TextView) convertView
					.findViewById(R.id.fileName);
			viewHolder.fileSizeTextView = (TextView) convertView
					.findViewById(R.id.fileSize);
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
