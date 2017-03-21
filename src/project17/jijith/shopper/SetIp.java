package project17.jijith.shopper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetIp extends Activity {
	Button button_save;
	EditText edittext_ip;
	public static String ip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_ip);
		setTitle("Server Configuration");

		button_save = (Button) findViewById(R.id.buttonlike);
		edittext_ip = (EditText) findViewById(R.id.editText1);
		edittext_ip.setText("192.168.43.40");

		button_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ip = edittext_ip.getText().toString();

				if (ip.equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(), "Enter IP address", Toast.LENGTH_SHORT).show();
				} else {
					String[] spl = ip.split("\\.");
					if (spl.length == 4) {
						// // globally saving the value of ip into ipad
						// SharedPreferences sh = PreferenceManager
						// .getDefaultSharedPreferences(getApplicationContext());
						// Editor ed = sh.edit();
						// ed.putString("ipad", ip);
						// ed.commit();

						General_Data.SERVER_IP_ADDRESS= ip;

						Intent i = new Intent(getApplicationContext(), Launch.class);
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "Invalid IP Configuration", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
}
