package com.example.todoapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private View back;
    private TextView tv_Register;
    private DatabaseHelper database;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        back = (View) this.findViewById(R.id.view_back);
        tv_Register = (TextView) this.findViewById(R.id.tv_login) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoBackToWelcome();
            }
        });

        tv_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToRegister();
            }
        });
    }
    private void GoBackToWelcome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void ToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
