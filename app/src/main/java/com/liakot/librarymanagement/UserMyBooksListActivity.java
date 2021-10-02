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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserMyBooksListActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView bookName, authorName, edition, remainingTime, position;
    Button increaseTimeBtn, returnBtn, contactBtn;
    String bookNameSt, authorNameSt, editionSt, remainingTimeSt, positionSt, pageSt, quantitySt, bookId, departmentSt;
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
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference returnRef = database.getReference("Admin").child("ReturnList");
                //TODO

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
