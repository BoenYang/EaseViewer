package team.androidreader.mainview;

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
	private Button delete;
	private Button choice;
	private Button selectAll;
	private boolean selectedAll = false;

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
		delete = (Button) view.findViewById(R.id.delete);
		delete.setOnClickListener(new BtnListener());
		selectAll = (Button) view.findViewById(R.id.selectall);
		selectAll.setOnClickListener(new BtnListener());
		choice = (Button) view.findViewById(R.id.choice);
		choice.setOnClickListener(new BtnListener());
	}

	class BtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.selectall:
				if(selectedAll){
					MainActivity.fileListModel.selectedAll(false);
					selectAll.setText("selectedall");
					MainActivity.fileListModel.setSelectedNum(0);
					selectedAll = false;
				}else{
					MainActivity.fileListModel.selectedAll(true);
					selectAll.setText("cancelall");
					selectedAll = true;
				}
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
			case R.id.choice:
				ChoiceDialog choiceDialog = new ChoiceDialog(view.getContext());
				choiceDialog.show();
				break;
			default:
				break;
			}
		}
	}
}
