package com.example.todoapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.dialog.EditNote_Dialog;
import com.example.todoapp.dialog.ForgotPassword_Dialog;

public class LoginActivity extends AppCompatActivity {
    private View back;
    private TextView tv_Register;
    private TextView tv_ForgotPassword;
    private EditText mail, pass;
    private Button btnlogin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        back = (View) this.findViewById(R.id.view_back);
        tv_Register = (TextView) this.findViewById(R.id.tv_login) ;
        tv_ForgotPassword = (TextView) this.findViewById(R.id.tv_forgotpassword);
        mail = (EditText)this.findViewById(R.id.ed_email1) ;
        pass = (EditText)this.findViewById(R.id.ed_password);
        btnlogin=(Button) this.findViewById(R.id.btn_login);


        Bundle data = getIntent().getExtras();
        if (data != null) {
            String email = data.getString("email");
            if (!email.equals("")) {
                mail.setText(email);
                pass.requestFocus();
            }
            Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_LONG).show();
        }



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
        tv_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToForgotPassword();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin(mail.getText().toString(), pass.getText().toString());
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

    private void ToForgotPassword() {
        FragmentManager fm = getSupportFragmentManager ();
        final ForgotPassword_Dialog dialog = new ForgotPassword_Dialog();

        dialog.show(fm, null);
    }

    private void handleLogin(String mail, String pass){
        Intent intent = new Intent(this, EmailPasswordActivity.class);
        intent.putExtra("email", mail);
        intent.putExtra("pass", pass);
        intent.putExtra("src","login");

        startActivity(intent);
    }
}
