package com.niit.pojo.util;

public enum requestUrl {

    UserLogin("/User/login"),
    AdminToHome("/admin/to-home"),
    AdminAddNewStudent("/Admin/add_new_students"),
    AdminDeleteStudent("/Admin/delete_new_students?stuid="),
    AdminLookStudentLeave("/Admin/look_students_leave"),
    StudentModifyDetail("/Student/modify_detail"),
    StudentAskLeave("/Student/ask_leave"),
    StudentLookDetail("/Student/look_detail"),
    AdminModifyStuInfo("/Admin/modify_students_info"),
    AdminAgreeLeave("/Admin/agree_leave");

    private  final  String prefix="http://192.168.43.134:8086/restful";
    private final String suffix;



    private requestUrl(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getAll(){
        return prefix+suffix;
    }


}