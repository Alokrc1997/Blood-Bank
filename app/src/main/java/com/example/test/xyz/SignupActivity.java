package com.example.test.xyz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    EditText nam,phon,email,pass;
    Button reg;
    String name,phone,mail,password;
    TextView textView;
    Spinner spinner;
    String blood;
    String group[]={"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nam=findViewById(R.id.editText1);
        phon=findViewById(R.id.editText2);
        email =findViewById(R.id.editText3);
        pass =findViewById(R.id.editText4);
        reg=findViewById(R.id.button1);
        textView=findViewById(R.id.textView1);
        spinner= findViewById(R.id.spinner);
        mAuth=FirebaseAuth.getInstance();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,group);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                blood=((TextView)view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Choose any one",Toast.LENGTH_SHORT).show();

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
            }
        });

    }
    void createAccount()
    {
        name=nam.getText().toString().trim();
        phone=phon.getText().toString().trim();
        mail=email.getText().toString().trim();
        password=pass.getText().toString().trim();
        if(name.isEmpty())
        {
            nam.setError("Enter the name");
            nam.requestFocus();
            return;
        }
        if(phone.isEmpty())
        {
            phon.setError("Enter the mobile number");
            phon.requestFocus();
            return;
        }
        if(phone.length()!=10)
        {
            phon.setError("Enter the correct mobile no");
            phon.requestFocus();
            return;
        }
        if(mail.isEmpty())
        {
            email.setError("Enter the mail");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            email.setError("Enter the correct mail");
            email.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            pass.setError("Minimum 6 digits required");
            pass.requestFocus();
            return;
        }
       mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

               if(task.isSuccessful())
               {


                   FirebaseUser user=mAuth.getCurrentUser();
                   DatabaseReference db= FirebaseDatabase.getInstance().getReference("User Information");
                   Data data=new Data(name,phone,blood);
                   db.child(user.getUid()).setValue(data);
                   Toast.makeText(getApplicationContext(),"Signup Completed",Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(SignupActivity.this,ProfileActivity.class));

               }
               else if(task.getException()instanceof FirebaseAuthUserCollisionException)
               {
                   Toast.makeText(getApplicationContext(),"Account Already Exist",Toast.LENGTH_SHORT).show();
               }
           }
       });


    }
}
