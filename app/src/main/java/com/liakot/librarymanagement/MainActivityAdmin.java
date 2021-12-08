package com.liakot.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivityAdmin extends AppCompatActivity {

    ImageView addNewBook, increaseSize, requestList, submitList, addContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        addNewBook = findViewById(R.id.adminAddNewBook);
        increaseSize = findViewById(R.id.adminIncreaseSize);
        requestList = findViewById(R.id.adminRequestList);
        submitList = findViewById(R.id.adminSubmitList);
        addContact = findViewById(R.id.adminAddContact);


        addNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAdmin.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
        increaseSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivityAdmin.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAdmin.this, AdminAddContactActivity.class);
                startActivity(intent);
            }
        });
        requestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this, AdminRequestListActivity.class);
                startActivity(intent);
            }
        });
        submitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this, AdminSubmitListActivity.class);
                startActivity(intent);
            }
        });

    }
}
