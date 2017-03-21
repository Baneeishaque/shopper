package project17.jijith.shopper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home_Owner extends Activity {

	private Button button_shops;
	private Button button_add_stores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_owner);
		button_shops = (Button) findViewById(R.id.button_bill);
		button_add_stores = (Button) findViewById(R.id.button_cancel_action);
		
		button_shops.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch_activcity(Shops.class);

			}
		});
		
		button_add_stores.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch_activcity(Register_Shop.class);

			}
		});

	}
	
	public void switch_activcity(Class to_class)
	{
		Intent target = new Intent(this,to_class);
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

	

	@Override
	public void onBackPressed() {

	}

}
