package com.example.Diary;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener{
    private TextView back,register;
    private EditText name,email,password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        back =(TextView) findViewById(R.id.backTextView);
        back.setOnClickListener(this);
        register=(Button) findViewById(R.id.registerbtn);
        register.setOnClickListener(this);
        name=(EditText)findViewById(R.id.nameEditText);
        email=(EditText)findViewById(R.id.emailEditText);
        password=(EditText)findViewById(R.id.passwordEditText);
        progressBar =(ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.backTextView:
                startActivity(new Intent(this, SingIn.class));
                break;
            case R.id.registerbtn:
                register();
                break;

        }

    }

    private void register() {

        String emailv = email.getText().toString().trim();
        String usernamev = name.getText().toString().trim();
        String passv = password.getText().toString().trim();

        if(usernamev.isEmpty()){
            name.setError("name is required");
            name.requestFocus();
            return;
        }

        if(emailv.isEmpty()){
            email.setError("email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailv).matches()){
            email.setError("please provide a valid email");
            email.requestFocus();
            return;
        }
        if(passv.isEmpty()){
            password.setError("password is required");
            password.requestFocus();
            return;
        }
        if(passv.length()<6){
            password.setError("password should be more than 6 digits");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailv,passv)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            User user =new User(usernamev,emailv);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register.this, "user has been registered",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(register.this, SingIn.class));


                                    }else{
                                        Toast.makeText(register.this, "not registered",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }else{ Toast.makeText(register.this, "not registered",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);}
                    }
                });


    }
}