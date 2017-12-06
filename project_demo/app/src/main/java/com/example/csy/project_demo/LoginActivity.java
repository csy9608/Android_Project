package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by csy on 2017-11-17.
 */

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText login_et_ID = (EditText)findViewById(R.id.login_et_ID);
        final EditText login_et_password = (EditText)findViewById(R.id.login_et_password);
        final Button login_btn_login = (Button)findViewById(R.id.login_btn_login);
        final TextView login_tv_regis = (TextView) findViewById(R.id.login_tv_regis);

        login_tv_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivtiy.class));
            }
        });
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                final String userID = login_et_ID.getText().toString();
                String userPassword = login_et_password.getText().toString();
                params.put("userID", userID);
                params.put("userPassword",userPassword);

                Response.Listener<String> responseListener  = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                CurrentInfo.SET(CurrentInfo.ID, userID);
                                startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.LOGIN, params, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(volleyRequest);
            }
        });
    }
}
