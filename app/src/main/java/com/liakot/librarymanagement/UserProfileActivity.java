package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView profilePicture;
    TextView firstName, lastName, studentId, email, phone, faculty, department;
    Button editProfileBtn;

    FirebaseAuth mAuth;
    FirebaseDatabase  database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Initialize();

        String uniqueId = mAuth.getUid();
        assert uniqueId != null;
        DatabaseReference profileRef = database.getReference("Student").child("User").child(uniqueId).child("Profile");

        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfileClass user = snapshot.getValue(UserProfileClass.class);

                assert user != null;
                if(!user.getPicture().isEmpty())
                {
                    //TODO------ set picture ic imageView----;
                    profilePicture.setImageResource(R.drawable.icon_user_profile_24dp);
                }
                firstName.setText(user.getFirstName());
                lastName.setText(user.getLastName());
                studentId.setText(user.getStudentId());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
                faculty.setText(user.getFaculty());
                department.setText(user.getDepartment());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO--goto updateProfile and pass required information-------
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

        //-------------Initialization Section------------
        profilePicture = findViewById(R.id.profilePicture);
        firstName = findViewById(R.id.profileFirstName);
        lastName = findViewById(R.id.profileLastName);
        studentId = findViewById(R.id.profileStudentId);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhoneNumber);
        faculty = findViewById(R.id.profileFaculty);
        department = findViewById(R.id.profileDepartment);
        editProfileBtn = findViewById(R.id.profileEditBtn);

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
