package team.androidreader.pdf;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ZoomListener implements OnTouchListener {

	private float oldx;
	private float oldy;
	private float x1;
	private float y1;
	private float x2;
	private float y2;
	private float currDistance;
	private float preDistance;
	private View v;

	public enum ActionMode {
		MOVE, ZOOM
	}

	public ActionMode mode;

	public ZoomListener(View v) {
		this.v = v;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (event.getPointerCount() == 2) {
				x1 = event.getX(0) * v.getScaleX();
				y1 = event.getY(0) * v.getScaleY();
				x2 = event.getX(1) * v.getScaleX();
				y2 = event.getY(1) * v.getScaleY();
				preDistance = currDistance = (float) Math.sqrt((x1 - x2)
						* (x1 - x2) + (y1 - y2) * (y1 - y2));
			} else {
				oldx = event.getX(0) * v.getScaleX();
				oldy = event.getY(0) * v.getScaleY();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getPointerCount() == 2) {
				x1 = event.getX(0) * v.getScaleX();
				y1 = event.getY(0) * v.getScaleY();
				x2 = event.getX(1) * v.getScaleX();
				y2 = event.getY(1) * v.getScaleY();
				currDistance = (float) Math.sqrt((x1 - x2) * (x1 - x2)
						+ (y1 - y2) * (y1 - y2));
				mode = ActionMode.ZOOM;
			} else {
				x1 = event.getX(0) * v.getScaleX();
				mode = ActionMode.MOVE;
			}

			break;
		}
		actionStart(mode);
		preDistance = currDistance;
		return false;
	}

	public void actionStart(ActionMode mode) {
		if (mode == ActionMode.ZOOM) {
			if (currDistance - preDistance > 10) {
				if (v.getScaleX() <= 1.9) {
					v.setScaleX(v.getScaleX() + 0.1f);
					v.setScaleY(v.getScaleY() + 0.1f);
				}
			} else if (currDistance - preDistance < -10) {
				if (v.getScaleX() >= 1.1) {
					v.setScaleX(v.getScaleX() - 0.1f);
					v.setScaleY(v.getScaleY() - 0.1f);
				}else{
					v.setX(0.0f);
					v.setY(0.0f);
				}
			}
		} else if (mode == ActionMode.MOVE) {
			float outX = (v.getWidth() * v.getScaleX() - v.getWidth()) / 2.0f;
			float outy = (v.getHeight() * v.getScaleY() - v.getHeight()) / 2.0f;
			if (x1 - oldx > 10) {
				if (v.getX() <= (outX - 10)) {
					v.setX(v.getX() + 10);
				}
			} else if (x1 - oldx < -10) {
				if (v.getX() >= -(outX - 10)) {
					v.setX(v.getX() - 10);
				}
			}
		}
	}

}
