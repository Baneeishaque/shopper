package project17.jijith.shopper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Introduction4 extends Activity {

    ImageView intro_imageView;
	FileOutputStream fos;

	int s = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction4);
        intro_imageView=(ImageView) findViewById(R.id.intro_imageView);
        intro_imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            /*public void onSwipeTop() {
                //Toast.makeText(Introduction1.this, "top", Toast.LENGTH_SHORT).show();
            }*/
            public void onSwipeRight() {
                //Toast.makeText(Introduction2.this, "right", Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Introduction4.this, Introduction3.class);
                startActivity(target);
                finish();

            }
            public void onSwipeLeft() {
            	String FILE = "ini";

        		try {

        			fos = openFileOutput(FILE, Context.MODE_PRIVATE);
        			try {
        				fos.write(s);
        				fos.close();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        		} catch (FileNotFoundException e) {
        		}
        		Intent target = new Intent(Introduction4.this, Login.class);
        		startActivity(target);
                finish();
            }
            /*public void onSwipeBottom() {
                //Toast.makeText(Introduction1.this, "bottom", Toast.LENGTH_SHORT).show();
            }*/

        });
    }

}
