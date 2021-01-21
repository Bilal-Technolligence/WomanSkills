package com.example.womanskills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.library.NavigationBar;
import com.library.NvTab;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button btnSkill;
    ArrayList personNames = new ArrayList<>(Arrays.asList("Design a creative minimal logo", "Design a creative minimal logo", "Design animation videos", "Design a creative minimal logo", "Design a creative minimal logo", "Design animation videos","Design a creative minimal logo", "Design a creative minimal logo", "Design animation videos","Design a creative minimal logo", "Design a creative minimal logo", "Design animation videos", "Design a creative minimal logo", "Design a creative minimal logo", "Design animation videos","Design a creative minimal logo", "Design a creative minimal logo", "Design animation videos"));
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.back, R.drawable.back, R.drawable.abc, R.drawable.logoa, R.drawable.logoa, R.drawable.logoc, R.drawable.download, R.drawable.logoa,R.drawable.logoa, R.drawable.logoc, R.drawable.download, R.drawable.logoa,R.drawable.back, R.drawable.back, R.drawable.abc, R.drawable.logoa, R.drawable.logoa, R.drawable.logoc, R.drawable.download, R.drawable.logoa,R.drawable.logoa));
   // DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSkill = findViewById(R.id.addSkill);
        btnSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dref.child("Skills/Category/Sub").setValue( "Android Developer" );
                startActivity(new Intent(getApplicationContext(),ChooseSkillActivity.class));
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // set a GridLayoutManager with 2 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, personNames,personImages);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        //set Home Seleceted
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), AccountProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), ChooseSkillActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_message:
                        startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


}