package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyBooksActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView myBookList;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    ArrayList<AddBookClass> arrayList;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        Initialize();

        String userId = mAuth.getUid();
        assert userId != null;
        DatabaseReference myBookRef = database.getReference("Student").child("User").child(userId).child("MyBooks");

        myBookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    AddBookClass newBook = snapshot1.getValue(AddBookClass.class);
                    arrayList.add(newBook);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });

        Adapter();
        myBookList.setAdapter(adapter);

        myBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AddBookClass newBook = arrayList.get(position);
                Intent intent = new Intent(MyBooksActivity.this, UserMyBooksListActivity.class);
                intent.putExtra("bookName", newBook.getBookName());
                intent.putExtra("authorName", newBook.getAuthorName());
                intent.putExtra("edition", newBook.getEdition());
                intent.putExtra("page", newBook.getPage());
                intent.putExtra("department", newBook.getDepartment());
                intent.putExtra("quantity", newBook.getQuantity());
                intent.putExtra("position", newBook.getPosition());
                intent.putExtra("bookId", newBook.getBookId());
                intent.putExtra("remainingTime", newBook.getTime());
                startActivity(intent);
            }
        });




    }
//-----Out of main section---------
    private void Initialize() {

        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mAuth = FirebaseAuth.getInstance();

        //------------Initialization Section---------
//        progressDialog = new ProgressDialog(NextBookActivity.this);
//        progressDialog.setTitle("Please Wait");
//        progressDialog.setMessage("We working on your book..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        arrayList = new ArrayList<>();

        myBookList = findViewById(R.id.mybooksListView);
    }


    //------------Adapter all work----------
    public void Adapter()
    {
        adapter = new BaseAdapter() {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return arrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @SuppressLint("InflateParams")
            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                if (view == null)
                {
                    view = inflater.inflate(R.layout.list_item_search, null);
                }

                TextView bookName, authorName, quantity;
                bookName = view.findViewById(R.id.itemSearchBookName);
                authorName = view.findViewById(R.id.itemSearchAuthor);
                quantity = view.findViewById(R.id.itemSearchQuantity);

                bookName.setText(arrayList.get(position).getBookName());
                authorName.setText(arrayList.get(position).getAuthorName());
                quantity.setText(arrayList.get(position).getTime());

                return view;
            }
        };
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
