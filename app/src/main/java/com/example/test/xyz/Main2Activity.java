package com.example.test.xyz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText searchText;
    Button imageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        searchText=findViewById(R.id.searchText);
        imageView=findViewById(R.id.imageView);
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String search = searchText.getText().toString().trim();
                if(search.equals("A+")||search.equals("A-")||search.equals("B+")||search.equals("B-")||search.equals("AB+")||search.equals("AB-")||search.equals("O+")||search.equals("O-"))
                {
                    Intent i = new Intent(Main2Activity.this, SearchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("search", search);
                    i.putExtras(bundle);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter correct Blood group",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

            }
        });


    }
}
