package com.human.sqlite_leesieun;
/**
 * StudentVO 클래스는 xml과 메인 액티비티 Java와 데이터를 get/set 하기위해
 */

public class StudentVO {
    // VO 클래스의 멤버변수
    private int mId; // Cursor id (레코드-한줄 아이디)
    private int mGrade; //학년
    private int mNumber; //학번
    private String mName; //이름

    public StudentVO(int p_id, int p_grade, int p_number, String p_name) {
        mId = p_id;
        mGrade = p_grade;
        mNumber = p_number;
        mName = p_name;
    }


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmGade() {
        return mGrade;
    }

    public void setmGade(int mGade) {
        this.mGrade = mGade;
    }

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
