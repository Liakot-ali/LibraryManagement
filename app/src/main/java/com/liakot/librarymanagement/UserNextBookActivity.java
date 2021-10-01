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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserNextBookActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView bookName, authorName, edition, page, position, quantity, department;
    Button requestBtn, deleteBtn;

    String bookNameSt, authorNameSt, editionSt, pageSt, positionSt, quantitySt, bookId;

    FirebaseDatabase database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_next_book);

        Initialize();

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = mAuth.getUid();
                assert userId != null;
                DatabaseReference myRef = database.getReference("Student").child("User").child(userId).child("NextList").child(bookId);
                myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Book remove from Next List", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserNextBookActivity.this, NextBookActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
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
        bookName = findViewById(R.id.nextBookName);
        authorName = findViewById(R.id.nextAuthorName);
        edition = findViewById(R.id.nextEdition);
        position = findViewById(R.id.nextPosition);
        quantity = findViewById(R.id.nextQuantity);
        requestBtn = findViewById(R.id.nextRequestBtn);
        deleteBtn = findViewById(R.id.nextDeleteBtn);

        //-----------get value from previous activity--------------
        bookNameSt = getIntent().getStringExtra("bookName");
        authorNameSt = getIntent().getStringExtra("authorName");
        editionSt = getIntent().getStringExtra("edition");
        pageSt = getIntent().getStringExtra("page");
        quantitySt = getIntent().getStringExtra("quantity");
        positionSt = getIntent().getStringExtra("position");
        bookId = getIntent().getStringExtra("bookId");

        //----------------set value to the texrView-----------------
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
