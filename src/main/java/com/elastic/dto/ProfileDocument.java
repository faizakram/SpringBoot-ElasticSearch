package com.elastic.dto;

import java.util.List;

public class ProfileDocument {

	private String id;
	private String firstName;
	private String lastName;
	private List<String> technologies;
	private List<String> emails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<String> technologies) {
		this.technologies = technologies;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

}
