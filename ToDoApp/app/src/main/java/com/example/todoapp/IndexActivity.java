package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private View add;
    private View view_img;
    private TextView tv_what;
    private TextView tv_add;
    private EditText search_bar;
    private ListView lv_task;
    static ArrayList<Task> Tasks = new ArrayList<>();
    ArrayList<Task> tmp_Tasks = new ArrayList<>();

    private View view_calender;
    private View view_note;
    private View view_user;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        view_img = (View) this.findViewById(R.id.view_img);
        tv_what = (TextView) this.findViewById(R.id.tv_what);
        tv_add = (TextView) this.findViewById(R.id.tv_add);;
        search_bar = (EditText) this.findViewById(R.id.search_bar);
        lv_task = (ListView) this.findViewById(R.id.lv_note);
        view_calender = (View) this.findViewById(R.id.view_calender);
        view_note = (View) this.findViewById(R.id.view_note);
        view_user = (View) this.findViewById(R.id.view_user);
        synchronized (this){
            readData(user, Tasks);
        }


        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = Tasks.get(i);
                if (!search_bar.getText().toString().equals("")) {
                    task = tmp_Tasks.get(i);
                }

                Intent intent = new Intent(getApplicationContext(), TaskScreenActivity.class);
                intent.putExtra("title", task.getTitle());
                intent.putExtra("desc", task.getDescription());
                intent.putExtra("priority", task.getPriority());
                intent.putExtra("comp", task.isCompleted());
                intent.putExtra("start", task.getStartDate());
                intent.putExtra("end", task.getEndDate());
                intent.putExtra("category", task.getCategory());
                startActivity(intent);
            }
        });

        add = (View) this.findViewById(R.id.view_add);
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOpenDialogClicked();
            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tmp_Tasks.clear();
                for (int i = 0; i < Tasks.size(); i++) {
                    if (Tasks.get(i).getTitle().contains(editable.toString())) {
                        tmp_Tasks.add(Tasks.get(i));
                    }
                }
                final TaskListAdapter adapter = new TaskListAdapter(IndexActivity.this, tmp_Tasks);
                lv_task.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        view_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        view_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent);
            }
        });
        view_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonOpenDialogClicked() {
        FragmentManager fm = getSupportFragmentManager();
        AddTask_Dialog dialog = new AddTask_Dialog();
        dialog.show(fm, null);
    }

    synchronized void readData(FirebaseUser user, ArrayList<Task> List) {
        Query allTask = FirebaseDatabase
                .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("tasks")
                .child(user.getUid());

        allTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tasks.clear();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Task task = item.getValue(Task.class);
                    List.add(task);
                }

                if (List.size() > 0) {
                    view_img.setVisibility(View.INVISIBLE);
                    tv_what.setVisibility(View.INVISIBLE);
                    tv_add.setVisibility(View.INVISIBLE);
                    search_bar.setVisibility(View.VISIBLE);
                    lv_task.setVisibility(View.VISIBLE);

                    for (int i = 0; i < List.size() - 1 ; i++) {
                        for (int j = i+1; j <List.size(); j++) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                Date pre_Date = sdf.parse(List.get(i).getEndDate());
                                Date af_Date = sdf.parse(List.get(j).getEndDate());
                                int check = pre_Date.compareTo(af_Date);
                                if (check<0) {
                                    Collections.swap(List, i, j);
                                } else if (check == 0) {
                                    if (List.get(i).getPriority() < List.get(j).getPriority()) {
                                        Collections.swap(List, i, j);
                                    }
                                }
                            } catch (java.text.ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    if (search_bar.getText().toString().equals("")) {
                        final TaskListAdapter adapter = new TaskListAdapter(IndexActivity.this, List);
                        lv_task.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        String tmp = search_bar.getText().toString();
                        tmp_Tasks.clear();
                        for (int i = 0; i < Tasks.size(); i++) {
                            if (Tasks.get(i).getTitle().contains(tmp)) {
                                tmp_Tasks.add(Tasks.get(i));
                            }
                        }
                        final TaskListAdapter adapter = new TaskListAdapter(IndexActivity.this, tmp_Tasks);
                        lv_task.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }
                else {
                    view_img.setVisibility(View.VISIBLE);
                    tv_what.setVisibility(View.VISIBLE);
                    tv_add.setVisibility(View.VISIBLE);
                    search_bar.setVisibility(View.INVISIBLE);
                    lv_task.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Read data failed: " + databaseError.getMessage(),null );
            }
        });
    }

}
