package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    private DayScrollDatePicker dayDatePicker;
    private String SelectedDate;
    private View view_index;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        dayDatePicker = findViewById(R.id.dayDatePicker);
        dayDatePicker.setStartDate(12,5,2022);
        dayDatePicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                SelectedDate=date.toString();
                Toast.makeText(CalendarActivity.this,SelectedDate,Toast.LENGTH_SHORT).show();
            }
        });

        view_index=(View) findViewById(R.id.view_index);
        this.view_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),IndexActivity.class);
                startActivity(intent);
            }
        });
    }
}
