package com.niit.pojo;

import java.util.Date;

public class Leave {
    private Integer leaveid;

    private Integer stuid;

    private Date leavedate;

    private String reason;

    public Integer getLeaveid() {
        return leaveid;
    }

    public void setLeaveid(Integer leaveid) {
        this.leaveid = leaveid;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public Date getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(Date leavedate) {
        this.leavedate = leavedate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "leaveid=" + leaveid +
                ", stuid=" + stuid +
                ", leavedate=" + leavedate +
                ", reason='" + reason + '\'' +
                '}';
    }

    public Leave(Integer leaveid, Integer stuid, Date leavedate, String reason) {
        this.leaveid = leaveid;
        this.stuid = stuid;
        this.leavedate = leavedate;
        this.reason = reason;
    }
    public Leave(){}

}