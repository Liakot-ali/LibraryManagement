package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSubmitBookActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView studentName, studentId, bookName, authorName, remainingTime, edition, position;
    Button approveBtn, denyBtn, contactBtn;
    FirebaseDatabase database;
    String bookNameSt, authorNameSt, studentProfilePicture, studentNameSt, studentIdSt, studentEmailSt, studentPhoneSt, studentDepartmentSt, studentFacultySt, bookIdSt, userIdSt;
    String bookPageSt, bookEditionSt, bookPositionSt, bookRemainingTime, bookDepartmentSt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_submit_book);

        Initialize();

        DatabaseReference userRef = database.getReference("Student").child("User").child(userIdSt).child("Profile");
        DatabaseReference returnRef = database.getReference("Admin").child("ReturnList").child(bookIdSt);
        DatabaseReference stuReturnRef = database.getReference("Student").child("User").child(userIdSt).child("ReturnList").child(bookIdSt);
        DatabaseReference myBookRef = database.getReference("Student").child("User").child(userIdSt).child("MyBooks").child(bookIdSt);


        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            stuReturnRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Book Returned", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminSubmitBookActivity.this, AdminSubmitListActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO

            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserProfileClass profile = snapshot.getValue(UserProfileClass.class);
                        assert profile != null;
                        studentEmailSt = profile.getEmail();
                        studentPhoneSt = profile.getPhone();
                        studentDepartmentSt = profile.getDepartment();
                        studentFacultySt = profile.getFaculty();
                        studentProfilePicture = profile.getPicture();

                        Intent intent = new Intent(AdminSubmitBookActivity.this, ContactStudentActivity.class);
                        intent.putExtra("name", studentNameSt);
                        intent.putExtra("studentId", studentIdSt);
                        intent.putExtra("email", studentEmailSt);
                        intent.putExtra("phone", studentPhoneSt);
                        intent.putExtra("faculty", studentFacultySt);
                        intent.putExtra("department", studentDepartmentSt);
                        intent.putExtra("profilePicture", studentProfilePicture);
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
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

        //-------------Initialization Section--------------
        studentName = findViewById(R.id.adminSubmitStudentName);
        studentId = findViewById(R.id.adminSubmitStudentID);
        bookName = findViewById(R.id.adminSubmitBookName);
        authorName = findViewById(R.id.adminSubmitAuthorName);
        edition = findViewById(R.id.adminSubmitEdition);
        position = findViewById(R.id.adminSubmitPosition);
        remainingTime = findViewById(R.id.adminSubmitRemainingTime);
        approveBtn = findViewById(R.id.adminSubmitApproveBtn);
        denyBtn = findViewById(R.id.adminSubmitDenyBtn);
        contactBtn = findViewById(R.id.adminSubmitStudentContact);

        //-----------get value from previous activity--------------
        bookNameSt = getIntent().getStringExtra("bookName");
        authorNameSt = getIntent().getStringExtra("authorName");
        studentNameSt = getIntent().getStringExtra("studentName");
        studentIdSt = getIntent().getStringExtra("studentId");
        bookEditionSt = getIntent().getStringExtra("bookEdition");
        bookPositionSt = getIntent().getStringExtra("bookPosition");
        bookRemainingTime = getIntent().getStringExtra("bookRemainingTime");
        bookPageSt = getIntent().getStringExtra("bookPage");
        bookDepartmentSt = getIntent().getStringExtra("bookDepartment");
        bookIdSt = getIntent().getStringExtra("bookId");
        userIdSt = getIntent().getStringExtra("userId");


        //----------------set value to the textView-----------------
        studentName.setText(studentNameSt);
        studentId.setText(studentIdSt);
        bookName.setText(bookNameSt);
        authorName.setText(authorNameSt);
        edition.setText(bookEditionSt);
        remainingTime.setText(bookRemainingTime);
        position.setText(bookPositionSt);

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
