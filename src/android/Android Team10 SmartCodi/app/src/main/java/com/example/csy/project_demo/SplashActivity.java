package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by csy on 2017-12-01.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }, 3000); // start intent after 3000 milliseconds
    }
    public void gonext(View v)
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
