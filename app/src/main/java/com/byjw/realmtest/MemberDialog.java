package com.byjw.realmtest;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.byjw.realmtest.Presenter.MainPresenter;
import com.byjw.realmtest.Model.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

public class MemberDialog extends Dialog {

    // 0 : add, 1 : edit
    int flag;
    MainPresenter mainPresenter;

    public MemberDialog(Context context, MainPresenter mainPresenter, int flag) {
        super(context);
        this.mainPresenter = mainPresenter;
        this.flag = flag;
    }

    @BindView(R.id.edit_index)
    EditText editIndex;

    @BindView(R.id.edit_name)
    EditText editName;

    @BindView(R.id.edit_department)
    EditText editDepartment;

    @BindView(R.id.edit_title)
    EditText editTitle;

    @BindView(R.id.edit_salary)
    EditText editSalary;

    @OnClick(R.id.btn_confirm)
    void btnConfirm() {
        if (editIndex.getText() != null && flag != 0) {
            index = Integer.parseInt(editIndex.getText().toString());
        }

        if (editName.getText() != null) {
            name = editName.getText().toString();
        }

        if (editDepartment.getText() != null) {
            department = editDepartment.getText().toString();
        }

        if (editTitle.getText() != null) {
            title = editTitle.getText().toString();
        }

        if (editSalary.getText() != null) {
            salary = Integer.parseInt(editSalary.getText().toString());
        }

        if (flag == 0) {
            DBHelper.getInstance().insertMember(name, department, title, salary);
        }
        else if (flag == 1) {
            DBHelper.getInstance().editMemberWithIndex(index, name, department, title, salary);
        }

        dismiss();

        mainPresenter.loadItems(getContext(), true);
    }

    private int index = 0;
    private String name = null;
    private String department = null;
    private String title = null;
    private int salary = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member);

        ButterKnife.bind(this);

        if (flag == 0) {
            editIndex.setEnabled(false);
            editIndex.setText(DBHelper.getInstance().getNewMemberIndex()+"");
        }

    }
}
