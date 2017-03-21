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
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Bills extends Activity {

//	String shop_name="";
	
	ProgressDialog pd;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	public static String bill_no;
	Button button_new_bill;
	ArrayList<String> item_list_array;
	ListView list;

	// private Vector ls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bills);
		button_new_bill = (Button) findViewById(R.id.button_new_bill);
		button_new_bill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch_activcity(New_Bill.class);

			}
		});
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
		SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
				Context.MODE_PRIVATE);
		setTitle(settings.getString("shop_name", "Shop Name")+" Bills");
		pd = ProgressDialog.show(this, "", "Please wait...");
		
		
		new Thread(new Runnable() {
			public void run() {
				get_bills_by_shop();
			}
		}).start();
		
		
	}
	
	public void switch_activcity(Class to_class) {
		Intent target = new Intent(this, to_class);
		startActivity(target);

	}

	private void get_bills_by_shop() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_IP_ADDRESS + "/android-billing-server/android/get_bills_all.php");
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
					// Toast.makeText(Shops.this, response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();

					item_list_array = new ArrayList<String>();
					// shop_Modal_list = new ArrayList<>();
					try {

						JSONArray json = new JSONArray(response);
						for (int i = 0; i < json.length(); i++) {

							// Populate spinner with country names
							item_list_array.add(json.getJSONObject(i).getString("bill-no")+"-"+
							json.getJSONObject(i).getString("customer-name")+"-"+
									json.getJSONObject(i).getString("day")+":"+
											json.getJSONObject(i).getString("month")+"-"+
													json.getJSONObject(i).getString("year"));

					

						}
						list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
								android.R.layout.simple_list_item_1, item_list_array));
						list.setOnItemClickListener(new OnItemClickListener() {
							

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								Intent target = new Intent(getBaseContext(), Bill_View.class);
								get_bill_thread(list.getItemAtPosition(position).toString().
										substring(0, list.getItemAtPosition(position).toString().indexOf("-")));
								bill_no=list.getItemAtPosition(position).toString().
										substring(0, list.getItemAtPosition(position).toString().indexOf("-"));
								
								startActivity(target);
							
								
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
					Toast.makeText(Bills.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}

	}
	
	private void get_bill_thread(final String bill) {
		// TODO Auto-generated method stub
		 pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_bill(bill);
			}
		}).start();
	}
	
	private void get_bill(String bill) {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_IP_ADDRESS + "/android-billing-server/android/get_bill.php");
			nvp = new ArrayList<NameValuePair>(1);

			nvp.add(new BasicNameValuePair("bill", bill));
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(Shops.this, response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();

					SharedPreferences settings = getApplicationContext()
							.getSharedPreferences(General_Data.SHARED_PREFERENCE, Context.MODE_PRIVATE);
					settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
							Context.MODE_PRIVATE);
					
					try {

						JSONArray json = new JSONArray(response);
						for (int i = 0; i < json.length(); i++) {
							Editor editor = settings.edit();
							editor.putString("Customer","Customer : " + json.getJSONObject(i).getString("customer-name"));
							editor.putString("Customer Phone","Customer Phone : " + json.getJSONObject(i).getString("customer-mob"));
							
							editor.putString("Date","Date : " + json.getJSONObject(i).getString("day") + ":"
									+ json.getJSONObject(i).getString("month") + "-"
									+ json.getJSONObject(i).getString("year"));
							editor.putString("Time","Time : " + json.getJSONObject(i).getString("time")
									.substring(json.getJSONObject(i).getString("time").indexOf(" ") + 1));
							editor.commit();
						}
						

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
					Toast.makeText(getBaseContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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