package com.netpay.fs.models;

public class Directory {

	private int id;
	private String name;
	private Directory parent;
	
	public Directory(String name, Directory parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Directory getParent() {
		return parent;
	}
	public void setParent(Directory parent) {
		this.parent = parent;
	}
	
}
