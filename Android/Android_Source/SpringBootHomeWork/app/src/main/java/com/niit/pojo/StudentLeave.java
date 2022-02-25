package com.niit.pojo;

import java.util.Date;

public class StudentLeave {

	private String name;
	private String batch;
	private String age;
	private Date leaveDate;
	private String reason;

	public StudentLeave(String name, String batch, String age, Date leaveDate, String reason) {
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

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
