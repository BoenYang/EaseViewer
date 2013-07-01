package team.androidreader.mail;

import java.util.List;

import team.androidreader.mainview.FileInfo;
import team.androidreader.mainview.MainActivity;
import team.top.activity.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;

public class MailSendService extends Service {

	private static List<String> recipientList;
	private static String userAddrres;
	private static String body;
	private static String subject;
	private static String passwd;
	private static String servlet;
	private static List<FileInfo> attaches;
	private static NotificationManager nm;
	private static Notification notification;
	private CharSequence from = "邮件正在发送";
	private static PendingIntent contentIntent;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();
		nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.ic_launcher, from,
				System.currentTimeMillis());
		Intent intent1 = new Intent();
		contentIntent = PendingIntent.getActivity(this, 0, intent1, 0);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		Bundle bundle = intent.getExtras();
		recipientList = bundle.getStringArrayList("recipients");
		userAddrres = intent.getStringExtra("useraddress");
		subject = intent.getStringExtra("subject");
		body = intent.getStringExtra("body");
		passwd = intent.getStringExtra("passwd");
		servlet = intent.getStringExtra("servlet");
		attaches = MainActivity.fileListModel.getSelectFiles();

		MailUtil mailUtil = new MailUtil(servlet, 25, userAddrres, passwd);
		for (String recipient : recipientList) {
			notification.setLatestEventInfo(this, from, recipient,
					contentIntent);
			nm.notify(R.string.app_name, notification);
			String msg;
			if (mailUtil.sendEmail(recipient, subject, body, attaches)) {
				msg = "发送成功";
			} else {
				msg = "发送失败";
			}
			notification.setLatestEventInfo(this, from, recipient + " " + msg,
					contentIntent);
			nm.notify(R.string.app_name, notification);
		}
		notification.setLatestEventInfo(this, "邮件发送完成", "", contentIntent);
		nm.notify(R.string.app_name, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
