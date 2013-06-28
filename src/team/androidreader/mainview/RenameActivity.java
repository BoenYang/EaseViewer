package team.androidreader.mainview;

import java.util.ArrayList;
import java.util.List;

import team.androidreader.utils.FileOperationHelper;
import team.top.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RenameActivity extends Activity {

	private List<FileInfo> fileList;
	private Button cancel;
	private Button save;
	private List<EditText> editTexts ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rename);
		LinearLayout layout = (LinearLayout) findViewById(R.id.editarea);
		cancel = (Button) findViewById(R.id.cancel);
		save = (Button) findViewById(R.id.save);
		fileList = MainActivity.fileListModel.getSelectFiles();
		editTexts = new ArrayList<EditText>();
		for (int i = 0; i < fileList.size(); i++) {
			TextView textView = new TextView(this);
			textView.setText(fileList.get(i).fileName);
			layout.addView(textView);
			EditText editText = new EditText(this);
			editText.setId(i);
			editText.setText(fileList.get(i).fileName);
			editTexts.add(editText);
			layout.addView(editText);
		}

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				List<String> fileNames = new ArrayList<String>();
				for (int i = 0; i < fileList.size(); i++) {
					fileNames.add(editTexts.get(i).getText().toString());
				}
				FileOperationHelper.Rename(fileList, fileNames);
				finish();
			}
		});
	}

}
