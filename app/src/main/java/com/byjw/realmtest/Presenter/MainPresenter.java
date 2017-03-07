package com.byjw.realmtest.Presenter;

import android.content.Context;

import com.byjw.realmtest.Adapter.Contract.MyAdapterContract;
import com.byjw.realmtest.Listener.OnItemClickListener;
import com.byjw.realmtest.Model.MemberItem;
import com.byjw.realmtest.Model.DBHelper;

import java.util.ArrayList;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

public class MainPresenter implements MainContract.Presenter, OnItemClickListener {

    private MainContract.View view;
    private MyAdapterContract.View adapterView;
    private MyAdapterContract.Model adapterModel;

    @Override
    public void setMyAdapterView(MyAdapterContract.View adapterView) {
        this.adapterView = adapterView;

        this.adapterView.setOnClickListener(this);
    }

    @Override
    public void setMyAdapterModel(MyAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void loadItems(Context context, boolean isClear) {
        // 이미
        ArrayList<MemberItem> memberItems = DBHelper.getInstance().getMembers();

        if (isClear) {
            adapterModel.clearItems();
        }

        adapterModel.addItems(memberItems);
        adapterView.notifyAdapter();
    }

    @Override
    public void onItemClick(int position) {
        MemberItem memberItem = adapterModel.getItem(position);
        view.showToast(memberItem.getIndex());
    }
}
