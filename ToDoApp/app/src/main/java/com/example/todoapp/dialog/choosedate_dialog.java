package com.example.todoapp.dialog;

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
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.EmailPasswordActivity;
import com.example.todoapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class choosedate_dialog  extends DialogFragment {
    public Context context;
    public CalendarView calendarView;
    public Button btn_choose;
    public Button btn_cancel;
    public String selectedDate;

    public static choosedate_dialog newInstance(String data) {
        choosedate_dialog dialog = new choosedate_dialog();
        Bundle args = new Bundle();
        args.putString("data", data);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choosedate_layout, container);
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height/2);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // lấy giá trị tự bundle
        String title = getArguments().getString("title", "");
        String desc = getArguments().getString("desc", "");


        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        btn_choose = (Button) view.findViewById(R.id.btn_edit);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

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
                            buttonOpenDialogClicked_Priority(title, desc, selectedDate);
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

    private void buttonOpenDialogClicked_Priority(String title, String desc, String date) {
        FragmentManager fm = getFragmentManager ();
        final ChoosePriority_Dialog dialog = new ChoosePriority_Dialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("desc", desc);
        bundle.putString("date", date);
        dialog.setArguments(bundle);
        dialog.show(fm, null);
    }

}
