package team.androidreader.mainview;

import java.io.File;
import java.util.List;

import team.androidreader.mainview.FileListHelper.FileCategory;
import team.androidreader.utils.FileSystem;
import team.top.activity.R;
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

/**
 * 
 * @author ybw ht
 * 
 */
public class FileListFragment extends Fragment {

	private static ListView listView;
	private static List<FileInfo> fileList;
	private static FileListAdapter adapter;
	private static View view;
	private FileListHelper fileListHelper;
	public static String currentDir = FileSystem.SDCARD_PATH;
	public static FileCategory fileCategory;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_filelist, null);
		fileListHelper = new FileListHelper(view.getContext());
		listView = (ListView) view.findViewById(R.id.filelistview);
		fileList = GetFiles(FileSystem.SDCARD_PATH);
		fileCategory = FileCategory.SDCARD;
		adapter = new FileListAdapter(view.getContext(), fileList,
				R.layout.file_item);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ItemOnClickListener());
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	/**
	 * 
	 */
	public void backToParentPath() {
		fileList.clear();
		if (fileCategory != FileListHelper.FileCategory.SDCARD) {
			fileList.addAll(GetFiles(FileSystem.SDCARD_PATH));
			fileCategory = FileCategory.SDCARD;
		} else {
			File file = new File(currentDir);
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
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		String type = FileSystem.getMIMEType(file);
		String entension = type.substring(type.indexOf('/') + 1);
		intent.putExtra("extension", entension);
		intent.putExtra("path", file.getAbsolutePath());
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);
	}

}
