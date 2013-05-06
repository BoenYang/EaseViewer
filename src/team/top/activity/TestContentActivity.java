package team.top.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import team.top.Camera.cameraToPdf;
import team.top.utils.FileSystem;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class TestContentActivity extends Activity {

	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;
	Button btn5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testcontent);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.cameraBtn);
		btn5.setOnClickListener(new BtnListener());
	
	}

	class BtnListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.btn1:
				break;
			case R.id.btn2:
				break;
			case R.id.btn3:
				break;
			case R.id.btn4:
				break;
			case R.id.cameraBtn:
				Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent_camera, 1);
				break;
			}
		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            FileOutputStream b = null;
            File file = new File(FileSystem.SDCARD_PATH + "/myImage/");
            file.mkdirs();
            String fileName = FileSystem.SDCARD_PATH + "/myImage/111.png";
            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Intent intent_confirm = new Intent();
            intent_confirm.setClass(TestContentActivity.this, cameraToPdf.class);
            TestContentActivity.this.startActivity(intent_confirm);
        	super.onActivityResult(requestCode, resultCode, data);
        }
	}
}
