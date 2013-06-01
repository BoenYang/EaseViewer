package team.androidreader.dialog;

import team.top.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class WaittingDialog extends Dialog{

	public WaittingDialog(Context context) {
		super(context);
	}


	public WaittingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_waitting);
	}
}
