package com.niit.pojo;

public class Admin {
    private Integer adminid;

    private String name;

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminid=" + adminid +
                ", name='" + name + '\'' +
                '}';
    }
}