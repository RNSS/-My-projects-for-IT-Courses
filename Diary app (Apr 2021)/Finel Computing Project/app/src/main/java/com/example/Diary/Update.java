package com.example.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Update extends AppCompatActivity implements View.OnClickListener {

    EditText edTit, edDia, edAdd;
    Button btnUpdate;
    String userid,idNum, tit,dia,add;
    private Button PhomeBtn,PaddBtn,PprofileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edTit = findViewById(R.id.edTitel);
        edDia = findViewById(R.id.edDiary);
        edAdd = findViewById(R.id.edAdditional);

        btnUpdate = findViewById(R.id.updatebtn);

        Intent intent = getIntent();
        idNum = intent.getStringExtra("idNum");
        userid = intent.getStringExtra("UserId");
        tit = intent.getStringExtra("title");
        dia = intent.getStringExtra("diary");
        add = intent.getStringExtra("additional");

        PhomeBtn  =(Button)findViewById(R.id.PhomeBtn);
        PhomeBtn.setOnClickListener(this);
        PaddBtn  =(Button)findViewById(R.id.PaddBtn);
        PaddBtn.setOnClickListener(this);
        PprofileBtn  =(Button)findViewById(R.id.PprofileBtn);
        PprofileBtn.setOnClickListener(this);

        edTit.setText(tit);
        edDia.setText(dia);
        edAdd.setText(add);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utitle, udiary, uadditional;

                utitle = edTit.getText().toString();
                udiary = edDia.getText().toString();
                uadditional = edAdd.getText().toString();

                Multi_info multi_info = new Multi_info(idNum,utitle,udiary,uadditional,userid);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference.child("Users").child(userId).child("diary").child(idNum).setValue(multi_info);;

                Toast.makeText(Update.this, utitle +" Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Update.this, Home.class));
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
            case R.id.PprofileBtn:
                startActivity(new Intent(this, profile.class));
                break;
        }
    }
    public void back(View v){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }
}