package com.example.todoapp.dialog;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.R;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.choosedate_layout);
//
//        calendarView = (CalendarView) this.findViewById(R.id.calendarView);
//        btn_choose = (Button) this.findViewById(R.id.btn_save);
//        btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
//
//        btn_choose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                DatabaseReference mDatabase = FirebaseDatabase
//                        .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                        .getReference("tasks");
//                //mDatabase.child(user.getUid()).child(taskId).setValue(task);
//                long selectedDate = calendarView.getDate();
//            }
//        });
//
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                choosedate_dialog.dismiss();
//            }
//        });
//    }
@Override
public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // lấy giá trị tự bundle
    String data = getArguments().getString("id", "");

    calendarView = (CalendarView) view.findViewById(R.id.calendarView);
    btn_choose = (Button) view.findViewById(R.id.btn_save);
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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference mDatabase = FirebaseDatabase
                        .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("tasks");


                mDatabase.child(user.getUid()).child(data).child("endDate").setValue(selectedDate);
                buttonOpenDialogClicked_Priority(data);
                getDialog().dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
}

    private void buttonOpenDialogClicked_Priority(String id) {
        FragmentManager fm = getFragmentManager ();
        final ChoosePriority_Dialog dialog = new ChoosePriority_Dialog();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        dialog.setArguments(bundle);
        dialog.show(fm, null);
    }

}