package com.byjw.realmtest.Model;

/**
 * Created by jungwoon on 2017. 3. 7..
 */

// RecyclerView에서 사용할 모델
public class MemberItem {

    private int index;
    private String name;
    private String department;
    private String title;
    private int salary;

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }

    public int getSalary() {
        return salary;
    }

    public MemberItem(int index, String name, String department, String title, int salary) {
        this.index = index;
        this.name = name;
        this.department = department;
        this.title = title;
        this.salary = salary;
    }

}
