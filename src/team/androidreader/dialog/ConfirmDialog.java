package team.androidreader.dialog;

import team.top.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmDialog extends Dialog {

	private TextView textView;
	private Activity activity;
	private Button ok;
	private Button cancel;

	public ConfirmDialog(Context context) {
		super(context, R.style.MyDialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_confirm);
		textView = (TextView) findViewById(R.id.confirm_text);
		ok = (Button) findViewById(R.id.confirm_yes);
		cancel = (Button) findViewById(R.id.confirm_no);
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setText(String str) {
		textView.setText(str);
	}

	public void setText(int resId) {
		textView.setText(resId);
	}

	public void setConfirmBtn(Activity activity) {
		this.activity = activity;
	}

}
