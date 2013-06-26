package team.androidreader.mail;

import java.util.List;

import team.top.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendActivity extends Activity {

	private EditText subject;
	private EditText body;
	private TextView attach;
	private Button send;
	private String userAddress;
	private List<String> recipients;
	private Dialog inputDialog;
	private Button ensure;
	private EditText address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendmail);
		Intent intent = getIntent();
		userAddress = intent.getStringExtra("useraddress");
		Bundle bundle = intent.getExtras();
		recipients = bundle.getStringArrayList("recipients");
		init();
	}

	private void init() {
		subject = (EditText)findViewById(R.id.subject);
		body = (EditText)findViewById(R.id.body);
		attach = (TextView)findViewById(R.id.tvattach);
		send = (Button)findViewById(R.id.sendmail);
		inputDialog = new Dialog(SendActivity.this);
		inputDialog.setTitle("请输入您的邮箱密码");
		inputDialog.setContentView(R.layout.dialog_adduseraddress);
		ensure = (Button) inputDialog.findViewById(R.id.ensure);
		address = (EditText) inputDialog.findViewById(R.id.useraddress);
		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inputDialog.show();
				address.setText("");
			}
		});
		ensure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String subContent = subject.getText().toString();
				String bodyContent = body.getText().toString();
				String servlet = userAddress.substring(userAddress.indexOf('@')+1,userAddress.lastIndexOf('.'));
				String passwd = address.getText().toString();
				Intent intent = new Intent();
				intent.setClass(SendActivity.this, MailSendService.class);
				startService(intent);
				inputDialog.cancel();
				finish();
			}
		});
	}
}
