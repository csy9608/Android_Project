package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


/**
 * Created by GS43VR on 2017-11-30.
 */

public class WeatherActivity extends AppCompatActivity {

    ImageView weather_image;
    String path;
    private BottomNavigationView bottomNavigationItemView;


    @Override
    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        setContentView(R.layout.activity_weather);


       // Intent intent=getIntent();

        TextView weather_text=(TextView)findViewById(R.id.weather_text);
        weather_image=(ImageView)findViewById(R.id.weather_image);
        path=CurrentInfo.GET(CurrentInfo.URL);
        String temp=CurrentInfo.GET(CurrentInfo.WEATHER).toString();
        weather_text.setText(temp);
        loadImageFromUrl(path);

        Toast.makeText(getApplicationContext(),CurrentInfo.GET(CurrentInfo.ID),Toast.LENGTH_LONG).show();
/*
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                goNext();
            }
        }, 5000);*/

        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.weather_btm_nav);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_weather:
                        startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
                        break;
                    //weather activity 재진입시 에러발생.
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

    private void loadImageFromUrl(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(weather_image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    public void goNext(View v)
    {
        startActivity(new Intent(WeatherActivity.this, MainPageActivity.class));

    }
}
