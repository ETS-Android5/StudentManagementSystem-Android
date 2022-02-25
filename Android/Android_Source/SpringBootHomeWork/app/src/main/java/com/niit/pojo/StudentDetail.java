package com.niit.pojo;

public class StudentDetail {

	private Integer stuID;
	private String name;
	private String batch ;
	private String password;
	private String age;
	private String address;
	private String contactNumber;
	private String email;

	public StudentDetail() {
	}

	public Integer getStuID() {
		return stuID;
	}

	public void setStuID(Integer stuID) {
		this.stuID = stuID;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public StudentDetail(Integer stuID, String name, String batch, String password, String age, String address, String contactNumber, String email) {
		this.stuID = stuID;
		this.name = name;
		this.batch = batch;
		this.password = password;
		this.age = age;
		this.address = address;
		this.contactNumber = contactNumber;
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

