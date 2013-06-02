package team.androidreader.mainview;

import java.util.List;

import team.androidreader.helpabout.AboutActivity;
import team.androidreader.theme.ChangeThemeActivity;
import team.top.activity.R;
import android.R.integer;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author ybw ht
 * 
 */
public class RightCategoryFragment extends Fragment {

	private LinearLayout officeCategory;
	private LinearLayout pictureCategory;
	private LinearLayout musicCategory;
	private LinearLayout videoCategory;
	private LinearLayout zipCategory;
	private LinearLayout apkCategory;
	private LinearLayout picture2pdf;
	private LinearLayout changeTheme;
	private LinearLayout help;
	private LinearLayout about;
	// private List<FileInfo> officeList;
	private List<FileInfo> pictureList;
	private List<FileInfo> musicList;
	private List<FileInfo> videoList;
	// private List<FileInfo> zipList;
	// private List<FileInfo> apkList;
	// private TextView officeCount;
	private TextView musicCount;
	private TextView videoCount;
	private TextView pictureCount;
	// private TextView zipCount;
	// private TextView apkCount;
	private View view;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_rightcategory, null);
		init();
		return view;
	}

	public void init() {
		officeCategory = (LinearLayout) view
				.findViewById(R.id.categoryOfficeBtn);
		pictureCategory = (LinearLayout) view
				.findViewById(R.id.categoryPictureBtn);
		musicCategory = (LinearLayout) view.findViewById(R.id.categoryMusicBtn);
		videoCategory = (LinearLayout) view.findViewById(R.id.categoryVideoBtn);
		zipCategory = (LinearLayout) view.findViewById(R.id.categoryZipBtn);
		apkCategory = (LinearLayout) view.findViewById(R.id.categoryApkBtn);
		picture2pdf = (LinearLayout) view.findViewById(R.id.functionPic2PdfBtn);
		changeTheme = (LinearLayout) view
				.findViewById(R.id.functionChangeThemeBtn);
		help = (LinearLayout) view.findViewById(R.id.moreHelpBtn);
		about = (LinearLayout) view.findViewById(R.id.moreAboutBtn);
		officeCategory.setOnClickListener(new FileCategorySelectListener());
		pictureCategory.setOnClickListener(new FileCategorySelectListener());
		musicCategory.setOnClickListener(new FileCategorySelectListener());
		videoCategory.setOnClickListener(new FileCategorySelectListener());
		zipCategory.setOnClickListener(new FileCategorySelectListener());
		apkCategory.setOnClickListener(new FileCategorySelectListener());
		picture2pdf.setOnClickListener(new FileCategorySelectListener());
		changeTheme.setOnClickListener(new FileCategorySelectListener());
		help.setOnClickListener(new FileCategorySelectListener());
		about.setOnClickListener(new FileCategorySelectListener());
		// officeList =
		// fileListHelper.GetAllFiles(FileListHelper.FileCategory.DOC, true);
		pictureList = FileListHelper.GetCategory(getActivity(),
				FileListHelper.FileCategory.PICTURE, true);
		musicList = FileListHelper.GetCategory(getActivity(),
				FileListHelper.FileCategory.MUSIC, true);
		videoList = FileListHelper.GetCategory(getActivity(),
				FileListHelper.FileCategory.VIDEO, true);
		// zipList = fileListHelper.GetAllFiles(FileListHelper.FileCategory.ZIP,
		// true);
		// apkList = fileListHelper.GetAllFiles(FileListHelper.FileCategory.APK,
		// true);
		musicCount = (TextView) view.findViewById(R.id.categoryMusicCount);
		videoCount = (TextView) view.findViewById(R.id.categoryVideoCount);
		pictureCount = (TextView) view.findViewById(R.id.categoryPictureCount);
		// officeCount = (TextView)view.findViewById(R.id.categoryOfficeCount);
		// zipCount = (TextView)view.findViewById(R.id.categoryZipCount);
		// apkCount = (TextView)view.findViewById(R.id.categoryApkCount);
		musicCount.setText("(" + musicList.size() + ")");
		videoCount.setText("(" + videoList.size() + ")");
		pictureCount.setText("(" + pictureList.size() + ")");
		// officeCount.setText("(" + musicList.size() +")");
		// zipCount.setText("(" + musicList.size() +")");
		// apkCount.setText("(" + musicList.size() +")");

	}

	class FileCategorySelectListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			List<FileInfo> fileList = null;
			switch (id) {
			case R.id.categoryOfficeBtn:
				MainActivity.mSlidingMenu.showRightView();
				break;
			case R.id.categoryPictureBtn:
				fileList = pictureList;
				MainActivity.fileListController.handleDirectoryChange(pictureList, "");
				MainActivity.mSlidingMenu.showRightView();
				break;
			case R.id.categoryMusicBtn:
				fileList = musicList;
				MainActivity.fileListController.handleDirectoryChange(musicList, "");
				MainActivity.mSlidingMenu.showRightView();
				break;
			case R.id.categoryVideoBtn:
				fileList = videoList;
				MainActivity.fileListController.handleDirectoryChange(videoList, "");
				MainActivity.mSlidingMenu.showRightView();
				break;

			case R.id.categoryApkBtn:
				// fileList =
				// fileListHelper.GetAllFiles(FileListHelper.FileCategory.APK,
				// true);
				MainActivity.mSlidingMenu.showRightView();
				break;
			case R.id.categoryZipBtn:
				// fileList =
				// fileListHelper.GetAllFiles(FileListHelper.FileCategory.ZIP,
				// true);
				MainActivity.mSlidingMenu.showRightView();
				break;

			case R.id.functionPic2PdfBtn:
				// Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// File file = new File(FileSystem.CAMERA_CACHE + File.separator
				// + "IMAGE_" + FileSystem.GetNameByTime() + ".jpg");
				// if (!file.exists()) {
				// try {
				// file.createNewFile();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// }
				// intent1.putExtra(MediaStore.EXTRA_OUTPUT,
				// Uri.fromFile(file));
				// startActivity(intent1);
				break;
			case R.id.functionChangeThemeBtn:
				Intent intent2 = new Intent();
				intent2.setClass(getActivity(), ChangeThemeActivity.class);
				startActivity(intent2);
				break;
			case R.id.moreHelpBtn:
				Intent intent3 = new Intent();
				intent3.setClass(getActivity(), ChangeThemeActivity.class);
				startActivity(intent3);
				break;
			case R.id.moreAboutBtn:
				Intent intent4 = new Intent();
				intent4.setClass(getActivity(), AboutActivity.class);
				startActivity(intent4);
				break;
			default:
				break;
			}
		}
	}


}
