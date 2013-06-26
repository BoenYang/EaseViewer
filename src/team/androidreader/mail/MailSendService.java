package team.androidreader.mail;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MailSendService extends Service{

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println("service start");
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
