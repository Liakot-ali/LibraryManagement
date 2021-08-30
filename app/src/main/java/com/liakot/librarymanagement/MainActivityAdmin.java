package com.liakot.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivityAdmin extends AppCompatActivity {

    ImageView addNewBook, increaseSize, requestList, submitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        addNewBook = findViewById(R.id.adminAddNewBook);
        increaseSize = findViewById(R.id.adminIncreaseSize);
        requestList = findViewById(R.id.adminRequestList);
        submitList = findViewById(R.id.adminSubmitList);


        addNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAdmin.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

    }
}
