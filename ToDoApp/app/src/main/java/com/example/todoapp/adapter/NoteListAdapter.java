package com.example.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.model.Note;
import com.example.todoapp.model.Task;

import java.util.ArrayList;
import java.util.Collections;

public class NoteListAdapter extends BaseAdapter {
    private ArrayList<Note> listData;
    private Context context;
    private LayoutInflater li;

    public NoteListAdapter(Context context, ArrayList<Note> listData) {
        Collections.reverse(listData);
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
        NoteListAdapter.ViewHolder holder;
        if (view == null) {
            view = li.inflate(R.layout.note_item_layout, viewGroup, false);

            holder = new NoteListAdapter.ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.tv_note_title);
            holder.time = (TextView) view.findViewById(R.id.tv_time);
            holder.desc = (TextView) view.findViewById(R.id.tv_desc);
        }
        else {
            holder = (NoteListAdapter.ViewHolder) view.getTag();
        }
        Note model = listData.get(i);
        holder.title.setText(model.getTitle());
        holder.time.setText(model.getCreatedDate());
        holder.desc.setText(model.getDescription());
        return view;
    }

    public static class ViewHolder {
        private TextView title;
        private TextView time;
        private TextView desc;
    }
}
