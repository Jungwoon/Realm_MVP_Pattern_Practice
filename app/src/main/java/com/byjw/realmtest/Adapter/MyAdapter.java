package com.byjw.realmtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.byjw.realmtest.Adapter.Contract.MyAdapterContract;
import com.byjw.realmtest.Model.MemberItem;
import com.byjw.realmtest.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements MyAdapterContract.View, MyAdapterContract.Model {
    private Context context;
    private int lastPosition = -1;

    private ArrayList<MemberItem> memberItems;

    public MyAdapter(Context context) {
        this.context = context;
    }

    // 새로운 뷰 생성
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    // RecyclerView의 getview 부분을 담당하는 부분
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        if (myViewHolder == null) return;

        myViewHolder.textIndex.setText(memberItems.get(position).getIndex() + "");
        myViewHolder.textName.setText("Name : " + memberItems.get(position).getName());
        myViewHolder.textDepartment.setText("Department : " + memberItems.get(position).getDepartment());
        myViewHolder.textTitle.setText("Title : " + memberItems.get(position).getTitle());
        myViewHolder.textSalary.setText("Salary : " + makeStringComma(memberItems.get(position).getSalary()+""));

        setAnimation(myViewHolder.profilePhoto, position);
    }

    // Item 개수를 반환하는 부분
    @Override
    public int getItemCount() {
        return memberItems != null ? memberItems.size() : 0 ;
    }

    // Image Loading에 Animation을 주기 위한 부분분
   private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(animation);
            lastPosition = position;
        }
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
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    // 천단위 콤마 처리
    private String makeStringComma(String amount) {
        if (amount.length() == 0) {
            return "";
        }

        // 원래는 파라미터로 받는 부분인데 default를 true로 놔둠
        boolean ignoreZero = true;

        try {
            // 소수점 있는 경우
            if (amount.indexOf(".") >= 0) {
                double value = Double.parseDouble(amount);

                if (ignoreZero && value == 0) {
                    return "";
                }

                DecimalFormat format = new DecimalFormat("###,##0.00");

                return format.format(value);
            }
            // 소수점이 없는 경우
            else {
                long value = Long.parseLong(amount);
                if (ignoreZero && value == 0) {
                    return "";
                }
                DecimalFormat format = new DecimalFormat("###,###");
                return format.format(value);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return amount;
    }
}
