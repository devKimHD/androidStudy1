package com.example.androidexam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    StudentDao dao = StudentDao.getInstance();
    Button buttonInsert, buttonUpdate, buttonDelete,btnSearchName;
    ListView listView;
    List<StudentVo> listData = new ArrayList<>();
    DataBaseHelper helper;
    //dial components
    View dialView;
    EditText editTextDialogSno, editTextDialogSname, editTextDialogSyear, editTextDialogSmajor, editTextDialogSscore;
    EditText editTextName;

    RadioButton radioButtonM, radioButtonW;
    StudentListAdapter studentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWigets();
        setListeners();
        //mainActivity 용 helper 생성
        setHelper();
        dao.setHelper(helper);
        listData = dao.selectAllStudent();
        /*helper.getWritableDatabase();
        helper.close();*/
        createAdapter();

    }

    private void setWigets() {
        listView = findViewById(R.id.listView);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        btnSearchName=findViewById(R.id.btnSearchName);
        editTextName=findViewById(R.id.editTextName);
    }

    private void setHelper() {
        helper = new DataBaseHelper(this, "student.db", null, 1);
    }

    private void setListeners() {
        buttonInsert.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                StudentVo vo = listData.get(i);
                intent.putExtra("vo", vo);
                startActivity(intent);
            }
        });
        btnSearchName.setOnClickListener(this);
    }

    private void setDialComponetsIds(View dialView) {
        editTextDialogSno = dialView.findViewById(R.id.editTextDialogSno);
        editTextDialogSname = dialView.findViewById(R.id.editTextDialogSname);
        editTextDialogSyear = dialView.findViewById(R.id.editTextDialogSyear);
        editTextDialogSmajor = dialView.findViewById(R.id.editTextDialogSmajor);
        editTextDialogSscore = dialView.findViewById(R.id.editTextDialogSscore);
        radioButtonM = dialView.findViewById(R.id.radioButtonM);
        radioButtonW = dialView.findViewById(R.id.radioButtonW);

    }

    private void createAdapter() {
        studentListAdapter = new StudentListAdapter(this, R.layout.view_cell, listData);
        listView.setAdapter(studentListAdapter);

    }

    private void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onClick(View view) {
        dialView = View.inflate(this, R.layout.view_dialog, null);
        setDialComponetsIds(dialView);
        if (view == buttonInsert) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("입력창");
            //View dialView=View.inflate(this,R.layout.view_dialog,null);
            dialog.setView(dialView);
            dialog.setPositiveButton("취소", null);
            dialog.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setDialComponetsIds(dialView);
                    /*   editTextDialogSno=dialView.findViewById(R.id.editTextDialogSno);
                    editTextDialogSname=dialView.findViewById(R.id.editTextDialogSname);
                    editTextDialogSyear=dialView.findViewById(R.id.editTextDialogSyear);
                    editTextDialogSmajor=dialView.findViewById(R.id.editTextDialogSmajor);
                    editTextDialogSscore=dialView.findViewById(R.id.editTextDialogSscore);
                    radioButtonM=dialView.findViewById(R.id.radioButtonM);
                    radioButtonW=dialView.findViewById(R.id.radioButtonW);*/
                    //자료를 담을 vo
                    StudentVo vo = new StudentVo();
                    String sno = editTextDialogSno.getText().toString();
                    boolean isSno = vo.setSno(sno, listData);
                    if (!isSno) {
                        makeToast("잘못되거나 중복된 학번입니다");
                        return;
                    }
                    String sname = editTextDialogSname.getText().toString();
                    boolean isSname = vo.setSname(sname);
                    if (!isSname) {
                        makeToast("잘못된 이름입니다");
                        return;
                    }
                    int syear;
                    try {
                        syear = Integer.parseInt(editTextDialogSyear.getText().toString());
                    } catch (NumberFormatException e) {
                        makeToast("학년은 1~4학년까지입니다");
                        return;
                    }
                    boolean isSyear = vo.setSyear(syear);
                    if (!isSyear) {
                        makeToast("학년은 1~4학년까지입니다");
                        return;
                    }
                    String gender = null;
                    if (radioButtonM.isChecked()) {
                        gender = "M".toUpperCase();
                    } else if (radioButtonW.isChecked()) {
                        gender = "W".toUpperCase();
                    }
                    boolean isGender = vo.setGender(gender);
                    if (!isGender) {
                        makeToast("성별을 잘못 입력하셨습니다.");
                        return;
                    }

                    String major = editTextDialogSmajor.getText().toString();
                    boolean isMajor = vo.setMajor(major);
                    if (!isMajor) {
                        makeToast("전공이 적절하지 않거나 너무 깁니다");
                        return;
                    }
                    int score;
                    try{
                         score = Integer.parseInt(editTextDialogSscore.getText().toString());
                    }
                    catch(NumberFormatException e){
                        makeToast("점수는 3자리 숫자까지 입력가능합니다");
                        return;
                    }
                    boolean isScore = vo.setScore(score);
                    if (!isScore) {
                        makeToast("점수는 3자리 숫자까지 입력가능합니다");
                        return;
                    }
                    //vo 셋팅완료
                    // /*StudentVo vo=new StudentVo(sno,sname,syear,gender,major,score);*/
                    boolean success = dao.addStudentData(vo);
                    if (success) {
                        makeToast("입력에 성공하셨습니다");
                        int index=Integer.parseInt(vo.getSno())-1;
                        listData.add(index,vo);
                        studentListAdapter.notifyDataSetChanged();
                        return;

                    }
                }
            });
            dialog.show();
        } else if (view == buttonUpdate) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("수정창");
            dialog.setView(dialView);
            dialog.setPositiveButton("취소", null);
            dialog.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   /* editTextDialogSno=dialView.findViewById(R.id.editTextDialogSno);
                    editTextDialogSname=dialView.findViewById(R.id.editTextDialogSname);
                    editTextDialogSyear=dialView.findViewById(R.id.editTextDialogSyear);
                    editTextDialogSmajor=dialView.findViewById(R.id.editTextDialogSmajor);
                    editTextDialogSscore=dialView.findViewById(R.id.editTextDialogSscore);
                    radioButtonM=dialView.findViewById(R.id.radioButtonM);
                    radioButtonW=dialView.findViewById(R.id.radioButtonW);*/
                    setDialComponetsIds(dialView);
                    //자료를 담을 vo
                    StudentVo vo = new StudentVo();
                    String sno = editTextDialogSno.getText().toString();
                    boolean isSno = vo.setUpDateDelSno(sno,listData);
                    if (!isSno) {
                        makeToast("잘못되거나 없는 학번입니다");
                        return;
                    }
                    String sname = editTextDialogSname.getText().toString();
                    boolean isSname = vo.setSname(sname);
                    if (!isSname) {
                        makeToast("잘못된 이름입니다");
                        return;
                    }
                    int syear;
                    try {
                        syear = Integer.parseInt(editTextDialogSyear.getText().toString());
                    } catch (NumberFormatException e) {
                        makeToast("학년은 1~4학년까지입니다");
                        return;
                    }
                    boolean isSyear = vo.setSyear(syear);
                    if (!isSyear) {
                        makeToast("학년은 1~4학년까지입니다");
                        return;
                    }
                    String gender = null;
                    if (radioButtonM.isChecked()) {
                        gender = "M".toUpperCase();
                    } else if (radioButtonW.isChecked()) {
                        gender = "W".toUpperCase();
                    }
                    boolean isGender = vo.setGender(gender);
                    if (!isGender) {
                        makeToast("성별을 잘못 입력하셨습니다.");
                        return;
                    }

                    String major = editTextDialogSmajor.getText().toString();
                    boolean isMajor = vo.setMajor(major);
                    if (!isMajor) {
                        makeToast("전공이 적절하지 않거나 너무 깁니다");
                        return;
                    }
                    int score;
                    try{
                        score = Integer.parseInt(editTextDialogSscore.getText().toString());
                    }
                    catch(NumberFormatException e){
                        makeToast("점수는 3자리 숫자까지 입력가능합니다");
                        return;
                    }
                    boolean isScore = vo.setScore(score);
                    if (!isScore) {
                        makeToast("점수는 3자리 숫자까지 입력가능합니다");
                        return;
                    }
                    //vo 셋팅완료
                    boolean suceess = dao.updateStudent(vo);
                    //학번이 규칙적이지 않음
                    if (suceess) {
                        listData = dao.selectAllStudent();
                        createAdapter();
                        makeToast("수정이 완료되었습니다");
                        return;
                    }
                }
            });
            dialog.show();
        } else if (view == buttonDelete) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("삭제창");
            dialog.setView(dialView);
            LinearLayout layoutHide = dialView.findViewById(R.id.layoutHide);
            layoutHide.setVisibility(View.GONE);
            dialog.setPositiveButton("취소", null);
            dialog.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editTextDialogSno = dialView.findViewById(R.id.editTextDialogSno);
                    String sno = editTextDialogSno.getText().toString();
                    StudentVo vo=new StudentVo();
                    boolean isSno=vo.setUpDateDelSno(sno,listData);
                    if(!isSno){
                        makeToast("잘못되거나 없는 학번입니다");
                        return;
                    }
                    boolean success = dao.deleteStudent(vo.getSno());
                    if (success) {
                        listData = dao.selectAllStudent();
                        createAdapter();
                        makeToast("삭제가 완료되었습니다.");
                        return;
                    }

                }
            });
            dialog.show();
        }
        //이름검색
        else if(btnSearchName ==view){
            String targetName=editTextName.getText().toString();
            if("".equals(targetName)){
                makeToast("이름을 입력해주세요");
                return;
            }
            List<StudentVo> tempList=dao.searchName(targetName);
            View viewName=View.inflate(this,R.layout.name_view,null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(viewName);
            EditText edtNameResult=viewName.findViewById(R.id.edtNameResult);
            edtNameResult.setText("");
            String resultStr="";
            for(StudentVo vo:tempList){
                resultStr += " 학번: "+vo.getSno();
                resultStr += " 이름: "+vo.getSname();
                if(vo.getGender().equals("M")){
                    resultStr += " 성별 : 남자 ";
                }
                else if(vo.getGender().equals("W")){
                    resultStr += " 성별 : 여자 ";
                }
                resultStr += " 학년: "+vo.getSyear();
                resultStr += " 전공: "+vo.getMajor();
                resultStr += " 점수: "+vo.getScore()+"\n\n";
            }
            if(tempList.size() <=0){
                resultStr="검색결과가 없습니다.";
            }
            edtNameResult.append(resultStr);
            dialog.setTitle("검색결과");
            dialog.setNegativeButton("닫기",null);
            dialog.show();

        }
    }
}