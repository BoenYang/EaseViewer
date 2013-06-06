package team.androidreader.mainview;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team.androidreader.mainview.FileSortHelper.SortMethod;
import team.androidreader.utils.FileSystem;
import team.top.activity.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
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
				R.layout.frame_right, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.frame_center, null));

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();

		rightClassifyFragment = new RightCategoryFragment();
		t.replace(R.id.right_frame, rightClassifyFragment);

		centerViewPagerFragment = new CenterViewPagerFragment();
		t.replace(R.id.center_frame, centerViewPagerFragment);

		t.commit();
		FileSystem.makeAppDirTree();
		List<FileInfo> filelist = FileListHelper.GetSortedFiles(
				FileSystem.SDCARD_PATH, false, SortMethod.name);
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
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			this.openOptionsMenu();
			System.out.println("menu clicked");
		}
		return centerViewPagerFragment.fileListFragment.onKeyDown(keyCode,
				event);
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "跳转到OtherActivity");
		menu.add(0, 2, 0, "菜单到SubActivity");
		System.out.println("-----------------oncreate options menu");
		return super.onCreateOptionsMenu(menu);
	}

}
