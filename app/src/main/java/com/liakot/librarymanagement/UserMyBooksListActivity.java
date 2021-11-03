package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class UserMyBooksListActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView bookName, authorName, edition, remainingTime, position;
    Button increaseTimeBtn, returnBtn, contactBtn;
    String bookNameSt, authorNameSt, editionSt, remainingTimeSt, positionSt, pageSt, quantitySt, bookId, departmentSt;
    String studentName, studentId;
    FirebaseDatabase database ;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_books_list);

        Initialize();

        //TODO
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMyBooksListActivity.this, PersonContactActivity.class);
                startActivity(intent);
            }
        });

        increaseTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                Toast.makeText(UserMyBooksListActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(UserMyBooksListActivity.this);
                dialog.setTitle("Are you Sure").setMessage("Want to return this book?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference returnRef = database.getReference("Admin").child("ReturnList").child(bookId);
                        String userId = mAuth.getUid();
                        assert userId != null;
                        DatabaseReference profileRef = database.getReference("Student").child("User").child(userId).child("Profile");
                        DatabaseReference returnPendingRef = database.getReference("Student").child("User").child(userId).child("ReturnList").child(bookId);
                        DatabaseReference myBooksRef = database.getReference("Student").child("User").child(userId).child("MyBooks").child(bookId);

                        RequestBookClass newReturn = new RequestBookClass();

                        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserProfileClass profile = snapshot.getValue(UserProfileClass.class);
                                assert profile != null;
                                newReturn.setStudentName(profile.getFirstName() + " " + profile.getLastName());
                                newReturn.setStudentId(profile.getStudentId());

                                AddBookClass newPendingBook = new AddBookClass(bookNameSt, authorNameSt, editionSt, pageSt, departmentSt, quantitySt, positionSt, bookId);
//                newReturn = new RequestBookClass(bookNameSt, authorNameSt, studentName, studentId, editionSt, positionSt, quantitySt, pageSt, departmentSt, bookId, userId);

                                newReturn.setBookName(bookNameSt);
                                newReturn.setAuthorName(authorNameSt);
                                newReturn.setBookEdition(editionSt);
                                newReturn.setBookPosition(positionSt);
                                newReturn.setBookQuantity(quantitySt);
                                newReturn.setBookPage(pageSt);
                                newReturn.setBookDepartment(departmentSt);
                                newReturn.setBookId(bookId);
                                newReturn.setUserId(userId);

                                returnRef.setValue(newReturn).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        returnPendingRef.setValue(newPendingBook).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    myBooksRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getApplicationContext(), "Return request sent. Check this in your pending list", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(UserMyBooksListActivity.this, MyBooksActivity.class);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UserMyBooksListActivity.this, "Return request canceled", Toast.LENGTH_SHORT).show();
                    }
                }).show();


//                AddBookClass newPendingBook = new AddBookClass(bookNameSt, authorNameSt, editionSt, pageSt, departmentSt, quantitySt, positionSt, bookId);
////                newReturn = new RequestBookClass(bookNameSt, authorNameSt, studentName, studentId, editionSt, positionSt, quantitySt, pageSt, departmentSt, bookId, userId);
//
//                newReturn.setBookName(bookNameSt);
//                newReturn.setAuthorName(authorNameSt);
//                newReturn.setBookEdition(editionSt);
//                newReturn.setBookPosition(positionSt);
//                newReturn.setBookQuantity(quantitySt);
//                newReturn.setBookPage(pageSt);
//                newReturn.setBookDepartment(departmentSt);
//                newReturn.setBookId(bookId);
//                newReturn.setUserId(userId);
//
//                returnRef.setValue(newReturn).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        returnPendingRef.setValue(newPendingBook).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()) {
//                                    myBooksRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(getApplicationContext(), "Return request sent. Check this in your pending list", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(UserMyBooksListActivity.this, MyBooksActivity.class);
//                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                startActivity(intent);
//                                                finish();
//                                            } else {
//                                                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                }
//                                else {
//                                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                });
            }
        });
    }


//---------Out of main section-----------
    private void Initialize() {
        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mAuth = FirebaseAuth.getInstance();

        //-----------get value from previous activity--------------
        bookNameSt = getIntent().getStringExtra("bookName");
        authorNameSt = getIntent().getStringExtra("authorName");
        editionSt = getIntent().getStringExtra("edition");
        remainingTimeSt = getIntent().getStringExtra("remainingTime");
        pageSt = getIntent().getStringExtra("page");
        quantitySt = getIntent().getStringExtra("quantity");
        departmentSt = getIntent().getStringExtra("department");
        positionSt = getIntent().getStringExtra("position");
        bookId = getIntent().getStringExtra("bookId");


        //--------initialization-------
        bookName = findViewById(R.id.myBooksName);
        authorName = findViewById(R.id.myBooksAuthor);
        edition = findViewById(R.id.myBooksEdition);
        remainingTime = findViewById(R.id.myBooksRemainingTIme);
        position = findViewById(R.id.myBooksPosition);
        increaseTimeBtn = findViewById(R.id.myBooksIncreaseTimeBtn);
        returnBtn = findViewById(R.id.myBooksReturnBtn);
        contactBtn = findViewById(R.id.myBooksContactLibrarianBtn);


        //----------------set value to the textView-----------------
        bookName.setText(bookNameSt);
        authorName.setText(authorNameSt);
        edition.setText(editionSt);
        position.setText(positionSt);
        remainingTime.setText(remainingTimeSt);

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
