package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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

public class AdminSubmitListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView returnList;
    ArrayList<RequestBookClass> arrayList;
    BaseAdapter adapter;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_submit_list);

        Initialize();

        DatabaseReference returnRef = database.getReference("Admin").child("ReturnList");

        returnRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    RequestBookClass r = snapshot1.getValue(RequestBookClass.class);
                    arrayList.add(r);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        Adapter();
        returnList.setAdapter(adapter);

        //---------------------for specific item click------------------
        returnList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestBookClass newBook = arrayList.get(position);
                String bookName, authorName, studentName, studentId, bookEdition, bookQuantity, bookPosition, bookPage, bookDepartment, bookId, userId;

                bookName = newBook.getBookName();
                authorName = newBook.getAuthorName();
                studentName = newBook.getStudentName();
                studentId = newBook.getStudentId();
                bookEdition = newBook.getBookEdition();
                bookPosition = newBook.getBookPosition();
                bookQuantity = newBook.getBookQuantity();
                bookPage = newBook.getBookPage();
                bookDepartment = newBook.getBookDepartment();
                bookId = newBook.getBookId();
                userId = newBook.getUserId();


                Intent intent = new Intent(AdminSubmitListActivity.this, AdminSubmitBookActivity.class);
                intent.putExtra("bookName", bookName);
                intent.putExtra("authorName", authorName);
                intent.putExtra("studentName", studentName);
                intent.putExtra("studentId", studentId);
                intent.putExtra("bookEdition", bookEdition);
                intent.putExtra("bookPosition", bookPosition);
                intent.putExtra("bookQuantity", bookQuantity);      //---Missing bookRemaining Time
                intent.putExtra("bookPage", bookPage);
                intent.putExtra("bookDepartment", bookDepartment);
                intent.putExtra("bookId", bookId);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


    }

//----------Out of main section-------------
    private void Initialize() {

        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");

        //-------Initialization section--------

        returnList = findViewById(R.id.adminReturnListView);
        arrayList = new ArrayList<>();
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
                quantity.setText(arrayList.get(position).getStudentId());

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
