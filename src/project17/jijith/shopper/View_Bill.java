package project17.jijith.shopper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class View_Bill extends Activity {

	private ListView list_bill_items;
	private int product_position;
	private ArrayAdapter<String> bill_items_adaptor;
	String old_item_price;
	Double item_price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_bill);
		list_bill_items = (ListView) findViewById(R.id.list);
		bill_items_adaptor = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
				New_Bill.vector_bill_items);
		list_bill_items.setAdapter(bill_items_adaptor);
		bill_items_adaptor.add("");
		bill_items_adaptor.add("Net Price : " + New_Bill.calculate_total());
		list_bill_items.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				show_item_dialogue();
				product_position = position;
				// Intent i = new Intent(getApplicationContext(),
				// Product_View.class);
				// i.putExtra("pid", product_Modal_list.get(position).getId());
				// i.putExtra("pname",
				// product_Modal_list.get(position).getName());
				// i.putExtra("pprice",
				// product_Modal_list.get(position).getUnitprice());
				// i.putExtra("pstock",
				// product_Modal_list.get(position).getStock());
				// i.putExtra("mstock",
				// product_Modal_list.get(position).getMinimumstock());
				//
				// startActivity(i);
			}

			private void show_item_dialogue() {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				// get prompts.xml view
				LayoutInflater li = LayoutInflater.from(getBaseContext());
				View promptsView = li.inflate(R.layout.manipulate_item, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(View_Bill.this);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText item_name = (EditText) promptsView.findViewById(R.id.item_name);
				final EditText quantity = (EditText) promptsView.findViewById(R.id.item_q);
				Button add_item = (Button) promptsView.findViewById(R.id.button_add_item);
				Button delete_item = (Button) promptsView.findViewById(R.id.button_delete_item);

				final TextView item_price_label = (TextView) promptsView.findViewById(R.id.textView_price);

				item_name.setText(list_bill_items.getItemAtPosition(product_position).toString());

				item_name.setEnabled(false);

				add_item.setOnClickListener(new OnClickListener() {

					String unit_price, old_quantity;

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String name = item_name.getText().toString().substring(0,
								item_name.getText().toString().indexOf("x"));
						item_price_label.setText(name + "x" + quantity.getText().toString() + "  Price "
								+ String.valueOf(calculate_item_price()));

					}

					private String calculate_item_price() {
						// TODO Auto-generated method stub
						old_quantity = item_name.getText().toString()
								.substring(item_name.getText().toString().indexOf("x") + 1);
						old_quantity = old_quantity.substring(0, old_quantity.indexOf(" "));
						old_item_price = item_name.getText().toString()
								.substring(item_name.getText().toString().indexOf("Price ") + 6);
						unit_price = String
								.valueOf(Double.parseDouble(old_item_price) / Double.parseDouble(old_quantity));
						item_price = Double.parseDouble(unit_price) * Double.parseDouble(quantity.getText().toString());
						return String.valueOf(item_price);
					}

				});

				// set dialog message
				alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// get user input and set it to result
						// blank_adapter.add(item_price_label.getText().toString());
						// item_adapter.remove(item_name.getText().toString());
						// vector_bill_items.add(item_price_label.getText().toString());
						// vector_total.add(item_price);
						// blank_adapter.clear();
						// blank_adapter.add("");
						// blank_adapter.add("Net Price : " +
						// calculate_total());
						// dialog.cancel();
						New_Bill.vector_bill_items
								.remove(list_bill_items.getItemAtPosition(product_position).toString());
						New_Bill.vector_bill_items.add(item_price_label.getText().toString());
						New_Bill.vector_total.remove(old_item_price);
						New_Bill.vector_total.add(item_price);
						switch_activcity(View_Bill.class);
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
		});
	}
	public void switch_activcity(Class to_class) {
		Intent target = new Intent(this, to_class);
		startActivity(target);
		finish();

	}


}
