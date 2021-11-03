package com.liakot.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ContactStudentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView name, studentId, email, phone, department, faculty;
    ImageView picture;
    String nameSt, sIdSt, emailSt, phoneSt, departmentSt, facultySt, pictureSt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_student);

        Initialize();

    }

    private void Initialize() {

        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //-------Initialization----------

        name = findViewById(R.id.studentName);
        studentId = findViewById(R.id.studentId);
        email = findViewById(R.id.studentEmail);
        phone = findViewById(R.id.studentPhone);
        department = findViewById(R.id.studentDepartment);
        faculty = findViewById(R.id.studentFaculty);
        picture = findViewById(R.id.studentProfilePicture);

        nameSt = getIntent().getStringExtra("name");
        sIdSt = getIntent().getStringExtra("studentId");
        emailSt = getIntent().getStringExtra("email");
        phoneSt = getIntent().getStringExtra("phone");
        facultySt = getIntent().getStringExtra("faculty");
        departmentSt = getIntent().getStringExtra("department");
        pictureSt = getIntent().getStringExtra("profilePicture");
        try {
            if (!pictureSt.equals("")) {
                Picasso.get().load(pictureSt).into(picture);
            }
        }
        catch (ActivityNotFoundException exception){
            Log.e("Activity", "Initialize: " + exception.getMessage());
        }
        name.setText(nameSt);
        studentId.setText(sIdSt);
        email.setText(emailSt);
        phone.setText(phoneSt);
        faculty.setText(facultySt);
        department.setText(departmentSt);
    }
}
