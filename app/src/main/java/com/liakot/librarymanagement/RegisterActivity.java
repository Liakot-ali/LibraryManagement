package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText firstName, lastName, studentId, email, passWord, confirmPassword;
    Button registerBtn;
    TextView gotoSignIn;

    FirebaseDatabase database;
    FirebaseAuth mAuth;

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Initialize();

        //--------On Click Section
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameSt, lastNameSt, studentIdSt, emailSt, passwordSt, conPasswordSt;
                firstNameSt = firstName.getText().toString();
                lastNameSt = lastName.getText().toString();
                studentIdSt = studentId.getText().toString().trim();
                emailSt = email.getText().toString().trim();
                passwordSt = passWord.getText().toString();
                conPasswordSt = confirmPassword.getText().toString();

                if(firstNameSt.isEmpty())
                {
                    firstName.setError("Enter your first name");
                    firstName.requestFocus();
                    lastName.clearFocus();
                    studentId.clearFocus();
                    email.clearFocus();
                    passWord.clearFocus();
                    confirmPassword.clearFocus();
                }
                else  if(lastNameSt.isEmpty())
                {
                    lastName.setError("Enter your last name");
                    lastName.requestFocus();
                    firstName.clearFocus();
                    studentId.clearFocus();
                    email.clearFocus();
                    passWord.clearFocus();
                    confirmPassword.clearFocus();
                }
                else if(studentIdSt.isEmpty())
                {
                    studentId.setError("Enter your student ID");
                    studentId.requestFocus();
                    firstName.clearFocus();
                    lastName.clearFocus();
                    email.clearFocus();
                    passWord.clearFocus();
                    confirmPassword.clearFocus();
                }
                else if(studentIdSt.length()!=7)
                {
                    studentId.setError("Student ID must be 7 digits");
                    studentId.requestFocus();
                    firstName.clearFocus();
                    lastName.clearFocus();
                    email.clearFocus();
                    passWord.clearFocus();
                    confirmPassword.clearFocus();
                }
                else if(emailSt.isEmpty())
                {
                    firstName.clearFocus();
                    lastName.clearFocus();
                    studentId.clearFocus();
                    email.setError("Enter your email");
                    email.requestFocus();
                    passWord.clearFocus();
                    confirmPassword.clearFocus();
                }
                else if(!emailSt.matches(emailPattern))
                {
                    firstName.clearFocus();
                    lastName.clearFocus();
                    studentId.clearFocus();
                    email.setError("Enter valid email");
                    email.requestFocus();
                    passWord.clearFocus();
                    confirmPassword.clearFocus();
                }
                else if(passwordSt.isEmpty())
                {
                    firstName.clearFocus();
                    lastName.clearFocus();
                    studentId.clearFocus();
                    passWord.setError("Enter a password");
                    email.clearFocus();
                    passWord.requestFocus();
                    confirmPassword.clearFocus();
                }
                else if(passwordSt.length()<6)
                {
                    firstName.clearFocus();
                    lastName.clearFocus();
                    studentId.clearFocus();
                    passWord.setError("Password must be greater than 6 characters");
                    email.clearFocus();
                    passWord.requestFocus();
                    confirmPassword.clearFocus();
                }
                else if(!conPasswordSt.matches(passwordSt))
                {
                    firstName.clearFocus();
                    lastName.clearFocus();
                    studentId.clearFocus();
                    confirmPassword.setError("Password must be matched with previous password");
                    email.clearFocus();
                    passWord.clearFocus();
                    confirmPassword.requestFocus();
                }
                else {

                    DatabaseReference myRef = database.getReference("Student").child("UserDetails");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean indEmail = true;
                            boolean indId = true;
                            for(DataSnapshot snap : snapshot.getChildren())
                            {
                                UserProfileClass profile = snap.getValue(UserProfileClass.class);
                                if(emailSt.equals(profile.getEmail()))
                                {
                                    indEmail = false;
                                    break;
                                }
                                if(studentIdSt.equals(profile.getStudentId()))
                                {
                                    indId = false;
                                    break;
                                }
                            }
                            if(!indEmail)
                            {
                                firstName.clearFocus();
                                lastName.clearFocus();
                                studentId.clearFocus();
                                email.setError("This email is already registered");
                                email.requestFocus();
                                passWord.clearFocus();
                                confirmPassword.clearFocus();
                            }
                            else if(!indId)
                            {
                                studentId.setError("This Student Id is already registered");
                                studentId.requestFocus();
                                firstName.clearFocus();
                                lastName.clearFocus();
                                email.clearFocus();
                                passWord.clearFocus();
                                confirmPassword.clearFocus();
                            }
                            else{

                                //------------- for hide the keyboard------
                                InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                methodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                //---------------- create new user with email and password-------------
                                mAuth.createUserWithEmailAndPassword(emailSt,passwordSt)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        String userUniqueId = mAuth.getUid();
                                        //------------- add the data to App/Student/User/UserUniqueId/Profile------------------
                                        DatabaseReference profileRef = database.getReference("Student").child("User").child(userUniqueId).child("Profile");
                                        UserProfileClass profile = new UserProfileClass(firstNameSt, lastNameSt, studentIdSt,emailSt, "", "", "", "",passwordSt);

                                        profileRef.setValue(profile)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                //--------- add the data to App/Student/UserDetails/UserUniqueId-----------
                                                UserProfileClass details = new UserProfileClass(firstNameSt, studentIdSt, emailSt, passwordSt);

                                                myRef.child(userUniqueId).setValue(details)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                        else{
                                                            Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                            }
                                        });
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
//--------------out of main Section----------------
    public void Initialize()
    {
        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mAuth = FirebaseAuth.getInstance();

        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //-------------Initialization Section---------------
        firstName = findViewById(R.id.registerFirstName);
        lastName = findViewById(R.id.registerLastName);
        studentId = findViewById(R.id.registerStudentid);
        email = findViewById(R.id.registerEmail);
        passWord = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.registerConfirmPassword);
        registerBtn = findViewById(R.id.registerRegisterBtn);
        gotoSignIn = findViewById(R.id.registerGotoSignIn);

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
