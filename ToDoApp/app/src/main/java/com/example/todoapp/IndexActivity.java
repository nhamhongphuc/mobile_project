package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.adapter.TaskListAdapter;
import com.example.todoapp.dialog.AddTask_Dialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private View add;
<<<<<<< Updated upstream
=======
    public ArrayList<Task> Tasks;
    private View view_img;
    private TextView tv_what;
    private TextView tv_add;
    private EditText search_bar;
    private ListView lv_task;
>>>>>>> Stashed changes

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

<<<<<<< Updated upstream
        if (user != null) {
            String name = user.getEmail();
            String id = user.getUid();
        }

        add = (View) this.findViewById(R.id.view_add);
=======

        view_img = (View) this.findViewById(R.id.view_img);
        tv_what = (TextView) this.findViewById(R.id.tv_what);
        tv_add = (TextView) this.findViewById(R.id.tv_add);;
        search_bar = (EditText) this.findViewById(R.id.search_bar);
        lv_task = (ListView) this.findViewById(R.id.lv_task);

        TaskListAdapter adapter = new TaskListAdapter(this, Tasks);
        lv_task.setAdapter(adapter);
        Query allTask = FirebaseDatabase
                .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference()
                .child("tasks").child(user.getUid());
        Tasks = null;
        allTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tasks = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Task task = item.getValue(Task.class);
                    Tasks.add(task);
                }
                if (Tasks.size() > 0) {
                    view_img.setVisibility(View.INVISIBLE);
                    tv_what.setVisibility(View.INVISIBLE);
                    tv_add.setVisibility(View.INVISIBLE);
                    search_bar.setVisibility(View.VISIBLE);
                    lv_task.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        add = (View) this.findViewById(R.id.view_add);
        Tasks = null;
>>>>>>> Stashed changes

        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOpenDialogClicked();
            }
        });

<<<<<<< Updated upstream
=======


>>>>>>> Stashed changes
    }

    private void buttonOpenDialogClicked() {
        final AddTask_Dialog dialog = new AddTask_Dialog(this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }

//    private ArrayList<Task> getListData() {
//
//    }
}
