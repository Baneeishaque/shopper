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

public class Shops extends Activity {

	ProgressDialog pd;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	ArrayList<String> item_list_array;
	ListView list;
	ArrayList<Shop_Modal> shop_Modal_list;

	// private Vector ls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shops);
		list = (ListView) findViewById(R.id.list);

		// ls=new Vector();
		// ls.add("Product 1");
		// ls.add("Product 2");
		// ls.add("Product 3");
		// ls.add("Product 4");
		// ls.add("Product 5");

		// ArrayAdapter<String> adapter=new
		// ArrayAdapter<String>(this,R.layout.listview,ls);
		// list_notifications.setAdapter(adapter);

		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_shops_by_owner();

			}

		}).start();
	}

	private void get_shops_by_owner() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost("http://" + General_Data.SERVER_IP_ADDRESS
					+ "/android-billing-server/android/get_shops_by_owner.php");
			nvp = new ArrayList<NameValuePair>(1);
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
					// Toast.makeText(Shops.this,
					// response,Toast.LENGTH_LONG).show();
					pd.dismiss();

					item_list_array = new ArrayList<String>();
					shop_Modal_list = new ArrayList<>();
					try {

						JSONArray json = new JSONArray(response);
						for (int i = 0; i < json.length(); i++) {

							// Populate spinner with country names
							item_list_array.add(json.getJSONObject(i).getString("name"));

							Shop_Modal sample_Shop_Modal = new Shop_Modal();
							sample_Shop_Modal.setId(json.getJSONObject(i).getString("reg.no"));
							sample_Shop_Modal.setName(json.getJSONObject(i).getString("name"));
							sample_Shop_Modal.setLocation(json.getJSONObject(i).getString("location"));
							sample_Shop_Modal.setCategory(json.getJSONObject(i).getString("category"));
							sample_Shop_Modal.setAdmin(json.getJSONObject(i).getString("admin"));

							shop_Modal_list.add(sample_Shop_Modal);

						}
						list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
								android.R.layout.simple_list_item_1, item_list_array));
						list.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

								SharedPreferences settings;
								SharedPreferences.Editor editor;
								settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
										Context.MODE_PRIVATE);
								editor = settings.edit();
								editor.putString("shop_id", shop_Modal_list.get(position).getId());
								editor.commit();

								Intent i = new Intent(getApplicationContext(), Shop_View.class);
								i.putExtra("sid", shop_Modal_list.get(position).getId());
								i.putExtra("sname", shop_Modal_list.get(position).getName());
								i.putExtra("scategory", shop_Modal_list.get(position).getCategory());
								i.putExtra("slocation", shop_Modal_list.get(position).getLocation());
								i.putExtra("sadmin", shop_Modal_list.get(position).getAdmin());

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
					Toast.makeText(Shops.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

}