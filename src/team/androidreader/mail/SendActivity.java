package team.androidreader.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import team.top.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendActivity extends Activity {

	private EditText subject;
	private EditText body;
	private TextView attach;
	private Button send;
	private String userAddress;
	private Dialog inputDialog;
	private Button ensure;
	private EditText passwdEditext;
	private List<String> recipients;
	private String mailServlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendmail);
		Intent intent = getIntent();
		userAddress = intent.getStringExtra("useraddress");
		mailServlet = intent.getStringExtra("servlet");
		Bundle recipientsBundle = intent.getExtras();
		recipients = recipientsBundle.getStringArrayList("recipients");
		init();
	}

	private void init() {
		subject = (EditText)findViewById(R.id.subject);
		body = (EditText)findViewById(R.id.body);
		attach = (TextView)findViewById(R.id.tvattach);
		send = (Button)findViewById(R.id.sendmail);
		inputDialog = new Dialog(SendActivity.this);
		inputDialog.setTitle(R.string.promot_enter_passwd);
		inputDialog.setContentView(R.layout.dialog_input);
		ensure = (Button) inputDialog.findViewById(R.id.ensure);
		passwdEditext = (EditText) inputDialog.findViewById(R.id.useraddress);
		passwdEditext.setTransformationMethod(PasswordTransformationMethod.getInstance());
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inputDialog.show();
				passwdEditext.setText("");
			}
		});
		
		ensure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String subContent = subject.getText().toString();
				String bodyContent = body.getText().toString();
				String passwd = passwdEditext.getText().toString();
				if(!checkNetworkState()){
					Toast.makeText(SendActivity.this, R.string.net_work_connect_failed, Toast.LENGTH_SHORT).show();
					return ;
				}
				
				if(!checkPasswd(passwd)){
					Toast.makeText(SendActivity.this, R.string.passwd_incorrect, Toast.LENGTH_SHORT).show();
					return ;
				}
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				if(passwd.equals("")){
					Toast.makeText(SendActivity.this, R.string.promot_enter_passwd, Toast.LENGTH_SHORT).show();
					return ;
				}
				bundle.putStringArrayList("recipients", (ArrayList<String>)recipients);
				intent.putExtra("subject", subContent);
				intent.putExtra("body", bodyContent);
				intent.putExtra("servlet", mailServlet);
				intent.putExtra("passwd", passwd);
				intent.putExtra("useraddress", userAddress);
				intent.putExtras(bundle);
				intent.setClass(SendActivity.this, MailSendService.class);
				startService(intent);
				inputDialog.cancel();
				finish();
			}
		});
	}
	
	private boolean checkPasswd(String passwd){
		Properties props = new Properties();
		props.put("mail.smtp.host", mailServlet);
		props.put("mail.smtp.port", String.valueOf(25));
		props.put("mail.smtp.auth", "true");
		Transport transport = null;
		Session session = Session.getDefaultInstance(props, null);
		try {
			transport = session.getTransport("smtp");
			transport.connect(mailServlet, userAddress, passwd);
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean checkNetworkState(){
		ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
}
