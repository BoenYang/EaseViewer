package team.top.fragment;

import java.util.List;

import team.top.activity.MainActivity;
import team.top.activity.R;
import team.top.data.FileInfo;
import team.top.utils.FileListHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class RightCategoryFragment extends Fragment {

	private LinearLayout officeCategory;
	private LinearLayout pictureCategory;
	private LinearLayout musicCategory;
	private LinearLayout videoCategory;
	private LinearLayout zipCategory;
	private LinearLayout apkCategory;
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
		officeCategory = (LinearLayout) view.findViewById(R.id.categoryOfficeBtn);
		pictureCategory = (LinearLayout) view.findViewById(R.id.categoryPictureBtn);
		musicCategory = (LinearLayout) view.findViewById(R.id.categoryMusicBtn);
		videoCategory = (LinearLayout) view.findViewById(R.id.categoryVideoBtn);
		zipCategory = (LinearLayout) view.findViewById(R.id.categoryZipBtn);
		apkCategory = (LinearLayout) view.findViewById(R.id.categoryApkBtn);
		officeCategory.setOnClickListener(new FileCategorySelectListener());
		pictureCategory.setOnClickListener(new FileCategorySelectListener());
		musicCategory.setOnClickListener(new FileCategorySelectListener());
		videoCategory.setOnClickListener(new FileCategorySelectListener());
		zipCategory.setOnClickListener(new FileCategorySelectListener());
		apkCategory.setOnClickListener(new FileCategorySelectListener());
		return view;
	}

	class FileCategorySelectListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			List<FileInfo> fileList = null;
			switch (id) {
			case R.id.categoryOfficeBtn:
				fileList = fileListHelper.GetAllFiles(FileListHelper.FileCategory.DOC, true);
				((MainActivity)getActivity()).showRight();
				break;
			case R.id.categoryPictureBtn:
				fileList = fileListHelper.GetAllFiles(FileListHelper.FileCategory.PICTURE, true);
				((MainActivity)getActivity()).showRight();
				break;
			case R.id.categoryMusicBtn:
				fileList = fileListHelper.GetAllFiles(FileListHelper.FileCategory.MUSIC, true);
				((MainActivity)getActivity()).showRight();
				break;
			case R.id.categoryVideoBtn:
				fileList = fileListHelper.GetAllFiles(FileListHelper.FileCategory.VIDEO, true);
				((MainActivity)getActivity()).showRight();
				break;
			case R.id.categoryZipBtn:
				fileList = fileListHelper.GetAllFiles(FileListHelper.FileCategory.ZIP, true);
				((MainActivity)getActivity()).showRight();
				break;
			case R.id.categoryApkBtn:
				fileList = fileListHelper.GetAllFiles(FileListHelper.FileCategory.APK, true);
				((MainActivity)getActivity()).showRight();
				break;
			default:
				break;
			}
			FileListFragment.setData(fileList);
		}

	}

}
