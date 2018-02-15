package com.example.android.gridview.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import com.example.android.gridview.R;

import static com.example.android.gridview.R.id.grid;

public class SplashScreenActivity extends Activity {

    private static final int SPLASH_TIME_OUT = 1500; // splash ce biti vidljiv minimum SPLASH_TIME_OUT milisekundi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // uradi inicijalizaciju u pozadinksom threadu
        new InitTask().execute();
    }

    private class InitTask extends AsyncTask<Void, Void, Void>
    {
        private long startTime;

        @Override
        protected void onPreExecute()
        {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            continueLogin();
            return null;
        }

        private void continueLogin()
        {
            // sacekaj tako da splash bude vidljiv minimum SPLASH_TIME_OUT milisekundi
            long timeLeft = SPLASH_TIME_OUT - (System.currentTimeMillis() - startTime);
            if(timeLeft < 0) timeLeft = 0;
            SystemClock.sleep(timeLeft);

            // uloguj se
            login();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_splash_screen);
    }


    private void login()
    {
        startMainActivity();
    }
    private void startMainActivity()
    {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish(); // da nebi mogao da ode back na splash
    }
}
