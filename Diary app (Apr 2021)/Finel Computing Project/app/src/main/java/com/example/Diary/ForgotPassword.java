package com.example.Diary;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
private EditText Uemail;
    private Button reset;
    private ProgressBar resetbar;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Uemail=(EditText)findViewById(R.id.UemailEditText);
        reset=(Button) findViewById(R.id.resetBTN);
        resetbar=(ProgressBar) findViewById(R.id.resetProgressBar);
        resetbar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    public void back(View v){
        Intent intent = new Intent(this, SingIn.class);
        startActivity(intent);

    }
    private void resetPassword() {
        String email=Uemail.getText().toString().trim();
        if(email.isEmpty()){Uemail.setError("Email is required"); Uemail.requestFocus(); return;}
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){Uemail.setError("please provide a valid Email!"); Uemail.requestFocus(); return;}
        resetbar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your Email to reset password",Toast.LENGTH_LONG).show();
                    resetbar.setVisibility(View.GONE);
                    startActivity(new Intent(ForgotPassword.this,SingIn.class));
                }else{Toast.makeText(ForgotPassword.this,"Try again ! something wrong happened!",Toast.LENGTH_LONG).show();
                       resetbar.setVisibility(View.GONE);}
            }
        });
    }
}