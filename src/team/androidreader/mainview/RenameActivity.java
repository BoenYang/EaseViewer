package team.androidreader.mainview;

import java.util.ArrayList;
import java.util.List;

import team.androidreader.dialog.ConfirmDialog;
import team.androidreader.utils.FileOperationHelper;
import team.top.activity.R;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class RenameActivity extends Activity {

	private List<FileInfo> fileList;
	private Button back;
	private Button save;
	private List<EditText> editTexts ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rename);
		LinearLayout layout = (LinearLayout) findViewById(R.id.editarea);
		back = (Button) findViewById(R.id.back_btn_rename);
		save = (Button) findViewById(R.id.save);
		fileList = MainActivity.fileListModel.getSelectFiles();
		editTexts = new ArrayList<EditText>();
		for (int i = 0; i < fileList.size(); i++) {
			TextView textView = new TextView(this);
			textView.setText(fileList.get(i).fileName);
			textView.setSingleLine();
			textView.setEllipsize(TruncateAt.MIDDLE);
			textView.setBackgroundResource(R.drawable.bg_rounded_rect);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(5, 5, 5, 5);
			layout.addView(textView,layoutParams);
			textView.setPadding(10, 10, 10, 10);
			textView.setTypeface(Typeface.DEFAULT_BOLD);
			EditText editText = new EditText(this);
			editText.setId(i);
			editText.setSingleLine();
			editText.setText(fileList.get(i).fileName);
			editText.setBackgroundResource(R.drawable.bg_rounded_rect);
			editText.setPadding(10, 10, 10, 10);
			
			editTexts.add(editText);
			layout.addView(editText,layoutParams);
		}

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConfirmDialog comfirmDialog = new ConfirmDialog(RenameActivity.this);
				comfirmDialog.show();
				comfirmDialog.setText(R.string.dialog_confirm_rename);
				comfirmDialog.setConfirmBtn(RenameActivity.this);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			ConfirmDialog comfirmDialog = new ConfirmDialog(RenameActivity.this);
			comfirmDialog.show();
			comfirmDialog.setText(R.string.dialog_confirm_rename);
			comfirmDialog.setConfirmBtn(RenameActivity.this);
		}
		return true;
	}

}
