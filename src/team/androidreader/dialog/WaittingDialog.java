package team.androidreader.dialog;

import team.top.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class WaittingDialog extends Dialog{
	
	private TextView textView;

	public WaittingDialog(Context context) {
		super(context);
	}


	public WaittingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_waitting);
		textView = (TextView)findViewById(R.id.textwaitting);
	}
	
	public void setText(String string){
		textView.setText(string);
		textView.setVisibility(TextView.VISIBLE);
	}
	
	public void setText(int resId){
		textView.setText(resId);
		textView.setVisibility(TextView.VISIBLE);
	}
}
