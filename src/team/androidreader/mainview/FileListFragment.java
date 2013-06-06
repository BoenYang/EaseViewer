package team.androidreader.mainview;

import java.io.File;
import java.util.List;

import team.androidreader.mainview.FileListModel.FileListChangeListener;
import team.androidreader.mainview.FileSortHelper.SortMethod;
import team.androidreader.utils.FileSystem;
import team.top.activity.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
public class FileListFragment extends Fragment implements
		FileListChangeListener {

	private ListView listView;
	private FileListAdapter adapter;
	private View view;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_filelist, null);
		init();
		return view;
	}

	private void init() {
		MainActivity.fileListModel.addFileListenerChangeListener(this);
		listView = (ListView) view.findViewById(R.id.filelistview);
		adapter = new FileListAdapter(view.getContext(),
				MainActivity.fileListModel.getFileList(), R.layout.item_file);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ItemOnClickListener());
	}

	public boolean onKeyDown(int keycode, KeyEvent keyEvent) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			String currentDir = MainActivity.fileListModel
					.getCurrentDirectory();
			File file = new File(currentDir);
			String parentDir = file.getParent();
			List<FileInfo> fileList = FileListHelper.GetSortedFiles(parentDir,
					false, SortMethod.name);
			MainActivity.fileListController.handleDirectoryChange(fileList,
					parentDir);
		} 
		return true;
	}

	class ItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			FileInfo file = MainActivity.fileListModel.getFileList().get(
					position);
			if (file.isDirectory) {
				List<FileInfo> fileList = FileListHelper.GetSortedFiles(
						file.absolutePath, false, SortMethod.name);
				MainActivity.fileListController.handleDirectoryChange(fileList,
						file.absolutePath);
			} else {
				openFile(file);
			}
		}
	}

	/**
	 * 打开文件
	 * 
	 * @param file
	 */
	private void openFile(FileInfo fileInfo) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		String type = FileSystem.getMIMEType(fileInfo);
		String entension = type.substring(type.indexOf('/') + 1);
		intent.putExtra("extension", entension);
		intent.putExtra("path", fileInfo.absolutePath);
		intent.setDataAndType(Uri.fromFile(new File(fileInfo.absolutePath)),
				type);
		startActivity(intent);
	}

	@Override
	public void onFileListChange(FileListModel fileListModel) {
		adapter.notifyDataSetChanged();
	}

}
