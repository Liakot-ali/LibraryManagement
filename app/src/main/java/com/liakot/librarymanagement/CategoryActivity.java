package com.liakot.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CategoryActivity extends AppCompatActivity {

    LinearLayout agronomy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        agronomy = findViewById(R.id.agronomy);
        agronomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });
    }
}
