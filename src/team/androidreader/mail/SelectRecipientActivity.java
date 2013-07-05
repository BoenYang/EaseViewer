package team.androidreader.mail;

import java.util.ArrayList;
import java.util.List;

import team.androidreader.theme.SetBackgroundImage;
import team.androidreader.utils.ADO;
import team.top.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectRecipientActivity extends Activity {

	private String userAddr;
	private String mailServlet;
	private ListView repipientListView;
	private List<String> recipientList;
	private RecipientShowAdapter adapter;
	private ADO ado;
	private Button addrecipient;
	private Button ok;
	private Button back;
	private Dialog inputDialog;
	private Button ensure;
	private EditText address;
	private List<Boolean> selected = new ArrayList<Boolean>();
	private List<String> selectedRecipient = new ArrayList<String>();
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectrecipient);
		layout = (LinearLayout)findViewById(R.id.title_recipientaddr);
		SetBackgroundImage.setBackGround(this, layout);
		ado = new ADO(this);
		recipientList = ado.queryRecipientMail();
		for (String string : recipientList) {
			selected.add(false);
		}
		init();
		Intent intent = getIntent();
		userAddr = intent.getStringExtra("useraddress");
		mailServlet = intent.getStringExtra("servlet");
	}

	private void init() {
		addrecipient = (Button) findViewById(R.id.addrecipient);
		ok = (Button) findViewById(R.id.ok);
		back = (Button)findViewById(R.id.back_btn_selectrecipient);
		repipientListView = (ListView) findViewById(R.id.recipientaddrlistview);
		adapter = new RecipientShowAdapter(this);
		repipientListView.setAdapter(adapter);
		inputDialog = new Dialog(this, R.style.MyDialog);
		//inputDialog.setTitle(R.string.add_recupient_dialog_title);
		inputDialog.setContentView(R.layout.dialog_input);
		address = (EditText) inputDialog.findViewById(R.id.useraddress);
		address.setHint(R.string.add_recupient_dialog_title);
		ensure = (Button) inputDialog.findViewById(R.id.ensure);
		ensure.setOnClickListener(new BtnListener());

		addrecipient.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				inputDialog.show();
				address.setText("");
			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedRecipient.size() == 0) {
					Toast.makeText(SelectRecipientActivity.this,
							R.string.promot_select_recipient,
							Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent();
				intent.setClass(SelectRecipientActivity.this,
						SendActivity.class);
				intent.putExtra("useraddress", userAddr);
				intent.putExtra("servlet", mailServlet);
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("recipients",
						(ArrayList<String>) selectedRecipient);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		ensure.setOnClickListener(new BtnListener());
	}

	class BtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String temp = address.getText().toString();

			if (!temp.matches(SelectedMailActicity.FORMAT)) {
				Toast.makeText(SelectRecipientActivity.this,
						R.string.mail_format_incorrect, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			String mail = temp.substring(temp.indexOf('@') + 1,
					temp.lastIndexOf('.'));
			if (!SelectedMailActicity.supportMail.containsKey(mail
					.toLowerCase())) {
				Toast.makeText(SelectRecipientActivity.this,
						R.string.not_support, Toast.LENGTH_SHORT).show();
				return;
			}
			if (!ado.addRecipientMail(temp)) {
				Toast.makeText(SelectRecipientActivity.this,
						R.string.write_failed, Toast.LENGTH_SHORT).show();
				return;
			}
			inputDialog.cancel();
			recipientList.add(0, temp);
			selected.add(0, false);
			adapter.notifyDataSetChanged();
		}
	}

	class RecipientShowAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private ViewHolder viewHolder;

		class ViewHolder {
			public TextView mail;
			public CheckBox checkBox;
		}

		public RecipientShowAdapter(Context context) {
			layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			if (recipientList != null) {
				return recipientList.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			if (recipientList != null) {
				return recipientList.get(position);
			} else {
				return null;
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				viewHolder = (ViewHolder) convertView.getTag();
			} else {
				convertView = layoutInflater.inflate(
						R.layout.item_recipientaddr, null);
				viewHolder = new ViewHolder();
				viewHolder.mail = (TextView) convertView
						.findViewById(R.id.item_recipientaddr);
				viewHolder.checkBox = (CheckBox) convertView
						.findViewById(R.id.recipientSelected);
				convertView.setTag(viewHolder);
			}
			viewHolder.mail.setText(recipientList.get(position));
			viewHolder.checkBox.setChecked(selected.get(position));
			viewHolder.checkBox.setOnClickListener(new CheckBoxOnClickListener(
					position));
			return convertView;
		}

		class CheckBoxOnClickListener implements View.OnClickListener {

			private int position;

			public CheckBoxOnClickListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(View v) {
				if (selected.get(position) == false) {
					selected.set(position, true);
					selectedRecipient.add(recipientList.get(position));
				} else {
					selected.set(position, false);
					selectedRecipient.remove(recipientList.get(position));
				}
			}
		}

	}
}
