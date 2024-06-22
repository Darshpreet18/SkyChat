package com.coinstar.fronx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatwin extends AppCompatActivity {
    String reciverimg,reciverUid,reciverName, SenderUID;
    TextView reciverNName;
    CircleImageView profile;
CardView sentbtn;
EditText textmessg;
FirebaseAuth firebaseAuth;
FirebaseDatabase database;
public static String senderImg;
public static String reciverIImg;
String senderRoom, reciverRoom;
RecyclerView mmessangesAdpter;
ArrayList<msgmodleclass> messagessArraylist;
messagesadapter mmessagesAdapter;


@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwin);
        getSupportActionBar().hide();
        mmessangesAdpter = findViewById(R.id.msgadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessangesAdpter.setLayoutManager(linearLayoutManager);
        mmessagesAdapter = new messagesadapter(chatwin.this,messagessArraylist);
        mmessangesAdpter.setAdapter(mmessagesAdapter);

        reciverName = getIntent().getStringExtra("nameeee");
        reciverimg = getIntent().getStringExtra("reciverImg");
        reciverUid = getIntent().getStringExtra("uid");
        messagessArraylist= new ArrayList<>();

        sentbtn = findViewById(R.id.sendbtnn);
        textmessg = findViewById(R.id.textmsg);
        sentbtn = findViewById(R.id.sendbtnn);
        textmessg = findViewById(R.id.textmsg);


        profile =findViewById(R.id.profileimgg);
        reciverNName = findViewById(R.id.recivername);
        Picasso.get().load(reciverimg).into(profile);
        reciverNName.setText(""+reciverName);
        DatabaseReference reference = database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatreference = database.getReference().child("user").child(senderRoom).child("messages");
        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    msgmodleclass messages = dataSnapshot.getValue(msgmodleclass.class);
                    messagessArraylist.add(messages);
                }
                mmessagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
senderImg = snapshot.child("profilepic").getValue().toString();
reciverIImg = reciverimg;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        SenderUID = firebaseAuth.getUid();
        senderRoom = SenderUID +reciverUid;
        reciverRoom = reciverUid+SenderUID;



        sentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textmessg.getText().toString();
if (message.isEmpty()){
    Toast.makeText(chatwin.this,"Enter the message first",Toast.LENGTH_SHORT).show();

}
textmessg.setText("");
Date date = new Date();
msgmodleclass messagess = new msgmodleclass(message,SenderUID,date.getTime());
database =FirebaseDatabase.getInstance();
database.getReference().child("chats").child("senderRoom").child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
database.getReference().child("chats").child("reciverRoom").child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {

    }
});
    }
});
            }
        });






    }
}