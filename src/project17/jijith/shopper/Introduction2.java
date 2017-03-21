package project17.jijith.shopper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Introduction2 extends Activity {

    ImageView intro_imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction2);
        intro_imageView=(ImageView) findViewById(R.id.intro_imageView);
        intro_imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            /*public void onSwipeTop() {
                //Toast.makeText(Introduction1.this, "top", Toast.LENGTH_SHORT).show();
            }*/
            public void onSwipeRight() {
                //Toast.makeText(Introduction2.this, "right", Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Introduction2.this, Introduction1.class);
                startActivity(target);
                finish();

            }
            public void onSwipeLeft() {
                //Toast.makeText(Introduction2.this, "left", Toast.LENGTH_SHORT).show();
                Intent target = new Intent(Introduction2.this, Introduction3.class);
                startActivity(target);
                finish();
            }
            /*public void onSwipeBottom() {
                //Toast.makeText(Introduction1.this, "bottom", Toast.LENGTH_SHORT).show();
            }*/

        });
    }

}
