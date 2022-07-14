package com.example.androidexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class InfoActivity extends AppCompatActivity {
EditText editTextInfoActiSno,editTextInfoActiSname,editTextInfoActiSyear,editTextInfoActiSmajor,
        editTextInfoActiSscore;
        RadioButton radioButtonInfoM,radioButtonInfoW;
        Button buttonInfoFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setWidgets();
        setData();
        buttonInfoFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    private void setWidgets() {
        editTextInfoActiSno=   findViewById(R.id.editTextInfoActiSno);
        editTextInfoActiSname=   findViewById(R.id.editTextInfoActiSname);
        editTextInfoActiSyear=   findViewById(R.id.editTextInfoActiSyear);
        radioButtonInfoM=   findViewById(R.id.radioButtonInfoM);
        radioButtonInfoW=   findViewById(R.id.radioButtonInfoW);
        editTextInfoActiSmajor=   findViewById(R.id.editTextInfoActiSmajor);
        editTextInfoActiSscore=   findViewById(R.id.editTextInfoActiSscore);
        buttonInfoFinish=findViewById(R.id.buttonInfoFinish);
    }
    private void setData(){
        Intent intent=getIntent();
        StudentVo vo=(StudentVo)intent.getSerializableExtra("vo");
       String sno=vo.getSno();
        String sname=vo.getSname();
        int syear=vo.getSyear();
       String gender=vo.getGender().toUpperCase();
         String major=vo.getMajor();
       int score=vo.getScore();
       editTextInfoActiSno.setText(sno);
        editTextInfoActiSname.setText(sname);
        editTextInfoActiSyear.setText(String.valueOf(syear));
        if("M".equals(gender)){
            radioButtonInfoM.setChecked(true);
        }
        else if("W".equals(gender)){
            radioButtonInfoW.setChecked(true);
        }
        editTextInfoActiSmajor.setText(major);
        editTextInfoActiSscore.setText(String.valueOf(score));
    }
}