package com.example.womanskills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    ArrayList<String> chaterId=new ArrayList<>();
    ArrayList<UserAttr> userAttrs=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dref.child("ChatList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    chaterId.clear();
                    userAttrs.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        if (ds.exists()) {
                            if (ds.child("receiverId").getValue().equals(userId))
                            {
                                chaterId.add(ds.child("senderId").getValue().toString());
                            }
                            if(ds.child("senderId").getValue().equals(userId)) {
                                chaterId.add(ds.child("receiverId").getValue().toString());
                            }
                        }
                    }
                    showChatList();
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        //set Home Seleceted
        bottomNavigationView.setSelectedItemId(R.id.nav_message);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), ChooseSkillActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), AccountProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_setting:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private void showChatList() {
        Set<String> set = new HashSet<>(chaterId);
        chaterId.clear();
        chaterId.addAll(set);

        for(int i=0;i<chaterId.size();i++) {
            dref.child("Users").child(chaterId.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){
                        UserAttr p = dataSnapshot.getValue(UserAttr.class);
                        userAttrs.add(p);
                    }
                    recyclerView.setAdapter(new ChatListAdapter(userAttrs , getApplicationContext() , NotificationActivity.this));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
}