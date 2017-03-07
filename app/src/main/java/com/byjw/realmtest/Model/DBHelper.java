package com.byjw.realmtest.Model;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by jungwoon on 2017. 3. 6..
 */
public class DBHelper {
    private static DBHelper ourInstance = new DBHelper();

    public static DBHelper getInstance() {
        return ourInstance;
    }

    Realm realm;

    public DBHelper() {
        realm = Realm.getDefaultInstance();
    }

    public Realm getRealmInstance() {
        return realm;
    }

    // 새로 추가하는 부분
    public void insertMember(String name, String department, String title, int salary) {
        Member member = new Member();

        member.setIndex(getAutoIncrementIndex(member));
        member.setName(name);
        member.setDepartment(department);
        member.setTitle(title);
        member.setSalary(salary);

        realm.beginTransaction();
        realm.copyToRealm(member);
        realm.commitTransaction();

    }

    // 현제 테이블에 있는 모든 Member를 리스트로 받는 부분
    public ArrayList<MemberItem> getMembers() {
        RealmResults<Member> members = realm.where(Member.class).findAll().sort("index", Sort.DESCENDING);
        ArrayList<MemberItem> memberItems = new ArrayList<>();

        // 질의한 결과를 RecyclerView에서 이용할 수 있도록 arrayList에 넣어주는 부분
        for (int i = 0; i < members.size(); i++) {
            memberItems.add(new MemberItem(
                    members.get(i).getIndex(),
                    members.get(i).getName(),
                    members.get(i).getDepartment(),
                    members.get(i).getTitle(),
                    members.get(i).getSalary()
            ));
        }

        return memberItems;
    }

    public RealmResults<Member> getMemberWithIndex(int index) {
        return realm.where(Member.class).equalTo("index", index).findAll();
    }

    // index에 해당하는 정보를 변경
    public void editMemberWithIndex(int index, String name, String department, String title, int salary) {
        RealmResults<Member> result = getMemberWithIndex(index);

        if (result.isEmpty()) {
            return;
        }

        Member member = result.get(0);

        realm.beginTransaction();
        member.setName(name);
        member.setDepartment(department);
        member.setTitle(title);
        member.setSalary(salary);
        realm.commitTransaction();

    }

    // index 값을 받아서 해당하는 member를 삭제
    public void deleteMemberWithIndex(int index) {
        RealmResults<Member> result = getMemberWithIndex(index);

        if (result.isEmpty()) {
            return;
        }

        realm.beginTransaction();
        result.deleteAllFromRealm();
        realm.commitTransaction();

    }

    public int getNewMemberIndex() {
        return getAutoIncrementIndex(new Member());
    }

    // Realm에 Autoincrement가 없기 때문에 수동으로 처리해주는 부분
    private int getAutoIncrementIndex(Object object) {
        int nextIndex;
        Number currentIndex = null;

        // 질의 부분에서 instance를 확인하여 들어가는 부분
        // index에서 가장 큰 값을 구해서, 1을 더해준다.
        if (object instanceof Member) {
            currentIndex = realm.where(Member.class).max("index");
        }

        if (currentIndex == null) {
            nextIndex = 0;
        }
        else {
            nextIndex = currentIndex.intValue() + 1;
        }

        return nextIndex;
    }

}
