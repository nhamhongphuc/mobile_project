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

public class ChooseCategory_Dialog extends DialogFragment {
    private RadioButton r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11;
    private Button btn_edit;

    public ChooseCategory_Dialog() {
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = 100 + getResources().getDisplayMetrics().heightPixels ;
        getDialog().getWindow().setLayout(width, height);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editcategory_layout, container);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("title", "");
        String desc = getArguments().getString("desc", "");
        String date = getArguments().getString("date", "");
        int priority = getArguments().getInt("priority");

        r1 = (RadioButton) view.findViewById(R.id.radio_grocery);
        r2 = (RadioButton) view.findViewById(R.id.radio_work);
        r3 = (RadioButton) view.findViewById(R.id.radio_sport);
        r4 = (RadioButton) view.findViewById(R.id.radio_design);
        r5 = (RadioButton) view.findViewById(R.id.radio_uni);
        r6 = (RadioButton) view.findViewById(R.id.radio_social);
        r7 = (RadioButton) view.findViewById(R.id.radio_music);
        r8 = (RadioButton) view.findViewById(R.id.radio_health);
        r9 = (RadioButton) view.findViewById(R.id.radio_movie);
        r10 = (RadioButton) view.findViewById(R.id.radio_home);
        r11 = (RadioButton) view.findViewById(R.id.radio_new);

        btn_edit = (Button) view.findViewById(R.id.btn_edit);

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Grocery");
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Work");
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Sport");
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Design");
            }
        });
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "University");
            }
        });
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Social");
            }
        });
        r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Music");
            }
        });
        r8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Health");
            }
        });
        r9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Movie");
            }
        });
        r10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Home");
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickCategory(title, desc, priority, date, "Category");
            }
        });



    }
    public void handleClickCategory(String title, String desc, int priority, String date, String category) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference mDatabase = FirebaseDatabase
                .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("tasks");

        String taskId = mDatabase.push().getKey();
        Task task = new Task(title, desc);
        task.setPriority(priority);
        task.setCompleted(false);
        task.setEndDate(date);
        task.setCategory(category);

        mDatabase.child(user.getUid()).child(taskId).setValue(task);
        getDialog().dismiss();
    }
}
