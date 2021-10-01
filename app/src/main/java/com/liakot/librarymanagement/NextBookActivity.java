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

public class NextBookActivity extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ArrayList<AddBookClass> arrayList;
    ListView nextListView;
    ProgressDialog progressDialog;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_book);

        Initialize();

        String userId = mAuth.getUid();
        assert userId != null;
        DatabaseReference myRef = database.getReference("Student").child("User").child(userId).child("NextList");
        //-------------retrieve value from firebase to ArrayList-------------
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    AddBookClass newBook = snap.getValue(AddBookClass.class);
                    arrayList.add(newBook);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Please Check your Internet connection",Toast.LENGTH_SHORT).show();
            }
        });
        Adapter();
        nextListView.setAdapter(adapter);
        progressDialog.dismiss();

        //---------------------for specific item click------------------
        nextListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddBookClass newBook = arrayList.get(position);
                String bookName, authorName, edition, page, department, quantity, bookPosition, bookId;
                bookName = newBook.getBookName();
                authorName = newBook.getAuthorName();
                edition = newBook.getEdition();
                page = newBook.getPage();
                department = newBook.getDepartment();
                quantity = newBook.getQuantity();
                bookPosition = newBook.getPosition();
                bookId = newBook.getBookId();

                //---TODO
                Intent intent = new Intent(NextBookActivity.this, UserNextBookActivity.class);
                intent.putExtra("bookName", bookName);
                intent.putExtra("authorName", authorName);
                intent.putExtra("edition", edition);
                intent.putExtra("page", page);
                intent.putExtra("department", department);
                intent.putExtra("quantity", quantity);
                intent.putExtra("position", bookPosition);
                intent.putExtra("bookId", bookId);
                startActivity(intent);
            }
        });


    }

    public void Initialize()
    {
        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mAuth = FirebaseAuth.getInstance();

        //------------Initialization Section---------
        progressDialog = new ProgressDialog(NextBookActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("We working on your book..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        arrayList = new ArrayList<>();

        nextListView = findViewById(R.id.nextBookListView);

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
                quantity.setText(arrayList.get(position).getQuantity());

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
