package team.top.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import team.top.activity.FileListAdapter;
import team.top.activity.R;
import team.top.constant.Constant;
import team.top.data.FileInfo;
import team.top.utils.FileSystem;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FileListFragment extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private static ListView listView;
	private static List<FileInfo> fileList;
	private static FileListAdapter adapter;
	public static String currDirectory = "/";
	private static View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_filelist, null);
		listView = (ListView) view.findViewById(R.id.filelistview);
		//fileList = GetFiles(FileSystem.SDCARD_PATH);
		fileList = new ArrayList<FileInfo>();
		String volume = "external";
		
		Uri uri = Audio.Media.getContentUri(volume);
		
		String[] colums = new String[] {"COUNT(*)","SUM(_size)"};
		Cursor c = view.getContext().getContentResolver().query(uri, colums, null, null, null);
		while (c.moveToNext()) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.absolutePath = c.getString(1);
			File file = new File(fileInfo.absolutePath);
			fileInfo.fileName = file.getName();
			fileInfo.isDirectory = file.isDirectory();
			if(!fileInfo.isDirectory){
				fileInfo.fileSize = file.length();
			}
			
			fileList.add(fileInfo);
		}
		for (FileInfo file : fileList) {		
			System.out.println(file);
		}
		adapter = new FileListAdapter(view.getContext(), fileList,
				R.layout.file_item);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ItemOnClickListener());
		return view;
	}

	public void backToParentPath() {
		fileList.clear();
		File file = new File(currDirectory);
		File parent = file.getParentFile();
		fileList.addAll(GetFiles(parent.getAbsolutePath()));
		adapter.notifyDataSetChanged();
	}

	private List<FileInfo> GetFiles(String path) {
		List<FileInfo> fileNames = new ArrayList<FileInfo>();
		List<FileInfo> dirList = new ArrayList<FileInfo>();
		List<FileInfo> fList = new ArrayList<FileInfo>();
		File file = new File(path);
		currDirectory = path;
		if (file != null && file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File temp : files) {
					FileInfo fileInfo = new FileInfo();
					fileInfo.fileName = temp.getName();
					fileInfo.absolutePath = temp.getAbsolutePath();
					if (temp.isDirectory()) {
						fileInfo.isDirectory = true;
						if (!isHidden(fileInfo.fileName))
							dirList.add(fileInfo);
					} else {
						fileInfo.isDirectory = false;
						fileInfo.fileSize = temp.length();
						if (!isHidden(fileInfo.fileName))
							fList.add(fileInfo);
					}
				}
			}
		}
		fileNames.addAll(dirList);
		fileNames.addAll(fList);
		return fileNames;
	}

	private boolean isHidden(String fileName) {
		if (fileName.charAt(0) == '.') {
			return true;
		}
		return false;
	}

	public boolean onKeyDown(int keycode, KeyEvent keyEvent) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			if (!currDirectory.equals(FileSystem.SDCARD_PATH))
				backToParentPath();
			else {
				System.exit(0);
			}
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
		FileListFragment.fileList.clear();
		FileListFragment.fileList.addAll(fileList);
		adapter.notifyDataSetChanged();
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
