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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class BookActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView bookName, authorName, edition, page, position, quantity, department;
    Button requestBtn, addNextBtn;

    String bookNameSt, authorNameSt, editionSt, pageSt, positionSt, quantitySt, departmentSt;

    FirebaseDatabase database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Initialize();

        //TODO
        requestBtn.setOnClickListener(view -> {
            if (!quantitySt.equals("0")) {

                String userId = mAuth.getUid();
                String uniqueId = UUID.randomUUID().toString();
                assert userId != null;
                DatabaseReference studentProfileRef = database.getReference("Student").child("User").child(userId).child("Profile");
                DatabaseReference pendingRef = database.getReference("Student").child("User").child(userId).child("PendingList").child(uniqueId);
                DatabaseReference adminRef = database.getReference("Admin").child("RequestList").child(uniqueId);

                //------------- add the book to the pending List-------------
                AddBookClass requestBook = new AddBookClass(bookNameSt, authorNameSt, editionSt, pageSt, departmentSt, quantitySt, positionSt, uniqueId);

                //-----------add the book and student details in admin Request List--------
                RequestBookClass newRequestBook = new RequestBookClass();
                studentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserProfileClass userProfile = snapshot.getValue(UserProfileClass.class);
                        assert userProfile != null;
                        newRequestBook.setStudentName(userProfile.getFirstName() + " " + userProfile.getLastName());
                        newRequestBook.setStudentId(userProfile.getStudentId());
                        newRequestBook.setBookName(bookNameSt);
                        newRequestBook.setAuthorName(authorNameSt);
                        newRequestBook.setBookEdition(editionSt);
                        newRequestBook.setBookPosition(positionSt);
                        newRequestBook.setBookQuantity(quantitySt);
                        newRequestBook.setBookPage(pageSt);
                        newRequestBook.setBookDepartment(departmentSt);
                        newRequestBook.setBookId(uniqueId);
                        newRequestBook.setUserId(userId);

                        pendingRef.setValue(requestBook).addOnCompleteListener(task -> {
                            adminRef.setValue(newRequestBook).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "The book needs to be approved by librarian", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "This book is not available now. Add it to next list", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO
        addNextBtn.setOnClickListener(v -> {
            String userId = mAuth.getUid();
            String uniqueId = UUID.randomUUID().toString();
            assert userId != null;
            DatabaseReference nextRef = database.getReference("Student").child("User").child(userId).child("NextList").child(uniqueId);
            //--------------- add the book to the nextList ------------------
            AddBookClass nextBook = new AddBookClass(bookNameSt, authorNameSt, editionSt, pageSt, departmentSt, quantitySt, positionSt, uniqueId);
            nextRef.setValue(nextBook).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "The book is added to your Next List", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                }
            });
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
        bookName = findViewById(R.id.bookName);
        authorName = findViewById(R.id.bookAuthorName);
        edition = findViewById(R.id.bookEdition);
        position = findViewById(R.id.booksPosition);
        quantity = findViewById(R.id.bookQuantity);
        requestBtn = findViewById(R.id.bookRequestBtn);
        addNextBtn = findViewById(R.id.bookAddNextBtn);

        //-----------get value from previous activity--------------
        bookNameSt = getIntent().getStringExtra("bookName");
        authorNameSt = getIntent().getStringExtra("authorName");
        editionSt = getIntent().getStringExtra("edition");
        pageSt = getIntent().getStringExtra("page");
        departmentSt = getIntent().getStringExtra("department");
        quantitySt = getIntent().getStringExtra("quantity");
        positionSt = getIntent().getStringExtra("position");

        //----------------set value to the textView-----------------
        bookName.setText(bookNameSt);
        authorName.setText(authorNameSt);
        edition.setText(editionSt);
        position.setText(positionSt);
        quantity.setText(quantitySt);
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
