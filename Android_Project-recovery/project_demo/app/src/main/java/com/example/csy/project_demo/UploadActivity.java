package com.example.csy.project_demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by csy on 2017-11-18.
 */

public class UploadActivity extends AppCompatActivity {

    final private int IMG_REQUEST = 1;
    private ImageView upload_iv;
    private Button upload_btn_upload;
    private Bitmap bitmap;
    private BottomNavigationView bottomNavigationItemView;
    private Button upload_btn_add_tag;
    private Button upload_btn_reset_tag;
    private TextView upload_tv_tags;
    private EditText upload_et_tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        upload_iv = (ImageView) findViewById(R.id.upload_iv);
        upload_btn_upload = (Button) findViewById(R.id.upload_btn_upload);
        upload_btn_add_tag = (Button) findViewById(R.id.upload_btn_add_tag);
        upload_btn_reset_tag = (Button) findViewById(R.id.upload_btn_reset_tag);
        upload_tv_tags = (TextView) findViewById(R.id.upload_tv_tags);
        upload_et_tag = (EditText) findViewById(R.id.upload_et_tag);

        upload_btn_add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upload_et_tag.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요 !",Toast.LENGTH_LONG).show();
                }
                else{
                    upload_tv_tags.setText(upload_tv_tags.getText().toString()+ " #" + upload_et_tag.getText());
                    upload_et_tag.setText("");
                }
            }
        });

        upload_btn_reset_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_tv_tags.setText("");
            }
        });

        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.upload_btm_nav);
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


        upload_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        upload_btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tags = upload_tv_tags.toString();

                Response.Listener<String> listener  = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                int boardID = jsonResponse.getInt("boardID");
                                AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
                                builder.setMessage("이미지 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                upload_iv.setImageResource(0);
                                upload_iv.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                intent.putExtra("boardID", boardID);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
                                builder.setMessage("이미지 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Map<String, String> params = new HashMap<>();
                params.put("userID", CurrentInfo.GET(CurrentInfo.ID));
                params.put("temperature", CurrentInfo.GET(CurrentInfo.TEMPER));
                params.put("encoded_string", imageToString(bitmap));
                params.put("imageTags",tags);
                VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.UPLOAD,params, listener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(volleyRequest);
            }
        });
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!= null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                upload_iv.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
