package com.example.todoapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.adapter.TaskListAdapter;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private DayScrollDatePicker dayDatePicker;
    private String SelectedDate;
    private View view_index;
    private ListView lv_task;
    private String CurrentDate;
    static ArrayList<Task> Tasks = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        lv_task=(ListView) this.findViewById(R.id.lstview_calendar);
        dayDatePicker = findViewById(R.id.dayDatePicker);
        dayDatePicker.setStartDate(1, 6, 2022);
        dayDatePicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                SelectedDate= sdf.format(date);
                synchronized (this){
                    readData(user, Tasks);
                }
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
                    final TaskListAdapter adapter = new TaskListAdapter(CalendarActivity.this, List);
                    lv_task.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Read data failed: " + databaseError.getMessage(),null );
            }
        });
    }
}

