package com.human.sqlite_leesieun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * DatabaseHelper 클래스는 DB 생성 및 테이블 생성을 처리하는 기능.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //멤버 변수 생성(쿼리구문)
    private String CreateTableStudent = "CREATE TABLE student(" +
            "_id INTEGER PRIMARY KEY" +
            ", grade INTEGER" +
            ", number INTEGER" +
            ", name TEXT" +
            ")";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        //신규 데이터베이스 생성하는 생성자(아래)
        //(현재 컨텐츠 위치 this, DB명, 테이블팩토리명, 버전)
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //신규 테이블 만들기(아래)
        db.execSQL(CreateTableStudent); //학생 테이블
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
