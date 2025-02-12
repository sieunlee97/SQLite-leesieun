package com.human.sqlite_leesieun;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.human.sqlite_leesieun.DatabaseTables.StudentTable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //현재클래스에서 사용할 멤버변수 선언(아래)
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;//sql템플릿(insert,select..)이 여기포함.
    private RecyclerAdapter mRecyclerAdapter;
    private List mItemList = new ArrayList<StudentVO>();//객체생성,셀렉트 쿼리결과 저장객체
    //입력,수정,삭제 EditText 변수선언
    private EditText mEditTextGrade;
    private EditText mEditTextNumber;
    private EditText mEditTextName;
    //어댑터에서 선택한 값 확인 변수
    private int currentCursorId = -1;
    //버튼 Button 변수선언
    private Button mButtonInsert;
    private Button mButtonUpdate;
    private Button mButtonDelete;
    //가상키보드제어하는 변수선언
    private InputMethodManager mInputMethodManager;

    //MainActivity가 실행되면, 자동으로 실행되는 메서드 = onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//부모 클래스 초기화(메모리로딩)
        setContentView(R.layout.activity_main);//화면 렌더링 됨.
        //신규 데이터베이스 객체 생성=메모리에 올리기=실행가능하게 만들기(아래)
        //=데이터베이스헬퍼클래스의 생성자 매서드 실행
        mDatabaseHelper = new DatabaseHelper(this,"school.db",null,1);
        //데이터베이스 파일 만들기(아래)
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();//싱클톤으로 만들어진 매서드
        //싱글톤만들었다는 의미는 인스턴스는 1번만 실행됨. 즉, school.db파일이 있으면, 생성 안 됨.
        //테스트로 mSqLiteDatabse 객체를 이용해서 더미데이터 인서트 테스트
        //자바의 HashMap형식과 비슷한 안드로이드 데이터형 ContentsValues형
        /*ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.GRADE, 4);
        contentValues.put(StudentTable.NUMBER,20210004);
        contentValues.put(StudentTable.NAME,"아무개4");
        mSqLiteDatabase.insert(StudentTable.TABLE_NAME,null,contentValues);
        */
        //mItemList에 셀렉트 쿼리 결과값이 셋 되어 있어야 함.
        //멤버변수로 객체 초기화(아래)
        bindObject();
        //List 실행 리사이클러 어댑터 바인딩(아래)
        bindList();//공간마련
        //List 반영(화면출력: 입력,수정,삭제시 화면 리프레시가 필요하고, 구현하는 메서드)
        updateList();//여기에서 데이터바인딩되서 RecyclerAdaper가 화면에 재생됩니다.
        //update버튼 클릭이벤트(아래)
        btnUpdate();
        //delete버튼 클릭이벤트(아래)
        btnDelete();
        //insert버튼 클릭이벤트(아래)
        btnInsert();
    }

    //EditText 컴포넌트 입력값 없애기
    private void clearComponent() {
        //EditText객체의 값 비우기
        mEditTextGrade.setText("");
        mEditTextNumber.setText("");
        mEditTextName.setText("");
        currentCursorId = -1;//현재 테이블커서ID가 지워졌으니, 초기화시킴
    }
//insert버튼 클릭이벤트(아래) ============================================================================================
    private void btnInsert() {
        mButtonInsert.setOnClickListener(v -> {
            //"".equals()공백체크 && 오브젝트 == null체크 2개의 조건을 1개로 해결한 매서드 isEmpty() 로 개선.
            if(mEditTextGrade.getText().toString().isEmpty() || "".equals(mEditTextNumber.getText().toString())) {
                Toast.makeText(getApplicationContext(),"학년/학번 값은 필수 입니다.",Toast.LENGTH_SHORT).show();
                return;
            }
            final int grade = Integer.parseInt(mEditTextGrade.getText().toString());
            final int number = Integer.parseInt(mEditTextNumber.getText().toString());
            final String name = mEditTextName.getText().toString();
            ContentValues contentValues = new ContentValues();
            contentValues.put(StudentTable.GRADE,grade);
            contentValues.put(StudentTable.NUMBER,number);
            contentValues.put(StudentTable.NAME,name);
            //insert쿼리 호출
            insertData(contentValues);
            //EditText 컴포넌트 입력값 없애기
            clearComponent();
            //화면 리프레스
            updateList();
            //키보드 숨기기
            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        });
        /*
        mButtonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"".equals()공백체크 && 오브젝트 == null체크 2개의 조건을 1개로 해결한 매서드 isEmpty() 로 개선.
                if(mEditTextGrade.getText().toString().isEmpty() || "".equals(mEditTextNumber.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"학년/학번 값은 필수 입니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                final int grade = Integer.parseInt(mEditTextGrade.getText().toString());
                final int number = Integer.parseInt(mEditTextNumber.getText().toString());
                final String name = mEditTextName.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(StudentTable.GRADE,grade);
                contentValues.put(StudentTable.NUMBER,number);
                contentValues.put(StudentTable.NAME,name);
                //insert쿼리 호출
                insertData(contentValues);
                //EditText 콤포넌트 입력값 없애기
                clearComponent();
                //화면 리프레스
                updateList();
                //키보드 숨기기
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        */
    }
    //SQLiteDatabase 템플릿 insert메서드 실행
    private void insertData(ContentValues contentValues) {
        mSqLiteDatabase.insert(StudentTable.TABLE_NAME,null,contentValues);
    }

//delete버튼 클릭이벤트(아래) ============================================================================================
    private void btnDelete() {
        mButtonDelete.setOnClickListener(v -> {
            if(currentCursorId == -1){
                Toast.makeText(getApplicationContext(), "선택된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            //삭제쿼리 호출
            deleteData(currentCursorId);
            //EditText콤포넌트 값 없애기
            clearComponent();
            //리사이클러뷰 화면 리프레시(아래)
            updateList();
        });
        /*
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCursorId == -1) {
                    Toast.makeText(getApplicationContext(),"선택된 값이 없습니다.",Toast.LENGTH_SHORT).show();
                    return;//선택된 값이 없으면 삭제이벤트는 종료하기
                }
                //삭제쿼리 호출
                deleteData(currentCursorId);
                //EditText콤포넌트 값 없애기
                clearComponent();
                //리사이클러뷰 화면 리프레시(아래)
                updateList();
            }
        });
    */
    }
    //SQLiteDatabase 템플릿 delete메서드 실행
    private void deleteData(int currentCursorId) {
        mSqLiteDatabase.delete(StudentTable.TABLE_NAME, StudentTable._ID + "=" + currentCursorId, null);
    }

//update버튼 클릭이벤트(아래) ============================================================================================
    private void btnUpdate() {
        mButtonUpdate.setOnClickListener(v -> {
            if(currentCursorId == -1){
                Toast.makeText(getApplicationContext(), "선택된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            //DB갱신
            final int grade = Integer.parseInt(mEditTextGrade.getText().toString());
            final int number = Integer.parseInt(mEditTextNumber.getText().toString());
            final String name = mEditTextName.getText().toString();
            //쿼리메서드 호출
            updateData(currentCursorId,grade,number,name);
            //화면 리프레시 재생(아래)
            updateList();
        });
        /*
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCursorId == -1) {
                    Toast.makeText(getApplicationContext(),"선택된 값이 없습니다.",Toast.LENGTH_SHORT).show();
                    return;//클릭이벤트 진행중지
                }
                //DB갱신
                final int grade = Integer.parseInt(mEditTextGrade.getText().toString());
                final int number = Integer.parseInt(mEditTextNumber.getText().toString());
                final String name = mEditTextName.getText().toString();
                //쿼리메서드 호출
                updateData(currentCursorId,grade,number,name);
                //화면 리프레시 재생(아래)
                updateList();
            }
        });
     */
    }
    //update버튼에 대한 쿼리
    private void updateData(int currentCursorId, int grade, int number, String name) {
        //쿼리 매개변수로 1개의 객체에 값을 담기 위해서 객체변수 생성(아래)
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.GRADE,grade);
        contentValues.put(StudentTable.NUMBER,number);
        contentValues.put(StudentTable.NAME,name);
        //SQLiteDatabase에 있는 템플릿메서드중 update사용(아래)
        mSqLiteDatabase.update(StudentTable.TABLE_NAME,contentValues,StudentTable._ID+"="+currentCursorId,null);
    }

    //멤버변수로 객체 초기화(아래)
    private void bindObject() {
        //EditText변수를 객체로 생성(아래3줄)
        mEditTextGrade = findViewById(R.id.editTextGrade);
        mEditTextNumber = findViewById(R.id.editTextNumber);
        mEditTextName = findViewById(R.id.editTextName);
        //Button변수를 객체로 생성(아래3줄)
        mButtonInsert = findViewById(R.id.btnInsert);
        mButtonUpdate = findViewById(R.id.btnUpdate);
        mButtonDelete = findViewById(R.id.btnDelete);
        //키보드제어 객체 생성(아래)
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }
    //화면 리프레스
    private void updateList() {
        mItemList.clear();
        mItemList.addAll(getAllData());
        //안드로이드 전용 클래스이고, 메서드입니다.(아래)
        mRecyclerAdapter.notifyDataSetChanged();//어댑터에 실제값이 들어가면서 화면에 뿌려짐
    }
    //셀렉트 쿼리결과를 리턴합니다.
    private List getAllData() {
        List tableList = new ArrayList<>();//studentTable내용이 담을 예정.
        //쿼리작업에 사용될 필드명 바인딩
        String[] projection = {
                StudentTable._ID,//AutoIncrement자동증가 PK
                StudentTable.GRADE,
                StudentTable.NUMBER,
                StudentTable.NAME
        };
        //쿼리 템플릿 메서드사용(아래) Cursor커서는 레코드위치를 가지는 테이블과 같음.
        Cursor cursor = mSqLiteDatabase.query(StudentTable.TABLE_NAME,projection,null,null,null,null,"_id desc");
        //반복문조건은 커서테이블의 다음레코드가 있을때까지
        while (cursor.moveToNext()) {//student테이블에 있는 필드값을 하나씩 뽑아서
            //tableList 리스트객체에 1레코드씩 저장
            int p_id = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable._ID));
            int p_grade = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable.GRADE));
            int p_number = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable.NUMBER));
            String p_name = cursor.getString(cursor.getColumnIndexOrThrow(StudentTable.NAME));
            //매개변수가 없는 클래스의 생성자 메서드는 자바가 자동으로 만둘어줌.
            tableList.add(new StudentVO(p_id,p_grade,p_number,p_name));
        }
        return tableList;
    }

    //List 실행 리아시클러 어댑터 바인딩(아래)
    private void bindList() {
        //객체 생성
        mRecyclerAdapter = new RecyclerAdapter(mItemList);
        //어댑터의 OnItemClickListener 추가예정....
        //애로우 함수는 함수 이름이 없음 = 콜백 함수 = 자동실행함수
        mRecyclerAdapter.setOnItemClickListener((v, position) -> {
            StudentVO studentVO = (StudentVO) mItemList.get(position);
            //디버그
            currentCursorId = studentVO.getmId();
            Toast.makeText(getApplicationContext(),"현재 선택한 커서레코드 ID는 "+currentCursorId,Toast.LENGTH_SHORT).show();
            mEditTextGrade.setText(Integer.toString(studentVO.getmGrade()));
            mEditTextNumber.setText(Integer.toString(studentVO.getmNumber()));
            mEditTextName.setText(studentVO.getmName());
        });
        /*
        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                StudentVO studentVO = (StudentVO) mItemList.get(position);
                //디버그
                currentCursorId = studentVO.getmId();
                Toast.makeText(getApplicationContext(),"현재 선택한 커서레코드 ID는 "+currentCursorId,Toast.LENGTH_SHORT).show();
                mEditTextGrade.setText(Integer.toString(studentVO.getmGrade()));
                mEditTextNumber.setText(Integer.toString(studentVO.getmNumber()));
                mEditTextName.setText(studentVO.getmName());
            }
        });
        */
        //리사이클러뷰xml과 어댑터 바인딩(attach) No adapter attached
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);//리사이클러 뷰의 높이를 고정한다.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRecyclerAdapter);//실제 attach(바인딩)
    }
}