package com.example.androidexam;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentListAdapter extends BaseAdapter {
    Context context;
    int viewId;
    List<StudentVo> listData;

    public StudentListAdapter(Context context, int viewId, List<StudentVo> listData) {
        this.context = context;
        this.viewId = viewId;
        this.listData = listData;
    }


    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view ==null){
            view=View.inflate(context,viewId,null);
        }
        TextView textViewCell_Id= view.findViewById(R.id.textViewCell_Sid);
        TextView textViewCell_Sname= view.findViewById(R.id.textViewCell_Sname);

        TextView textViewCell_Sscore= view.findViewById(R.id.textViewCell_Sscore);

        textViewCell_Id.setText(listData.get(i).getSno());
        textViewCell_Sname.setText(listData.get(i).getSname());
        textViewCell_Sscore.setText(String.valueOf(listData.get(i).getScore()));
        return view;
    }
}
