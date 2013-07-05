package team.androidreader.mainview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.androidreader.mainview.FileListModel.onSelectedAllListener;
import team.top.activity.R;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author ybw
 * 
 */
public class FileListAdapter extends BaseAdapter implements onSelectedAllListener{

	private Context context;
	private List<FileInfo> fileList;
	private int layoutId;
	private LayoutInflater layoutInflater;
	private ViewHolder viewHolder;
	private static Map<String, Integer> iconMap = new HashMap<String, Integer>();
	private PackageManager pm;

	static {
		iconMap.put("mp3", R.drawable.mp3);
		iconMap.put("ape", R.drawable.ape);
		iconMap.put("flac", R.drawable.flac);
		iconMap.put("wmv", R.drawable.wmv);
		iconMap.put("wav", R.drawable.wav);
		iconMap.put("wma", R.drawable.wma);
		iconMap.put("avi", R.drawable.avi);
		iconMap.put("flac", R.drawable.flac);
		iconMap.put("rmvb", R.drawable.rmvb);
		iconMap.put("mkv", R.drawable.mkv);
		iconMap.put("mp4", R.drawable.mp4);
		iconMap.put("jpg", R.drawable.jpg);
		iconMap.put("gif", R.drawable.gif);
		iconMap.put("bmp", R.drawable.bmp);
		iconMap.put("png", R.drawable.png);
		iconMap.put("doc", R.drawable.doc);
		iconMap.put("ppt", R.drawable.ppt);
		iconMap.put("xls", R.drawable.xls);
		iconMap.put("pdf", R.drawable.pdf);
		iconMap.put("txt", R.drawable.txt);
		iconMap.put("log", R.drawable.log);
		iconMap.put("html", R.drawable.html);
		iconMap.put("zip", R.drawable.zip);
		iconMap.put("rar", R.drawable.rar);
	}

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
		pm = context.getPackageManager();
		MainActivity.fileListModel.addSelectedAllListener(this);
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
		viewHolder.checkBox
				.setChecked(MainActivity.fileListModel.getSeletct()[position]);
		if(MainActivity.fileListModel.getOpeartion() != FileListController.DEFAULT){
			System.out.println("set gone");
			viewHolder.checkBox.setVisibility(View.GONE);
		}else{
			viewHolder.checkBox.setVisibility(View.VISIBLE);
		}
		viewHolder.fileNameTextView.setText(file.fileName);
		viewHolder.modifyTimeTextview.setText(file.lastModify);
		viewHolder.checkBox.setOnClickListener(new CheckBoxOnClickListener(
				position));
		if (!file.isDirectory) {
			viewHolder.fileSizeTextView.setText(file.fileSize);
			String extension = file.fileName.substring(file.fileName
					.lastIndexOf('.') + 1);
			if (iconMap.containsKey(extension)) {
				viewHolder.fileIconImageView.setImageResource(iconMap
						.get(extension));
			} else if (extension.equals("apk")) {
				PackageInfo pkgInfo = pm.getPackageArchiveInfo(
						file.absolutePath, PackageManager.GET_ACTIVITIES);
				if (pkgInfo != null) {
					ApplicationInfo appInfo = pkgInfo.applicationInfo;
					appInfo.sourceDir = file.absolutePath;
					appInfo.publicSourceDir = file.absolutePath;
					Drawable icon1 = pm.getApplicationIcon(appInfo);// 得到图标信息
					viewHolder.fileIconImageView.setImageDrawable(icon1);
				}
			} else {
				viewHolder.fileIconImageView
						.setImageResource(R.drawable.unknown);
			}
		} else {
			viewHolder.fileSizeTextView.setText("");
			viewHolder.fileIconImageView.setImageResource(R.drawable.folder);
		}
		return convertView;
	}

	class CheckBoxOnClickListener implements View.OnClickListener {

		private int position;

		public CheckBoxOnClickListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {

			boolean[] select = MainActivity.fileListModel.getSeletct();
			if (select[position] == false) {
				select[position] = true;
				MainActivity.fileListController.handSelectFile(
						fileList.get(position), FileListController.ADD);
			} else {
				select[position] = false;
				MainActivity.fileListController.handSelectFile(
						fileList.get(position), FileListController.DELETE);
			}
			
			
			if(MainActivity.fileListModel.getSelectedNum() < select.length){
				BottomMenuFragment.selectedAll = false;
				BottomMenuFragment.selectAll.setText(R.string.bottom_selectall);
			}else{
				BottomMenuFragment.selectedAll = true;
				BottomMenuFragment.selectAll.setText(R.string.bottom_cancelall);
			}
			BottomMenuFragment.selectAll.invalidate();
		}
	}

	@Override
	public void onSelectedAll(boolean flag) {
		notifyDataSetChanged();
	}
}
