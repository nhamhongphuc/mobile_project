package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    private View add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_list_layout);

        add = (View) this.findViewById(R.id.view_add);

        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOpenDialogClicked();
            }
        });
    }
    private void buttonOpenDialogClicked() {
        final ChooseDate_Dialog dialog = new ChooseDate_Dialog(this);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }
}