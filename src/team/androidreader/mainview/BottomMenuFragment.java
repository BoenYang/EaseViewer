package team.androidreader.mainview;

import java.util.List;

import team.androidreader.dialog.WaittingDialog;
import team.androidreader.mainview.FileSortHelper.SortMethod;
import team.androidreader.utils.FileOperationHelper;
import team.androidreader.utils.OnProgressListener;
import team.top.activity.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
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
import android.widget.TextView;

public class BottomMenuFragment extends Fragment implements OnProgressListener {

	private View view;
	private Button delete;
	private Button choice;
	public static Button selectAll;
	public static boolean selectedAll = false;
	private WaittingDialog waittingDialog;
	Dialog dialog;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_bottommenu, null);
		waittingDialog = new WaittingDialog(view.getContext(), R.style.MyDialog);
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
		dialog = new Dialog(getActivity(), R.style.MyDialog);
		dialog.setContentView(R.layout.dialog_confirm);
		dialog.show();
		TextView title = (TextView) dialog.findViewById(R.id.confirm_text);
		title.setText(R.string.dialog_confirm_delete);

		Button confirm = (Button) dialog.findViewById(R.id.confirm_yes);
		confirm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				BottomMenuFragment.this.OnProgressStart();
				waittingDialog.setText(R.string.dialog_waitting_delete);
			}
		});

		Button cancel = (Button) dialog.findViewById(R.id.confirm_no);// 注：“View.”别忘了，不然则找不到资源
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

	}

	class DeleteThread implements Runnable {

		@Override
		public void run() {
			FileOperationHelper.Delete(MainActivity.fileListModel
					.getSelectFiles());
			handler.sendEmptyMessage(0);

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
		waittingDialog.cancel();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String currDir = MainActivity.fileListModel.getCurrentDirectory();
			List<FileInfo> fileList = null;
			if (currDir.equals("")) {
				fileList = FileListHelper.GetSortedFileByCategory(
						getActivity(),
						MainActivity.fileListModel.getFileCategory(), false,
						SortMethod.name);
			} else {
				fileList = FileListHelper.GetSortedFiles(currDir, false,
						SortMethod.name);
			}
			MainActivity.fileListController.handleDirectoryChange(fileList,
					currDir);
			BottomMenuFragment.this.OnProgressFinished();
		}
	};

}
