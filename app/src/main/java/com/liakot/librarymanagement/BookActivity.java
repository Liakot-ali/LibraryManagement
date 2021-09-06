package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class BookActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView bookName, authorName, edition, page, position, quantity, department;
    Button requestBtn, addNextBtn;

    String bookNameSt, authorNameSt, editionSt, pageSt, positionSt, quantitySt, departmentSt;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Initialize();

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniqueId = UUID.randomUUID().toString();
                DatabaseReference myRef = database.getReference("Student").child("RequestList").child(uniqueId);
                //------------- add the book to the pending List-------------
                //TODO
            }
        });

        addNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniqueId = UUID.randomUUID().toString();
                DatabaseReference myRef = database.getReference("Student").child("NextList").child(uniqueId);
                //--------------- add the book to the nextList ------------------
                //TODO
            }
        });

    }
//-------Out if Main Section-------------

    public void Initialize()
    {
        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
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
        quantitySt = getIntent().getStringExtra("quantity");
        positionSt = getIntent().getStringExtra("position");

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
