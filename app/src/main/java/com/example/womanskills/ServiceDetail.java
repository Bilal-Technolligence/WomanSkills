package com.example.womanskills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ServiceDetail extends AppCompatActivity {
    ImageView img1, profile;
    TextView frwd, back, name, title, description;
    Button contact;
    String id , uid;
    String i1, i2, i3;
    int i=0;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        id = getIntent().getStringExtra("id");
        img1 = (ImageView) findViewById(R.id.img1);
        profile = (ImageView) findViewById(R.id.imgProfile);
        name = (TextView) findViewById(R.id.txtName);
        title = (TextView) findViewById(R.id.txtTitle);
        description = (TextView) findViewById(R.id.txtDescription);
        frwd = (TextView) findViewById(R.id.btnFrwd);
        back = (TextView) findViewById(R.id.btnBack);
        contact = (Button) findViewById(R.id.btnContact);
        databaseReference.child("Services").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    back.setVisibility(View.GONE);
                    i1 = dataSnapshot.child("image1").getValue().toString();
                    i2 = dataSnapshot.child("image2").getValue().toString();
                    i3 = dataSnapshot.child("image3").getValue().toString();
                    description.setText(dataSnapshot.child("decription").getValue().toString());
                    title.setText(dataSnapshot.child("title").getValue().toString());
                    uid = dataSnapshot.child("userId").getValue().toString();
                    databaseReference.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                try {
                                    String Name = snapshot.child("fullname").getValue().toString();
                                    name.setText(Name);
                                    String img = snapshot.child("img").getValue().toString();
                                    Picasso.get().load(img).into(profile);
                                }
                                catch (Exception e){}
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    frwd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            i++;
                            if(i==0){
                                Picasso.get().load(i1).into(img1);
                                back.setVisibility(View.GONE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                            if(i==2){
                                Picasso.get().load(i3).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.GONE);
                            }
                            if (i==1){
                                Picasso.get().load(i2).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            i--;
                            if(i==0){
                                Picasso.get().load(i1).into(img1);
                                back.setVisibility(View.GONE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                            if(i==2){
                                Picasso.get().load(i3).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.GONE);
                            }
                            if (i==1){
                                Picasso.get().load(i2).into(img1);
                                back.setVisibility(View.VISIBLE);
                                frwd.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent o = new Intent(ServiceDetail.this , Chat.class);
                            o.putExtra("chaterId", uid);
                            startActivity(o);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}