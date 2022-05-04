package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //private View add;
    private DatabaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_list_layout);

        database = new DatabaseHelper(MainActivity.this);
        database.createDataBase();
        database.openDataBase();
        //database.queryData("INSERT INTO USER(ISADMIN, NAME, AVT, EMAIL, PASSWORD) VALUES(0,'Hong Phuc','','nhamphuc414@gmail.com','12345')");
        Cursor data = database.getData("SELECT * FROM USER");
        while (data.moveToNext())
        {
            String name = data.getString(2);
            String email = data.getString(4);
            String password = data.getString(5);
            Log.e("User", name + " " + email + " " + password);
        }


//        add = (View) this.findViewById(R.id.view_add);
//
//        this.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonOpenDialogClicked();
//            }
//        });
    }
    private void buttonOpenDialogClicked() {
        final ChooseDate_Dialog dialog = new ChooseDate_Dialog(this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }
}