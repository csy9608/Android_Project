package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by GS43VR on 2017-11-30.
 */

public class WeatherActivity extends AppCompatActivity {

    ImageView weather_image;
    String path;
    private BottomNavigationView bottomNavigationItemView;
    private TextView weather_tv_tags;

    @Override
    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        setContentView(R.layout.activity_weather);

        TextView weather_text=(TextView)findViewById(R.id.weather_text);
        weather_image=(ImageView)findViewById(R.id.weather_image);
        weather_tv_tags = (TextView)findViewById(R.id.weather_tv_tags);
        path=CurrentInfo.GET(CurrentInfo.URL);
        String temp=CurrentInfo.GET(CurrentInfo.WEATHER).toString();
        weather_text.setText(temp);
        loadImageFromUrl(path);

        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.weather_btm_nav);
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
                    case R.id.action_search:
                        startActivity(new Intent(getApplicationContext(), SearchPageActivity.class));
                        break;
                }
                return true;
            }
        });

        Response.Listener<String> listener  = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String tags = jsonResponse.getString("tags");
                    System.out.println(tags);
                    weather_tv_tags.setText(tags);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Map<String, String> params = new HashMap<>();
        System.out.println("Weather " + CurrentInfo.GET(CurrentInfo.TEMPER));
        params.put("temperature", CurrentInfo.GET(CurrentInfo.TEMPER));
        VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.GETFREQTAGS,params, listener);
        RequestQueue queue = Volley.newRequestQueue(WeatherActivity.this);
        queue.add(volleyRequest);
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
    public void goMain(View v)
    {
        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
    }
}

