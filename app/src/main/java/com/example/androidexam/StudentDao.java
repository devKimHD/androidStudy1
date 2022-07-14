package com.example.androidexam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    DataBaseHelper helper;
    private static StudentDao instance = new StudentDao();
    private StudentDao(){}
    public static StudentDao getInstance(){
        return instance;
    }
    public void setHelper(DataBaseHelper helper){
        this.helper=helper;
    }
    public List<StudentVo> selectAllStudent(){
        SQLiteDatabase db =helper.getReadableDatabase();
        List<StudentVo> listData=new ArrayList<>();
        String sql="select * from tbl_student order by sno";
        Cursor cursor=db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String sno=cursor.getString(0);
            String sname=cursor.getString(1);
            int syear=cursor.getInt(2);
            String gender=cursor.getString(3);
            String major=cursor.getString(4);
            int score=cursor.getInt(5);
            StudentVo vo=new StudentVo(sno,sname,syear,gender,major,score);
            listData.add(vo);
        }
            cursor.close();
            db.close();
                return listData;
    }
    public boolean addStudentData(StudentVo vo){
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql="insert into tbl_student values(" +
                "?,?,?,?,?,?)";
        SQLiteStatement stmt =db.compileStatement(sql);
        stmt.bindString(1,vo.getSno());
        stmt.bindString(2,vo.getSname());
        stmt.bindLong(3,vo.getSyear());
        stmt.bindString(4,vo.getGender());
        stmt.bindString(5,vo.getMajor());
        stmt.bindLong(6,vo.getScore());
        long count= stmt.executeInsert();
        stmt.close();
        db.close();
        if(count>0){
            return true;
        }
        return false;
    }
    public boolean updateStudent(StudentVo vo){
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql="update tbl_student set" +
                "   sname=?," +
                "   syear=?," +
                "   gender=?," +
                "   major=?," +
                "   score=?" +
                "   where sno=?";
        SQLiteStatement stmt =db.compileStatement(sql);
        stmt.bindString(1,vo.getSname());
        stmt.bindLong(2,vo.getSyear());
        stmt.bindString(3,vo.getGender());
        stmt.bindString(4,vo.getMajor());
        stmt.bindLong(5,vo.getScore());
        stmt.bindString(6,vo.getSno());
        int count= stmt.executeUpdateDelete();
        stmt.close();
        db.close();
        if(count>0){
            return true;
        }
        return false;
    }
    public boolean deleteStudent(String sno){
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql="delete from tbl_student where sno=?";
        SQLiteStatement stmt =db.compileStatement(sql);
        stmt.bindString(1,sno);
        int count= stmt.executeUpdateDelete();
        stmt.close();
        db.close();
        if(count>0){
            return true;
        }
        return false;
    }
    public List<StudentVo> searchName(String target){
        SQLiteDatabase db=helper.getReadableDatabase();
        List<StudentVo> listData=new ArrayList<>();
        String sql="select * from tbl_student where sname like '%' || ? ||'%' ";
        String [] selectionArgs  = new String[]{"%" + target + "%"};
        Cursor cursor=db.rawQuery(sql,selectionArgs);
        while(cursor.moveToNext()){
            String sno=cursor.getString(0);
            String sname=cursor.getString(1);
            int syear=cursor.getInt(2);
            String gender=cursor.getString(3);
            String major=cursor.getString(4);
            int score=cursor.getInt(5);
            StudentVo vo=new StudentVo(sno,sname,syear,gender,major,score);
            listData.add(vo);
        }
        return listData;
    }
}
