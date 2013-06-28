package team.androidreader.mainview;

import java.util.List;

import team.androidreader.dialog.WaittingDialog;
import team.androidreader.mainview.FileSortHelper.SortMethod;
import team.androidreader.utils.FileOperationHelper;
import team.androidreader.utils.OnProgressListener;
import team.top.activity.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
	public static Button selectAll;
	public static boolean selectedAll = false;
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
					MainActivity.fileListModel.clearSelectFIles();
					selectedAll = false;
				} else {
					MainActivity.fileListModel.getSelectFiles().clear();
					MainActivity.fileListModel.selectedAll(true);
					selectAll.setText(R.string.bottom_cancelall);
					System.out.println(MainActivity.fileListModel.getSelectedNum());
					selectedAll = true;
				}
				break;
			case R.id.delete:	
				confirm();
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

	private void confirm() {
			AlertDialog.Builder builder = new Builder(
					view.getContext());
			builder.setMessage("确认删除？");

			builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					BottomMenuFragment.this.OnProgressStart();
					waittingDialog.setText(R.string.dialog_waitting_delete);
				}
			});

			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			builder.create().show();

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
