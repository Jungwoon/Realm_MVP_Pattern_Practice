package com.byjw.realmtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.byjw.realmtest.Adapter.Contract.MyAdapterContract;
import com.byjw.realmtest.Listener.OnItemClickListener;
import com.byjw.realmtest.Model.MemberItem;

import java.util.ArrayList;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements MyAdapterContract.View, MyAdapterContract.Model {
    private Context context;
    private OnItemClickListener onItemClickListener;

    private ArrayList<MemberItem> memberItems;

    public MyAdapter(Context context) {
        this.context = context;
    }

    // 새로운 뷰 생성
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(context, parent, onItemClickListener);
    }

    // RecyclerView의 getview 부분을 담당하는 부분
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        if (myViewHolder == null) return;

        myViewHolder.onBind(memberItems.get(position), position);
    }

    // Item 개수를 반환하는 부분
    @Override
    public int getItemCount() {
        return memberItems != null ? memberItems.size() : 0 ;
    }

    @Override
    public void addItems(ArrayList<MemberItem> memberItems) {
        this.memberItems = memberItems;
    }

    @Override
    public void clearItems() {
        if (memberItems != null)  {
            memberItems.clear();
        }
    }

    @Override
    public MemberItem getItem(int position) {

        return memberItems.get(position);
    }

    @Override
    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

}
