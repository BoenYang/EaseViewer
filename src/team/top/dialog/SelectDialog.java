package team.top.dialog;

import team.top.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class SelectDialog extends Dialog{

	public SelectDialog(Context context) {
		super(context);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_select);
		setTitle("please select photo");
	}
}
