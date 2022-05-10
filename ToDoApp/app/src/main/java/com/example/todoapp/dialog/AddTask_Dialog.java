package com.example.todoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;

import com.example.todoapp.R;
import com.example.todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTask_Dialog extends Dialog{
    public Context context;
    private EditText ed_title;
    private EditText ed_desc;
    private View view_next;

    public AddTask_Dialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addtask_dialog);


        ed_title = (EditText) this.findViewById(R.id.ed_title);
        ed_desc = (EditText) this.findViewById(R.id.ed_description);
        view_next = (View) this.findViewById(R.id.view_send);


        this.view_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference mDatabase = FirebaseDatabase
                        .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("tasks");

                //creating task object
                String title = ed_title.getText().toString();
                String desc = ed_desc.getText().toString();
                Task task = new Task(title, desc);

                // pushing task to 'tasks' node using the userId
                mDatabase.child(user.getUid()).setValue(task);
                buttonOpenDialogClicked();
            }
        });
    }
    private void buttonOpenDialogClicked() {
        final choosedate_dialog dialog = new choosedate_dialog(context);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }
}
