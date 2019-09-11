package com.example.csy.project_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.TooManyListenersException;

/**
 * Created by csy on 2017-12-01.
 */

public class MainPageActivity extends AppCompatActivity {
    private ListView main_lv;
    private MainPageListAdapter adapter;
    private List<BoardItem> mMainPageItemList;
    private BottomNavigationView bottomNavigationItemView;
    public Handler mHandler;
//    public View ftView;
    public boolean isLoading = false;
    public int currentID = 0;
    final int limit = 5;
    Map<String,String > params = new HashMap<>();
    private ToggleButton main_btn_align_latest;
    private ToggleButton main_btn_align_likest;
    private boolean align_likes = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
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
        main_btn_align_latest = (ToggleButton) findViewById(R.id.main_btn_align_latest);
        main_btn_align_likest = (ToggleButton) findViewById(R.id.main_btn_align_likest);

        if(align_likes){
            main_btn_align_likest.setChecked(true);
            main_btn_align_latest.setChecked(false);
        }
        else{
            main_btn_align_latest.setChecked(true);
            main_btn_align_likest.setChecked(false);
        }

        main_btn_align_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(align_likes){
                    main_btn_align_likest.setChecked(false);
                    Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
            }
        });

        main_btn_align_likest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!align_likes){
                    main_btn_align_latest.setChecked(false);
                    Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                    intent.putExtra("align_likes",true);
                    startActivity(intent);
                }
            }
        });

        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.main_btm_nav);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_weather:
                        startActivity(new Intent(MainPageActivity.this, WeatherActivity.class));
                        break;
                    case R.id.action_main:
                        startActivity(new Intent(MainPageActivity.this, MainPageActivity.class));
                        break;
                    case R.id.action_my:
                        startActivity(new Intent(MainPageActivity.this, MyPageActivity.class));
                        break;
                    case R.id.action_search:
                        startActivity(new Intent(MainPageActivity.this, SearchPageActivity.class));
                        break;
                }
                return true;
            }
        });


        main_lv = (ListView) findViewById(R.id.main_lv);
        mMainPageItemList = new ArrayList<>();
//        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ftView = (View) li.inflate(R.layout.footer_view, null,false);
        mHandler = new MyHandler();

        // Init adapter
        adapter = new MainPageListAdapter(MainPageActivity.this, mMainPageItemList);
        main_lv.setAdapter(adapter);

        main_lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Do something , Ex: displat msg
                Intent intent = new Intent(MainPageActivity.this, DetailActivity.class);
                intent.putExtra("boardID", Integer.parseInt(view.getTag().toString()));
                startActivity(intent);
            }
        });

        main_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // check when scroll to last item
                if(view.getLastVisiblePosition() == totalItemCount-1 && isLoading == false){
                    isLoading = true;
                    Thread thread = new ThreadGetMoreData();
                    // Start Thread
                    thread.start();
                }
            }
        });
    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    // add loading view
                    //      IvProduct.addFooterView(ftView);
                    break;
                case 1:
                    // Update data adapter
                    adapter.addListItemToAdapter((ArrayList<BoardItem>)msg.obj);
                    // Remove loading view after update listview
                    //       IvProduct.removeFooterView(ftView);
                    isLoading=false;
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<BoardItem> getMoreData(){

        final ArrayList<BoardItem> lst = new ArrayList<>();
        // add list
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray boardIDs = jsonResponse.getJSONArray("boardID");
                    JSONArray imagePaths = jsonResponse.getJSONArray("imagePath");
                    JSONArray imageTagss = jsonResponse.getJSONArray("imageTags");
                    JSONArray boardLikes = jsonResponse.getJSONArray("boardLikes");
                    JSONArray liked = jsonResponse.getJSONArray("liked");
                    currentID = jsonResponse.getInt("end");

                    for(int i=0 ; i<boardIDs.length(); i++){
                        lst.add(new BoardItem(boardIDs.getInt(i), imagePaths.getString(i) ,boardLikes.getInt(i), imageTagss.getString(i), liked.getBoolean(i)));
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        params.put("start", Integer.toString(currentID));
        params.put("userID", CurrentInfo.GET(CurrentInfo.ID));
        params.put("temperature", CurrentInfo.GET(CurrentInfo.TEMPER));
        params.put("limit",Integer.toString(limit));

        if(align_likes){
            params.put("align_likes","true");
        }

        VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.MAINPAGE, params, listener);
        RequestQueue queue = Volley.newRequestQueue(MainPageActivity.this);
        queue.add(volleyRequest);

        return lst;
    }

    public  class ThreadGetMoreData extends Thread{
        @Override
        public void run() {
            // Add footer view after get data
            mHandler.sendEmptyMessage(0);
            ArrayList<BoardItem> lstResult = getMoreData();

            // Delay time to show loading footer when debug, remove it when releasing
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // send the result to Handle
            Message msg = mHandler.obtainMessage(1,lstResult);
            mHandler.sendMessage(msg);
        }
    }
}
