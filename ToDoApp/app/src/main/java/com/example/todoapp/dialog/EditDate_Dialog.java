package com.example.todoapp.dialog;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditDate_Dialog extends DialogFragment {
    public Context context;
    public CalendarView calendarView;
    public Button btn_choose;
    public Button btn_cancel;
    public String selectedDate;

    public EditDate_Dialog() {
    }
    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height/2);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_date_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String date = getArguments().getString("date", "");

        selectedDate = date;
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        btn_choose = (Button) view.findViewById(R.id.btn_edit);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date d = f.parse(selectedDate);
            long milliseconds = d.getTime();
            calendarView.setDate(milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                selectedDate = year + "/" + String.format("%02d",(month+1)) + "/" + String.format("%02d",day);
                Log.d("calendar", String.valueOf(view.getDate()));
            }
        });

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = sdf.parse(selectedDate);
                    Date curDate = sdf.parse(sdf.format(new Date()));
                    if (date.before(curDate)) {
                        Toast.makeText(getContext(), "Not valid date.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        onSendUpdateData(selectedDate);
                        getDialog().dismiss();
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }

    public static interface OnCompleteListener_Date {
        public abstract void onComplete_date(String date);
    }
    public void onSendUpdateData(String date) {
        this.mListener.onComplete_date(date);
    }

    private EditTitle_Dialog.OnCompleteListener mListener;

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (EditTitle_Dialog.OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
}
