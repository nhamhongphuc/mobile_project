package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private View back;
    private TextView tv_Login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        back = (View) this.findViewById(R.id.view_back);
        tv_Login = (TextView) this.findViewById(R.id.tv_login) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoBackToWelcome();
            }
        });

        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToLogin();
            }
        });
    }
    private void GoBackToWelcome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void ToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
