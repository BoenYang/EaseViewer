package team.top.dialog;

import team.top.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class SetNameDialog extends Dialog{

	public SetNameDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_setname);
	}

}
