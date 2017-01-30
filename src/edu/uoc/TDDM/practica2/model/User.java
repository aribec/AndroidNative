package edu.uoc.TDDM.practica2.model;

public class User {
	String userName;
	String password;
		
	public User(String userName,String password){
	
		this.userName = userName;
		this.password = password;
	}
	
	public String getuserName(){
		return userName;
	}
	
	public void setuserName(String userName){
		this.userName = userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password= password;
	}
}
