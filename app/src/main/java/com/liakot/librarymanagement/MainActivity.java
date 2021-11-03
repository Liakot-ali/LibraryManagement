package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView profilePicture;
    ImageView searchBook, contacts, category, myBooks, nextBookList, pendingList, signOut;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //----------------Initialization Section-----------

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mAuth = FirebaseAuth.getInstance();

        profilePicture = findViewById(R.id.homeProfilePicture);
        searchBook = findViewById(R.id.homeSearchBook);
        category = findViewById(R.id.homeCategory);
        contacts = findViewById(R.id.homeContacts);
        myBooks = findViewById(R.id.homeMyBooks);
        pendingList = findViewById(R.id.homePendingList);
        nextBookList = findViewById(R.id.homeNextBook);
        signOut = findViewById(R.id.homeSignOut);

        DatabaseReference profileRef = database.getReference("Student").child("User").child(Objects.requireNonNull(mAuth.getUid())).child("Profile");
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfileClass profileClass = snapshot.getValue(UserProfileClass.class);
                assert profileClass != null;
                if(!profileClass.getPicture().isEmpty()) {
                    Picasso.get().load(profileClass.getPicture()).into(profilePicture);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //--------------On click Section-------------
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ContactsActivity.class);
                startActivity(intent);
            }
        });

        myBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyBooksActivity.class);
                startActivity(intent);
            }
        });

        pendingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PendingListActivity.class);
                startActivity(intent);
            }
        });

        nextBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NextBookActivity.class);
                startActivity(intent);
            }
        });

        // --TODO---------- implement sharedPreferences ----
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setIcon(R.drawable.icon_sign_out_24dp);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Do you want to log out?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("No", null);
                dialog.show();
            }
        });
    }
}
