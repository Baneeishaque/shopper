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

public class Add_product extends Activity {

	ProgressDialog pd;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	Button button_ok, button_cancel;

	EditText unit, stock, name, minimum_stock,tax;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_products);
		unit = (EditText) findViewById(R.id.item_q);
		stock = (EditText) findViewById(R.id.stock);
		tax = (EditText) findViewById(R.id.tax);

		name = (EditText) findViewById(R.id.item_p);
		minimum_stock = (EditText) findViewById(R.id.minimum_stock);

		button_ok = (Button) findViewById(R.id.button_ok_add);
		button_cancel = (Button) findViewById(R.id.button_cancel_action);

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				add_product();

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

	public void add_product() {
		pd = ProgressDialog.show(this, "", "Adding Product...");
		new Thread(new Runnable() {
			public void run() {
				add_product_thread();

			}

		}).start();
	}

	private void add_product_thread() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost("http://" + General_Data.SERVER_IP_ADDRESS
					+ "/android-billing-server/android/register_product.php");
			nvp = new ArrayList<NameValuePair>(5);
			nvp.add(new BasicNameValuePair("unit-price", unit.getText().toString()));
			nvp.add(new BasicNameValuePair("tax", tax.getText().toString()));
			
			nvp.add(new BasicNameValuePair("stock", stock.getText().toString()));
			nvp.add(new BasicNameValuePair("name", name.getText().toString()));
			nvp.add(new BasicNameValuePair("minimum-stock", minimum_stock.getText().toString()));
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("shop", settings.getString("shop_id", "0")));
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
						Toast.makeText(Add_product.this, "Product Registration successfully!", Toast.LENGTH_LONG)
								.show();
						// clear(v);
						switch_activcity(Products.class);
						finish();
					} else {
						Toast.makeText(Add_product.this, "Product Registration failure!", Toast.LENGTH_LONG).show();
					}
				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Add_product.this, "error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
	}

	public void switch_activcity(Class to_class) {
		Intent target = new Intent(this, to_class);
		startActivity(target);

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

	public void next(View view) {

		Intent target = new Intent(this, Home_Employee.class);
		startActivity(target);

	}

	public void exit(View view) {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}

	public void forgot(View view) {

		Intent target = new Intent(this, Forgot.class);
		startActivity(target);

	}

	public void register(View view) {

		Intent target = new Intent(this, Add_product.class);
		startActivity(target);

	}

}