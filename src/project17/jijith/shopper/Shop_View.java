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
import android.widget.TextView;
import android.widget.Toast;

public class Shop_View extends Activity {

	TextView sname, scategory, slocation;

	ProgressDialog pd;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;

	private Button button_employees, button_assign_admin, button_edit, button_delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_view);
		sname = (TextView) findViewById(R.id.pname);
		scategory = (TextView) findViewById(R.id.pprice);
		slocation = (TextView) findViewById(R.id.pstock);
		if (getIntent().getStringExtra("sadmin").equals("0")) {
			sname.setText(getIntent().getStringExtra("sname") + " (No Admin)");
		} else {
			sname.setText(
					getIntent().getStringExtra("sname") + " (Admin : " + getIntent().getStringExtra("sadmin") + ")");
		}

		scategory.setText(getIntent().getStringExtra("scategory"));
		slocation.setText(getIntent().getStringExtra("slocation"));

		button_employees = (Button) findViewById(R.id.button_ok_add);
		button_employees.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch_activcity(Employees_By_Shop.class);

			}
		});

		button_assign_admin = (Button) findViewById(R.id.button_admin);
		button_assign_admin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch_activcity(Assign_Admin.class);

			}
		});

		button_edit = (Button) findViewById(R.id.button_exit);
		button_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();

			}
		});
		button_delete = (Button) findViewById(R.id.button_register);
		button_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void switch_activcity(Class to_class) {
		Intent target = new Intent(this, to_class);
		startActivity(target);

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