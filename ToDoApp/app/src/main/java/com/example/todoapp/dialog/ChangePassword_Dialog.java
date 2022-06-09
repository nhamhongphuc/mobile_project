package com.example.todoapp.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.R;
import com.example.todoapp.model.Note;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class ChangePassword_Dialog extends DialogFragment{
    private EditText OldPassword;
    private EditText NewPassword;
    private Button btn_cancel;
    private Button btn_edit;
    private int tmp = 3;
    private String TAG;
    public ChangePassword_Dialog(){}

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = (getResources().getDisplayMetrics().heightPixels)/2;
        getDialog().getWindow().setLayout(width, height);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OldPassword = (EditText) view.findViewById(R.id.editTextTextPassword);
        NewPassword = (EditText) view.findViewById(R.id.editTextTextPassword2);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ChangePassword(OldPassword.getText().toString(), NewPassword.getText().toString() );
//                Log.d(TAG, "" + tmp );
//                if(tmp == 1 ) {
//                    getDialog().dismiss();
//                    Toast.makeText(getContext(), "Update completed!", Toast.LENGTH_SHORT).show();
//                }
//                else if(tmp == 2){
//                    Toast.makeText(getContext(), "Old Password Incorrect!", Toast.LENGTH_SHORT).show();
//                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), OldPassword.getText().toString());

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "User re-authenticated.");
                                if(task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String newPassword = NewPassword.getText().toString();
                                    tmp = 1;
                                    user.updatePassword(newPassword)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User password updated.");
                                                    }
                                                }
                                            });
                                    getDialog().dismiss();
                                    Toast.makeText(getContext(), "Update completed!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(), "Old Password Incorrect!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.changpassword_dialog, container);
    }

    public void ChangePassword(String Old, String New) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), Old);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String newPassword = New;
                            tmp = 1;
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User password updated.");
                                            }
                                        }
                                    });
                        }
                        else {
                            tmp = 2;
                        }
                    }
                });
    }
}
