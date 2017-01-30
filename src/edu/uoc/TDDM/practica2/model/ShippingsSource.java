package edu.uoc.TDDM.practica2.model;

import java.util.ArrayList;

//Singleton que emmagatzema els enviaments
public class ShippingsSource {
	private ArrayList<Shipping> shippings;
	
	private static ShippingsSource myInstance= new ShippingsSource();
	 
	private ShippingsSource(){
		shippings = new ArrayList<Shipping>();
	}


	public static ShippingsSource instance() {
	 return myInstance;
	}

	public ArrayList<Shipping> getShippings() {
	 return shippings;
	}
	
	public void clear(){
		shippings.clear();
	}
	
	public void addShipping(Shipping shipping){
		shippings.add(shipping);
	}
	
	
}
