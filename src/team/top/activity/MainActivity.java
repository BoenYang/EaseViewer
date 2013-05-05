package team.top.activity;

import java.util.Timer;
import java.util.TimerTask;

import team.top.fragment.CenterViewPagerFragment;
import team.top.fragment.FileListFragment;
import team.top.fragment.RightCategoryFragment;
import team.top.views.SlidingMenu;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	SlidingMenu mSlidingMenu;
	RightCategoryFragment rightClassifyFragment;
	CenterViewPagerFragment centerViewPagerFragment;
	FragmentPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
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
	}

	/**
	 * 显示右边页面
	 */
	public void showRight() {
		mSlidingMenu.showRightView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("mainacivity");
		if (keyCode == event.KEYCODE_BACK) {
			if (mSlidingMenu.isShowRight == true) {
				mSlidingMenu.showRightView();
			} else {
				if (FileListFragment.currDirectory.equals("/")) {
					exitBy2Click();
				} else {
					centerViewPagerFragment.onKeyDown(keyCode, event);
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
