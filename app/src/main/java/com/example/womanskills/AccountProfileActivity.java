package com.example.womanskills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AccountProfileActivity extends AppCompatActivity {
    ImageView profileImage;
    private Uri imagePath;
    TextView name, email;
    int count = 0;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    TextView add1, add2 , txt1, txt2;
    Button addSummary, addProducts , addServices;
    EditText add;
    int i = 0;
    RecyclerView recyclerView;
    ArrayList<ServiceAttr> serviceAttrs;
    ArrayList<ProductAttr> productAttrs;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);
        name = findViewById(R.id.txtEmail);
        email = findViewById(R.id.txtName);
        add1 = findViewById(R.id.txtAdd1);
        add2 = findViewById(R.id.txtAdd2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        addSummary = findViewById(R.id.btnAddSummary);
        addProducts = findViewById(R.id.btnAddProducts);
        addServices = findViewById(R.id.btnAddServices);
        add = findViewById(R.id.edtSummary);
        addSummary.setVisibility(View.VISIBLE);
        add2.setVisibility(View.VISIBLE);
        add.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recycler);
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading..... ");
        //progressDialog.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        serviceAttrs = new ArrayList<ServiceAttr>();
        productAttrs = new ArrayList<ProductAttr>();

        databaseReference.child("Products").orderByChild("userId").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    addServices.setVisibility(View.GONE);
                    txt1.setVisibility(View.GONE);
                    txt2.setVisibility(View.GONE);
                    databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                productAttrs.clear();
                                //profiledata.clear();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    ServiceAttr p = dataSnapshot1.getValue(ServiceAttr.class);
                                    serviceAttrs.add(p);
                                }
                                Collections.reverse(serviceAttrs);
                                recyclerView.setAdapter(new ServiceListAdapter(serviceAttrs, getApplicationContext()));
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
                else
                    addServices.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("Services").orderByChild("userId").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    addProducts.setVisibility(View.GONE);
                    txt1.setVisibility(View.GONE);
                    txt2.setVisibility(View.GONE);
                    databaseReference.child("Services").addValueEventListener(new ValueEventListener() {
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
                                recyclerView.setAdapter(new ServiceListAdapter(serviceAttrs, getApplicationContext()));
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
                else {
                    addProducts.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountProfileActivity.this , AddProducts.class));
            }
        });
        addServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountProfileActivity.this , SetSkillActivity.class));
            }
        });
        //Getting value from Firebase Database
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String eName = dataSnapshot.child("email").getValue().toString();
                    String eEmail = dataSnapshot.child("fullname").getValue().toString();
                    name.setText(eName);
                    email.setText(eEmail);
                    try {
                        String summary = dataSnapshot.child("summary").getValue().toString();
                        if (summary.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please add something about you.", Toast.LENGTH_SHORT).show();
                        } else {
                            add1.setVisibility(View.GONE);
                            add2.setText(summary);
                            addSummary.setVisibility(View.VISIBLE);
                            addSummary.setText("Update Summary");
                        }
                    }
                    catch (Exception e){}
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                databaseReference.child("Users").child(uid).child("summary").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            add.setText(dataSnapshot.getValue().toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(i==1) {
                    add2.setVisibility(View.GONE);
                    add.setVisibility(View.VISIBLE);
                }
                else {

                    String S = add.getText().toString();
                        if (S.isEmpty())
                            add.setError("Field can not be Empty");
                        else
                        {
                            databaseReference.child("Users").child(uid).child("summary").setValue(S);
                            add2.setVisibility(View.VISIBLE);
                            add.setVisibility(View.GONE);
                            i =0;
                        }
                }
            }
        });
//        profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 2);
//            }
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        //set Home Seleceted
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), ChooseSkillActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_message:
                        startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}