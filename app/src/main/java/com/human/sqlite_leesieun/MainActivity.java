package com.human.sqlite_leesieun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.human.sqlite_leesieun.DatabaseTables.StudentTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //현재 클래스에서 사용할 멤버변수 선언(아래)
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase; ///sql템플릿(insert, select,...)이 여기 포함.
    private RecyclerAdapter mRecyclerAdapter;
    private List mItemList = new ArrayList<StudentVO>(); //객체 생성, select 쿼리 결과 저장 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //신규 데이터베이스 생성-메모리에 올리기=실행가능하게 만들기(아래)
        //=데이터베이스헬퍼 클래스의 생성자 메소드 실행
        mDatabaseHelper = new DatabaseHelper(this, "school.db", null, 1);
        //데이터베이스 파일 만들기(아래)
        mSqLiteDatabase = mDatabaseHelper.getReadableDatabase();
        //테스트로 mSqLiteDatabase 객체를 이용해서 더미데이터 인서트 테스트
        //자바의 HashMap형식과 비슷한 안드로이드 데이터형 ContentValues형

        /*ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.GRADE, 3);
        contentValues.put(StudentTable.NUMBER, 20210103);
        contentValues.put(StudentTable.NAME, "아무개");
        mSqLiteDatabase.insert(StudentTable.TABLE_NAME, null, contentValues);*/

        //mItemList에 select쿼리 결과 값이 set되어있어야 함.

        //List 실행 리사이클러 어댑터 바인딩(아래)
        bindList(); //여기서 공간 마련
        //List 반영(화면출력)
        updateList(); //여기에서 데이터가 바인딩돼서 RecyclerAdapter가 화면에 재생된다.
    }

    private void updateList() {
        mItemList.clear();
        mItemList.addAll(getAllData());
        mRecyclerAdapter.notifyDataSetChanged(); //어댑터에 실제 값이 들어가면서 화면에 뿌려짐
    }

    //select 쿼리 결과를 리턴한다.
    private List getAllData() { 
        List tableList = new ArrayList(); //studentTable 내용이 담길 예정
        //쿼리작업
        String[] projection ={
                StudentTable._ID, //AutoIncrement 자동증가 PK
                StudentTable.GRADE,
                StudentTable.NUMBER,
                StudentTable.NAME
        };
        //쿼리 템플릿 메서드 사용(아래), Cursor는 레코드 위치를 가지는 테이블
        Cursor cursor = mSqLiteDatabase.query(StudentTable.TABLE_NAME, projection,null,null,null, null, "_id desc");
        //반복문 조건은 커서의 다음 레코드가 존재할 때까지
        while(cursor.moveToNext()){//StudentTable에 있는 필드값을 하나씩 뽑아서
            //tableList 리스트객체에 1레코드씩 저장
            int p_id = cursor.getColumnIndexOrThrow(StudentTable._ID);
            int p_grade = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable.GRADE));
            int p_number = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable.NUMBER));
            String p_name = cursor.getString(cursor.getColumnIndexOrThrow(StudentTable.NAME));
            //매개변수
            tableList.add(new StudentVO(p_id, p_grade, p_number, p_name));
        }
        return tableList;
    }

    //List 실행 리사이클러 어댑터 바인딩(아래)
    private void bindList() {
        //객체 생성
        mRecyclerAdapter = new RecyclerAdapter(mItemList);
        //리사이클러뷰xml과 어댑터 바인딩
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); //리사이클러 뷰의 높이를 고정한다.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRecyclerAdapter); //실제 attach(바인딩)
    }
}