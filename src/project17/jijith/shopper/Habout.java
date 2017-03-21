package project17.jijith.shopper;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.webkit.WebView;

public class Habout extends Activity {

	NotificationManager nm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.habout);
		
		
		WebView wv = (WebView) findViewById(R.id.wbvwabout);
		wv.loadUrl("file:///android_asset/about.html");

	}
}
