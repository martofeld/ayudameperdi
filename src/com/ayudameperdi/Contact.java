package com.ayudameperdi;

public class Contact {
	private String number;
	private String name;
	
	public Contact(){
		number = "";
		name = "";
	}
	public Contact(String name, String number){
		this.number = number;
		this.name = name;
	}
	public void setNumber(String number){
		this.number=number;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getNumber(){
		return this.number;
	}
	public String getName(){
		return this.name;
	}
}
