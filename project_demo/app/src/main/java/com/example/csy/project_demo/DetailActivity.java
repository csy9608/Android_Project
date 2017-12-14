package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csy on 2017-12-05.
 */

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity{
    private ImageView detail_iv;
    private TextView detail_date_tv;
    private TextView detail_userID_tv;
    private TextView detail_like_tv;
    private ToggleButton detail_like_btn;
    private Button detail_modify_btn;
    private Button  detail_delete_btn;
    private TextView detail_tags_tv;
    private int boardID;
    private String imagePath;
    private String imageTags;
    private BottomNavigationView bottomNavigationItemView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        boardID = getIntent().getIntExtra("boardID",0);
        detail_iv = (ImageView) findViewById(R.id.detail_iv);
        detail_date_tv = (TextView) findViewById(R.id.detail_date_tv);
        detail_userID_tv = (TextView) findViewById(R.id.detail_userID_tv);
        detail_like_tv = (TextView) findViewById(R.id.detail_like_tv);
        detail_like_btn = (ToggleButton) findViewById(R.id.detail_like_btn);
        detail_modify_btn = (Button) findViewById(R.id.detail_modify_btn);
        detail_delete_btn = (Button) findViewById(R.id.detail_delete_btn);
        detail_tags_tv = (TextView) findViewById(R.id.detail_tags_tv);

        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.detail_btm_nav);
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

        detail_like_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Response.Listener<String> listener = new Response.Listener<String >() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                int boardLikes = jsonResponse.getInt("boardLikes");
                                detail_like_tv.setText(Integer.toString(boardLikes));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Map<String ,String > params = new HashMap<>();
                params.put("userID", CurrentInfo.GET(CurrentInfo.ID));
                params.put("boardID", Integer.toString(boardID));

                if(!isChecked){
                    params.put("undo", Boolean.toString(true));
                }

                VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.ULIKE, params, listener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(volleyRequest);
            }
        });


        detail_modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ModifyBoardActivity.class );
                intent.putExtra("boardID", boardID);
                intent.putExtra("imagePath",imagePath);
                intent.putExtra("imageTags",imageTags);
                startActivity(intent);
            }
        });

        detail_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("삭제에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("삭제에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Map params = new HashMap();
                params.put("boardID", Integer.toString(boardID));
                params.put("userID", CurrentInfo.GET(CurrentInfo.ID));
                params.put("delete", Boolean.toString(true));

                VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.MBOARD, params, listener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(volleyRequest);
            }
        });

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String userID = jsonResponse.getString("userID");
                    String boardDate = jsonResponse.getString("boardDate");
                    int boardLikes = jsonResponse.getInt("boardLikes");
                    imagePath = jsonResponse.getString("imagePath");
                    imageTags = jsonResponse.getString("imageTags");
                    boolean liked = jsonResponse.getBoolean("liked");

                    Picasso.with(getApplicationContext()).load(imagePath).into(detail_iv);

                    detail_date_tv.setText(boardDate);
                    detail_userID_tv.setText(userID);
                    detail_like_tv.setText(Integer.toString(boardLikes));
                    detail_tags_tv.setText(imageTags);

                    if(liked){
                        detail_like_btn.setChecked(true);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Map<String,String> params = new HashMap<>();
        params.put("userID",CurrentInfo.GET(CurrentInfo.ID));
        params.put("boardID", Integer.toString(boardID));
        VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.DETAIL, params, listener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(volleyRequest);
    }
}
