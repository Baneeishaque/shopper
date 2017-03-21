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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Stock extends Activity {

	ProgressDialog pd;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	Button button_ok, button_cancel;

	EditText unit, stock, nstock, name, minimum_stock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_stock);
		unit = (EditText) findViewById(R.id.item_q);
		stock = (EditText) findViewById(R.id.stock);
		nstock = (EditText) findViewById(R.id.nstock);

		name = (EditText) findViewById(R.id.item_p);
		minimum_stock = (EditText) findViewById(R.id.minimum_stock);

		button_ok = (Button) findViewById(R.id.button_ok_add);
		button_cancel = (Button) findViewById(R.id.button_cancel_action);

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				update_stock();

			}
		});

		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		name.setText(getIntent().getStringExtra("pname"));
		unit.setText(getIntent().getStringExtra("pprice"));
		stock.setText("Current Stock : " + getIntent().getStringExtra("pstock"));
		minimum_stock.setText(getIntent().getStringExtra("mstock"));

		name.setEnabled(false);
		unit.setEnabled(false);
		stock.setEnabled(false);
		minimum_stock.setEnabled(false);
	}

	public void update_stock() {
		pd = ProgressDialog.show(this, "", "Adding Product...");
		new Thread(new Runnable() {
			public void run() {
				update_stock_thread();

			}

		}).start();
	}

	private void update_stock_thread() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_IP_ADDRESS + "/android-billing-server/android/update_stock.php");
			nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("id", getIntent().getStringExtra("pid")));
			nvp.add(new BasicNameValuePair("stock",
					String.valueOf(Integer.parseInt(getIntent().getStringExtra("pstock"))
							+ Integer.parseInt(nstock.getText().toString()))));

			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(Addcrop.this,response,Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();
					if (response.equals("0")) {
						Toast.makeText(Add_Stock.this, "Stock Addition successfully!", Toast.LENGTH_LONG).show();
						// clear(v);
						switch_activcity(Products.class);
						finish();
					} else {
						Toast.makeText(Add_Stock.this, "Stock Addition failure!", Toast.LENGTH_LONG).show();
					}
				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Add_Stock.this, "error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

}