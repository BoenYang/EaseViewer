package team.androidreader.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import team.androidreader.dialog.ConfirmDialog;
import team.androidreader.theme.SetBackgroundImage;
import team.androidreader.utils.FileSystem;
import team.top.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;

public class Activity_Multichooser extends Activity {

	private GridView gridView;
	private ArrayList<String> dataList = new ArrayList<String>();
	private Adapter_GridImage gridImageAdapter;
	private Dialog dialog;
	private File image_file;
	private EditText fileName;
	private Button generatePDF;
	private Button backBtn;
	LinearLayout title_choosePic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multichooser);
		init();
		SetBackgroundImage.setBackGround(Activity_Multichooser.this,
				title_choosePic);
		initListener();

	}

	private void init() {
		title_choosePic = (LinearLayout) findViewById(R.id.title_choosePic);
		backBtn = (Button) findViewById(R.id.back_btn_choosePic);
		fileName = (EditText) findViewById(R.id.name_pdf);
		generatePDF = (Button) findViewById(R.id.btn_generate_pdf);
		gridView = (GridView) findViewById(R.id.Grid_chooser);
		dataList.add("camera_default");
		gridImageAdapter = new Adapter_GridImage(this, dataList);
		gridView.setAdapter(gridImageAdapter);
	}

	private void initListener() {
		gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (dataList.get(position).contains("camera_default")) {
					dialog = new Dialog(Activity_Multichooser.this,
							R.style.MyDialog);
					dialog.setContentView(R.layout.dialog_select);
					Button fromCamera = (Button) dialog
							.findViewById(R.id.Select_fromCamera);
					Button fromMedia = (Button) dialog
							.findViewById(R.id.Select_fromMedia);
					dialog.show();

					fromCamera.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							image_file = new File(FileSystem.CAMERA_CACHE
									+ File.separator + "IMAGE_"
									+ FileSystem.GetTimeFileName() + ".jpg");
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(image_file));
							startActivityForResult(intent, 1);
						}
					});

					fromMedia.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							Intent intent = new Intent(
									Activity_Multichooser.this,
									Activity_Album.class);
							Bundle bundle = new Bundle();
							bundle.putStringArrayList("dataList",
									getIntentArrayList(dataList));
							intent.putExtras(bundle);
							startActivityForResult(intent, 0);

						}
					});

				} else {
					Intent intent = new Intent(Activity_Multichooser.this,
							Activity_Album.class);
					Bundle bundle = new Bundle();
					bundle.putStringArrayList("dataList",
							getIntentArrayList(dataList));
					intent.putExtras(bundle);
					startActivityForResult(intent, 0);
				}
			}
		});

		generatePDF.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = fileName.getText().toString().trim();
				if (name.equals("")) {
					Toast.makeText(getApplicationContext(),
							R.string.null_fileName, Toast.LENGTH_SHORT).show();
				} else {
					if (dataList.size() > 1) {
						removeOneData(dataList, "camera_default");
						try {
							PicToPdf.convertPicToPdf(dataList,
									FileSystem.SDCARD_PATH + File.separator
											+ name + ".pdf");
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (BadElementException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (DocumentException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						Toast.makeText(getApplicationContext(),
								R.string.generate_success, Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(getApplicationContext(),
								R.string.null_pic, Toast.LENGTH_SHORT).show();
					}

				}
			}
		});

		backBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exitEdit();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				ArrayList<String> tDataList = (ArrayList<String>) bundle
						.getSerializable("dataList");
				if (tDataList != null) {
					if (tDataList.size() < 10) {
						tDataList.add("camera_default");
					}
					dataList.clear();
					dataList.addAll(tDataList);
					gridImageAdapter.notifyDataSetChanged();
				}
			}
		}
		// capture by camera test
		if (requestCode == 1) {
			if (image_file != null && image_file.exists()) {
				String path = image_file.getPath();
				dataList.add(dataList.size() - 1, path);
				if (dataList.size() > 10) {
					removeOneData(dataList, "camera_default");
				}
				gridImageAdapter.notifyDataSetChanged();
			}
		}
	}

	private void removeOneData(ArrayList<String> arrayList, String s) {
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i).equals(s)) {
				arrayList.remove(i);
				return;
			}
		}
	}

	private ArrayList<String> getIntentArrayList(ArrayList<String> dataList) {

		ArrayList<String> tDataList = new ArrayList<String>();

		for (String s : dataList) {
			if (!s.contains("default")) {
				tDataList.add(s);
			}
		}
		return tDataList;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitEdit();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exitEdit() {
		if (dataList.size() > 1
				|| !"".equals(fileName.getText().toString().trim())) {
			ConfirmDialog comfirmDialog = new ConfirmDialog(this);
			comfirmDialog.show();
			comfirmDialog.setText(R.string.dialog_confirm_exit);
			comfirmDialog.setConfirmBtn(this);
		} else {
			finish();
		}
	}

}
