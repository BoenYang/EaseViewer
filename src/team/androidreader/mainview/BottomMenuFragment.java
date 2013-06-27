package team.androidreader.mainview;

import java.util.List;

import team.androidreader.dialog.WaittingDialog;
import team.androidreader.mainview.FileSortHelper.SortMethod;
import team.androidreader.utils.FileOperationHelper;
import team.androidreader.utils.OnProgressListener;
import team.top.activity.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class BottomMenuFragment extends Fragment implements OnProgressListener {

	private View view;
	private Button delete;
	private Button choice;
	private Button selectAll;
	private boolean selectedAll = false;
	private WaittingDialog waittingDialog;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_bottommenu, null);
		waittingDialog = new WaittingDialog(view.getContext());
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
				if (selectedAll) {
					MainActivity.fileListModel.selectedAll(false);
					selectAll.setText(R.string.bottom_selectall);
					MainActivity.fileListModel.setSelectedNum(0);
					selectedAll = false;
				} else {
					MainActivity.fileListModel.selectedAll(true);
					selectAll.setText(R.string.bottom_cancelall);
					selectedAll = true;
				}
				break;
			case R.id.delete:
				BottomMenuFragment.this.OnProgressStart();
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

	class DeleteThread implements Runnable {

		@Override
		public void run() {
			FileOperationHelper.Delete(MainActivity.fileListModel
					.getSelectFiles());
			BottomMenuFragment.this.OnProgressFinished();
		}
	}

	@Override
	public void OnProgressStart() {
		waittingDialog.show();
		Thread thread = new Thread(new DeleteThread());
		thread.start();
	}

	@Override
	public void OnProgressFinished() {
		handler.sendEmptyMessage(0);

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			waittingDialog.dismiss();
			List<FileInfo> fileList = FileListHelper.GetSortedFiles(
					MainActivity.fileListModel.getCurrentDirectory(), false,
					SortMethod.name);
			MainActivity.fileListController.handleDirectoryChange(fileList,
					MainActivity.fileListModel.getCurrentDirectory());
		}
	};

}
