package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.dialog.AddTask_Dialog;
import com.example.todoapp.dialog.DeleteTask_Dialog;

public class TaskScreenActivity extends AppCompatActivity {
    private TextView tv_title;
    private TextView tv_desc;
    private Button btn_time;
    private Button btn_priority;
    private View view_back;
    private View edit_title;

    private TextView tv_delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskcreen_layout);

        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_desc = (TextView) this.findViewById(R.id.tv_desc);
        btn_time = (Button) this.findViewById(R.id.btn_time);
        btn_priority = (Button) this.findViewById(R.id.btn_priority);
        view_back = (View) this.findViewById(R.id.view_back);
        edit_title = (View) this.findViewById(R.id.view_edit_title);
        tv_delete = (TextView) this.findViewById(R.id.textView26);

        Bundle data = getIntent().getExtras();
        String title = data.getString("title");
        String desc = data.getString("desc");
        Boolean isComp = data.getBoolean("comp");
        int priority = data.getInt("priority");
        String startDate = data.getString("start");
        String endDate = data.getString("end");

        tv_title.setText(title);
        tv_desc.setText(desc);
        btn_time.setText(endDate);
        btn_priority.setText(String.valueOf(priority));

        view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDeleteButton();
            }
        });

    }

    private void handleDeleteButton() {
        FragmentManager fm = getSupportFragmentManager();
        DeleteTask_Dialog dialog = new DeleteTask_Dialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", tv_title.getText().toString());
        dialog.setArguments(bundle);

        dialog.show(fm, null);
    }
}
