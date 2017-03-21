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

public class Register_Owner extends Activity {
	ProgressDialog pd;
	EditText title, date, content;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	EditText username, password, email, password2, email2;
	Button button_ok, button_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_owner);
		username = (EditText) findViewById(R.id.category);
		password = (EditText) findViewById(R.id.item_p);
		password2 = (EditText) findViewById(R.id.item_q);
		email = (EditText) findViewById(R.id.stock);
		email2 = (EditText) findViewById(R.id.minimum_stock);
		button_ok = (Button) findViewById(R.id.button_ok_add);
		button_cancel = (Button) findViewById(R.id.button_cancel_action);

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				validate_register_data();

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

	protected void validate_register_data() {
		// TODO Auto-generated method stub
		if (password.getText().toString().equals(password2.getText().toString())) {
			if (email.getText().toString().equals(email2.getText().toString())) {
				add_owner();
			} else {
				Toast.makeText(getApplicationContext(), "Unmatched Emails", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "Unmatched Passcodes", Toast.LENGTH_LONG).show();
		}
	}

	public void add_owner() {
		pd = ProgressDialog.show(this, "", "Adding Owner...");
		new Thread(new Runnable() {
			public void run() {
				add_owner_thread();

			}

		}).start();
	}

	private void add_owner_thread() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_IP_ADDRESS + "/android-billing-server/android/register_owner.php");
			nvp = new ArrayList<NameValuePair>(4);
			nvp.add(new BasicNameValuePair("username", username.getText().toString()));
			nvp.add(new BasicNameValuePair("password", password.getText().toString()));
			nvp.add(new BasicNameValuePair("email", email.getText().toString()));
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
						Toast.makeText(Register_Owner.this, "Owner Registration successfully!", Toast.LENGTH_LONG)
								.show();

						finish();
					} else {
						Toast.makeText(Register_Owner.this, "Owner Registration failure!", Toast.LENGTH_LONG).show();
					}
				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Register_Owner.this, "error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
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

}