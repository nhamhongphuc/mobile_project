package com.example.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.model.Task;

import java.util.ArrayList;

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
            holder.title = (TextView) view.findViewById(R.id.tv_note_title);
            holder.time = (TextView) view.findViewById(R.id.tv_time);
            holder.priority = (TextView) view.findViewById(R.id.tv_priority);
            holder.category = (TextView) view.findViewById(R.id.tv_category);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Task model = listData.get(i);
        holder.title.setText(model.getTitle());
        holder.time.setText(model.getEndDate());
        holder.priority.setText(String.valueOf(model.getPriority()));
        holder.category.setText(model.getCategory());
        if (model.getCategory().equals("Grocery")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grocery_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_grocery_xml));
        } else if (model.getCategory().equals("Work")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_work_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_work_xml));
        } else if (model.getCategory().equals("Sport")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sport_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_sport_xml));
        } else if (model.getCategory().equals("Design")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_design_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_design_xml));
        } else if (model.getCategory().equals("University")) {
            holder.category.setText("Uni");
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_uni_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_uni_xml));
        } else if (model.getCategory().equals("Social")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_social_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_social_xml));
        } else if (model.getCategory().equals("Music")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_music_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_music_xml));
        } else if (model.getCategory().equals("Health")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_health_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_health_xml));
        } else if (model.getCategory().equals("Movie")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_movie_xml));
        } else if (model.getCategory().equals("Home")) {
            holder.category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_lite,0,0,0);
            holder.category.setBackground(context.getResources().getDrawable(R.drawable.back_home_xml));
        }

        return view;
    }

    public static class ViewHolder {
        private TextView title;
        private TextView time;
        private TextView priority;
        private TextView category;
    }
}