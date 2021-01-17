package com.example.womanskills;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.library.NavigationBar;
import com.library.NvTab;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements NavigationBar.OnTabSelected, NavigationBar.OnTabClick {
    private static final String TAG = "SignupActivity";
    CardView btnFirst,btnSecond,btnFinish;
    LinearLayout layoutFirst,layoutSecond,layoutFinal;
   // TextInputLayout userName, userDob;

    private NavigationBar bar;
    private int position = 0;
    private EditText mDisplayDate,userName,password,rePassword,fullName,userEmail,userCnic,userAddress;
    Button btnMale,btnFemale,btnOther;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bar = (NavigationBar) findViewById(R.id.navBar);
        btnFirst = (CardView) findViewById(R.id.btn_firstStep);
        btnSecond = (CardView) findViewById(R.id.btn_secondStep);
        btnFinish = (CardView) findViewById(R.id.btn_finalStep);
        layoutFirst =(LinearLayout) findViewById(R.id.firststep);
        layoutSecond =(LinearLayout) findViewById(R.id.secondstep);
        layoutFinal =(LinearLayout) findViewById(R.id.finalstep);
        bar.setOnTabSelected(this);
        bar.setOnTabClick(this);
        setup(true, 3);
        //StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        // stateProgressBar.setStateDescriptionData(descriptionData);    }
        password = (EditText) findViewById(R.id.txtPassword);
        userName = (EditText) findViewById(R.id.txtName);
        rePassword =(EditText) findViewById(R.id.txtRePassword);
        mDisplayDate = (EditText) findViewById(R.id.datepicker);
        fullName =(EditText) findViewById(R.id.txtFullName);
        userEmail = (EditText) findViewById(R.id.txtEmail);
        userCnic = (EditText) findViewById(R.id.txtCnic);
        userAddress = (EditText) findViewById(R.id.txtAddress);

        //Button
        btnMale =(Button)findViewById(R.id.btnMale);
        btnFemale =(Button)findViewById(R.id.btnFeMale);
        btnOther =(Button)findViewById(R.id.btnOther);




        //Set Date

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
                layoutFirst.setVisibility(View.GONE);
                layoutSecond.setVisibility(View.VISIBLE);
                layoutFinal.setVisibility(View.GONE);
                bar.setCurrentPosition(++position);
                break;
            case R.id.btn_secondStep:
                layoutFinal.setVisibility(View.VISIBLE);
                layoutSecond.setVisibility(View.GONE);
                layoutFirst.setVisibility(View.GONE);
                bar.setCurrentPosition(++position);
                break;
            case R.id.btn_finalStep:
                layoutFirst.setVisibility(View.VISIBLE);
                layoutSecond.setVisibility(View.GONE);
                layoutFinal.setVisibility(View.GONE);
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

//    public void ShowHidePass(View view){
//
//        if(view.getId()==R.id.show_pass_btn){
//
//            if(edit_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//                ((new ImageView(view)).setImageResource(R.drawable.eye_24px));
//
//                //Show Password
//                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//            else{
//                ((ImageView)(view)).setImageResource(R.drawable.hide_24px);
//
//                //Hide Password
//                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//
//            }
//        }
//    }
}