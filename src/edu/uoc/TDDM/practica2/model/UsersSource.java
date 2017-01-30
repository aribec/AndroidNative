package edu.uoc.TDDM.practica2.model;

import java.util.ArrayList;

public class UsersSource {
	private ArrayList<User> users;
	
	private static UsersSource myInstance= new UsersSource();
	 
	private UsersSource(){
		users = new ArrayList<User>();
	}


	public static UsersSource instance() {
	 return myInstance;
	}

	public ArrayList<User> getUsers() {
	 return users;
	}
	
	public void addUser(User user){
		users.add(user);
	}
}
