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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.adapter.TaskListAdapter;
import com.example.todoapp.dialog.AddTask_Dialog;
import com.example.todoapp.dialog.TaskComplete_Dialog;
import com.example.todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private View add;
    private View view_img;
    private TextView tv_what;
    private DayScrollDatePicker dayDatePicker;
    private String SelectedDate;
    private View view_index;
    private View view_note;
    private View view_user;
    private ListView lv_task;
    static ArrayList<Task> Tasks = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        lv_task=(ListView) this.findViewById(R.id.lstview_calendar);
        view_img = (View) this.findViewById(R.id.view_img);
        tv_what = (TextView) this.findViewById(R.id.tv_what);
        dayDatePicker = findViewById(R.id.dayDatePicker);
        view_note = (View) this.findViewById(R.id.view_note);
        view_user = (View) this.findViewById(R.id.view_user);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.SelectedDate = dateFormat.format(date).toString();

        synchronized (this){
            readData(user, Tasks);
        }

        dayDatePicker.setStartDate(1, 6, 2022);
        dayDatePicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                SelectedDate= sdf.format(date);
                view_img.setVisibility(View.VISIBLE);
                tv_what.setVisibility(View.VISIBLE);
                synchronized (this){
                    readData(user, Tasks);
                }
            }
        });

        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = Tasks.get(i);

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

        lv_task.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!Tasks.get(i).isCompleted()) {
                    FragmentManager fm = getSupportFragmentManager();
                    TaskComplete_Dialog dialog = new TaskComplete_Dialog();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", Tasks.get(i).getTitle());
                    dialog.setArguments(bundle);

                    dialog.show(fm, null);
                }

                return true;
            }
        });

        add = (View) this.findViewById(R.id.view_add);
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOpenDialogClicked();
            }
        });

        view_index = (View) findViewById(R.id.view_index);
        this.view_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
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
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }


    private void buttonOpenDialogClicked() {
        FragmentManager fm = getSupportFragmentManager();
        AddTask_Dialog dialog = new AddTask_Dialog();
        dialog.show(fm, null);
    }

    synchronized void readData (FirebaseUser user, ArrayList<Task> List) {
        Query dailyTask = FirebaseDatabase
                .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("tasks")
                .child(user.getUid()).orderByChild("endDate").equalTo(SelectedDate);
        dailyTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Task task = item.getValue(Task.class);
                    List.add(task);
                }
                if (List.size() > 0) {
                    view_img.setVisibility(View.INVISIBLE);
                    tv_what.setVisibility(View.INVISIBLE);
                    lv_task.setVisibility(View.VISIBLE);
                    final TaskListAdapter adapter = new TaskListAdapter(CalendarActivity.this, List);
                    lv_task.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    view_img.setVisibility(View.VISIBLE);
                    tv_what.setVisibility(View.VISIBLE);
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

