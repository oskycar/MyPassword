package cn.xing.mypassword.activity;

import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import cn.xing.mypassword.R;
import cn.xing.mypassword.app.BaseActivity;
import cn.zdx.lib.annotation.FindViewById;

/**
 * ���ڽ���
 * 
 * @author zengdexing
 * 
 */
public class AboutActivity extends BaseActivity {

	/** Դ���ַ */
	private static final String GITHUB_SOURCE = "https://github.com/oskycar/MyPassword";

	/** �汾��ʾ�ؼ� */
	@FindViewById(R.id.about_version)
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		initActionBar();

		/** ��ʾ�汾 */
		textView.setText(getMyApplication().getVersionName());

		Log.d("DeviceInfo", getDeviceInfo(this));
	}

	/**
	 * ��ȡ�����豸��Ϣ�������豸���Ϊ�����豸
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void onFeedbackClick(View view) {
		startActivity(new Intent(this, FeedbackActivity.class));
	}

	public void onSourceClick(View view) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(GITHUB_SOURCE));
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			showToast(R.string.about_source_open_failed);
		}
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
