package project17.jijith.shopper;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class New_Bill extends Activity {

	ProgressDialog pd;
	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	private double item_price;
	ArrayList<String> item_list_array;
	ListView list_products, list_bill;
	ArrayList<Product_Modal> product_Modal_list;
	// private EditText name;
	// private EditText price;
	// private EditText quantity;
	Button view_bill;
	static Vector<Double> vector_total;
	public static Vector<String> vector_bill_items;
	private Vector<String> vector_items;
	int product_position;
	// Button add_item;
	ArrayAdapter<String> blank_adapter;
	public static ArrayAdapter<String> item_adapter;
	List<String> blank_array = new ArrayList<String>();
	private Button process_bill;
	// String listItem[]={"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC
	// Sense", "HTC Sensation XE"};

	public static String calculate_total() {
		// TODO Auto-generated method stub
		double total = 0;
		for (int i = 0; i < vector_total.size(); i++) {
			total = total + Double.parseDouble(vector_total.elementAt(i).toString());
		}
		return String.valueOf(total);
	}

	public void switch_activcity(Class to_class) {
		Intent target = new Intent(this, to_class);
		startActivity(target);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_bill);
		view_bill = (Button) findViewById(R.id.button_view);
		view_bill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch_activcity(View_Bill.class);

			}
		});
		
		process_bill = (Button) findViewById(R.id.button_bill);
		process_bill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				add_bill();

			}
		});
		
		vector_total = new Vector<>();
		vector_items = new Vector<>();
		vector_bill_items = new Vector<>();
		list_products = (ListView) findViewById(R.id.list2);
		list_bill = (ListView) findViewById(R.id.list_bill);

		// for (int i = 0; i < listItem.length; i++) {
		// blank_array.add(listItem[i]);
		// }
		blank_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
				blank_array);
		blank_adapter.add("");
		blank_adapter.add("Net Price : 0");
		list_bill.setAdapter(blank_adapter);

		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_products();

			}

		}).start();

	}
	
	public void add_bill() {
		pd = ProgressDialog.show(this, "", "Adding Owner...");
		new Thread(new Runnable() {
			public void run() {
				add_bill_thread();

			}

		}).start();
	}

	private void add_bill_thread() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://" + General_Data.SERVER_IP_ADDRESS + "/android-billing-server/android/register_bill.php");
			nvp = new ArrayList<NameValuePair>(3);
			nvp.add(new BasicNameValuePair("total_amount", calculate_total()));
			SharedPreferences settings = getApplicationContext().getSharedPreferences(General_Data.SHARED_PREFERENCE,
					Context.MODE_PRIVATE);
			nvp.add(new BasicNameValuePair("shop", settings.getString("shop_id", "0")));
			String item_amount="";
			for(int i=0;i<vector_items.size();i++)
			{
				item_amount=item_amount+vector_items.elementAt(i)+":"+vector_total.elementAt(i)+":"+"-";
			}
			nvp.add(new BasicNameValuePair("item_amount", item_amount));
			
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					 Toast.makeText(New_Bill.this,response,Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();
					if (response.equals("0")) {
						Toast.makeText(New_Bill.this, "Bill Registration successfully!", Toast.LENGTH_LONG)
								.show();

						finish();
					} else {
						Toast.makeText(New_Bill.this, "Bill Registration failure!", Toast.LENGTH_LONG).show();
					}
				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(New_Bill.this, "error" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					pd.dismiss();
				}
			});
		}
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
//					Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
					pd.dismiss();

					item_list_array = new ArrayList<String>();
					product_Modal_list = new ArrayList<>();
					try {

						JSONArray json = new JSONArray(response);
						for (int i = 0; i < json.length(); i++) {

							// Populate spinner with country names
							item_list_array.add(json.getJSONObject(i).getString("name"));

							Product_Modal sample_Product_Modal = new Product_Modal();
							sample_Product_Modal.setId(json.getJSONObject(i).getString("id"));
							sample_Product_Modal.setName(json.getJSONObject(i).getString("name"));
							sample_Product_Modal.setUnitprice(json.getJSONObject(i).getString("unit-price"));
							sample_Product_Modal.setStock(json.getJSONObject(i).getString("stock"));
							sample_Product_Modal.setMinimumstock(json.getJSONObject(i).getString("minimum-stock"));

							product_Modal_list.add(sample_Product_Modal);

						}
						item_adapter = new ArrayAdapter<String>(getApplicationContext(),
								android.R.layout.simple_list_item_1, item_list_array);
						list_products.setAdapter(item_adapter);
						list_products.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

								product_position = position;

								show_input_dialogue();
								// show_dialogue();
							}

							// private void show_dialogue() {
							// // TODO Auto-generated method stub
							// final Dialog dialog= new
							// Dialog(getBaseContext());
							// dialog.setContentView(R.layout.add_item);
							// String[] tittlearray ={"Mr.","Mrs.","Ms"};
							// Spinner tittleSpinner = (Spinner)
							// dialog.findViewById(R.id.Tittle);
							// ArrayAdapter<String> dataAdapter = new
							// ArrayAdapter<String>(this,
							// android.R.layout.simple_spinner_item,
							// tittlearray);
							//
							// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
							// tittleSpinner.setAdapter(dataAdapter);
							// dialog.show();
							//
							// }

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
					Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
							.show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}

	}

	private void show_input_dialogue() {
		// TODO Auto-generated method stub
		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(getBaseContext());
		View promptsView = li.inflate(R.layout.add_item, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(New_Bill.this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText item_name = (EditText) promptsView.findViewById(R.id.item_name);
		Button add_item = (Button)  promptsView.findViewById(R.id.button_add_item);

		EditText price = (EditText) promptsView. findViewById(R.id.item_p);
		final EditText quantity = (EditText) promptsView. findViewById(R.id.item_q);
		final TextView item_price_label = (TextView) promptsView. findViewById(R.id.textView_price);

		item_name.setText(product_Modal_list.get(product_position).getName());
		price.setText("Unit Price : " + product_Modal_list.get(product_position).getUnitprice());
		
		item_name.setEnabled(false);
		price.setEnabled(false);


		add_item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				item_price_label.setText(product_Modal_list.get(product_position).getName() + "x"
						+ quantity.getText().toString() + "  Price " + String.valueOf(calculate_item_price()));
				
			}

			private String calculate_item_price() {
				// TODO Auto-generated method stub
				item_price = Double.parseDouble(product_Modal_list.get(product_position).getUnitprice())
						* Double.parseDouble(quantity.getText().toString());
				return String.valueOf(item_price);
			}

		});

		// quantity.setKeyListener(new KeyListener() {
		//
		// @Override
		// public boolean onKeyUp(View view, Editable text, int keyCode,
		// KeyEvent event) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean onKeyOther(View view, Editable text, KeyEvent event) {
		// // TODO Auto-generated method stub
		// if(event.getKeyCode()==10)
		// {
		// name.setText(product_Modal_list.get(product_position).getName()+"
		// Price
		// "+String.valueOf(Double.parseDouble(product_Modal_list.get(product_position).getUnitprice())*Double.parseDouble(quantity.getText().toString())));
		// }
		// return false;
		// }
		//
		// @Override
		// public boolean onKeyDown(View view, Editable text, int keyCode,
		// KeyEvent event) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public int getInputType() {
		// // TODO Auto-generated method stub
		// return 0;
		// }
		//
		// @Override
		// public void clearMetaKeyState(View view, Editable content, int
		// states) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		
		// set dialog message
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			

			public void onClick(DialogInterface dialog, int id) {
				// get user input and set it to result
//				blank_adapter.add(item_price_label.getText().toString());
				vector_items.add(item_name.getText().toString());
				item_adapter.remove(item_name.getText().toString());
				vector_bill_items.add(item_price_label.getText().toString());
				vector_total.add(item_price);
				blank_adapter.clear();
				blank_adapter.add("");
				blank_adapter.add("Net Price : " + calculate_total());
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
}
