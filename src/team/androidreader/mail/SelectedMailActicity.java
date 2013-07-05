package team.androidreader.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.androidreader.theme.SetBackgroundImage;
import team.androidreader.utils.ADO;
import team.top.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class SelectedMailActicity extends Activity {

	private Button addUserAddress;
	private Button ensure;
	private Button back;
	private EditText address;
	private ListView addressListView;
	private List<String> addressList;
	ArrayAdapter<String> adapter;
	private Dialog inputDialog;
	public final static String FORMAT = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	private ADO ado;
	public static Map<String, String> supportMail = new HashMap<String, String>();
	private LinearLayout layout;
	

	static {
		supportMail.put("126", "smtp.126.com");
		supportMail.put("163", "smtp.163.com");
		supportMail.put("yahoo", "smtp.mail.yahoo.com.cn ");
		supportMail.put("Sohu", "smtp.sohu.com");
		supportMail.put("gmail", "smtp.gmail.com");
		supportMail.put("qq", "smtp.qq.com");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectmailaddr);
		layout = (LinearLayout)findViewById(R.id.title_useraddr);
		SetBackgroundImage.setBackGround(this, layout);
		ado = new ADO(this);
		addressList = ado.queryUserMail();
		init();
	}

	private void init() {
		addUserAddress = (Button) findViewById(R.id.adduseraddr);
		addressListView = (ListView) findViewById(R.id.useraddrlistview);
		back = (Button)findViewById(R.id.back_btn_selectMail);
		adapter = new ArrayAdapter<String>(this, R.layout.item_mailaddr,
				R.id.item_addr, addressList);
		addressListView.setAdapter(adapter);
		inputDialog = new Dialog(SelectedMailActicity.this, R.style.MyDialog);
		inputDialog.setContentView(R.layout.dialog_input);
		ensure = (Button) inputDialog.findViewById(R.id.ensure);
		address = (EditText) inputDialog.findViewById(R.id.useraddress);
		address.setHint(R.string.add_addresser_dialog_title);
		ensure.setOnClickListener(new BtnListener());
		addUserAddress.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				inputDialog.show();
				address.setText("");
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		addressListView.setOnItemClickListener(new ItemOnClickListener());
	}

	class ItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String addresser = addressList.get(position);
			Intent intent = new Intent();
			intent.setClass(SelectedMailActicity.this,
					SelectRecipientActivity.class);
			intent.putExtra("useraddress", addresser);
			String mailServlet = addresser.substring(
					addresser.indexOf('@') + 1, addresser.lastIndexOf('.'));
			intent.putExtra("servlet", supportMail.get(mailServlet));
			startActivity(intent);
		}
	}

	class BtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String temp = address.getText().toString();

			if (!temp.matches(FORMAT)) {
				Toast.makeText(SelectedMailActicity.this,
						R.string.mail_format_incorrect, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			String mailServlet = temp.substring(temp.indexOf('@') + 1,
					temp.lastIndexOf('.'));
			if (!supportMail.containsKey(mailServlet.toLowerCase())) {
				Toast.makeText(SelectedMailActicity.this, R.string.not_support,
						Toast.LENGTH_SHORT).show();
				return;
			}
			ADO ado = new ADO(SelectedMailActicity.this);
			if (!ado.addUserMail(temp)) {
				Toast.makeText(SelectedMailActicity.this,
						R.string.write_failed, Toast.LENGTH_SHORT).show();
				return;
			}
			inputDialog.cancel();
			addressList.add(temp);
			adapter.notifyDataSetChanged();
		}
	}

}
