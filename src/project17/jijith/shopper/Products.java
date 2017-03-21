package project17.jijith.shopper;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Products extends Activity {

	ProgressDialog pd;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	ArrayList<String> item_list_array;
	ListView list_products;
	ArrayList<Product_Modal> product_Modal_list;

	// private Vector ls;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.products);
		//
		//
		//
		// ls=new Vector();
		// ls.add("Product 1");
		// ls.add("Product 2");
		// ls.add("Product 3");
		// ls.add("Product 4");
		// ls.add("Product 5");
		//
		//
		// ArrayAdapter<String> adapter=new
		// ArrayAdapter<String>(this,R.layout.listview,ls);
		list_products = (ListView) findViewById(R.id.list);
		// list_notifications.setAdapter(adapter);

		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_products();

			}

		}).start();
	}

	private void get_products() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_IP_ADDRESS + "/android-billing-server/android/getproductsall.php");
			nvp = new ArrayList<NameValuePair>(1);
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
					Toast.makeText(Products.this, response, Toast.LENGTH_LONG).show();
					pd.dismiss();

					item_list_array = new ArrayList<String>();
					product_Modal_list = new ArrayList<>();
					try {

						JSONArray json = new JSONArray(response);
						for (int i = 0; i < json.length(); i++) {

							// Populate spinner with country names
							item_list_array.add(json.getJSONObject(i).getString("name"));

							Product_Modal sample_notificationModal = new Product_Modal();
							sample_notificationModal.setId(json.getJSONObject(i).getString("id"));
							sample_notificationModal.setName(json.getJSONObject(i).getString("name"));
							sample_notificationModal.setUnitprice(json.getJSONObject(i).getString("unit-price"));
							sample_notificationModal.setStock(json.getJSONObject(i).getString("stock"));
							sample_notificationModal.setMinimumstock(json.getJSONObject(i).getString("minimum-stock"));

							product_Modal_list.add(sample_notificationModal);

						}
						list_products.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
								android.R.layout.simple_list_item_1, item_list_array));
						list_products.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

								Intent i = new Intent(getApplicationContext(), Product_View.class);
								i.putExtra("pid", product_Modal_list.get(position).getId());
								i.putExtra("pname", product_Modal_list.get(position).getName());
								i.putExtra("pprice", product_Modal_list.get(position).getUnitprice());
								i.putExtra("pstock", product_Modal_list.get(position).getStock());
								i.putExtra("mstock", product_Modal_list.get(position).getMinimumstock());

								startActivity(i);
							}
						});
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
								.show();
						Log.d(General_Data.TAG, e.getLocalizedMessage());
					}

				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Products.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
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

		Intent target = new Intent(this, Products.class);
		startActivity(target);

	}

}