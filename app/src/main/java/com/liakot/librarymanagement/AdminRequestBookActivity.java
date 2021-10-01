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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminRequestBookActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView studentName, studentId, bookName, authorName, quantity, edition, position;
    Button approveBtn, denyBtn, contactBtn;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    String bookNameSt, authorNameSt, studentProfilePicture, studentNameSt, studentIdSt, studentEmailSt, studentPhoneSt, studentDepartmentSt, studentFacultySt, bookIdSt, userIdSt;
    String bookPageSt, bookEditionSt, bookPositionSt, bookQuantitySt, bookDepartmentSt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_request_book);

        Initialize();

        DatabaseReference userRef = database.getReference("Student").child("User").child(userIdSt).child("Profile");
//        DatabaseReference bookRef = database.getReference("Student").child("Books").child(bookIdSt);
//        DatabaseReference bookRef = database.getReference("Student").child("User").child(userIdSt).child("PendingList").child(bookIdSt);
        DatabaseReference requestRef = database.getReference("Admin").child("RequestList").child(bookIdSt);
        DatabaseReference pendingRef = database.getReference("Student").child("User").child(userIdSt).child("PendingList").child(bookIdSt);
        DatabaseReference myBookRef = database.getReference("Student").child("User").child(userIdSt).child("MyBooks").child(bookIdSt);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfileClass profile = snapshot.getValue(UserProfileClass.class);
//                studentNameSt = profile.getFirstName() + " " + profile.getLastName();
//                studentIdSt = profile.getStudentId();
                assert profile != null;
                studentEmailSt = profile.getEmail();
                studentPhoneSt = profile.getPhone();
                studentDepartmentSt = profile.getDepartment();
                studentFacultySt = profile.getFaculty();
                studentProfilePicture = profile.getPicture();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });

        requestRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddBookClass approveBook = snapshot.getValue(AddBookClass.class);
                assert approveBook != null;
                bookNameSt = approveBook.getBookName();
                authorNameSt = approveBook.getAuthorName();
                bookPageSt = approveBook.getPage();
                bookEditionSt = approveBook.getEdition();
                bookQuantitySt = approveBook.getQuantity();
                bookDepartmentSt = approveBook.getDepartment();
                bookPositionSt = approveBook.getPosition();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO
        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBookClass approveBook = new AddBookClass(bookNameSt, authorNameSt, bookEditionSt, bookPageSt, bookDepartmentSt, bookQuantitySt, bookPositionSt, bookIdSt);
                myBookRef.setValue(approveBook).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            requestRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    pendingRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(), "Book Approved", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AdminRequestBookActivity.this, AdminRequestListActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Check internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //TODO
        denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               requestRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {
                           pendingRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   Toast.makeText(getApplicationContext(), "Book request denyed", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(AdminRequestBookActivity.this, AdminRequestListActivity.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   startActivity(intent);
                                   finish();
                               }
                           });
                       }
                       else{
                           Toast.makeText(getApplicationContext(), "Check internet connection", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }
        });

        //TODO
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminRequestBookActivity.this, ContactStudentActivity.class);
                intent.putExtra("name", studentNameSt);
                intent.putExtra("studentId", studentIdSt);
                intent.putExtra("email", studentEmailSt);
                intent.putExtra("phone", studentPhoneSt);
                intent.putExtra("faculty", studentFacultySt);
                intent.putExtra("department", studentDepartmentSt);
                intent.putExtra("profilePicture", studentProfilePicture);
                startActivity(intent);
            }
        });
    }

    //-------Out if Main Section-------------

    public void Initialize() {
        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mAuth = FirebaseAuth.getInstance();

        //-------------Initialization Section--------------
        studentName = findViewById(R.id.adminRqstStudentName);
        studentId = findViewById(R.id.adminRqstStudentID);
        bookName = findViewById(R.id.adminRqstBookName);
        authorName = findViewById(R.id.adminRqstAuthorName);
        edition = findViewById(R.id.adminRqstEdition);
        position = findViewById(R.id.adminRqstPosition);
        quantity = findViewById(R.id.adminRqstQuantity);
        approveBtn = findViewById(R.id.adminRqstApproveBtn);
        denyBtn = findViewById(R.id.adminRqstDenyBtn);
        contactBtn = findViewById(R.id.adminRqstStudentContact);

        //-----------get value from previous activity--------------
        bookNameSt = getIntent().getStringExtra("bookName");
        authorNameSt = getIntent().getStringExtra("authorName");
        studentNameSt = getIntent().getStringExtra("studentName");
        studentIdSt = getIntent().getStringExtra("studentId");
//        studentEmailSt = getIntent().getStringExtra("studentEmail");
//        studentPhoneSt = getIntent().getStringExtra("studentPhone");
//        studentDepartmentSt = getIntent().getStringExtra("studentDepartment");
        bookIdSt = getIntent().getStringExtra("bookId");
        userIdSt = getIntent().getStringExtra("userId");

        //----------------set value to the textView-----------------
        studentName.setText(studentNameSt);
        studentId.setText(studentIdSt);
        bookName.setText(bookNameSt);
        authorName.setText(authorNameSt);
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
