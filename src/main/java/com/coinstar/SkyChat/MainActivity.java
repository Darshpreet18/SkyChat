package com.coinstar.fronx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
FirebaseAuth auth;
adapter Adapter;
FirebaseDatabase database;
ArrayList<Users>usersArrayList;
RecyclerView mainuserrecyclerview;
ImageView imglogoutop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("user");
        auth = FirebaseAuth.getInstance();
        usersArrayList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    usersArrayList.add(users);

                }
                Adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//        imglogoutop.findViewById(R.id.logoutimg);
//        imglogoutop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialog dialog = new Dialog(MainActivity.this,R.style.dialoge);
//                dialog.setContentView(R.layout.dialoge_layout);
//                Button no,yes;
//                yes = dialog.findViewById(R.id.yesbnt);
//                no = dialog.findViewById(R.id.nobnt);
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        FirebaseAuth.getInstance().signOut();
//                        Intent intent = new Intent(MainActivity.this,login.class);
//                        startActivity(intent);
//                    }
//                });
//                no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });
        mainuserrecyclerview  = findViewById(R.id.mainuserrecyclerview);
        mainuserrecyclerview.setLayoutManager(new LinearLayoutManager((this)));
        Adapter = new adapter(MainActivity.this,usersArrayList);
        mainuserrecyclerview.setAdapter(Adapter);
        if(auth.getCurrentUser()== null){
            Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
        }
    }
}