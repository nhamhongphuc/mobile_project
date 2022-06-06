package com.example.todoapp.dialog;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.NoteActivity;
import com.example.todoapp.R;
import com.example.todoapp.TaskScreenActivity;
import com.example.todoapp.model.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditNote_Dialog extends DialogFragment {
    public Context context;
    private View view_back;
    private TextView tv_note;
    private TextView tv_save;
    private EditText ed_title;
    private EditText ed_desc;
    private EditText ed_content;
    private TextView tv_del;

    public EditNote_Dialog(){}
    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height/2);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_back = (View) view.findViewById(R.id.view_back);
        tv_note = (TextView) view.findViewById(R.id.tv_note);
        tv_save = (TextView) view.findViewById(R.id.tv_save);
        tv_del = (TextView) view.findViewById(R.id.tv_delete);
        ed_title = (EditText) view.findViewById(R.id.ed_title);
        ed_desc = (EditText) view.findViewById(R.id.ed_desc);
        ed_content = (EditText) view.findViewById(R.id.ed_content);

        tv_del.setVisibility(View.VISIBLE);

        String title = getArguments().getString("title", "");
        String desc = getArguments().getString("desc", "");
        String content = getArguments().getString("content", "");
        String date = getArguments().getString("date", "");

        ed_title.setText(title);
        ed_desc.setText(desc);
        ed_content.setText(content);

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
                if (title.equals(ed_title.getText().toString()) && desc.equals(ed_desc.getText().toString())
                    && content.equals(ed_content.getText().toString())) {
                    Toast.makeText(getContext(), "you have not changed anything!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    Query getTask = FirebaseDatabase
                            .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            .getReference("notes")
                            .child(user.getUid()).orderByChild("title").equalTo(title);
                    getTask.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().child("title").setValue(ed_title.getText().toString());
                                appleSnapshot.getRef().child("description").setValue(ed_desc.getText().toString());
                                appleSnapshot.getRef().child("content").setValue(ed_content.getText().toString());
                            }
                            getDialog().dismiss();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });
                }
            }
        });

        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DeleteNote_Dialog dialog = new DeleteNote_Dialog();

                Bundle bundle = new Bundle();
                bundle.putString("title", ed_title.getText().toString());
                bundle.putString("desc", ed_desc.getText().toString());
                dialog.setArguments(bundle);

                dialog.show(fm, null);
                getDialog().dismiss();
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addnote_dialog, container);
    }
}
