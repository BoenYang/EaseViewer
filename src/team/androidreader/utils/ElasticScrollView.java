package team.androidreader.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;

public class ElasticScrollView extends ScrollView {
	private static final int MAX_Y_OVERSCROLL_DISTANCE = 100;

	private Context mContext;
	private int mMaxYOverscrollDistance;

	public ElasticScrollView(Context context) {
		super(context);
		mContext = context;
		initBounceListView();
	}

	public ElasticScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initBounceListView();
	}

	public ElasticScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initBounceListView();
	}

	private void initBounceListView() {
		final DisplayMetrics metrics = mContext.getResources()
				.getDisplayMetrics();
		final float density = metrics.density;

		mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// 这块是关键性代码
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX,
				mMaxYOverscrollDistance, isTouchEvent);
	}
}
