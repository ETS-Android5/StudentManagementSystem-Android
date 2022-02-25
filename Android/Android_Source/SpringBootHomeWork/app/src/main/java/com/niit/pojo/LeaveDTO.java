package com.niit.pojo;

public class LeaveDTO {
    private Integer leaveid;

    private Integer stuid;

    private String leavedate;

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

    public String getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(String leavedate) {
        this.leavedate = leavedate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveDTO(Integer leaveid, Integer stuid, String leavedate, String reason) {
        this.leaveid = leaveid;
        this.stuid = stuid;
        this.leavedate = leavedate;
        this.reason = reason;
    }
    public LeaveDTO(){}
}
