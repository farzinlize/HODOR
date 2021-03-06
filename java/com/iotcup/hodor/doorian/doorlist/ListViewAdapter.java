package com.iotcup.hodor.doorian.doorlist;

import static com.iotcup.hodor.doorian.Constants.FIRST_COLUMN;
import static com.iotcup.hodor.doorian.Constants.SECOND_COLUMN;
import static com.iotcup.hodor.doorian.Constants.THIRD_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iotcup.hodor.doorian.R;

public class ListViewAdapter extends BaseAdapter{

    private ArrayList<HashMap<String, String>> list;
    private Activity activity;
    private TextView txtFirst;
    private TextView txtSecond;
    private TextView txtThird;

    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater=activity.getLayoutInflater();
        if(convertView == null){
            convertView=inflater.inflate(R.layout.colmn_row, null);
            txtFirst=(TextView) convertView.findViewById(R.id.door_name);
            txtSecond=(TextView) convertView.findViewById(R.id.door_staus);
            txtThird=(TextView) convertView.findViewById(R.id.last_activity);
        }
        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        return convertView;
    }

}