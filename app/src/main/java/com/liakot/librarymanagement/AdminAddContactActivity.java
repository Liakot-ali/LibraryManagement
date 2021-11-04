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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAddContactActivity extends AppCompatActivity {

    static final int PICK_IMAGE = 10;

    ProgressDialog dialog;
    Toolbar toolbar;
    EditText name, position, phone, email;
    Button addImage, addContact;
    CircleImageView profile;

    Uri imageUri;

    FirebaseDatabase database;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_contact);
        Initialize();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                String  currentTime = String.valueOf(System.currentTimeMillis());

                DatabaseReference contactRef = database.getReference("Student").child("Contacts").child(currentTime);

                String nameSt, positionSt, phoneSt, emailSt;
                nameSt = name.getText().toString();
                positionSt = position.getText().toString();
                phoneSt = phone.getText().toString();
                emailSt = email.getText().toString();

                if(imageUri != null)
                {
                    String imageName;
                    imageName = currentTime + getFileExtension(imageUri);
                    StorageReference sRef = storage.getReference("Student").child("Contacts").child(imageName);
                    sRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    PersonContactClass newPerson = new PersonContactClass(nameSt, positionSt, phoneSt, emailSt, uri.toString());
                                    contactRef.setValue(newPerson).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                dialog.dismiss();
                                                Toast.makeText(AdminAddContactActivity.this, "New person contact added", Toast.LENGTH_SHORT).show();
                                                name.setText("");
                                                position.setText("");
                                                phone.setText("");
                                                email.setText("");
                                                profile.setImageResource(R.drawable.icon_user_profile_24dp);
                                            }
                                            else{
                                                dialog.dismiss();
                                                Toast.makeText(AdminAddContactActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(AdminAddContactActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    PersonContactClass newPerson = new PersonContactClass(nameSt, positionSt, phoneSt, emailSt, "");
                    contactRef.setValue(newPerson).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(AdminAddContactActivity.this, "New person contact added", Toast.LENGTH_SHORT).show();
                                name.setText("");
                                position.setText("");
                                phone.setText("");
                                email.setText("");
                            }
                            else{
                                dialog.dismiss();
                                Toast.makeText(AdminAddContactActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
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


        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        storage = FirebaseStorage.getInstance();

        name = findViewById(R.id.addContactName);
        position = findViewById(R.id.addContactPosition);
        phone = findViewById(R.id.addContactPhone);
        email = findViewById(R.id.addContactEmail);
        addImage = findViewById(R.id.addContactAddImage);
        addContact = findViewById(R.id.addContactBtn);
        profile = findViewById(R.id.addContactProfile);

        dialog = new ProgressDialog(AdminAddContactActivity.this);
        dialog.setTitle("Please Wait..");
        dialog.setMessage("We are working on it..");


    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver resolver = getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(imageUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null)
        {
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