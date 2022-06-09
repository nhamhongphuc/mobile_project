package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.dialog.ChangePassword_Dialog;
import com.example.todoapp.dialog.EditName_Dialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ProfileActivity extends AppCompatActivity implements EditName_Dialog.OnCompleteListener_Name {
    private ImageView profileImageView;

    private TextView tv_changeImage;

    private TextView changeImageTV, tv_changaccountpassword;

    private TextView profileName, tv_changeaccountname;
    private View viewNote, viewCalendar, viewIndex;
    private static final String TAG = "ProfileActivity";
    private TextView tv_taskLeft;
    private TextView tv_taskDone;
    private TextView tv_logOut;
    boolean iscompleted;
    static ArrayList<com.example.todoapp.model.Task> Tasks = new ArrayList<>();

    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        profileImageView = findViewById(R.id.img_profile);
        tv_changeImage = findViewById(R.id.tv_changeaccountimage);
        profileName = findViewById(R.id.profile_name);
        tv_taskDone = findViewById(R.id.tv_taskdone);
        tv_taskLeft = findViewById(R.id.tv_taskleft);
        tv_logOut = findViewById(R.id.tv_Logout);
        synchronized (this){
            readData(user, Tasks);
        }
        //Dieu huong <<
        viewNote = findViewById(R.id.view_note);
        viewCalendar = findViewById(R.id.view_calender);
        viewIndex = findViewById(R.id.view_index);
        tv_changeaccountname = findViewById(R.id.tv_changeaccountname);

        //Dieu Huong >>

        tv_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //<<Hien thi ten Profile
        if (user.getDisplayName() == null) {
            profileName.setText(user.getEmail());
        } else
            profileName.setText(user.getDisplayName());
        ;
        viewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent);
            }
        });
        //Hien thi ten Profile >>


        tv_changaccountpassword = findViewById(R.id.tv_changaccountpassword);
        //Dieu Huong

        viewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent);
            }
        });

        viewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        viewIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
                startActivity(intent);
            }
        });

        //vao storage
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        //vao storage

        // Completed va Left task <<
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();

        //vao Task cua current user
        myRef.child("tasks/" + fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            int completed = 0;
            int left = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                    iscompleted = (boolean) appleSnapshot.child("completed").getValue();
                    if (iscompleted == true) {
                        completed = completed + 1;
                    } else left = left + 1;
                }
                tv_taskDone.setText(Integer.toString(completed) + " Tasks Done");

                /*Log.d(TAG, "check completed" + Integer.toString(compledted));
                Log.d(TAG, "check completed" + Integer.toString(left));*/
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        // Completed va Left task >>


        //Doi Anh Dai dien<<
        if (user.getPhotoUrl() == null) {
            profileImageView.setImageResource(R.drawable.ic_user);
        } else {
            StorageReference profileRef = storageReference.child("user/" + fAuth.getCurrentUser().getUid() + "/profile.jpg"); // lay hinh tu store
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profileImageView);
                }

            });
        }

        tv_changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }

        });
        //Doi Anh Dai dien >>

        // edit name dialog<<
        tv_changeaccountname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToEditName();
            }
        });
        // edit name dialog>>

        //chang password dialog
        tv_changaccountpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToChangePassword();
            }
        });

    }

    private void ToEditName() {
        FragmentManager fm = getSupportFragmentManager();
        final EditName_Dialog dialog = new EditName_Dialog();

        dialog.show(fm, null);
    }

    private void ToChangePassword(){
        FragmentManager fm = getSupportFragmentManager();
        final ChangePassword_Dialog dialog = new ChangePassword_Dialog();
        dialog.show(fm, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                profileImageView.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {

        StorageReference fileRef = storageReference.child("user/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");

        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ProfileActivity.this, "ProfilePicUpdated", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "UpdatedFailed", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void onComplete_Name(String NewName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(NewName)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            profileName.setText(fAuth.getCurrentUser().getDisplayName());
                        }
                    }
                });
    }


    synchronized void readData(FirebaseUser user, ArrayList<com.example.todoapp.model.Task> List) {
        Query allTask = FirebaseDatabase
                .getInstance("https://todoapp-ptk-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("tasks")
                .child(user.getUid());

        allTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tasks.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    com.example.todoapp.model.Task task = item.getValue(com.example.todoapp.model.Task.class);
                    List.add(task);
                }
                // Task left và done cach  2
                int count=0;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

                    Date curDate = sdf.parse(sdf.format(new Date()));
                    for (int i = 0; i < List.size(); i++) {
                        Date date = sdf.parse(List.get(i).getEndDate());
                        if (date.equals(curDate) || date.after(curDate) && !List.get(i).isCompleted()) {
                            count += 1;
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tv_taskLeft.setText(count + " Tasks Left");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Read data failed: " + databaseError.getMessage(), null);
            }
        });
    }
}
// Doi Ten
//    public void updateName(){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//            .setDisplayName("Jane Q. User")
//            .build();
//        user.updateProfile(profileUpdates)
//            .addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "User profile updated.");
//                }
//            }
//        });
//    }



