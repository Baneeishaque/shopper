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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	EditText user, pass;

	ProgressDialog pd;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	private Button button_ok;

	private Button button_forgot;

	private Button button_exit;

	private Button button_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		user = (EditText) findViewById(R.id.item_p);
		pass = (EditText) findViewById(R.id.txtlpass);
		button_ok = (Button) findViewById(R.id.button_ok_add);
		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				check_login();

			}
		});
		
		button_forgot = (Button) findViewById(R.id.button_cancel_action);
		button_forgot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				forgot();

			}
		});
		
		button_exit = (Button) findViewById(R.id.button_exit);
		button_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exit();

			}
		});
		
		button_register = (Button) findViewById(R.id.button_register);
		button_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getOrder()) {

		case 100:

			Intent target3 = new Intent(this, Habout.class);
			startActivity(target3);
			return true;

		case 200:

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {

	}

	public void check_login() {

		

		// TODO login data to server...
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				check_login_thread();

			}

		}).start();

	}

	private void check_login_thread() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_IP_ADDRESS + "/android-billing-server/android/get_login.php");
			nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("username", user.getText().toString()));

			nvp.add(new BasicNameValuePair("password", pass.getText().toString()));

			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					Log.d(General_Data.TAG, response);
					pd.dismiss();

					try {
						JSONArray json = new JSONArray(response);
						String count = json.getJSONObject(0).getString("count");
						switch (count) {
						case "1":
							SharedPreferences settings;
							SharedPreferences.Editor editor;
							settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
									Context.MODE_PRIVATE);
							editor = settings.edit();
							editor.putString("shop_id", json.getJSONObject(0).getString("shop_id"));
							editor.putString("shop_name", json.getJSONObject(0).getString("shop_name")+" "+json.getJSONObject(0).getString("shop_category"));
							editor.putString("user_id", json.getJSONObject(0).getString("id"));
							editor.commit();
							Intent i;
							Toast.makeText(Login.this, json.getJSONObject(0).getString("role"), Toast.LENGTH_LONG)
									.show();
							if (json.getJSONObject(0).getString("role").equals("owner")) {

								i = new Intent(Login.this, Home_Owner.class);
								startActivity(i);
								finish();
							} else if (json.getJSONObject(0).getString("role").equals("keeper")) {
								i = new Intent(Login.this, Home_Employee.class);
								startActivity(i);
								finish();
							}

							break;
						case "0":
							Toast.makeText(Login.this, "Login Failure!", Toast.LENGTH_LONG).show();
							break;
						default:
							Toast.makeText(Login.this, "Error : Check json", Toast.LENGTH_LONG).show();
						}

					} catch (JSONException e) {
						Toast.makeText(Login.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
						Log.d(General_Data.TAG, e.getLocalizedMessage());
					}

				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Login.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}
	}

	public void exit() {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}

	public void forgot() {

		Intent target = new Intent(this, Forgot.class);
		startActivity(target);

	}

	public void register() {

		Intent target = new Intent(this, Register_Owner.class);
		startActivity(target);

	}

}