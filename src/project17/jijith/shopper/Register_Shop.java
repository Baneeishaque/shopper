package project17.jijith.shopper;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register_Shop extends Activity {
	ProgressDialog pd;
	EditText title, date, content;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	EditText shop_name, shop_category, shop_location;
	Button button_ok, button_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_shop);
		shop_name = (EditText) findViewById(R.id.shop_name);
		shop_category = (EditText) findViewById(R.id.shop_category);
		shop_location = (EditText) findViewById(R.id.shop_location);
		button_ok = (Button) findViewById(R.id.button_ok_add);
		button_cancel = (Button) findViewById(R.id.button_cancel_action);

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				add_shop();

			}
		});

		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});
	}

	public void add_shop() {
		pd = ProgressDialog.show(this, "", "Adding Store...");
		new Thread(new Runnable() {
			public void run() {
				add_shop_thread();

			}

		}).start();
	}

	private void add_shop_thread() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_IP_ADDRESS + "/android-billing-server/android/register_shop.php");
			nvp = new ArrayList<NameValuePair>(4);
			nvp.add(new BasicNameValuePair("name", shop_name.getText().toString()));
			nvp.add(new BasicNameValuePair("category", shop_category.getText().toString()));
			nvp.add(new BasicNameValuePair("location", shop_location.getText().toString()));
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("owner", settings.getString("user_id", "0")));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(Addcrop.this,response,
					// Toast.LENGTH_LONG).show();

					Log.d(General_Data.TAG, response);
					pd.dismiss();
					if (response.equals("0")) {
						Toast.makeText(Register_Shop.this, "Shop added successfully!", Toast.LENGTH_LONG).show();
						// clear(v);
						finish();
					} else {
						Toast.makeText(Register_Shop.this, "Shop addition failure!", Toast.LENGTH_LONG).show();
					}
				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Register_Shop.this, "error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getOrder()) {

		case 100:

			Intent target3 = new Intent(this, Habout.class);
			startActivity(target3);
			return true;

		case 200:

			Intent intent = new Intent(getApplicationContext(), Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

}