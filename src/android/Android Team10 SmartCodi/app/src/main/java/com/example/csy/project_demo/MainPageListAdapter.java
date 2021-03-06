package com.example.csy.project_demo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by csy on 2017-12-04.
 */

public class MainPageListAdapter extends BaseAdapter{
    private Context mContext;
    private List<BoardItem> mMainPageItemList;

    public MainPageListAdapter(Context mContext, List<BoardItem> mBoardItem) {
        this.mContext = mContext;
        this.mMainPageItemList = mBoardItem;
    }

    public void addListItemToAdapter(List<BoardItem> list){
        mMainPageItemList.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMainPageItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMainPageItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    // make view

        final View v = View.inflate(mContext, R.layout.item_main_page_list, null);
        final TextView mitm_like_tv = (TextView) v.findViewById(R.id.mitm_like_tv);
        final TextView mitm_tags_tv = (TextView) v.findViewById(R.id.mitm_tags_tv);
        final ImageView mitm_iv = (ImageView) v.findViewById(R.id.mitm_iv);
        final ToggleButton mitm_like_btn = (ToggleButton) v.findViewById(R.id.mitm_like_btn);

        mitm_like_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                                Log.i("Main", "MainPage - " + "success");
                                mMainPageItemList.get(position).setBoardLikes(boardLikes);
                                mitm_like_tv.setText(String.valueOf(mMainPageItemList.get(position).getBoardLikes()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Map<String ,String > params = new HashMap<>();
                params.put("boardID", Integer.toString(mMainPageItemList.get(position).getBoardID()));
                params.put("userID", CurrentInfo.GET(CurrentInfo.ID));

                if(!isChecked){
                    params.put("undo", Boolean.toString(true));
                }

                VolleyRequest volleyRequest = new VolleyRequest(VolleyRequest.MODE.ULIKE, params, listener);
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(volleyRequest);
            }
        });

        mitm_like_tv.setText(String.valueOf(mMainPageItemList.get(position).getBoardLikes()));
        mitm_tags_tv.setText(mMainPageItemList.get(position).getImageTags());
        Picasso.with(mContext).load(mMainPageItemList.get(position).getImagePath()).into(mitm_iv);

        if(mMainPageItemList.get(position).getLiked()){
            mitm_like_btn.setChecked(true);
        }

        // save product id
        v.setTag(mMainPageItemList.get(position).getBoardID());

        return v;
    }

    public void setmMainPageItemList(List<BoardItem> mMainPageItemList) {
        this.mMainPageItemList = mMainPageItemList;
    }
}
