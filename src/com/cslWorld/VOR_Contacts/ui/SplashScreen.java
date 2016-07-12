package com.cslWorld.VOR_Contacts.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.cslWorld.VOR_Contacts.R;
import android.os.Handler;

public class SplashScreen extends Activity {

    private static int SPLASH_TIMEOUT = 3000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this , ContactListActivity.class);
                startActivity(i);
                finish();

            }
        },SPLASH_TIMEOUT);


    }


}
