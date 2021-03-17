package com.human.sqlite_leesieun;

import android.provider.BaseColumns;

/**
 * DatabaseTables 클래스는 물리테이블과 DAO클래스를 데이터 연동할 때 필요.
 * 알려진 용어 Contract(계약서)
 */
public class DatabaseTables {
    // 학생 테이블용 필드값 클래스로 지정(아래)
    // 중첩 클래스 : 데이터형 클래스를 여러개 생성할 때 필요한 클래스 구조 -> 관리향상
    public static class StudentTable implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String GRADE = "grade";
        public static final String NUMBER = "number";
        public static final String NAME = "name";
    }
}
