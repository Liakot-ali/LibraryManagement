package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class DepartmentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarTextView;
    String departmentName;
    ListView departmentListView;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ArrayList<AddBookClass> arrayList;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        Initialize();

        DatabaseReference departmentRef = database.getReference("Student").child("Department").child(departmentName);
        departmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    AddBookClass newBook = snapshot1.getValue(AddBookClass.class);
                    arrayList.add(newBook);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        Adapter();
        departmentListView.setAdapter(adapter);

        //----------For Specific listItem---------
        departmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
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
                Intent intent = new Intent(DepartmentActivity.this, BookActivity.class);
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


//------------Out of main section-------
    private void Initialize() {

        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //------------Initialize--------------

        database = FirebaseDatabase.getInstance("https://library-management-8d07f-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mAuth = FirebaseAuth.getInstance();

        departmentName = getIntent().getStringExtra("department");
        toolbarTextView = findViewById(R.id.toolbarTextView);
        departmentListView =  findViewById(R.id.departmentListView);
        arrayList = new ArrayList<>();

        toolbarTextView.setText(departmentName);
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

}
