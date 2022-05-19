package com.example.todoapp.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;
import com.example.todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChoosePriority_Dialog extends DialogFragment {
    private RadioButton r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
    private Button btn_cancel, btn_save;

    public ChoosePriority_Dialog() {};

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = 100 + getResources().getDisplayMetrics().heightPixels/2 ;
        getDialog().getWindow().setLayout(width, height);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choosepriority, container);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("title", "");
        String desc = getArguments().getString("desc", "");
        String date = getArguments().getString("date", "");

        r1 = (RadioButton) view.findViewById(R.id.radio_1);
        r2 = (RadioButton) view.findViewById(R.id.radio_2);
        r3 = (RadioButton) view.findViewById(R.id.radio_3);
        r4 = (RadioButton) view.findViewById(R.id.radio_4);
        r5 = (RadioButton) view.findViewById(R.id.radio_5);
        r6 = (RadioButton) view.findViewById(R.id.radio_6);
        r7 = (RadioButton) view.findViewById(R.id.radio_7);
        r8 = (RadioButton) view.findViewById(R.id.radio_8);
        r9 = (RadioButton) view.findViewById(R.id.radio_9);
        r10 = (RadioButton) view.findViewById(R.id.radio_10);


        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_save = (Button) view.findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int priority = 0;
                if (r1.isChecked()) {
                    priority = 1;
                } else if (r2.isChecked()) {
                    priority = 2;
                } else if (r3.isChecked()) {
                    priority = 3;
                } else if (r4.isChecked()) {
                    priority = 4;
                } else if (r5.isChecked()) {
                    priority = 5;
                } else if (r6.isChecked()) {
                    priority = 6;
                } else if (r7.isChecked()) {
                    priority = 7;
                } else if (r8.isChecked()) {
                    priority = 8;
                } else if (r9.isChecked()) {
                    priority = 9;
                } else if (r10.isChecked()) {
                    priority = 10;
                }

                if (priority == 0) {
                    getDialog().dismiss();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    DatabaseReference mDatabase = FirebaseDatabase
                            .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            .getReference("tasks");

                    String taskId = mDatabase.push().getKey();
                    Task task = new Task(title, desc);
                    task.setPriority(priority);
                    task.setCompleted(false);
                    task.setEndDate(date);

                    mDatabase.child(user.getUid()).child(taskId).setValue(task);
                    getDialog().dismiss();
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
}
