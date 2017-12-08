package com.example.csy.project_demo;

import android.content.Context;
import android.content.Intent;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static java.util.Objects.isNull;

/**
 * Created by csy on 2017-12-08.
 */

public class MyPageListAdapter extends BaseAdapter{
    private Context mContext;
    private List<MyPageItem> mMyPageItemList;

    public MyPageListAdapter(Context mContext, List<MyPageItem> mMyPageItem) {
        this.mContext = mContext;
        this.mMyPageItemList = mMyPageItem;
    }

    public MyPageListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mMyPageItemList = new ArrayList<>();
    }

    public void addListItemToAdapter(List<MyPageItem> list){
        mMyPageItemList.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMyPageItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMyPageItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // make view

        final View v = View.inflate(mContext, R.layout.item_mypage_list, null);
        final ImageView[] myitm_iv = new ImageView[3];
        myitm_iv[0] = (ImageView) v.findViewById(R.id.myitm_iv1);
        myitm_iv[1] = (ImageView) v.findViewById(R.id.myitm_iv2);
        myitm_iv[2] = (ImageView) v.findViewById(R.id.myitm_iv3);

        for(int i=0 ; i< myitm_iv.length ; i++){
                Picasso.with(mContext).load(mMyPageItemList.get(position).getMyPageSubItem(i).getImagePaths()).into(myitm_iv[i]);

                final String tag = Integer.toString(mMyPageItemList.get(position).getMyPageSubItem(i).getBoardID());
                myitm_iv[i].setTag(tag);
                myitm_iv[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("boardID", Integer.parseInt(tag));
                        mContext.startActivity(intent);
                    }
                });
        }

        return v;
    }

}
