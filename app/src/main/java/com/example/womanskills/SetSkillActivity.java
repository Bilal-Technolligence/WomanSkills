package com.example.womanskills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SetSkillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_skill);

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
                    case R.id.nav_setting:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}