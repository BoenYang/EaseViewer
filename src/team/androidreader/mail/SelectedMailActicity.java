package team.androidreader.mail;

import java.util.ArrayList;
import java.util.List;

import team.top.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SelectedMailActicity extends Activity {

	private Button addCount;
	private String userCount;
	private Button ensure;
	private EditText address;
	private ListView countListView;
	private List<String> countList = new ArrayList<String>();
	ArrayAdapter<String> simpleAdapter;
	private static Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectmailcount);
		init();
	}

	private void init() {
		addCount = (Button) findViewById(R.id.addcount);
		countListView = (ListView) findViewById(R.id.mailcountlistview);
		simpleAdapter = new ArrayAdapter<String>(SelectedMailActicity.this,
				R.layout.item_mailcount, R.id.item_count, countList);
		countListView.setAdapter(simpleAdapter);
		dialog = new Dialog(SelectedMailActicity.this);
		dialog.getWindow().setContentView(R.layout.dialog_addcount);
		ensure = (Button) dialog.findViewById(R.id.ensure);
		address = (EditText) dialog.findViewById(R.id.useraddress);

		addCount.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});
		ensure.setOnClickListener(new BtnListener());
	}

	class BtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			userCount = address.getText().toString();
			dialog.cancel();
			countList.add(userCount);
			simpleAdapter.notifyDataSetChanged();
		}
	}

}
