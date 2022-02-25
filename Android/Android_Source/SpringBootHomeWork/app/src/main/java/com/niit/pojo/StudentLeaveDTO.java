package com.niit.pojo;

public class StudentLeaveDTO {
    private String name;
    private String batch;
    private String age;
    private String leaveDate;
    private String reason;
    private Integer leaveid;
    private Integer isagreed;

    public StudentLeaveDTO() {
    }

    public StudentLeaveDTO(String name, String batch, String age, String leaveDate, String reason) {
        this.name = name;
        this.batch = batch;
        this.age = age;
        this.leaveDate = leaveDate;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setLeaveId(Integer leaveid) {
        this.leaveid=leaveid;
    }

    public void setleaveIsagreed(Integer isagreed) {
        this.isagreed=isagreed;
    }

    public Integer getLeaveid() {
        return leaveid;
    }

    public Integer getIsagreed() {
        return isagreed;
    }
}
