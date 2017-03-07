package com.byjw.realmtest.Adapter.Contract;

import com.byjw.realmtest.Model.MemberItem;

import java.util.ArrayList;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

public interface MyAdapterContract {

    interface View {

        void notifyAdapter();

    }

    interface Model {

        void addItems(ArrayList<MemberItem> memberItems);

        void clearItems();

    }
}
