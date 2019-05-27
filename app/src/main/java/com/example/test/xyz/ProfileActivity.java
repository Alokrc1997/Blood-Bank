package com.example.test.xyz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class ProfileActivity extends AppCompatActivity {
    TextView name,mobile,blood,email;
    DatabaseReference db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.name2);
        mobile=findViewById(R.id.mobile2);
        blood=findViewById(R.id.blood2);
        email=findViewById(R.id.email2);
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        final String mail=user.getEmail();
        progressBar.setVisibility(View.VISIBLE);

        db= FirebaseDatabase.getInstance().getReference("User Information");
        db.addValueEventListener(new ValueEventListener() {
            Data data=new Data();
            String id=user.getUid();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    data=ds.getValue(Data.class);
                    String nam=data.getNam();
                    String mob=data.getPhon();
                    String blo=data.getBlood();
                    String id2=ds.getKey();
                    if(id.equals(id2))
                    {
                        name.setText(nam);
                        mobile.setText(mob);
                        blood.setText(blo);
                        email.setText(mail);
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search:
            {
                startActivity(new Intent(ProfileActivity.this,Main2Activity.class));
                break;
            }
            case R.id.logout:
            {
                mAuth.signOut();
                Toast.makeText(getApplicationContext(),"SignOut successfully",Toast.LENGTH_SHORT);
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                finish();
                break;

            }
        }
        return true;
    }
}
