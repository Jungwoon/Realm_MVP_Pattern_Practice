package com.byjw.realmtest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.byjw.realmtest.Listener.OnItemClickListener;
import com.byjw.realmtest.Model.MemberItem;
import com.byjw.realmtest.R;

import java.text.DecimalFormat;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView profilePhoto;
    TextView textIndex;
    TextView textName;
    TextView textDepartment;
    TextView textTitle;
    TextView textSalary;

    private Context context;
    private OnItemClickListener onItemClickListener;

    private int lastPosition = -1;

    public MyViewHolder(Context context, ViewGroup parent, OnItemClickListener onItemClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));

        this.context = context;
        this.onItemClickListener = onItemClickListener;

        profilePhoto = (ImageView) itemView.findViewById(R.id.profile_photo);
        textIndex = (TextView) itemView.findViewById(R.id.item_index);
        textName = (TextView) itemView.findViewById(R.id.item_name);
        textDepartment = (TextView) itemView.findViewById(R.id.item_department);
        textTitle = (TextView) itemView.findViewById(R.id.item_title);
        textSalary = (TextView) itemView.findViewById(R.id.item_salary);
    }

    public void onBind(MemberItem memberItem, final int position) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        textIndex.setText(memberItem.getIndex() + "");
        textName.setText("Name : " + memberItem.getName());
        textDepartment.setText("Department : " + memberItem.getDepartment());
        textTitle.setText("Title : " + memberItem.getTitle());
        textSalary.setText("Salary : " + makeStringComma(memberItem.getSalary()+""));

    }

    // Image Loading에 Animation을 주기 위한 부분
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(animation);
            lastPosition = position;
        }
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
