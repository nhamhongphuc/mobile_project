package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.dialog.AddTask_Dialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private View add;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getEmail();
            String id = user.getUid();
        }

        add = (View) this.findViewById(R.id.view_add);

        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOpenDialogClicked();
            }
        });

    }

    private void buttonOpenDialogClicked() {
        final AddTask_Dialog dialog = new AddTask_Dialog(this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }
}
