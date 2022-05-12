package com.example.todoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


import com.example.todoapp.EmailPasswordActivity;
import com.example.todoapp.R;
import com.example.todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTask_Dialog extends DialogFragment {
    public Context context;
    private EditText ed_title;
    private EditText ed_desc;
    private View view_next;
    private View view_date;
    private View view_category;
    private View view_priority;

//    public AddTask_Dialog(Context context) {
//        super(context);
//        this.context = context;
//    }
        public AddTask_Dialog() {

        }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.addtask_dialog);
//
//
//        ed_title = (EditText) this.findViewById(R.id.ed_title);
//        ed_desc = (EditText) this.findViewById(R.id.ed_description);
//        view_next = (View) this.findViewById(R.id.view_send);
//
//
//        this.view_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                DatabaseReference mDatabase = FirebaseDatabase
//                        .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                        .getReference("tasks");
//
//                //creating task object
//                String taskId = mDatabase.push().getKey();
//                String title = ed_title.getText().toString();
//                String desc = ed_desc.getText().toString();
//                Task task = new Task(title, desc);
//
//                //String tmp = task.getTimeDate(task.getDate().get("createdDate"));
//                // pushing task to 'tasks' node using the userId0
//                mDatabase.child(user.getUid()).child(taskId).setValue(task);
//
//
//                buttonOpenDialogClicked(taskId);
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height/3);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_title = (EditText) view.findViewById(R.id.ed_title);
        ed_desc = (EditText) view.findViewById(R.id.ed_description);
        view_next = (View) view.findViewById(R.id.view_send);
        view_date = (View) view.findViewById(R.id.view_timer);
        view_category = (View) view.findViewById(R.id.view_tag) ;
        view_priority = (View) view.findViewById(R.id.view_flag);



        this.view_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference mDatabase = FirebaseDatabase
                        .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("tasks");

                //creating task object
                String taskId = mDatabase.push().getKey();
                String title = ed_title.getText().toString();
                String desc = ed_desc.getText().toString();
                Task task = new Task(title, desc);

                //String tmp = task.getTimeDate(task.getDate().get("createdDate"));
                // pushing task to 'tasks' node using the userId0
                mDatabase.child(user.getUid()).child(taskId).setValue(task);

                buttonOpenDialogClicked(taskId);
            }
        });

        this.view_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //buttonOpenDialogClicked_Priority();
            }
        });

        this.view_priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addtask_dialog, container);
    }



    private void buttonOpenDialogClicked(String id) {
        FragmentManager fm = getFragmentManager ();
        final choosedate_dialog dialog = new choosedate_dialog();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        dialog.setArguments(bundle);
        dialog.show(fm, null);
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
