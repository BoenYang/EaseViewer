package team.androidreader.mainview;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team.androidreader.utils.FileSystem;
import team.top.activity.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	public static SlidingMenu mSlidingMenu;
	private RightCategoryFragment rightClassifyFragment;
	private CenterViewPagerFragment centerViewPagerFragment;
	public static int mScreenWidth;
	public static int mScreenHeight;
	public static FileListModel fileListModel;
	public static FileListController fileListController;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		/**
		 * 获取屏幕宽度和高度
		 */
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		init();
	}

	private void init() {
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();

		rightClassifyFragment = new RightCategoryFragment();
		t.replace(R.id.right_frame, rightClassifyFragment);

		centerViewPagerFragment = new CenterViewPagerFragment();
		t.replace(R.id.center_frame, centerViewPagerFragment);

		t.commit();
		FileSystem.makeAppDirTree();
		List<FileInfo> filelist = FileListHelper.GetAllFiles(
				FileSystem.SDCARD_PATH, false);
		fileListModel = new FileListModel(filelist, FileSystem.SDCARD_PATH);
		fileListController = new FileListController(fileListModel);
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
			if (fileListModel.getCurrentDirectory().equals(
					FileSystem.SDCARD_PATH)) {
				exitBy2Click();
			} else if (fileListModel.getCurrentDirectory().equals("")) {
				List<FileInfo> fileList = FileListHelper.GetAllFiles(
						FileSystem.SDCARD_PATH, true);
				fileListController.handleDirectoryChange(fileList, FileSystem.SDCARD_PATH);
			} else {
				centerViewPagerFragment.fileListFragment.onKeyDown(keyCode,
						event);
			}
		}
		return true;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
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

}
