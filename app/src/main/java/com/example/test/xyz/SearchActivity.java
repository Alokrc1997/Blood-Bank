package com.example.test.xyz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText searchText;
    Button imageView;
    ListView listView;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference db;
    int a=0;
    int b=0;
    Thread thread;
    ArrayList<Data> arrayList = new ArrayList<Data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchText = findViewById(R.id.searchText);
        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.listView);


        mAuth = FirebaseAuth.getInstance();
        arrayList.clear();
        user = mAuth.getCurrentUser();
        final String currentId = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("User Information");
        Bundle bundle = getIntent().getExtras();
        final String search = bundle.getString("search");


        db.addValueEventListener(new ValueEventListener() {
            Data user = new Data();



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               a= (int)dataSnapshot.getChildrenCount();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.getKey();
                    b++;
                    if (!id.equals(currentId)) {
                        user = ds.getValue(Data.class);
                        String blood = user.getBlood();
                        if (blood.equals(search)) {
                            Toast.makeText(getApplicationContext(), "Blood found", Toast.LENGTH_SHORT).show();
                            String name = user.getNam();
                            String phone = user.getPhon();
                            Data d = new Data(name, phone);
                            arrayList.add(d);

                        }
                    }
                }
                thread=Thread.currentThread();
                thread.interrupt();
                if(a==b) {
                    thread.start();
                }



                if (arrayList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Blood not found", Toast.LENGTH_SHORT).show();
                } else {
                    CustomAdapter2 customAdapter2 = new CustomAdapter2();
                    listView.setAdapter(customAdapter2);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    @Override
    public void onBackPressed() {
        arrayList.clear();
        a=0;
        b=0;
        Toast.makeText(getApplicationContext(),"Back pressed",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SearchActivity.this,ProfileActivity.class));
    }

    class CustomAdapter2 extends BaseAdapter {


        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.list, null);


            TextView tvname = view.findViewById(R.id.textView1);
            TextView tvphone = view.findViewById(R.id.textView2);
            tvname.setText(arrayList.get(i).getNam());
            tvphone.setText(arrayList.get(i).getPhon());


            return view;
        }
    }
}




