package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddBookActivity extends AppCompatActivity {

    AutoCompleteTextView department, position;
    EditText bookName, authorName, edition, totalPage, quantity;
    Button addBtn;
    Toolbar toolbar;

    ProgressDialog progressDialog;

    FirebaseDatabase database;
    String bookNameSt, authorNameSt, editionSt, pageSt, departmentSt, quantitySt, positionSt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Initialize();

        addBtn.setOnClickListener(v -> {
            progressDialog.show();

            //--------To hide the keyboard window-----------
            InputMethodManager methodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            bookNameSt = bookName.getText().toString();
            authorNameSt = authorName.getText().toString();
            editionSt = edition.getText().toString();
            pageSt = totalPage.getText().toString();
            departmentSt = department.getText().toString();
            quantitySt = quantity.getText().toString() + " Books";
            positionSt = position.getText().toString();

            String bookId = UUID.randomUUID().toString();

            DatabaseReference myRef = database.getReference("Student").child("Books").child(bookId);
            DatabaseReference departmentRef = database.getReference("Student").child("Department").child(departmentSt).child(bookId);
            AddBookClass book = new AddBookClass(bookNameSt, authorNameSt, editionSt, pageSt, departmentSt, quantitySt, positionSt, bookId);

            myRef.setValue(book)
                    .addOnSuccessListener(aVoid -> {
                        departmentRef.setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Book Added Successfully", Toast.LENGTH_SHORT).show();
                                bookName.setText("");
                                authorName.setText("");
                                edition.setText("");
                                totalPage.setText("");
                                department.setText("");
                                quantity.setText("");
                                position.setText("");
                            }
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    });
        });
    }
//------------------out of main Section--------------------

    public void Initialize()
    {
        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");

        progressDialog = new ProgressDialog(AddBookActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding to database..");
        progressDialog.setCancelable(false);

        //------------Initialization Section---------------
        bookName = findViewById(R.id.addBookBookName);
        authorName = findViewById(R.id.addBookAuthor);
        edition = findViewById(R.id.addBookEdition);
        totalPage = findViewById(R.id.addBookPage);
        department = findViewById(R.id.addBookDepartment);
        quantity = findViewById(R.id.addBookQuantity);
        position= findViewById(R.id.addBookPosition);
        addBtn = findViewById(R.id.addBookAddBtn);

        String[]  departmentArray = getResources().getStringArray(R.array.department);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, R.layout.custom_department_view, R.id.departmentName, departmentArray);
        department.setAdapter(departmentAdapter);

        String[] positionArray = getResources().getStringArray(R.array.position);
        ArrayAdapter<String> positionAdapter = new ArrayAdapter<>(this, R.layout.custom_department_view, R.id.departmentName, positionArray);
        position.setAdapter(positionAdapter);
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
