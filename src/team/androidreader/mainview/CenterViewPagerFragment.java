package team.androidreader.mainview;


import java.util.ArrayList;

import team.androidreader.test.TestContentActivity;
import team.top.activity.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 
 * @author ht
 *
 */
public class CenterViewPagerFragment extends Fragment {

	private Button showRightBtn;
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();// 中间的fragment存放多页面(fragment对象)
	public FileListFragment fileListFragment;
	private Button testOfficeBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.fragment_viewpager, null);
		showRightBtn = (Button) mView.findViewById(R.id.showRight);
		testOfficeBtn = (Button)mView.findViewById(R.id.test);
		mPager = (ViewPager) mView.findViewById(R.id.pager);
		fileListFragment = new FileListFragment();
		pagerItemList.add(fileListFragment);
		mAdapter = new MyAdapter(getFragmentManager());
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				if (myPageChangeListener != null)
					myPageChangeListener.onPageSelected(position);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int position) {

			}
		});
		
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		//点击显示右页按钮
		showRightBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).showRight();
			}
		});
		
		testOfficeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), TestContentActivity.class);
				startActivity(intent);
			}
		});
	}

	public boolean isFirst() {
		if (mPager.getCurrentItem() == 0)
			return true;
		else
			return false;
	}

	public boolean isEnd() {
		if (mPager.getCurrentItem() == pagerItemList.size() - 1)
			return true;
		else
			return false;
	}

	public class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return pagerItemList.size();
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = null;
			if (position < pagerItemList.size())
				fragment = pagerItemList.get(position);
			else
				fragment = pagerItemList.get(0);

			return fragment;

		}
	}

	private MyPageChangeListener myPageChangeListener;

	public void setMyPageChangeListener(MyPageChangeListener l) {

		myPageChangeListener = l;

	}
	
	public boolean onKeyDown(int keycode,KeyEvent keyEvent){
		
		return fileListFragment.onKeyDown(keycode, keyEvent);
	}

	public interface MyPageChangeListener {
		public void onPageSelected(int position);
	}

}
