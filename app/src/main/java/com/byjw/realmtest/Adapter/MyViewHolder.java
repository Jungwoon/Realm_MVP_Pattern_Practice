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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.profile_photo)
    ImageView profilePhoto;

    @BindView(R.id.item_index)
    TextView textIndex;

    @BindView(R.id.item_name)
    TextView textName;

    @BindView(R.id.item_department)
    TextView textDepartment;

    @BindView(R.id.item_title)
    TextView textTitle;

    @BindView(R.id.item_salary)
    TextView textSalary;

    private Context context;
    private OnItemClickListener onItemClickListener;
    private int lastPosition = -1;

    public MyViewHolder(Context context, ViewGroup parent, OnItemClickListener onItemClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));

        this.context = context;
        this.onItemClickListener = onItemClickListener;

        // Binding 부분을 Butter Knife에서 처리하도록 하기위해 Butter Knife 초기화
        ButterKnife.bind(this, itemView);
    }

    // Recycler의 각 Item을 바인딩 시켜주는 부분, Adapter에서 처리해줘도 되지만 여기서는 ViewHolder에서 처리하도록 빼놓음
    public void onBind(MemberItem memberItem, final int position) {
        // itemView는 기본적으로 ViewHolder에 있는 객체로 RecyclerView에서 선택한 view를 반환한다.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        textIndex.setText(String.valueOf(memberItem.getIndex()));
        textName.setText("Name : " + memberItem.getName());
        textDepartment.setText("Department : " + memberItem.getDepartment());
        textTitle.setText("Title : " + memberItem.getTitle());
        textSalary.setText("Salary : " + makeStringComma(String.valueOf(memberItem.getSalary())));

        // 프로필 사진 들어올때 애니메이션 효과 주는 부분
        setAnimation(profilePhoto, position);

    }

    // Animation을 주기 위한 부분
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
