package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by csy on 2017-12-05.
 */


public class MyPageActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationItemView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.mypage_btm_nav);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_weather:
                        startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
                        break;
                    case R.id.action_main:
                        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                        break;
                    case R.id.action_my:
                        startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
                        break;
                }
                return true;
            }
        });

    }
}
