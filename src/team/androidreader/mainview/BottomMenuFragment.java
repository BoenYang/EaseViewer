package team.androidreader.mainview;

import java.util.Collections;
import java.util.List;

import team.androidreader.mainview.FileSortHelper.SortMethod;
import team.androidreader.utils.FileOperationHelper;
import team.top.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class BottomMenuFragment extends Fragment {

	private View view;
	private Button copy;
	private Button delete;
	private Button move;
	private Button rename;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_bottommenu, null);
		init();
		return view;
	}

	private void init() {
		copy = (Button) view.findViewById(R.id.copy);
		delete = (Button) view.findViewById(R.id.delete);
		move = (Button) view.findViewById(R.id.move);
		rename = (Button) view.findViewById(R.id.rename);
		copy.setOnClickListener(new BtnListener());
		delete.setOnClickListener(new BtnListener());
		move.setOnClickListener(new BtnListener());
		rename.setOnClickListener(new BtnListener());
	}

	class BtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.copy:
				break;
			case R.id.delete:
				FileSortHelper fileSortHelper = new FileSortHelper();
				fileSortHelper.setSortMethog(SortMethod.name);
				FileOperationHelper.Delete(MainActivity.fileListModel
						.getSelectFiles());
				List<FileInfo> fileList = FileListHelper.GetSortedFiles(
						MainActivity.fileListModel.getCurrentDirectory(),
						false, SortMethod.name);
				MainActivity.fileListController.handleDirectoryChange(fileList,
						MainActivity.fileListModel.getCurrentDirectory());
				break;
			case R.id.rename:
				break;
			case R.id.move:
				break;
			}
		}
	}
}
