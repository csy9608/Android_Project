package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csy on 2017-12-05.
 */


public class MyPageActivity extends AppCompatActivity {

    private MyPageListAdapter adapter;
    private List<MyPageItem> myPageItemList;
    private BottomNavigationView bottomNavigationItemView;
    private FloatingActionButton floatingActionButton;
    private TextView mypage_userID_tv;
    private Button mypage_modifyInfo_btn;
    private ListView mypage_lv;
    public Handler mHandler;
    public View ftView;
    public boolean isLoading = false;
    public int currentID = 0;
    final int width = 3;
    final int limit = 15;
    Map<String, String> params = new HashMap<>();
    private boolean align_likes = false;
    private ToggleButton mypage_btn_align_latest;
    private ToggleButton mypage_btn_align_likest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        MenuView.ItemView tmp;
        tmp=(MenuView.ItemView) findViewById(R.id.action_my);
        tmp.setChecked(true);
        MenuView.ItemView tmp1;
        tmp1=(MenuView.ItemView) findViewById(R.id.action_weather);
        tmp1.setChecked(true);
        MenuView.ItemView tmp2;
        tmp2=(MenuView.ItemView) findViewById(R.id.action_main);
        tmp2.setChecked(true);
        MenuView.ItemView tmp3;
        tmp3=(MenuView.ItemView) findViewById(R.id.action_search);
        tmp3.setChecked(true);

        align_likes = getIntent().getBooleanExtra("align_likes",false);
        mypage_btn_align_latest = (ToggleButton) findViewById(R.id.mypage_btn_align_latest);
        mypage_btn_align_likest = (ToggleButton) findViewById(R.id.mypage_btn_align_likest);

        mypage_userID_tv = (TextView) findViewById(R.id.mypage_userID_tv);

        if(align_likes){
            mypage_btn_align_likest.setChecked(true);
            mypage_btn_align_latest.setChecked(false);
        }
        else{
            mypage_btn_align_latest.setChecked(true);
            mypage_btn_align_likest.setChecked(false);
        }

        mypage_btn_align_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(align_likes){
                    mypage_btn_align_likest.setChecked(false);
                    Intent intent = new Intent(MyPageActivity.this, MyPageActivity.class);
                    startActivity(intent);
                }
            }
        });

        mypage_btn_align_likest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!align_likes){
                    mypage_btn_align_latest.setChecked(false);
                    Intent intent = new Intent(MyPageActivity.this, MyPageActivity.class);
                    intent.putExtra("align_likes",true);
                    startActivity(intent);
                }
            }
        });

        mypage_userID_tv.setText(CurrentInfo.GET(CurrentInfo.ID).toString());

        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.mypage_btm_nav);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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

        floatingActionButton = (FloatingActionButton) findViewById(R.id.mypage_upload_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UploadActivity.class));
            }
        });

        mypage_userID_tv = (TextView) findViewById(R.id.mypage_userID_tv);
        mypage_modifyInfo_btn = (Button) findViewById(R.id.mypage_modifyInfo_btn);
        mypage_modifyInfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyInfoModifyActivity.class));
            }
        });

        mypage_lv = (ListView) findViewById(R.id.mypage_lv);

        myPageItemList = new ArrayList<>();
//        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ftView = (View) li.inflate(R.layout.footer_view, null,false);
        mHandler = new MyHandler();

        // Init adapter
        adapter = new MyPageListAdapter(getApplicationContext());
        mypage_lv.setAdapter(adapter);

        mypage_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // check when scroll to last item
                if (view.getLastVisiblePosition() == totalItemCount - 1 && isLoading == false) {
                    isLoading = true;
                    Thread thread = new ThreadGetMoreData();
                    // Start Thread
                    thread.start();
                }
            }
        });
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    // add loading view
                    //      IvProduct.addFooterView(ftView);
                    break;
                case 1:
                    // Update data adapter
                    adapter.addListItemToAdapter((ArrayList<MyPageItem>) msg.obj);
                    // Remove loading view after update listview
                    //       IvProduct.removeFooterView(ftView);
                    isLoading = false;
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<MyPageItem> getMoreData() {

        final ArrayList<MyPageItem> lst = new ArrayList<>();

        // add list
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray boardIDs = jsonResponse.getJSONArray("boardID");
                    JSONArray imagePaths = jsonResponse.getJSONArray("imagePath");
                    JSONArray boardLikes = jsonResponse.getJSONArray("boardLikes");
                    currentID = jsonResponse.getInt("end");

                    MyPageItem.MyPageSubItem[] sub_lst = new MyPageItem.MyPageSubItem[3];
                    for (int i = 0; i < boardIDs.length(); i++) {
                        sub_lst[i%3] = new MyPageItem.MyPageSubItem(boardIDs.getInt(i), imagePaths.getString(i), boardLikes.getInt(i));
                        if((i+1)%width==0 || i==(boardIDs.length()-1)){
                            lst.add(new MyPageItem(sub_lst));
                            sub_lst = new MyPageItem.MyPageSubItem[3];
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        params.put("userID",CurrentInfo.GET(CurrentInfo.ID));
        params.put("start", Integer.toString(currentID));
        params.put("limit", Integer.toString(limit));

        if(align_likes){
            params.put("align_likes","true");
        }
        VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.MYPAGE, params, listener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(volleyRequest);

        return lst;
    }

    public class ThreadGetMoreData extends Thread {
        @Override
        public void run() {
            // Add footer view after get data
            mHandler.sendEmptyMessage(0);
            ArrayList<MyPageItem> lstResult = getMoreData();

            // Delay time to show loading footer when debug, remove it when releasing
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // send the result to Handle
            Message msg = mHandler.obtainMessage(1, lstResult);
            mHandler.sendMessage(msg);
        }
    }
}
