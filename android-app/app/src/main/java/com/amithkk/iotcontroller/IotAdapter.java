package com.amithkk.iotcontroller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Amith on 18-08-2017.
 */

public class IotAdapter extends RecyclerView.Adapter<IotAdapter.IotHolder> {

    public ArrayList<iotDevice> mList = new ArrayList<iotDevice>();
    RequestQueue requestQueue;
    Context c;

    public IotAdapter(ArrayList<iotDevice> mList, Context c) {
        this.mList = mList;
        this.c = c;
        requestQueue = Volley.newRequestQueue(c);
    }

    public void httplight(boolean state, String ip) {

        String mState = state?"1":"0";
        String url = "http://"+ip+"/digital/5/" + state;
        // String url = "http://www.google.com";
        Toast.makeText(c,"Light Fn",Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c, "Done", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //add request to queue
        requestQueue.add(stringRequest);
        //Toast.makeText(MainActivity.this, "Command Executed", Toast.LENGTH_SHORT).show();
    }
    public void httplock(boolean state, String ip) {
        String lstate;
        if(state){
            lstate ="lock";
        }else{
            lstate="unlock";
        }
        Toast.makeText(c,"Lock Fn",Toast.LENGTH_SHORT).show();
        String url = "http://"+ip+"/"+lstate;
        // String url = "http://www.google.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c, "Done", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //add request to queue
        requestQueue.add(stringRequest);
        //Toast.makeText(MainActivity.this, "Command Executed", Toast.LENGTH_SHORT).show();
    }


    @Override
    public IotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.device_child, parent, false);
        return(new IotHolder(v));
    }

    @Override
    public void onBindViewHolder(IotHolder holder, final int position) {
            Log.i("S","OnBind:"+mList.get(position).type);
            if(mList.get(position).type.equals("Bulb")) {
                holder.mDeviceImage.setImageResource(R.drawable.ic_lightbulb_outline_black_24dp);
                holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            httplight(isChecked,mList.get(position).ipAddr);
                    }
                });
            }
            else {
                holder.mDeviceImage.setImageResource(R.drawable.ic_lock);
                holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            httplock(isChecked,mList.get(position).ipAddr);
                    }
                });
            }
            holder.mDeviceName.setText(mList.get(position).mDnsName);



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class IotHolder extends RecyclerView.ViewHolder {
        private ImageView mDeviceImage;
        private TextView mDeviceName;
        private Switch sw;

        //4
        public IotHolder(View v) {
            super(v);

            mDeviceImage = (ImageView) v.findViewById(R.id.typeImg);
            mDeviceName = (TextView) v.findViewById(R.id.name);
            sw = (Switch) v.findViewById(R.id.Switch);

        }

        //5
//        @Override
//        public void onClick(View v) {
//            Log.d("RecyclerView", "CLICK!");
//        }

    }
}
