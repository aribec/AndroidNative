package edu.uoc.TDDM.practica2;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.uoc.TDDM.practica2.model.Shipping;
import edu.uoc.TDDM.practica2.model.ShippingsSource;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShippingPointActivity extends ActionBarActivity implements 
	OnClickListener, 
	android.content.DialogInterface.OnClickListener,
	DatePickerDialog.OnDateSetListener{

	private GoogleMap googleMap;
	
	private ImageButton getDateBtn;

	private EditText nameTxt;
	private TextView deliveryDateTxt;
	
	private LocationManager locManager;
	private LocationListener locListener;
	private Location mobileLocation;
	
	private int year;
	private int month;
	private int day;
		
	private String originValue;
	private String destinationValue;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shipping_point);

        final Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.day = c.get(Calendar.DAY_OF_MONTH);

		
        nameTxt = (EditText)findViewById(R.id.nametxt);
        deliveryDateTxt = (TextView)findViewById(R.id.deliveryDateTxt);

        //s'estableix el mapa
        setUpMapIfNeeded();

        //es determina la posició actual
        getCurrentLocation();

		addListeners();

	}
	
    private void setUpMapIfNeeded() {
        if (googleMap == null) {
        	googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        	googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        }
    }
 
    private void setUpMap() {
    	LatLng latLng = new LatLng(mobileLocation.getLatitude(), mobileLocation.getLongitude());
    	
    	googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

    	googleMap.addMarker(new MarkerOptions()
    			.position(new LatLng(mobileLocation.getLatitude(), mobileLocation.getLongitude()))
    			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
    			.draggable(true));
    	googleMap.addMarker(new MarkerOptions()
    			.position(new LatLng(mobileLocation.getLatitude(), mobileLocation.getLongitude()))
    			.title("Desti")
    			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
    			.draggable(true));
    	
    	setAddress(mobileLocation.getLatitude(), mobileLocation.getLongitude(), true);
    	setAddress(mobileLocation.getLatitude(), mobileLocation.getLongitude(), false);


    }
	
	//S'afegeixen els listeners dels botons de l'activitat
	private void addListeners(){
		getDateBtn = (ImageButton) findViewById(R.id.getDateBtn);
		getDateBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buttonGetDateClick();
			}
		});
		
		googleMap.setOnMarkerDragListener(new OnMarkerDragListener() {
	        @Override
	        public void onMarkerDragStart(Marker marker) {
	            // TODO Auto-generated method stub
	        }

	        @Override
	        public void onMarkerDragEnd(Marker marker) {
	            // TODO Auto-generated method stub
	            if(marker.getTitle()=="Origen"){
	            	setAddress(marker.getPosition().latitude,marker.getPosition().longitude,true);
	            }else{
	            	setAddress(marker.getPosition().latitude,marker.getPosition().longitude,false);
	            }
	        }

	        @Override
	        public void onMarkerDrag(Marker marker) {
	            // TODO Auto-generated method stub
	            //LatLng temp = marker.getPosition();
	            //marker.setPosition(temp);
	
	        }
	    });  

		
	}
	
	private void save(){
        if(nameTxt.getText().toString().equals("") || 
        	deliveryDateTxt.getText().toString().equals("") ||
        	originValue== null || originValue.equals("") ||
        	destinationValue ==null || destinationValue.equals("")){
        	
	        Toast.makeText(getApplicationContext(),  getResources().getString(R.string.missingData),
	        Toast.LENGTH_LONG).show();
        }else{	
			final Calendar c = Calendar.getInstance();
	
	        String todayDate = c.get(Calendar.DAY_OF_MONTH) + " - " + c.get(Calendar.MONTH) + " - " +c.get(Calendar.YEAR);
			
			Shipping newShipping = new Shipping(
					nameTxt.getText().toString(), 
					getResources().getString(R.string.pending), 
					todayDate, 
					deliveryDateTxt.getText().toString(), 
					destinationValue, 
					getResources().getString(R.string.withoutData),
					originValue);
			ShippingsSource.instance().addShipping(newShipping);

	        Toast.makeText(getApplicationContext(),  getResources().getString(R.string.saveOk),
	        Toast.LENGTH_LONG).show();
	        
	        //Una vegada guardat es torna al a finestra d'estat 
        	Intent i = new Intent(getApplicationContext(),StateActivity.class);
        	startActivity(i);
		}
	}
	
	private void buttonGetDateClick(){
		DialogFragment newFragment = new TimePickerFragment(day,month,year);
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		

		deliveryDateTxt.setText(new StringBuilder().append(day)
		   .append("-").append(month + 1).append("-").append(year)
		   .append(" "));
	   
	}
	
	private void setAddress(double latitude, double longitude, boolean isOrigin){	
		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());                 
		try {
		    List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
		    if(null!=listAddresses && listAddresses.size()>0){
		        String address = listAddresses.get(0).getAddressLine(0);
		        if(isOrigin)
		        	originValue =address;
		        else
		        	destinationValue = address;
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}

	//S'obté la posició actual del teléfon
	private void getCurrentLocation() {
		locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locListener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				if(mobileLocation==null){
					mobileLocation = location;
					setUpMap();
				}
			}
		};
		//Comprova si estan actius el GPS i el wifi i intenta establir la 
		//localització amb els 2 per si un falla
		if(locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
		}
		if(locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
		}
		
		
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shipping_point, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;	
		}
		if (id == R.id.action_logout) {
        	Intent i = new Intent(getApplicationContext(),LoginActivity.class);
        	startActivity(i);
        	finish();
		}
		if (id == R.id.action_estat){
        	Intent i = new Intent(getApplicationContext(),StateActivity.class);
        	startActivity(i);
		}
		if (id == R.id.action_save){
			save();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
		

}
