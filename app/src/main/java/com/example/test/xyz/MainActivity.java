package com.example.test.xyz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    EditText email,pass;
    TextView textView;
    Button signin;
    String mail,password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email= findViewById(R.id.editText1);
        pass= findViewById(R.id.editText2);
        textView= findViewById(R.id.textView1);
        progressBar=findViewById(R.id.progress);
        mAuth=FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);
        signin=findViewById(R.id.button1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               login();
            }
        });

    }
    void login()
    {
        progressBar.setVisibility(View.VISIBLE);
        mail=email.getText().toString().trim();
        password=pass.getText().toString().trim();

        if(mail.isEmpty())
        {
            progressBar.setVisibility(View.INVISIBLE);
            email.setError("Enter the mail");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            progressBar.setVisibility(View.INVISIBLE);
            email.setError("Enter the correct mail");
            email.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            progressBar.setVisibility(View.INVISIBLE);
            pass.setError("Minimum 6 digits required");
            pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
