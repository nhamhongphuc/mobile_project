package com.example.todoapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    DayScrollDatePicker dayDatePicker;
    String SelectedDate;
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
    }
}
