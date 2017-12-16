package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class RegisterActivtiy extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText regis_et_ID = (EditText)findViewById(R.id.regis_et_ID);
        final EditText regis_et_password = (EditText)findViewById(R.id.regis_et_password);
        final EditText regis_et_name = (EditText)findViewById(R.id.regis_et_name);
        final EditText regis_et_age = (EditText)findViewById(R.id.regis_et_age);
        final EditText regis_et_gender = (EditText)findViewById(R.id.regis_et_gender);
        final Button regis_btn_regis = (Button)findViewById(R.id.regis_btn_regis);

        regis_btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userID = regis_et_ID.getText().toString();
                String userPassword = regis_et_password.getText().toString();
                String userName = regis_et_name.getText().toString();
                String userAge = regis_et_age.getText().toString();
                String userGender = regis_et_gender.getText().toString();

                Map <String,String> params = new HashMap<>();
                params.put("userID", userID);
                params.put("userPassword", userPassword);
                params.put("userName", userName);
                params.put("userAge", userAge);
                params.put("userGender", userGender);
                final Button IDcheckbutton=(Button)findViewById(R.id.IDcheckbutton);

                IDcheckbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* String userID = regis_et_ID.getText().toString();

                        Map <String,String> params = new HashMap<>();
                        params.put("userID", userID);

                        Response.Listener<String> responseListener  = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("exist");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivtiy.this);
                                        builder.setMessage("아이디가 존재합니다.")
                                                .setPositiveButton("확인", null)
                                                .create()
                                                .show();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivtiy.this);
                                        builder.setMessage("사용가능한 아이디입니다.")
                                                .setNegativeButton("확인", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.CHECKID, params, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(volleyRequest);
                    }
                });

<<<<<<< HEAD
               // Response.Listener<String> responseListener  = new Response.Listener<String>() {
*/
                Response.Listener<String> responseListener = new Response.Listener<String>() {
=======
                Response.Listener<String> responseListener  = new Response.Listener<String>() {
>>>>>>> parent of 04962e8... Merge branch 'csy' of https://github.com/csy9608/Android_Project into csy
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivtiy.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivtiy.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.SIGNUP, params, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(volleyRequest);

            }
        });
    }
}
