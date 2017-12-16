package com.example.csy.project_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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

    private EditText regis_et_ID;
    private EditText regis_et_password;
    private EditText regis_et_name;
    private EditText regis_et_age;
    private EditText regis_et_gender;
    private Button regis_btn_regis;
    private Button regis_btn_check_id;
    private boolean id_check = false;
    private String userID;
    private RadioButton regis_radio_man;
    private RadioButton regis_radio_woman;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regis_et_ID = (EditText)findViewById(R.id.regis_et_ID);
        regis_et_password = (EditText)findViewById(R.id.regis_et_password);
        regis_et_name = (EditText)findViewById(R.id.regis_et_name);
        regis_et_age = (EditText)findViewById(R.id.regis_et_age);
        regis_et_gender = (EditText)findViewById(R.id.regis_et_gender);
        regis_btn_regis = (Button)findViewById(R.id.regis_btn_regis);
        regis_btn_check_id = (Button) findViewById(R.id.regis_btn_check_id);
        regis_radio_man = (RadioButton) findViewById(R.id.regis_radio_man);
        regis_radio_woman = (RadioButton) findViewById(R.id.regis_radio_woman);

        regis_btn_check_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = regis_et_ID.getText().toString();


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

               // Response.Listener<String> responseListener  = new Response.Listener<String>() {
*/
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean exits = jsonResponse.getBoolean("exits");
                            if (!exits) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivtiy.this);
                                builder.setMessage("사용 가능한 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                id_check = true;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivtiy.this);
                                builder.setMessage("이미 사용 중인 아이디 입니다.")
                                        .setNegativeButton("아이디 재입력", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                Map<String, String> params = new HashMap<>();
                params.put("userID", userID);
                VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.CHECKID, params, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(volleyRequest);
            }
        });


        regis_btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!userID.equals(regis_et_ID.getText().toString())){
                    id_check = false;
                }

                if (id_check == false) {
                    Toast.makeText(RegisterActivtiy.this,"아이디 중복확인을 해주세요 !", Toast.LENGTH_LONG).show();
                }
                else {
                    String userID = regis_et_ID.getText().toString();
                    String userPassword = regis_et_password.getText().toString();
                    String userName = regis_et_name.getText().toString();
                    String userAge = regis_et_age.getText().toString();
                    String userGender = null;

                    if(regis_radio_man.isChecked()){
                        userGender = "M";
                    }
                    else if(regis_radio_woman.isChecked()){
                        userGender = "W";
                    }

                    if(userID.isEmpty() || userPassword.isEmpty() || userName.isEmpty() || userAge.isEmpty() || userGender.isEmpty()){
                        Toast.makeText(RegisterActivtiy.this, "정보를 모두 입력해주세요 !", Toast.LENGTH_LONG).show();
                    }

                    else{
                        Map<String, String> params = new HashMap<>();
                        params.put("userID", userID);
                        params.put("userPassword", userPassword);
                        params.put("userName", userName);
                        params.put("userAge", userAge);
                        params.put("userGender", userGender);

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivtiy.this);
                                        builder.setMessage("회원 등록에 성공했습니다.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                    }
                                                })
                                                .create()
                                                .show();
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
                }
            }
        });
    }
}
