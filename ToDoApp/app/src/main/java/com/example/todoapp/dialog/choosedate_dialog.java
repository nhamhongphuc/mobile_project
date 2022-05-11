package com.example.todoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.example.todoapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class choosedate_dialog  extends Dialog {
    public Context context;

    public choosedate_dialog(Context context) {
        super(context);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choosedate_layout);


    }
}
