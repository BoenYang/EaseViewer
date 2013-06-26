package team.androidreader.mainview;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team.androidreader.mainview.FileListModel.onSelectedListener;
import team.androidreader.mainview.FileSortHelper.SortMethod;
import team.androidreader.utils.FileSystem;
import team.androidreader.utils.MyApplication;
import team.top.activity.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		onSelectedListener {
	public static SlidingMenu mSlidingMenu;
	private RightCategoryFragment rightClassifyFragment;
	private CenterViewPagerFragment centerViewPagerFragment;
	public static FileListModel fileListModel;
	public static FileListController fileListController;
	private BottomMenuFragment bottomMenuFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		init();
	}

	private void init() {
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.frame_right, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.frame_center, null));
		mSlidingMenu.setBottomView(getLayoutInflater().inflate(
				R.layout.frame_bottom, null));

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();

		rightClassifyFragment = new RightCategoryFragment();
		t.replace(R.id.right_frame, rightClassifyFragment);

		centerViewPagerFragment = new CenterViewPagerFragment();
		t.replace(R.id.center_frame, centerViewPagerFragment);

		bottomMenuFragment = new BottomMenuFragment();
		t.replace(R.id.bottom_frame, bottomMenuFragment);
		t.commit();
		FileSystem.makeAppDirTree();
		List<FileInfo> filelist = FileListHelper.GetSortedFiles(
				FileSystem.SDCARD_PATH, false, SortMethod.name);
		fileListModel = new FileListModel(filelist, FileSystem.SDCARD_PATH);
		fileListController = new FileListController(fileListModel);
		fileListModel.addSelectedListener(this);
	}

	/**
	 * 显示右边页面
	 */
	public void showRight() {
		mSlidingMenu.showRightView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mSlidingMenu.isShowRight) {
				mSlidingMenu.showRightView();
				return true;
			} else {
				if (fileListModel.getCurrentDirectory().equals(
						FileSystem.SDCARD_PATH)) {
					exitBy2Click();
					return true;
				} else if (fileListModel.getCurrentDirectory().equals("")) {
					List<FileInfo> fileList = FileListHelper.GetSortedFiles(
							FileSystem.SDCARD_PATH, false, SortMethod.name);
					fileListController.handleDirectoryChange(fileList,
							FileSystem.SDCARD_PATH);
					MyApplication.setChoosed(-1);
					return true;
				}
			}
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!mSlidingMenu.isShowRight) {
				mSlidingMenu.showRightView();
			}
			return true;
		}
		return centerViewPagerFragment.onKeyDown(keyCode, event);
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, R.string.press_again, Toast.LENGTH_SHORT)
					.show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			finish();
			System.exit(0);
		}
	}

	@Override
	public void onSelected(int selectedCount) {
		if (selectedCount > 0) {
			mSlidingMenu.setMenuVisible();
		} else {
			mSlidingMenu.setMenuInvisibke();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
