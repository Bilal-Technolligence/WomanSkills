package com.example.womanskills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileDetail extends AppCompatActivity {
    ImageView profileImage;
    TextView name, email;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    TextView add1;
    RecyclerView recyclerView;
    ArrayList<ServiceAttr> serviceAttrs;
    ArrayList<ProductAttr> productAttrs;
    ProgressDialog progressDialog;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        name = findViewById(R.id.txtEmail);
        email = findViewById(R.id.txtName);
        add1 = findViewById(R.id.txtAdd1);
        profileImage = findViewById(R.id.txtImg);
        uid = getIntent().getStringExtra("uid");
        recyclerView = findViewById(R.id.recycler);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        progressDialog.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        serviceAttrs = new ArrayList<ServiceAttr>();
        productAttrs = new ArrayList<ProductAttr>();
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String eName = dataSnapshot.child("email").getValue().toString();
                    String eEmail = dataSnapshot.child("fullname").getValue().toString();
                    name.setText(eName);
                    email.setText(eEmail);
                    try {
                        String img = dataSnapshot.child("img").getValue().toString();
                        if (!img.equals("")) {
                            Picasso.get().load(img).into(profileImage);
                        }
                    } catch (Exception e) {
                    }
                    try {
                        String summary = dataSnapshot.child("summary").getValue().toString();
                        add1.setText(summary);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Products").orderByChild("userId").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    productAttrs.clear();
                    //profiledata.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ProductAttr p = dataSnapshot1.getValue(ProductAttr.class);
                        productAttrs.add(p);
                    }
                    Collections.reverse(serviceAttrs);
                    recyclerView.setAdapter(new ProductListAdapterNoClick(productAttrs, getApplicationContext(), ProfileDetail.this));
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("Services").orderByChild("userId").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    serviceAttrs.clear();
                    //profiledata.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ServiceAttr p = dataSnapshot1.getValue(ServiceAttr.class);
                        serviceAttrs.add(p);
                    }
                    Collections.reverse(serviceAttrs);
                    recyclerView.setAdapter(new ServiceListAdapterNoClick(serviceAttrs, getApplicationContext(), ProfileDetail.this));
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}