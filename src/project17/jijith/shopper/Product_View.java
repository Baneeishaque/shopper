package project17.jijith.shopper;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Product_View extends Activity {

	TextView pname, pprice, pstock, mstock;

	Button button_add_stock;
	ProgressDialog pd;

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_view);
		pname = (TextView) findViewById(R.id.pname);
		pprice = (TextView) findViewById(R.id.pprice);
		pstock = (TextView) findViewById(R.id.pstock);
		mstock = (TextView) findViewById(R.id.mstock);
		button_add_stock=(Button) findViewById(R.id.button_add_stock);
		
		button_add_stock.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Add_Stock.class);
                i.putExtra("pid", getIntent().getStringExtra("pid"));
                i.putExtra("pname", getIntent().getStringExtra("pname"));
                i.putExtra("pprice", "Price : "+getIntent().getStringExtra("pprice"));
                i.putExtra("pstock", getIntent().getStringExtra("pstock"));
                i.putExtra("mstock", "Minimum Stock : "+getIntent().getStringExtra("mstock"));
                
                startActivity(i);
				
			}
		});

		pname.setText(getIntent().getStringExtra("pname"));
		pprice.setText("Price : "+getIntent().getStringExtra("pprice"));
		pstock.setText("Current Stock : "+getIntent().getStringExtra("pstock"));

		mstock.setText("Minimum Stock : "+getIntent().getStringExtra("mstock"));
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

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

}