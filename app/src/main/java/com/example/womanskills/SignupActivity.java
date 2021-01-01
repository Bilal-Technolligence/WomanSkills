package com.example.womanskills;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.library.NavigationBar;
import com.library.NvTab;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements NavigationBar.OnTabSelected, NavigationBar.OnTabClick {
    private static final String TAG = "SignupActivity";
    CardView btnFirst,btnSecond,btnFinish;
    LinearLayout llfirst,llsecond,llfinal;
    private NavigationBar bar;
    private int position = 0;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bar = (NavigationBar) findViewById(R.id.navBar);
        btnFirst = (CardView) findViewById(R.id.btn_firstStep);
        btnSecond = (CardView) findViewById(R.id.btn_secondStep);
        btnFinish = (CardView) findViewById(R.id.btn_finalStep);
        llfirst =(LinearLayout) findViewById(R.id.firststep);
        llsecond =(LinearLayout) findViewById(R.id.secondstep);
        llfinal =(LinearLayout) findViewById(R.id.finalstep);
        bar.setOnTabSelected(this);
        bar.setOnTabClick(this);
        setup(true, 3);
        //StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        // stateProgressBar.setStateDescriptionData(descriptionData);    }


     //Set Dtae
        mDisplayDate = (TextView) findViewById(R.id.datepicker);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignupActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.btn_firstStep:
                llfirst.setVisibility(View.GONE);
                llsecond.setVisibility(View.VISIBLE);
                llfinal.setVisibility(View.GONE);
                bar.setCurrentPosition(++position);
                break;
            case R.id.btn_secondStep:
                llfinal.setVisibility(View.VISIBLE);
                llsecond.setVisibility(View.GONE);
                llfirst.setVisibility(View.GONE);
                bar.setCurrentPosition(++position);
                break;
            case R.id.btn_finalStep:
                llfirst.setVisibility(View.VISIBLE);
                llsecond.setVisibility(View.GONE);
                llfinal.setVisibility(View.GONE);
                setup(true, 3);
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                finish();
                break;

        }
    }
    private void setup(boolean reset, int count) {
        if (reset)
            bar.resetItems();
        bar.setTabCount(count);
        bar.animateView(3000);
        bar.setCurrentPosition(position <= 0 ? 0 : position);
    }

    @Override
    public void onTabClick(int touchPosition, NvTab prev, NvTab nvTab) {

    }

    @Override
    public void onTabSelected(int touchPosition, NvTab prev, NvTab nvTab) {

    }
}