package com.example.androidexam;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class StudentVo implements Serializable {
    private String sno;
    private String sname;
    private int syear;
    private String gender;
    private String major;
    private int score;
    public StudentVo() {
    }


    public StudentVo(String sno, String sname, int syear, String gender, String major, int score) {
        this.sno = sno;
        this.sname = sname;
        this.syear = syear;
        this.gender = gender;
        this.major = major;
        this.score = score;
    }
    public String getSno() {
        return sno;
    }

    public boolean setSno(String sno, List<StudentVo> list) {
        //유효성 중복체크
        String regExp="^[1-9][0-9]*$";
        for(StudentVo vo: list){
            String chkStr=vo.getSno().trim();
            if("".equals(sno)||sno.length()>8 || chkStr.equals(sno) || !(sno.matches(regExp)) ){
                return false;
            }

        }
        this.sno = sno;
        return true;
    }

    public String getSname() {
        return sname;
    }

    public boolean setSname(String sname) {
        String chkStr=sname.trim();
        //영어한글문자 포함
        String regExp="[a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*";
        if("".equals(sname) || chkStr.length()>20 || !(chkStr.matches(regExp)) ){
            return false;
        }
        this.sname = sname;
        return true;
    }

    public int getSyear() {
        return syear;
    }

    public boolean setSyear(int syear) {
        String regExp="[1-4]";
        //4학년까지만
        String chkStr=String.valueOf(syear).trim();
        if(!(chkStr.matches(regExp))){
            return false;
        }

        this.syear = Integer.parseInt(chkStr);
        return true;
    }

    public String getGender() {
        return gender;
    }

    public boolean setGender(String gender) {
        //성별 M,W 검사
        if(gender.toUpperCase().equals("M") || gender.toUpperCase().equals("W")){
            this.gender = gender;
            return true;
        }
      return  false;
    }

    public String getMajor() {
        return major;
    }

    public boolean setMajor(String major) {
        //전공검사
        String chkStr=major.trim();
        String regExp="[a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*";
        if("".equals(major) || chkStr.length()>20 || !(chkStr.matches(regExp)) ){
            return false;
        }
        this.major = major;
        return true;


    }

    public int getScore() {
        return score;
    }

    public boolean setScore(int score) {
        //3자리 숫자 입력
        String regExp="^[0-9]{1,3}";
        String chkStr=String.valueOf(score);
        if("".equals(chkStr) || !(chkStr.matches(regExp)) ){
            return false;
        }
        this.score = score;
        return true;
    }
    public boolean setUpDateDelSno(String sno,List<StudentVo> list) {
        //학번유효성
        String regExp = "^[0-9]{1,8}$";
        //list 비교
        for (StudentVo vo : list) {
            String chkStr = vo.getSno().trim();
            if (!("".equals(sno)) &&
                    chkStr.equals(sno) && (sno.matches(regExp))) {
                this.sno=sno;
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "StudentVo{" +
                "sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                ", syear=" + syear +
                ", gender='" + gender + '\'' +
                ", major='" + major + '\'' +
                ", score=" + score +
                '}';
    }
}
