package com.example.Diary;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity implements  View.OnClickListener {
    private Button logout;
    private Button PhomeBtn,PaddBtn,PprofileBtn;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        PhomeBtn  =(Button)findViewById(R.id.PhomeBtn);
        PhomeBtn.setOnClickListener(this);
        PaddBtn  =(Button)findViewById(R.id.PaddBtn);
        PaddBtn.setOnClickListener(this);
        PprofileBtn  =(Button)findViewById(R.id.PprofileBtn);
        PprofileBtn.setOnClickListener(this);
        logout= (Button)findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile.this,SingIn.class));
            }
        });

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        final TextView greeting= (TextView) findViewById(R.id.greetingTextView);
        final TextView name= (TextView) findViewById(R.id.NameTextView);
        final TextView useremail= (TextView) findViewById(R.id.EmailtextView);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile=snapshot.getValue(User.class);
                if(userprofile != null){

                    String fullname =userprofile.name;
                    String email =userprofile.email;
                    greeting.setText("welcome "+ fullname + "!");
                    name.setText(fullname);
                    useremail.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this,"something wrong happened!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.PhomeBtn:
                startActivity(new Intent(this, Home.class));
                break;

            case R.id.PaddBtn:
                startActivity(new Intent(this, Add.class));
                break;

        }
    }
}