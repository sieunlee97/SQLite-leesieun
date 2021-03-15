# SQLite 데이터베이스 기반 CRUD 앱

**목적: MainActivity를 이용한 xml 메인 디자인과 SQLite DB의 CRUD**
Read구현. 리사이클러뷰에 SQLite데이터를 바인딩하기 위한 2개의 클래스 생성.
Read_1. 리사이클러 어댑터클래스(List구현) 와 뷰 더클래스(아이템View구현) 생성(중첩클래스적용).
Read_2. 뷰홀더에 넣을 자료의 xml아이템 디자인 생성.
Read_3. 위 어댑터를 사용하여 MainActivity에 SQLite 데이터 바인딩 처리.
-------------------------- 여기까지 --------------------------------
Update구현, Delete구현, Create구현OK. 위 버튼중 Update,Delete,Create는 수업시 람다식으로 변경연습예정.

===============================================================================================
- 개선사항
- > 람다식으로 된 소스를 일반코드로 변경처리(수업시 람다식 연습예정)
- > android.support.v7 버전코드를 androidx.~ 최신버전으로 마이그레이션
-----------------------------------------------------------------------------------------------

#### 20210315(월)
- **RecyclerView** : 목록을 화면에 출력해주고 동적으로 표현해주는 컨테이너
- **어댑터(RecyclerAdapter)** : 리사이클러뷰(MainActivity)와 SQLite를 데이터 바인딩하기 위해서 중간 매개체로 사용하는 클래스
- > RecyclerAdapter 클래스 안에 ViewHolder 클래스가 포함
- > RecyclerView.Adapter 상속해서 구현
- > onCreateViewHolder, onBindViewHolder, getItemCount 메서드 구현
- onCreateViewHolder : 뷰홀더 생성(레이아웃 생성), LayoutInflater 사용(-특정 xml파일을 클래스로 변환)
- onBindViewHolder : 뷰홀더가 재활용될 때 실행되는 메서드
- getItemCount : 아이템 개수 조회
- **ViewHolder** : 현재 화면에 보이는 아이템 레이아웃 개수만큼 생성되고 새롭게 그려져야 할 아이템 레이아웃이 있다면 가장 위의 뷰홀더를 재사용해서 데이터만 바꿈, 앱의 효율 향상


#### 20210312(금)
- SQLite 생성 : DatabaseHelper.java 클래스 생성.
- SQLite DB는 있으나, DBMS(oracle, mysql)가 없음. 개발자가 직접 코드로 데이터베이스 파일을 만든다.
- > school.db

```java
//데이터베이스헬퍼 클래스의 생성자 메소드 실행
mDatabaseHelper = new DatabaseHelper(this, "school.db", null, 1);
//데이터베이스 파일 만들기
mSqLiteDatabase = mDatabaseHelper.getReadableDatabase();
```

- StudentVO(Value Objec) 생성
- DatabaseTables.java 계약서(Contract) 생성 (->필드명을 한 곳에서 관리)

```java
public class DatabaseTables {
    // 학생 테이블용 필드값 클래스로 지정(아래)
    public static class StudentTable implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String GRADE = "grade";
        public static final String NUMBER = "number";
        public static final String NAME = "name";
    }
}
```

- student 테이블 생성.  MainActivity 에서 insert 더미 실행.

```java
ContentValues contentValues = new ContentValues();
contentValues.put(StudentTable.GRADE, 1);
contentValues.put(StudentTable.NUMBER, 20210102);
contentValues.put(StudentTable.NAME, "홍길동");
mSqLiteDatabase.insert(StudentTable.TABLE_NAME, null, contentValues);
```

#### 20210311(목)
- 안드로이드 폰에 내장된 SQLite 데이터베이스를 기반으로 한 CRUD 앱 실습
- 리사이클러뷰(RecyclerView) 사용
- > 예전에 사용된 ListView를 대체해서 RecyclerView 사용
- 특징 1. 상하 스크롤 뿐 아니라 좌우 스크롤도 가능
- 특징 2. 레이아웃 매니저(LayoutManager클래스)와 뷰 홀더 패턴(View Holder Pattern)을 의무화
- > 메모리 절약을 위해
- 특징 3. ListView보다 다양한 형태로 커스터마이징 가능
- RecyclerView 예제 : https://webnautes.tistory.com/1214
- 3개 객체 : List객체(DB) <-> 어댑터 클래스 <-> RecyclerView 컴포넌트
