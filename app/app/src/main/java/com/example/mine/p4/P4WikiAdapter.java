package com.example.mine.p4;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mine.R;
import com.example.mine.mineinfo.MineInfo;

import java.util.List;

public class P4WikiAdapter extends ArrayAdapter<Pair<String,String>> {
    private int resourceId;
    public P4WikiAdapter(Context context, int textViewResourceId, List<Pair<String,String>> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        Pair<String,String> thepair=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView p4key=(TextView) view.findViewById(R.id.p4key);
        TextView p4value=(TextView) view.findViewById(R.id.p4value);
        p4key.setText(thepair.first);
        p4value.setText(thepair.second);
        return  view;
    }
}
