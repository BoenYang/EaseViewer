package team.top.fragment;

import java.io.File;
import java.util.List;

import team.top.activity.FileListAdapter;
import team.top.activity.R;
import team.top.constant.Constant;
import team.top.data.FileInfo;
import team.top.utils.FileListHelper;
import team.top.utils.FileListHelper.FileCategory;
import team.top.utils.FileSystem;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FileListFragment extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private static ListView listView;
	private static List<FileInfo> fileList;
	private static FileListAdapter adapter;
	private static View view;
	private FileListHelper fileListHelper;
	public static boolean isSdcard = false;
	public static String currentDir = FileSystem.SDCARD_PATH;
	public static FileCategory fileCategory;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_filelist, null);
		fileListHelper = new FileListHelper(view.getContext());
		listView = (ListView) view.findViewById(R.id.filelistview);
		fileList = GetFiles(FileSystem.SDCARD_PATH);
		adapter = new FileListAdapter(view.getContext(), fileList,
				R.layout.file_item);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ItemOnClickListener());
		return view;
	}

	public void backToParentPath() {
		fileList.clear();
		if(fileCategory != FileListHelper.FileCategory.SDCARD){
			fileList.addAll(GetFiles(FileSystem.SDCARD_PATH));
			fileCategory = FileCategory.SDCARD;
		}else{
			File file  = new File(currentDir);
			fileList.addAll(GetFiles(file.getParent()));
		}
		adapter.notifyDataSetChanged();
	}

	private List<FileInfo> GetFiles(String path) {
		List<FileInfo> fileNames = fileListHelper.GetAllFiles(path, false);
		currentDir = path;
		return fileNames;
	}

	public boolean onKeyDown(int keycode, KeyEvent keyEvent) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			backToParentPath();
		}
		return true;
	}

	class ItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			FileInfo file = fileList.get(position);
			if (file.isDirectory) {
				fileList.clear();
				String absolutePath = file.absolutePath;
				fileList.addAll(GetFiles(absolutePath));
				adapter.notifyDataSetChanged();
			} else {
				File f = new File(file.absolutePath);
				openFile(f);
			}
		}
	}

	public static void setData(List<FileInfo> fileList) {
		if (fileList != null) {
			FileListFragment.fileList.clear();
			FileListFragment.fileList.addAll(fileList);
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 打开文件
	 * 
	 * @param file
	 */
	private void openFile(File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = getMIMEType(file);
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	private String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < Constant.MIME_MapTable.length; i++) {
			if (end.equals(Constant.MIME_MapTable[i][0]))
				type = Constant.MIME_MapTable[i][1];
		}
		return type;
	}

}
