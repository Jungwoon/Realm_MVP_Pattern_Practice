package com.byjw.realmtest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.byjw.realmtest.Adapter.MyAdapter;
import com.byjw.realmtest.Presenter.MainContract;
import com.byjw.realmtest.Presenter.MainPresenter;
import com.byjw.realmtest.Model.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @BindView(R.id.floating_action_add)
    com.getbase.floatingactionbutton.FloatingActionButton fabAdd;

    @BindView(R.id.floating_action_delete)
    com.getbase.floatingactionbutton.FloatingActionButton fabDelete;

    @BindView(R.id.floating_action_edit)
    com.getbase.floatingactionbutton.FloatingActionButton fabEdit;

    @OnClick(R.id.floating_action_add)
    void addMember() {
        MemberDialog memberDialog = new MemberDialog(this, mainPresenter, 0);
        memberDialog.show();
    }

    @OnClick(R.id.floating_action_edit)
    void editMember() {
        MemberDialog memberDialog = new MemberDialog(this, mainPresenter, 1);
        memberDialog.show();
    }


    @OnClick(R.id.floating_action_delete)
    void deleteMember() {

        final EditText editIndex = new EditText(this);
        editIndex.setHint("index");
        editIndex.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        new AlertDialog.Builder(this)
                .setTitle("Delete Member")
                .setView(editIndex)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String index = editIndex.getText().toString();

                        if (index == "") {
                            Toast.makeText(MainActivity.this, "Please input index", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            DBHelper.getInstance().deleteMemberWithIndex(Integer.parseInt(index));
                        }

                        // 데이터가 바뀌었으니 다시한번 로드 해주는 부분
                        mainPresenter.loadItems(MainActivity.this, true);

                        dialog.dismiss();
                    }
                })
                .show();
    }

    private MainPresenter mainPresenter;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        Realm.init(this);

        // Floating Button에 아이콘 적용해주는 부분
        fabAdd.setIcon(R.drawable.ic_add);
        fabEdit.setIcon(R.drawable.ic_edit);
        fabDelete.setIcon(R.drawable.ic_delete);

        // RecyclerView 관련해서 필요한 부분들 설정해주는 부분
        myAdapter = new MyAdapter(this);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        // mainPresenter를 이용해서 처리를 해줌
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.setMyAdapterView(myAdapter);
        mainPresenter.setMyAdapterModel(myAdapter);
        mainPresenter.loadItems(this, false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Realm realm = DBHelper.getInstance().getRealmInstance();

        // onDestroy()를 탈 때, realm이 닫혀있지 않으면 닫아주어야 함
        if (!realm.isClosed()) {
            realm.close();
        }

        // 다 쓰고 view를 띄워놓는 부분
        mainPresenter.detachView();
    }

    @Override
    public void showToast(int index) {
        Toast.makeText(this, "(" + index + ") is clicked", Toast.LENGTH_SHORT).show();
    }
}