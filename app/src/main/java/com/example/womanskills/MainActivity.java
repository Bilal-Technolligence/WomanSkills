package com.example.womanskills;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.library.NavigationBar;
import com.library.NvTab;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickView(View v) {
        switch (v.getId()) {

            case R.id.btn_submit:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }



}