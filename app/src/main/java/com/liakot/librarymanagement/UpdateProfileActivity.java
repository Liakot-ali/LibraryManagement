package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfileActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 10;

    Uri imageUri;

    ProgressDialog dialog;
    Toolbar toolbar;
    EditText firstName, lastName, phone, faculty, department;
    TextView studentId, email;
    Button addImage, update;
    CircleImageView profile;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseStorage sRef;

    String firstNameSt, lastNameSt, studentIdSt,emailSt, phoneSt, facultySt, departmentSt, profileSt, passwordSt, uniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Initialize();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO-------- pick image form file
                //--------For hide the keyboard----------
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                //------------For open gallery---------
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

//                Toast.makeText(UpdateProfileActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                uniqueId = mAuth.getUid();

                DatabaseReference profileRef = database.getReference("Student").child("User").child(uniqueId).child("Profile");

                firstNameSt = firstName.getText().toString();
                lastNameSt = lastName.getText().toString();
                phoneSt = phone.getText().toString();
                facultySt = faculty.getText().toString();
                departmentSt = department.getText().toString();

                if(imageUri!=null)
                {
                    profileSt = uniqueId + getFileExtension(imageUri);

                    StorageReference storage = sRef.getReference("Student").child(uniqueId).child("Profile").child(profileSt);
                    storage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserProfileClass profileClass = new UserProfileClass(firstNameSt, lastNameSt, studentIdSt, emailSt, phoneSt, facultySt, departmentSt, uri.toString(), passwordSt, uniqueId);
                                    profileRef.setValue(profileClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                dialog.dismiss();
                                                Toast.makeText(UpdateProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(UpdateProfileActivity.this, UserProfileActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                dialog.dismiss();
                                                Toast.makeText(UpdateProfileActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else{
                    UserProfileClass profileClass = new UserProfileClass(firstNameSt, lastNameSt, studentIdSt, emailSt, phoneSt, facultySt, departmentSt, profileSt, passwordSt, uniqueId);
                    profileRef.setValue(profileClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                dialog.dismiss();
                                Toast.makeText(UpdateProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(UpdateProfileActivity.this, UserProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                dialog.dismiss();
                                Toast.makeText(UpdateProfileActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void Initialize() {

        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        sRef = FirebaseStorage.getInstance();

        //-------------Initialization Section------------
        profile = findViewById(R.id.addContactProfile);
        firstName = findViewById(R.id.updateFirstName);
        lastName = findViewById(R.id.updateLastName);
        studentId = findViewById(R.id.updateStudentid);
        email = findViewById(R.id.updateEmail);
        phone = findViewById(R.id.updatePhone);
        faculty = findViewById(R.id.updateFaculty);
        department = findViewById(R.id.updateDepartment);

        addImage = findViewById(R.id.addContactAddImage);
        update = findViewById(R.id.updateBtn);

        dialog = new ProgressDialog(UpdateProfileActivity.this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("We are working on update");


        firstNameSt = getIntent().getStringExtra("FirstName");
        lastNameSt = getIntent().getStringExtra("LastName");
        studentIdSt = getIntent().getStringExtra("StudentId");
        emailSt = getIntent().getStringExtra("Email");
        phoneSt = getIntent().getStringExtra("Phone");
        facultySt = getIntent().getStringExtra("Faculty");
        departmentSt = getIntent().getStringExtra("Department");
        profileSt = getIntent().getStringExtra("Picture");
        passwordSt = getIntent().getStringExtra("Password");

        if(!profileSt.equals("") && !profileSt.isEmpty())
        {
            Picasso.get().load(profileSt).into(profile);
        }

        firstName.setText(firstNameSt);
        lastName.setText(lastNameSt);
        studentId.setText(studentIdSt);
        email.setText(emailSt);
        phone.setText(phoneSt);
        faculty.setText(facultySt);
        department.setText(departmentSt);
    }
    //----------for access Image Extension-----------
    private String getFileExtension(Uri imageUri) {
        ContentResolver resolver = getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(imageUri));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(profile);
        }
        else{
            imageUri = null;
        }
    }

    //---------for back to home-------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}