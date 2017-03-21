package project17.jijith.shopper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home_Employee extends Activity {

	Button alerts, button_bill;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_employee);
		alerts = (Button) findViewById(R.id.button_alerts);
		button_bill = (Button) findViewById(R.id.button_bill);

		alerts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				switch_activcity(Product_Alerts.class);
			}
		});

		button_bill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				switch_activcity(Bills.class);
			}
		});

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

	public void change_pass(View view) {
		Intent target = new Intent(this, Change_pass.class);
		startActivity(target);
	}

	public void add_products(View view) {

		Intent target = new Intent(this, Add_product.class);
		startActivity(target);
	}

	public void products(View view) {

		Intent target = new Intent(this, Products.class);
		startActivity(target);
	}

	@Override
	public void onBackPressed() {

	}

}
