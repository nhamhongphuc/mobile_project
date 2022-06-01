package com.example.todoapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.dialog.DeleteTask_Dialog;
import com.example.todoapp.dialog.EditCategory_Dialog;
import com.example.todoapp.dialog.EditDate_Dialog;
import com.example.todoapp.dialog.EditPrority_Dialog;
import com.example.todoapp.dialog.EditTitle_Dialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TaskScreenActivity extends AppCompatActivity implements EditTitle_Dialog.OnCompleteListener,
                                                                    EditDate_Dialog.OnCompleteListener_Date,
                                                                    EditPrority_Dialog.OnCompleteListener_Priority
{
    private TextView tv_title;
    private TextView tv_desc;
    private Button btn_time;
    private Button btn_priority;
    private Button btn_category;
    private View view_back;
    private View edit_title;
    private View view_refresh;
    private Button btn_update;


    private TextView tv_delete;
    private View view_delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskcreen_layout);

        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_desc = (TextView) this.findViewById(R.id.tv_desc);
        btn_time = (Button) this.findViewById(R.id.btn_time);
        btn_priority = (Button) this.findViewById(R.id.btn_priority);
        btn_category = (Button) this.findViewById(R.id.btn_category);
        view_back = (View) this.findViewById(R.id.view_back);
        edit_title = (View) this.findViewById(R.id.view_edit_title);
        view_refresh = (View) this.findViewById(R.id.view_refresh);
        tv_delete = (TextView) this.findViewById(R.id.textView26);
        view_delete = (View) this.findViewById(R.id.view11);
        btn_update = (Button) this.findViewById(R.id.btn_update);

        Bundle data = getIntent().getExtras();
        String title = data.getString("title");
        String desc = data.getString("desc");
        Boolean isComp = data.getBoolean("comp");
        int priority = data.getInt("priority");
        String startDate = data.getString("start");
        String endDate = data.getString("end");
        String category = data.getString("category");

        tv_title.setText(title);
        tv_desc.setText(desc);
        btn_time.setText(endDate);
        btn_priority.setText(String.valueOf(priority));
        btn_category.setText(category);
        if (category.equals("Grocery")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grocery_lite,0,0,0);
        } else if (category.equals("Work")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_work_lite,0,0,0);
        } else if (category.equals("Sport")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sport_lite,0,0,0);
        } else if (category.equals("Design")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_design_lite,0,0,0);
        } else if (category.equals("University")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_uni_lite,0,0,0);
        } else if (category.equals("Social")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_social_lite,0,0,0);
        } else if (category.equals("Music")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_music_lite,0,0,0);
        } else if (category.equals("Health")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_health_lite,0,0,0);
        } else if (category.equals("Movie")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie_lite,0,0,0);
        } else if (category.equals("Home")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_lite,0,0,0);
        }

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

        edit_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditTitle();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { handleEditTime(); }
        });

        btn_priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditPriority();
            }
        });

        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditCategory();
            }
        });

        view_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title.setText(title);
                tv_desc.setText(desc);
                btn_time.setText(endDate);
                btn_priority.setText(String.valueOf(priority));
                btn_category.setText(category);
                if (category.equals("Grocery")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grocery_lite,0,0,0);
                } else if (category.equals("Work")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_work_lite,0,0,0);
                } else if (category.equals("Sport")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sport_lite,0,0,0);
                } else if (category.equals("Design")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_design_lite,0,0,0);
                } else if (category.equals("University")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_uni_lite,0,0,0);
                } else if (category.equals("Social")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_social_lite,0,0,0);
                } else if (category.equals("Music")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_music_lite,0,0,0);
                } else if (category.equals("Health")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_health_lite,0,0,0);
                } else if (category.equals("Movie")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie_lite,0,0,0);
                } else if (category.equals("Home")) {
                    btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_lite,0,0,0);
                }
                Log.e(TAG, "onClick: refresh success", null);
            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDeleteButton();
            }
        });
        view_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDeleteButton();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tv_title.getText().toString().equals(title) || !tv_desc.getText().toString().equals(desc)
                    || !btn_time.getText().toString().equals(endDate) || !btn_priority.getText().toString().equals(String.valueOf(priority))
                    || !btn_category.getText().toString().equals(category)) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    Query getTask = FirebaseDatabase
                            .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            .getReference("tasks")
                            .child(user.getUid()).orderByChild("title").equalTo(title);;
                    getTask.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().child("title").setValue(tv_title.getText().toString());
                                appleSnapshot.getRef().child("description").setValue(tv_desc.getText().toString());
                                appleSnapshot.getRef().child("endDate").setValue(btn_time.getText().toString());
                                appleSnapshot.getRef().child("priority").setValue(Integer.parseInt(btn_priority.getText().toString()));
                                appleSnapshot.getRef().child("category").setValue(btn_category.getText().toString());
                            }
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });
                }
                else {
                    Toast.makeText(TaskScreenActivity.this, "you have not changed anything!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Xu ly data tra ve tu Dialog Edit Title
    public void onComplete(String title, String desc) {
        if (!tv_title.getText().toString().equals(title) || !tv_desc.getText().toString().equals(desc)) {
            tv_title.setText(title);
            tv_desc.setText(desc);
        }
    }

    //Xu ly data tra ve tu Dialog Edit Date
    @Override
    public void onComplete_date(String date) {
        if (!btn_time.getText().toString().equals(date) ) {
            btn_time.setText(date);
        }
    }

    //Xu ly data tra ve tu Dialog Edit Priority
    @Override
    public void onComplete_priority(Integer priority) {
        if (!btn_priority.getText().toString().equals(String.valueOf(priority))) {
            btn_priority.setText(String.valueOf(priority));
        }
    }

    //Xu ly data tra ve tu Dialog Edit Category
    @Override
    public void onComplete_category(String category) {
        if (category.equals(btn_category.getText().toString())) {
            return;
        }
        btn_category.setText(category);
        if (category.equals("Grocery")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grocery_lite,0,0,0);
        } else if (category.equals("Work")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_work_lite,0,0,0);
        } else if (category.equals("Sport")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sport_lite,0,0,0);
        } else if (category.equals("Design")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_design_lite,0,0,0);
        } else if (category.equals("University")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_uni_lite,0,0,0);
        } else if (category.equals("Social")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_social_lite,0,0,0);
        } else if (category.equals("Music")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_music_lite,0,0,0);
        } else if (category.equals("Health")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_health_lite,0,0,0);
        } else if (category.equals("Movie")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie_lite,0,0,0);
        } else if (category.equals("Home")) {
            btn_category.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_lite,0,0,0);
        }
    }

    private void handleDeleteButton() {
        FragmentManager fm = getSupportFragmentManager();
        DeleteTask_Dialog dialog = new DeleteTask_Dialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", tv_title.getText().toString());
        dialog.setArguments(bundle);

        dialog.show(fm, null);
    }

    private void handleEditTitle() {
        FragmentManager fm = getSupportFragmentManager();
        EditTitle_Dialog dialog = new EditTitle_Dialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", tv_title.getText().toString());
        bundle.putString("desc", tv_desc.getText().toString());
        dialog.setArguments(bundle);

        dialog.show(fm, null);
    }

    private void handleEditTime() {
        FragmentManager fm = getSupportFragmentManager();
        EditDate_Dialog dialog = new EditDate_Dialog();

        Bundle bundle = new Bundle();
        bundle.putString("date", btn_time.getText().toString());
        dialog.setArguments(bundle);

        dialog.show(fm, null);
    }

    private void handleEditPriority() {
        FragmentManager fm = getSupportFragmentManager();
        EditPrority_Dialog dialog = new EditPrority_Dialog();

        Bundle bundle = new Bundle();
        bundle.putString("priority", btn_priority.getText().toString());
        dialog.setArguments(bundle);

        dialog.show(fm, null);
    }

    private void handleEditCategory() {
        FragmentManager fm = getSupportFragmentManager();
        EditCategory_Dialog dialog = new EditCategory_Dialog();


        dialog.show(fm, null);
    }
}
