package team.androidreader.mainview;

import java.util.List;

import team.top.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
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
	private boolean checked[];

	class ViewHolder {
		public ImageView fileIconImageView;
		public TextView fileNameTextView;
		public TextView fileSizeTextView;
		public CheckBox checkBox;
		public TextView modifyTimeTextview;
	}

	public FileListAdapter(Context context, List<FileInfo> fileList,
			int layoutId) {
		this.context = context;
		this.fileList = fileList;
		this.layoutId = layoutId;
		layoutInflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		checked = new boolean[fileList.size()];
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

			viewHolder.fileIconImageView = (ImageView) convertView
					.findViewById(R.id.fileIcon);
			viewHolder.fileNameTextView = (TextView) convertView
					.findViewById(R.id.fileName);
			viewHolder.fileSizeTextView = (TextView) convertView
					.findViewById(R.id.fileSize);
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.isSelected);
			viewHolder.modifyTimeTextview = (TextView) convertView
					.findViewById(R.id.fileMofidiedDate);
			convertView.setTag(viewHolder);
		}
		FileInfo file = fileList.get(position);
		
		viewHolder.checkBox.setOnCheckedChangeListener(new CheckBoxListener(
				position));
		viewHolder.checkBox.setChecked(checked[position]);
		viewHolder.fileNameTextView.setText(file.fileName);
		viewHolder.modifyTimeTextview.setText(file.lastModify);
		if (!file.isDirectory) {
			viewHolder.fileSizeTextView.setText(file.fileSize);
			viewHolder.fileIconImageView
					.setImageResource(R.drawable.file_icon_default);
		} else {
			viewHolder.fileSizeTextView.setText("");
			viewHolder.fileIconImageView.setImageResource(R.drawable.folder);
		}
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		checked = new boolean[fileList.size()];
		super.notifyDataSetChanged();
	}

	class CheckBoxListener implements OnCheckedChangeListener {

		private int position;

		public CheckBoxListener(int pos) {
			position = pos;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			checked[position] = isChecked;
		}
	}

}
