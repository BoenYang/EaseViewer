package team.androidreader.mainview;

import java.util.ArrayList;

import team.androidreader.mainview.FileListModel.onOperationModelChangListener;
import team.top.activity.R;
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
public class CenterViewPagerFragment extends Fragment implements
		onOperationModelChangListener {

	private Button showRightBtn;
	private Button operation;
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();// 中间的fragment存放多页面(fragment对象)
	private FileListFragment fileListFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.fragment_viewpager, null);
		init(mView);
		return mView;
	}

	private void init(View mView) {
		MainActivity.fileListModel.addFileOperationChangeListener(this);
		showRightBtn = (Button) mView.findViewById(R.id.showRight);
		operation = (Button) mView.findViewById(R.id.opeartion);
		mPager = (ViewPager) mView.findViewById(R.id.pager);
		fileListFragment = new FileListFragment();
		pagerItemList.add(fileListFragment);
		mAdapter = new MyAdapter(getFragmentManager());
		mPager.setAdapter(mAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		// 点击显示右页按钮
		showRightBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).showRight();
			}
		});
		operation.setOnClickListener(new OperationButtonClickListener());
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

	public boolean onKeyDown(int keycode, KeyEvent keyEvent) {

		return fileListFragment.onKeyDown(keycode, keyEvent);
	}

	@Override
	public void onModelChange(int model) {
		MainActivity.fileListModel.setSelectedNum(0);
		operation.setVisibility(View.VISIBLE);

	}

	class OperationButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int model = MainActivity.fileListModel.getOpeartion();
			switch (model) {
			case FileListController.COPY:
				System.out.println("copy file");
				break;
			case FileListController.MOVE:
				System.out.println("move file");
				break;
			default:
				break;
			}
			MainActivity.fileListController
					.handFileOperationChange(FileListController.DEFAULT);
			MainActivity.fileListModel.clearSelectFIles();
		}

	}

}
