package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.adapter.TaskListAdapter;
import com.example.todoapp.dialog.AddTask_Dialog;
import com.example.todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private View add;

    public ArrayList<Task> Tasks;
    private View view_img;
    private TextView tv_what;
    private TextView tv_add;
    private EditText search_bar;
    private ListView lv_task;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        view_img = (View) this.findViewById(R.id.view_img);
        tv_what = (TextView) this.findViewById(R.id.tv_what);
        tv_add = (TextView) this.findViewById(R.id.tv_add);;
        search_bar = (EditText) this.findViewById(R.id.search_bar);
        lv_task = (ListView) this.findViewById(R.id.lv_task);

        TaskListAdapter adapter = new TaskListAdapter(this, Tasks);
        lv_task.setAdapter(adapter);
        Query allTask = FirebaseDatabase
                .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference()
                .child("tasks").child(user.getUid());
        Tasks = null;
        allTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tasks = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Task task = item.getValue(Task.class);
                    Tasks.add(task);
                }
                if (Tasks.size() > 0) {
                    view_img.setVisibility(View.INVISIBLE);
                    tv_what.setVisibility(View.INVISIBLE);
                    tv_add.setVisibility(View.INVISIBLE);
                    search_bar.setVisibility(View.VISIBLE);
                    lv_task.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        add = (View) this.findViewById(R.id.view_add);
        Tasks = null;
      
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOpenDialogClicked();
            }
        });
    }

    private void buttonOpenDialogClicked() {
        FragmentManager fm = getSupportFragmentManager();
        AddTask_Dialog dialog = new AddTask_Dialog();
        dialog.show(fm, null);
    }

//    private ArrayList<Task> getListData() {
//
//    }
}
