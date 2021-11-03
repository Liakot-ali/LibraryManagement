package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    ProgressDialog dialog;

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
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Connecting to server..");


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
                        dialog.show();
                        DatabaseReference myRef = database.getReference("Student").child("UserDetails");
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String emailSt = null, mainPass = null;
                                boolean valid = false;
                                for(DataSnapshot snap : snapshot.getChildren())
                                {
                                    UserProfileClass profile = snap.getValue(UserProfileClass.class);
                                    if(profile.getStudentId().equals(studentIDSt))
                                    {
                                        valid = true;
                                        emailSt = profile.getEmail();
                                        mainPass = profile.getPassword();
                                        break;
                                    }
                                }
                                if (!valid)
                                {
                                    dialog.dismiss();
                                    studentId.setError("Not registered yet");
                                    studentId.requestFocus();
                                    password.clearFocus();
                                }
                                else if(!mainPass.equals(passwordSt))
                                {
                                    dialog.dismiss();
                                    password.setError("Password no match");
                                    password.requestFocus();
                                    studentId.clearFocus();
                                }
                                else{
                                    dialog.show();
                                    //------------- for hide the keyboard------
                                    InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    methodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                    studentId.clearFocus();
                                    password.clearFocus();
                                    mAuth.signInWithEmailAndPassword(emailSt, passwordSt)
                                            .addOnCompleteListener(task -> {
                                                if(task.isSuccessful()){

                                                    dialog.dismiss();
                                                    studentId.setText("");
                                                    password.setText("");

                                                    Toast.makeText(getApplicationContext(), "Log In successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else{
                                                    dialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                }

                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                        
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
