package com.example.Diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener{

    public RecyclerView recyclerListView;
    public  Multi_infoAdapter  myAdapter;
    DatabaseReference databaseReference;
    private Button PhomeBtn,PaddBtn,PprofileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String userID =   FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        creatingLayouts();

        PhomeBtn  =(Button)findViewById(R.id.PhomeBtn);
        PhomeBtn.setOnClickListener(this);
        PaddBtn  =(Button)findViewById(R.id.PaddBtn);
        PaddBtn.setOnClickListener(this);
        PprofileBtn  =(Button)findViewById(R.id.PprofileBtn);
        PprofileBtn.setOnClickListener(this);

        }

    public void creatingLayouts(){


        recyclerListView=(RecyclerView) findViewById(R.id.recylerview_list);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new Multi_infoAdapter(this);
        updateAdapter();
        recyclerListView.setAdapter(myAdapter);
    }




    public void updateAdapter(){
        final String userId =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        final List<Multi_info> listUsers= new ArrayList<>();
        databaseReference.child("Users").child( userId).child("diary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listUsers.add(dataSnapshot.getValue( Multi_info.class));
                displayUsers(listUsers);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Canceled", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void displayUsers(List<Multi_info> ls){
        recyclerListView.setVisibility( View.VISIBLE);

        myAdapter.setData(ls);
        myAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.PaddBtn:
                startActivity(new Intent(this, Add.class));
                break;
            case R.id.PprofileBtn:
                startActivity(new Intent(this, profile.class));
                break;
        }
    }
    }
