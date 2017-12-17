package com.example.csy.project_demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by csy on 2017-11-18.
 */

public class ModifyBoardActivity extends AppCompatActivity {

    final private int IMG_REQUEST = 1;

    private Bitmap bitmap;
    private BottomNavigationView bottomNavigationItemView;
    private int boardID;
    private String imagePath;
    private String imageTags;
    private boolean check=false;
    private Button modify_btn_reset_tag;
    private Button modify_btn_add_tag;
    private EditText modfiy_et_tag;
    private TextView modify_tv_tags;
    private ImageView modify_iv;
    private Button modify_btn_modify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_board);
        Intent intent=getIntent();

        modify_btn_reset_tag = (Button)findViewById(R.id.modify_btn_reset_tag);
        modify_btn_add_tag = (Button)findViewById(R.id.modify_btn_add_tag);
        modfiy_et_tag = (EditText)findViewById(R.id.modfiy_et_tag);
        modify_tv_tags = (TextView)findViewById(R.id.modify_tv_tags);
        modify_iv = (ImageView)findViewById(R.id.modify_iv);
        modify_btn_modify = (Button)findViewById(R.id.modify_btn_modify);

        boardID=intent.getIntExtra("boardID",0);
        imagePath=intent.getStringExtra("imagePath");
        imageTags=intent.getStringExtra("imageTags");

        Picasso.with(getApplicationContext()).load(imagePath).into(modify_iv);
        modify_tv_tags.setText(imageTags);

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
                }
                return true;
            }
        });

        modify_btn_add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modfiy_et_tag.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요 !",Toast.LENGTH_LONG).show();
                }
                else{
                    modify_tv_tags.setText(modify_tv_tags.getText().toString()+ " #" + modfiy_et_tag.getText());
                    modfiy_et_tag.setText("");
                }
            }
        });

        modify_btn_reset_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify_tv_tags.setText("");
            }
        });

        modify_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        modify_btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tags = modify_tv_tags.getText().toString();
                if (tags.equals("")) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_LONG).show();
                }
                else {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyBoardActivity.this);
                                builder.setMessage("이미지 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                modify_iv.setImageResource(0);
                                modify_iv.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                intent.putExtra("boardID", boardID);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyBoardActivity.this);
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
                params.put("boardID", Integer.toString(boardID));
                params.put("imageTags", tags);
                if (check)
                    params.put("encoded_string", imageToString(bitmap));
                VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.MBOARD, params, listener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(volleyRequest);
            }
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
                modify_iv.setImageBitmap(bitmap);
                check = true;
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
