package com.example.fyu.bean;

/**
 * Created by fyu on 2017-12-12.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Staff {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String sex;
    private String department;
    private double salary;
    @Generated(hash = 1231799507)
    public Staff(Long id, String name, String sex, String department,
            double salary) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.department = department;
        this.salary = salary;
    }
    @Generated(hash = 1774984890)
    public Staff() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public double getSalary() {
        return this.salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return getId()+" " + getName()+" " + getSex()+" " + getDepartment()+" " +getSalary();
    }
}
