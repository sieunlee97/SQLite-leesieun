package com.human.sqlite_leesieun;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerAdapter 클래스는 recyclerView 컴포넌트와 SQLiteDB를 바인딩시키는 기능
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    //안드로이드에서 제공되는 OnItemClickListener는 매개변수 4개이기 때문에 2개짜리로 추가
    public interface OnItemClickListener { //중첩 클래스 내에서 사용. 인터페이스메소드 명세를 모아놓은 클래스
        void onItemClick(View v, int position); //매개변수 2개인 메소드 명세 생성        
    }
    //멤버변수
    public OnItemClickListener mOnItemClickListener;
    private List mList;
    
    //인터페이스 OnItemClickListener에 대한 set메소드 생성(아래_MainActivity에서 사용 예정)
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    
    //ItemViewHolder클래스가 호출되면 자동으로 onCreateViewHolder메소드가 실행됨
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //레이아웃 xml을 자바단에서 화면을 로딩할 때 inflate 메소드 사용(아래) => startActivity(화면을 띄울 때)
        //inflate() 매개변수 xml 디자인이 필수. 리사이클러뷰 내에 들어가는 아이템디자인 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        return new ItemViewHolder(view);
    }

    //생성자 메소드: MainActivity에서 호출하면서 매개변수 리스트쿼리 값을 보낸다.
    public RecyclerAdapter(List itemList){
        mList = itemList; //현재 클래스에서 최초로 실행
    }
    //아래 onBindViewHolder에서 mList값을 사용하려면, mList값을 생성(위)
    //뷰홀더를 클릭했을 때, 뷰홀더와 포지션pos를 이용해서 입력박스에 기존 값 출력하기
    //개발자가 호출하는 것이 아니고, 안드로이드의 레이아웃 관리자 프로그램이 자동으로 호출
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //구현은 개발자가 코딩함(아래)
        StudentVO studentVO = (StudentVO) mList.get(position); //1개 레코드 저장
        //리사이클러뷰의 recyclerview_item.xml 디자인에 데이터를 출력한다.
        holder.itemGrade.setText(Integer.toString(studentVO.getmGade()));
        holder.itemNumber.setText(Integer.toString(studentVO.getmNumber()));
        holder.itemName.setText(studentVO.getmName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //중첩클래스 생성(아래_리사이클러뷰 컴포넌트에 데이터를 홀딩시키는 클래스)
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        //멤버변수 선언
        private TextView itemGrade; //홀더 내의 객체 중 학년 아이템
        private TextView itemNumber; //학번 아이템
        private TextView itemName; //이름 아이템
        public ItemViewHolder(@NonNull View itemView) { //new 생성자 메소드
            super(itemView); //메모리에 ViewHolder클래스를 로딩하게 됨.
            //변수 객체로 만듦 (메모리에 로딩 - 실행가능하게 변경)
            itemGrade = itemView.findViewById(R.id.item_grade); //xml의 컴포넌트를 객체화 시킴
            itemNumber = itemView.findViewById(R.id.item_number);
            itemName = itemView.findViewById(R.id.item_name);
            //New 키워드로 생성(객체가 만들어질 때)될 때 아이템 클릭 이벤트 생성
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition(); //리사이클러뷰 클릭한 리스트 위치
                    System.out.println("디버그 itemView.setOnClickListener: "+pos);
                    if(pos != RecyclerView.NO_POSITION) { //손가락으로 클릭했을 때
                        //손가락으로 선택한 학생이 없을 때 실행(아래)
                        if(mOnItemClickListener != null){
                            mOnItemClickListener.onItemClick(v,pos);
                        }
                    }
                }
            });
            //작동 확인 후 나중에 람다식(자바버전8이후부터 지원 가능)으로 변경 예정
        }
    }

}
