package com.example.todoapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
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

import com.example.todoapp.dialog.EditName_Dialog;
import com.example.todoapp.dialog.ForgotPassword_Dialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfileActivity extends AppCompatActivity {
    private ImageView profileImageView;
    private TextView changeImageTV;
    private TextView profileName, tv_changeaccountname;
    private View viewNote,viewCalendar,viewIndex;
    private static final String TAG = "ProfileActivity";
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        profileImageView = findViewById(R.id.img_profile);
        changeImageTV = findViewById(R.id.tv_changeaccountimage);
        profileName = findViewById(R.id.profile_name);

        //Dieu huong
        viewNote = findViewById(R.id.view_note);
        viewCalendar = findViewById(R.id.view_calender);
        viewIndex = findViewById(R.id.view_index);
        tv_changeaccountname = findViewById(R.id.tv_changeaccountname);
        //Dieu Huong
        viewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NoteActivity.class);
                startActivity(intent);
            }
        });

        viewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
            }
        });

        viewIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),IndexActivity.class);
                startActivity(intent);
            }
        });


        //Firebase va storage
        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference();
        //Firebase va storage


        //Doi Anh Dai dien
        profileName.setText(fAuth.getCurrentUser().getDisplayName());
        StorageReference profileRef= storageReference.child("user/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg"); // lay hinh tu store
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });

        changeImageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }

        });
        // edit name dialog
        tv_changeaccountname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToEditName();
            }
        });

    }

    private void ToEditName() {
        FragmentManager fm = getSupportFragmentManager ();
        final EditName_Dialog dialog = new EditName_Dialog();

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

        StorageReference fileRef = storageReference.child("user/"+ fAuth.getCurrentUser().getUid()+"/profile.jpg");

            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProfileActivity.this,"ProfilePicUpdated",Toast.LENGTH_SHORT).show();

                }
                    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this,"UpdatedFailed",Toast.LENGTH_SHORT).show();
                }
            });


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
}


