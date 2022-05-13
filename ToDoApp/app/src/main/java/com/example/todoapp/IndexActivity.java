package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.dialog.AddTask_Dialog;
import com.example.todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private View add;
    private ArrayList<Task> Tasks;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        add = (View) this.findViewById(R.id.view_add);
        Tasks = null;

        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOpenDialogClicked();
            }
        });

        Query allTask = FirebaseDatabase
                        .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference()
                        .child("tasks").child(user.getUid());

        allTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tasks = new ArrayList<Task>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Task task = item.getValue(Task.class);
                    Log.e(TAG, "onDataChange: " + task.getTitle(), null );
                    Tasks.add(task);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void buttonOpenDialogClicked() {
        FragmentManager fm = getSupportFragmentManager();
        AddTask_Dialog dialog = new AddTask_Dialog();
        dialog.show(fm, null);
    }
}
