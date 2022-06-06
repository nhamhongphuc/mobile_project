package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.adapter.NoteListAdapter;
import com.example.todoapp.adapter.TaskListAdapter;
import com.example.todoapp.dialog.AddNote_Dialog;
import com.example.todoapp.dialog.AddTask_Dialog;
import com.example.todoapp.dialog.EditNote_Dialog;
import com.example.todoapp.dialog.choosedate_dialog;
import com.example.todoapp.model.Note;
import com.example.todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private View add;
    private View view_img;
    private TextView tv_what;
    private TextView tv_add;
    private EditText search_bar;
    private ListView lv_note;
    static ArrayList<Note> Notes = new ArrayList<>();
    ArrayList<Note> tmp_Note = new ArrayList<>();

    private View view_calender;
    private View view_index;
    private View view_user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        view_img = (View) this.findViewById(R.id.view_img);
        tv_what = (TextView) this.findViewById(R.id.tv_what);
        tv_add = (TextView) this.findViewById(R.id.tv_add);

        search_bar = (EditText) this.findViewById(R.id.search_bar);
        lv_note = (ListView) this.findViewById(R.id.lv_note);
        view_calender = (View) this.findViewById(R.id.view_calender);
        view_index = (View) this.findViewById(R.id.view_index);

        synchronized (this){
            readData(user, Notes);
        }

        add = (View) this.findViewById(R.id.view_add);
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AddNote_Dialog dialog = new AddNote_Dialog();
                dialog.show(fm, null);
            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tmp_Note.clear();
                for (int i = 0; i < Notes.size(); i++) {
                    if (Notes.get(i).getTitle().contains(editable.toString())) {
                        tmp_Note.add(Notes.get(i));
                    }
                }
                final NoteListAdapter adapter = new NoteListAdapter(NoteActivity.this, tmp_Note);
                lv_note.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        lv_note.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = Notes.get(i);
                if (!search_bar.getText().toString().equals("")) {
                    note = tmp_Note.get(i);
                };

                FragmentManager fm = getSupportFragmentManager ();
                final EditNote_Dialog dialog = new EditNote_Dialog();

                Bundle bundle = new Bundle();
                bundle.putString("title", note.getTitle());
                bundle.putString("desc", note.getDescription());
                bundle.putString("content", note.getContent());
                bundle.putString("date", note.getCreatedDate());
                dialog.setArguments(bundle);
                dialog.show(fm, null);
            }
        });

        view_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        view_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
                startActivity(intent);
            }
        });
    }

    synchronized void readData(FirebaseUser user, ArrayList<Note> List) {
        Query allTask = FirebaseDatabase
                .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("notes")
                .child(user.getUid());

        allTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notes.clear();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Note note = item.getValue(Note.class);
                    List.add(note);
                }
                if (List.size() > 0) {
                    view_img.setVisibility(View.INVISIBLE);
                    tv_what.setVisibility(View.INVISIBLE);
                    tv_add.setVisibility(View.INVISIBLE);
                    search_bar.setVisibility(View.VISIBLE);
                    lv_note.setVisibility(View.VISIBLE);


                    if (search_bar.getText().toString().equals("")) {
                        final NoteListAdapter adapter = new NoteListAdapter(NoteActivity.this, List);
                        lv_note.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        String tmp = search_bar.getText().toString();
                        tmp_Note.clear();
                        for (int i = 0; i < Notes.size(); i++) {
                            if (Notes.get(i).getTitle().contains(tmp)) {
                                tmp_Note.add(Notes.get(i));
                            }
                        }
                        final NoteListAdapter adapter = new NoteListAdapter(NoteActivity.this, tmp_Note);
                        lv_note.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }
                else {
                    view_img.setVisibility(View.VISIBLE);
                    tv_what.setVisibility(View.VISIBLE);
                    tv_add.setVisibility(View.VISIBLE);
                    search_bar.setVisibility(View.INVISIBLE);
                    lv_note.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Read data failed: " + databaseError.getMessage(),null );
            }
        });
    }

}
