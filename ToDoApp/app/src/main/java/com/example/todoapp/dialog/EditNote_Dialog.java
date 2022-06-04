package com.example.todoapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;
import com.example.todoapp.model.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNote_Dialog extends DialogFragment {
    public Context context;
    private View view_back;
    private TextView tv_note;
    private TextView tv_save;
    private EditText ed_title;
    private EditText ed_desc;
    private EditText ed_content;

    public EditNote_Dialog(){}
    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_back = (View) view.findViewById(R.id.view_back);
        tv_note = (TextView) view.findViewById(R.id.tv_note);
        tv_save = (TextView) view.findViewById(R.id.tv_save);
        ed_title = (EditText) view.findViewById(R.id.ed_title);
        ed_desc = (EditText) view.findViewById(R.id.ed_desc);
        ed_content = (EditText) view.findViewById(R.id.ed_content);

        view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        tv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ed_title.getText().toString();
                String desc = ed_desc.getText().toString();
                String content = ed_content.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference mDatabase = FirebaseDatabase
                        .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("notes");

                String noteId = mDatabase.push().getKey();
                Note note = new Note(title, desc, content);


                mDatabase.child(user.getUid()).child(noteId).setValue(note);
                getDialog().dismiss();
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addnote_dialog, container);
    }
}
