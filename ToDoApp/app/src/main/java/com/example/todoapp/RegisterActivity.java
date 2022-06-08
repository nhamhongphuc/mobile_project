package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private View back;
    private TextView tv_Login;
    private EditText email;
    private EditText pass;
    private Button btn_register;
    private EditText pass_confirm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        back = (View) this.findViewById(R.id.view_back);
        tv_Login = (TextView) this.findViewById(R.id.tv_login) ;
        email = (EditText) this.findViewById(R.id.ed_email1);
        pass = (EditText) this.findViewById(R.id.ed_password);
        pass_confirm = (EditText) this.findViewById(R.id.ed_confirmpassword);
        btn_register = (Button) this.findViewById(R.id.btn_login);


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

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.getText().toString().equals(pass_confirm.getText().toString())) {
                    HandleRegister(email.getText().toString(), pass.getText().toString());
                }
                else {
                    Toast.makeText( getApplicationContext(), "Password doesn't match!", Toast.LENGTH_SHORT).show();
                    pass_confirm.setText("");
                }
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
    private void HandleRegister(String email, String pass) {

        Intent intent = new Intent(this, EmailPasswordActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("pass", pass);
        intent.putExtra("src","regis");
        startActivity(intent);
    }
}
