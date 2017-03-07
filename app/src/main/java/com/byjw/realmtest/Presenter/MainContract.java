package com.byjw.realmtest.Presenter;

import android.content.Context;

import com.byjw.realmtest.Adapter.Contract.MyAdapterContract;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

public interface MainContract {

    interface View {

        void showToast(int index);
    }

    interface Presenter {

        void setMyAdapterView(MyAdapterContract.View adapterView);

        void setMyAdapterModel(MyAdapterContract.Model adapterModel);

        void attachView(View view);

        void detachView();

        void loadItems(Context context, boolean isClear);

    }
}
