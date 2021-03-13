# SQLite 데이터베이스 기반 CRUD 앱

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
