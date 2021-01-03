package com.example.womanskills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ChooseSkillActivity extends AppCompatActivity  {
    Button btnAddSkills;
//RecyclerView recyclerView;
//ArrayList<String> Names= new ArrayList<>();
GridView simpleGrid;
    int logos[] = {R.drawable.logoa, R.drawable.logoc, R.drawable.keyskills, R.drawable.keyskills,R.drawable.logoa, R.drawable.logoc,R.drawable.logoa, R.drawable.logoc};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_skill);
        btnAddSkills =findViewById(R.id.addSkills);
        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
       // simpleList = (GridView) findViewById(R.id.simpleGridView);
//        Names.add(new Item("Website,IT",R.drawable.responsive));
//        Names.add(new Item("Writing & content",R.drawable.edit));
//        Names.add(new Item("Data entry",R.drawable.database));
//        Names.add(new Item("Design,media",R.drawable.design));
//        Names.add(new Item("Digital markiting",R.drawable.chat));
//        Names.add(new Item("Translation",R.drawable.profile));
//
//        MyAdapter myAdapter=new MyAdapter(this,R.layout.grid_view_items,birdList);
//        simpleList.setAdapter(myAdapter);

btnAddSkills.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),SetSkillActivity.class));
    }
});


        CustomAdapters customAdapter = new CustomAdapters(getApplicationContext(), logos);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(ChooseSkillActivity.this, SetSkillActivity.class);
                intent.putExtra("image", logos[position]); // put image data in Intent
                startActivity(intent); // start Intent
            }
        });
//        recyclerView = findViewById(R.id.skillsRecyclerView);
//        Names.add("Software Development");
//        Names.add("IT");
//        Names.add("HandiCraft");
//        Names.add("Painting");
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemViewCacheSize(20);
        //recyclerView.setAdapter(new RecyclerView(Adapter,getApplicationContext()));

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        //set Home Seleceted
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), AccountProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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