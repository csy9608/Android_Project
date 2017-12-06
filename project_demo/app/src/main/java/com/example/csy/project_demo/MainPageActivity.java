package com.example.csy.project_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
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
 * Created by csy on 2017-12-01.
 */

import static android.content.ContentValues.TAG;

public class MainPageActivity extends AppCompatActivity {
    private ListView main_lv;
    private MainPageItemListAdapter adapter;
    private List<MainPageItem> mMainPageItemList;
    private BottomNavigationView bottomNavigationItemView;
    public Handler mHandler;
//    public View ftView;
    public boolean isLoading = false;
    public int currentID = 0;
    final int limit = 5;
    Map<String,String > params = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.main_btm_nav);
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


        main_lv = (ListView) findViewById(R.id.main_lv);
        mMainPageItemList = new ArrayList<>();
//        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ftView = (View) li.inflate(R.layout.footer_view, null,false);
        mHandler = new MyHandler();

        // Init adapter
        adapter = new MainPageItemListAdapter(MainPageActivity.this, mMainPageItemList);
        main_lv.setAdapter(adapter);

        main_lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Do something , Ex: displat msg
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
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
                    adapter.addListItemToAdapter((ArrayList<MainPageItem>)msg.obj);
                    // Remove loading view after update listview
                    //       IvProduct.removeFooterView(ftView);
                    isLoading=false;
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<MainPageItem> getMoreData(){

        final ArrayList<MainPageItem> lst = new ArrayList<>();
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
                    currentID = jsonResponse.getInt("end");

                    for(int i=0 ; i<boardIDs.length(); i++){
                        lst.add(new MainPageItem(boardIDs.getInt(i), imagePaths.getString(i) ,boardLikes.getInt(i), imageTagss.getString(i)));
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        params.put("start", Integer.toString(currentID));
        params.put("temperature", CurrentInfo.GET(CurrentInfo.TEMPER));
        params.put("limit",Integer.toString(limit));
        VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.MAINPAGE, params, listener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(volleyRequest);

        return lst;
    }

    public  class ThreadGetMoreData extends Thread{
        @Override
        public void run() {
            // Add footer view after get data
            mHandler.sendEmptyMessage(0);
            ArrayList<MainPageItem> lstResult = getMoreData();

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
