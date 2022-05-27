package com.example.todoapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.model.Task;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends BaseAdapter {
    private ArrayList<Task> listData;
    private Context context;
    private LayoutInflater li;

    public TaskListAdapter(Context context, ArrayList<Task> listData) {
        this.context = context;
        this.listData = listData;
        this.li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listData!=null ? listData.size() : 0;
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
        ViewHolder holder;
        if (view == null) {
            view = li.inflate(R.layout.task_item_layout, viewGroup, false);

            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.tv_task_title);
            holder.time = (TextView) view.findViewById(R.id.tv_time);
            holder.priority = (TextView) view.findViewById(R.id.tv_priority);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Task model = listData.get(i);
        holder.title.setText(model.getTitle());
        holder.time.setText(model.getEndDate());
        holder.priority.setText(String.valueOf(model.getPriority()));

        return view;
    }

    public static class ViewHolder {
        private TextView title;
        private TextView time;
        private TextView priority;
    }
}