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


public class SingIn extends AppCompatActivity implements  View.OnClickListener {
    private TextView register,forgotPassword;
    private EditText email, password;
    private Button signin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    String mail,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        register = (TextView) findViewById(R.id.registerTextView);
        register.setOnClickListener(this);
        signin =(Button)findViewById(R.id.signinBtn);
        signin.setOnClickListener(this);
        email =(EditText) findViewById(R.id.EmailEditText);
        password =(EditText) findViewById(R.id.PasswordEditText);
        progressBar =(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        forgotPassword = (TextView)findViewById(R.id.forgotTextView);
        forgotPassword.setOnClickListener(this);
    }
    public void reg(View V){
        Intent i = new Intent(this,register.class);

        startActivity(i);

        finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.forgotTextView:
                startActivity(new Intent(this, ForgotPassword.class));
                break;

            case R.id.registerTextView:
                startActivity(new Intent(this, register.class));
                break;

            case R.id.signinBtn:
                Login();
                break;
        }

    }

    private void Login() {
        mail = email.getText().toString().trim();
        pass = password.getText().toString().trim();

        if(mail.isEmpty()){email.setError("email is required"); email.requestFocus(); return ;}
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){email.setError("please enter a valid email"); email.requestFocus(); return ;}
        if(pass.isEmpty()){password.setError("password is required"); password.requestFocus(); return ;}
        if(pass.length()<6){password.setError("password length is should be more than 6 digits"); password.requestFocus(); return ;}

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    // redirect to user Add
                    startActivity(new Intent(SingIn.this, Home.class));
                }
                else{   Toast.makeText(SingIn.this, "failed to Login ",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);}
            }
        });

    }
}