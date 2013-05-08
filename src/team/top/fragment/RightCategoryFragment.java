package team.top.fragment;

import java.util.List;

import team.top.activity.MainActivity;
import team.top.activity.R;
import team.top.data.FileInfo;
import team.top.utils.FileListHelper;
import team.top.utils.FileListHelper.FileCategory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RightCategoryFragment extends Fragment {

	private TextView officeTv;
	private TextView pictureTv;
	private TextView musicTv;
	private TextView videoTv;
	private TextView zipTv;
	private TextView apkTv;
	private FileListHelper fileListHelper;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_rightcategory, null);
		fileListHelper = new FileListHelper(view.getContext());
		officeTv = (TextView) view.findViewById(R.id.officeFile);
		pictureTv = (TextView) view.findViewById(R.id.pictureFile);
		musicTv = (TextView) view.findViewById(R.id.musicFile);
		videoTv = (TextView) view.findViewById(R.id.videoFile);
		zipTv = (TextView) view.findViewById(R.id.zipFile);
		apkTv = (TextView) view.findViewById(R.id.apkFile);
		officeTv.setOnClickListener(new FileCategorySelectListener());
		pictureTv.setOnClickListener(new FileCategorySelectListener());
		musicTv.setOnClickListener(new FileCategorySelectListener());
		videoTv.setOnClickListener(new FileCategorySelectListener());
		zipTv.setOnClickListener(new FileCategorySelectListener());
		apkTv.setOnClickListener(new FileCategorySelectListener());
		return view;
	}

	class FileCategorySelectListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			List<FileInfo> fileList = null;
			switch (id) {
			case R.id.officeFile:
				// fileList =
				// fileListHelper.GetAllFiles(FileListHelper.FileCategory.DOC,
				// true);
				//FileListFragment.fileCategory = FileCategory.DOC;
				break;
			case R.id.pictureFile:
				fileList = fileListHelper.GetAllFiles(
						FileListHelper.FileCategory.PICTURE, true);
				FileListFragment.fileCategory = FileCategory.PICTURE;
				break;
			case R.id.musicFile:
				fileList = fileListHelper.GetAllFiles(
						FileListHelper.FileCategory.MUSIC, true);
				FileListFragment.fileCategory = FileCategory.MUSIC;
				break;
			case R.id.videoFile:
				fileList = fileListHelper.GetAllFiles(
						FileListHelper.FileCategory.VIDEO, true);
				FileListFragment.fileCategory = FileCategory.VIDEO;
				break;
			case R.id.zipFile:
				// fileList =
				// fileListHelper.GetAllFiles(FileListHelper.FileCategory.ZIP,
				// true);
				//FileListFragment.fileCategory = FileCategory.ZIP;
				break;
			case R.id.apkFile:
				// fileList =
				// fileListHelper.GetAllFiles(FileListHelper.FileCategory.APK,
				// true);
				//FileListFragment.fileCategory = FileCategory.APK;
				break;
			default:
				break;
			}
			if (fileList != null) {
				FileListFragment.setData(fileList);
			}
			MainActivity.mSlidingMenu.showRightView();
		}

	}

}
