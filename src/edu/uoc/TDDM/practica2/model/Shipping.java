package edu.uoc.TDDM.practica2.model;

public class Shipping {
	String name;
	String state;
	String sendingDate;
	String deliveryDate;
	String deliveryAddress;
	String location;
	String sendingAddress;
	
	public Shipping(String name,String state,String sendingDate, 
			String deliveryDate, String deliveryAddress, String location, String sendingAddress){
	
		this.name = name;
		this.state = state;
		this.sendingDate = sendingDate;
		this.sendingAddress = sendingAddress;
		this.deliveryDate = deliveryDate;
		this.deliveryAddress = deliveryAddress;
		this.location = location;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getState(){
		return this.state;
	}
	
	public void setState(String state){
		this.state = state;
	}

	public String getSendingDate(){
		return this.sendingDate;
	}
	
	public void setSendingDate(String sendingDate){
		this.sendingDate = sendingDate;
	}

	public String getDeliveryDate(){
		return this.deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate){
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryAddress(){
		return deliveryAddress;
	}
	
	public void setDeliveryAddress(String deliveryAddress){
		this.deliveryAddress = deliveryAddress;
	}

	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location = location;
	}

	public String getSendingAddress(){
		return sendingAddress;
	}
	
	public void setSendingAddress(String sendingAddress){
		this.sendingAddress = sendingAddress;
	}
	
	public String getEstatComplet(){
		return this.name + "\t -> \t" + this.state;
	}
	
}
