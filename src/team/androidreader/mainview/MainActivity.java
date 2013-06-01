package team.androidreader.mainview;

import java.util.Timer;
import java.util.TimerTask;

import team.androidreader.utils.FileSystem;
import team.top.activity.R;
import team.top.activity.R.id;
import team.top.activity.R.layout;
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
			if (mSlidingMenu.isShowRight == true) {
				mSlidingMenu.showRightView();
			} else {
				if (FileListFragment.currentDir.equals(FileSystem.SDCARD_PATH)
						&& FileListFragment.fileCategory == FileListHelper.FileCategory.SDCARD) {
					exitBy2Click();
				} else {
					return centerViewPagerFragment.fileListFragment.onKeyDown(
							keyCode, event);
				}
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
