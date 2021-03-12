# SQLite 데이터베이스 기반 CRUD 앱

#### 20210312(금)
- SQLite DB는 있으나, DBMS(oracle, mysql)가 없음. 개발자가 직접 코드로 데이터베이스 파일을 만든다.
- > school.db

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
