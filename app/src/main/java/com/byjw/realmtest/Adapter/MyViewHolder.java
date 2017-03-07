package com.byjw.realmtest.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.byjw.realmtest.R;

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

    public MyViewHolder(View itemView) {
        super(itemView);

        profilePhoto = (ImageView) itemView.findViewById(R.id.profile_photo);
        textIndex = (TextView) itemView.findViewById(R.id.item_index);
        textName = (TextView) itemView.findViewById(R.id.item_name);
        textDepartment = (TextView) itemView.findViewById(R.id.item_department);
        textTitle = (TextView) itemView.findViewById(R.id.item_title);
        textSalary = (TextView) itemView.findViewById(R.id.item_salary);
    }
}
