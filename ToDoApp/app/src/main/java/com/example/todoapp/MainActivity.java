package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private View add;

    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        btnLogin = (Button) this.findViewById(R.id.btn_login);
        btnRegister = (Button) this.findViewById(R.id.btn_create);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonRegister();
            }
        });




//        database = new DatabaseHelper(MainActivity.this);
//        database.createDataBase();
//        database.openDataBase();
//        //database.queryData("INSERT INTO USER(ISADMIN, NAME, AVT, EMAIL, PASSWORD) VALUES(0,'Hong Phuc','','nhamphuc414@gmail.com','12345')");
//        Cursor data = database.getData("SELECT * FROM USER");
//        while (data.moveToNext())
//        {
//            String name = data.getString(2);
//            String email = data.getString(4);
//            String password = data.getString(5);
//            Log.e("User", name + " " + email + " " + password);
//        }


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
//        final ChooseDate_Dialog dialog = new ChooseDate_Dialog(this);
//
//        dialog.show();
//        Window window = dialog.getWindow();
//        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        setContentView(R.layout.login_layout);
    }

    //Login
    private void buttonLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void buttonRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


}