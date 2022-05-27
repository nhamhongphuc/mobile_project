package com.example.todoapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


import com.example.todoapp.R;

public class AddTask_Dialog extends DialogFragment {
    public Context context;
    private EditText ed_title;
    private EditText ed_desc;
    private View view_next;
    private View view_date;
    private View view_category;
    private View view_priority;


        public AddTask_Dialog() {

        }



    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height/3);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_title = (EditText) view.findViewById(R.id.ed_title);
        ed_desc = (EditText) view.findViewById(R.id.ed_desc);
        view_next = (View) view.findViewById(R.id.view_send);
        view_date = (View) view.findViewById(R.id.view_timer);
        view_category = (View) view.findViewById(R.id.view_tag) ;
        view_priority = (View) view.findViewById(R.id.view_flag);



        this.view_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ed_title.getText().toString();
                String desc = ed_desc.getText().toString();


                buttonOpenDialogClicked(title, desc);
            }
        });

        this.view_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //buttonOpenDialogClicked_Priority();
            }
        });

        this.view_priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addtask_dialog, container);
    }



    private void buttonOpenDialogClicked(String title, String desc) {
        FragmentManager fm = getFragmentManager ();
        final choosedate_dialog dialog = new choosedate_dialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("desc", desc);
        dialog.setArguments(bundle);
        dialog.show(fm, null);
    }

    private void buttonOpenDialogClicked_Priority(String id) {
        FragmentManager fm = getFragmentManager ();
        final ChoosePriority_Dialog dialog = new ChoosePriority_Dialog();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        dialog.setArguments(bundle);
        dialog.show(fm, null);
    }
}
