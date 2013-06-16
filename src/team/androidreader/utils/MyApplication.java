package team.androidreader.utils;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import team.androidreader.mainview.RightCategoryFragment;
import team.top.activity.R;
import android.app.Application;

public class MyApplication extends Application {

	private HttpClient httpClient;
	public boolean isLogin = false;
	public static int Choosed = -1;

	@Override
	public void onCreate() {
		super.onCreate();
		httpClient = this.createHttpClient();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		this.shutdownHttpClient();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		this.shutdownHttpClient();
	}

	// 创建HttpClient实例
	private HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(connMgr, params);
	}

	// 关闭连接管理器并释放资源
	private void shutdownHttpClient() {
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	// 对外提供HttpClient实例
	public HttpClient getHttpClient() {
		return httpClient;
	}

	public static void setChoosed(int choosed) {
		switch (choosed) {
		case 0:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.color.brown);
			break;
		case 1:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.color.brown);
			break;
		case 2:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.color.brown);
			break;
		case 3:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.color.brown);
			break;
		case 4:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.color.brown_pressed);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.color.brown);
			break;
		case 5:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.color.brown_pressed);
			break;
		default:
			RightCategoryFragment.officeCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.pictureCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.musicCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.videoCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.apkCategory
					.setBackgroundResource(R.color.brown);
			RightCategoryFragment.zipCategory
					.setBackgroundResource(R.color.brown);
			break;
		}
	}
}
