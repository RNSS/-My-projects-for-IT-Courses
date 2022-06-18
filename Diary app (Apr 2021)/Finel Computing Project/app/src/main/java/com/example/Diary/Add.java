package com.example.Diary;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Add extends AppCompatActivity implements View.OnClickListener {
    private Button add;
    private EditText title,diary,additional;
    private Button PhomeBtn,PaddBtn,PprofileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //declarion
    add = findViewById(R.id.add);
    title = findViewById(R.id.title);
    diary = findViewById(R.id.diary);
    additional = findViewById(R.id.additional);
    PhomeBtn  =(Button)findViewById(R.id.PhomeBtn);
    PhomeBtn.setOnClickListener(this);
    PaddBtn  =(Button)findViewById(R.id.PaddBtn);
    PaddBtn.setOnClickListener(this);
    PprofileBtn  =(Button)findViewById(R.id.PprofileBtn);
    PprofileBtn.setOnClickListener(this);

    //adding info to db
    add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String  txt_title = title.getText().toString().trim();
            String  txt_diary = diary.getText().toString().trim();
            String  txt_additional = additional.getText().toString().trim();
           String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            HashMap<String,Object> map = new HashMap<>();
            map.put("Title",txt_title);
            map.put("Diary",txt_diary);
            map.put("Additional",txt_additional);
            map.put("UserID",userID);


            if (txt_title.isEmpty()) {title.setError("title is required"); title.requestFocus(); return ;}

            else {
                String key = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("diary").push().getKey();
                map.put("ID_Number",key);
                FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("diary").child(key).updateChildren(map);
                Toast.makeText(Add.this, txt_title +" Added",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Add.this, Home.class));

            }
        }
    });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.PhomeBtn:
                startActivity(new Intent(this, Home.class));
                break;

            case R.id.PprofileBtn:
                startActivity(new Intent(this, profile.class));
                break;
        }
    }
}
