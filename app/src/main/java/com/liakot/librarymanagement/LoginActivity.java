package com.liakot.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button logIn;
    TextView gotoRegister;
    EditText studentId, password;
    String studentIDSt, passwordSt;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mAuth = FirebaseAuth.getInstance();

        //----------------Initialization Section-------------
        logIn = findViewById(R.id.logInLogInBtn);
        gotoRegister = findViewById(R.id.logInGotoRegister);
        studentId = findViewById(R.id.logInStudentID);
        password = findViewById(R.id.logInPassword);


        //----------On Click Section-----------
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                studentIDSt = studentId.getText().toString().trim();
                passwordSt = password.getText().toString();

                if(studentIDSt.isEmpty())
                {
                    studentId.setError("Enter your Student ID");
                    studentId.requestFocus();
                    password.clearFocus();
                }
                else if(studentIDSt.length()!=7)
                {
                    studentId.setError("Invalid Student ID");
                    studentId.requestFocus();
                    password.clearFocus();
                }
                else if(passwordSt.isEmpty())
                {
                    password.setError("Enter your Password");
                    password.requestFocus();
                    studentId.clearFocus();
                }
                else {
                    //-----------For admin------------
                    if(studentIDSt.equals("1234567") && passwordSt.equals("Librarian"))
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivityAdmin.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    //--------------For Student-----------------
                    else {
                        DatabaseReference myRef = database.getReference("Student");

                        //TODO
                    }
                }


                if(studentIDSt.equals("1000100"))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivityAdmin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
