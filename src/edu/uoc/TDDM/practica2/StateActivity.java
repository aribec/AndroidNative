package edu.uoc.TDDM.practica2;

import java.util.ArrayList;

import edu.uoc.TDDM.practica2.model.Shipping;
import edu.uoc.TDDM.practica2.model.ShippingsSource;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StateActivity extends ActionBarActivity {
	Context mContext;

	ArrayList<Shipping> enviaments;

	private ListView enviamentListView ;  
	private ListAdapter customAdapter;  
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_state);	
			mContext = this;
						
			enviamentListView = (ListView) findViewById( R.id.enviamentListView );  
	
			enviaments = ShippingsSource.instance().getShippings();

			ArrayList<String> enviamentsList = new ArrayList<String>();  
		    for (Shipping enviament:enviaments){
		    	enviamentsList.add(enviament.getEstatComplet());
		    }
		   
		    customAdapter = new ListAdapter(this,enviamentsList);
		    enviamentListView.setAdapter(customAdapter);
	
			DisplayMetrics display = this.getResources().getDisplayMetrics();
			int height = display.heightPixels;
		    
			LinearLayout ll = (LinearLayout) findViewById( R.id.listViewLayout );  
			ViewGroup.LayoutParams params = ll.getLayoutParams();
			
			params.height=3*(height/8);
			ll.setLayoutParams(params);
		   
			enviamentListView.setClickable(true);
			enviamentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				    	setShippingData(enviaments.get(position));		
				  }
			});
			
			//Carregar per defecte el primer enviament del llistat
			if(enviaments!=null && enviaments.size()>0){
				setShippingData(enviaments.get(0));
			}
	}

 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.state, menu);
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
		if (id == R.id.action_nouEnviament){
        	Intent i = new Intent(getApplicationContext(),ShippingPointActivity.class);
        	startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void nouEnviament(View view){
    	Intent i = new Intent(getApplicationContext(),LoginActivity.class);
    	startActivity(i);
	}
	
	public void setShippingData(Shipping shipping){
		TextView  location=null;
        location = (TextView)findViewById(R.id.locationtxt);
        location.setText(shipping.getLocation());

        TextView  estimatedTime=null;
		estimatedTime= (TextView)findViewById(R.id.estimatedTimetxt);
		estimatedTime.setText(shipping.getDeliveryDate());

		TextView deliveryAddress=null;
		deliveryAddress = (TextView)findViewById(R.id.deliveryAddresstxt);
		deliveryAddress.setText(shipping.getDeliveryAddress());
		
		TextView sendingAddress=null;
		sendingAddress = (TextView)findViewById(R.id.sendingAddresstxt);
		sendingAddress.setText(shipping.getSendingAddress());
		
		TextView shippingName=null;
		shippingName = (TextView)findViewById(R.id.shippingNametxt);
		shippingName.setText(shipping.getName());
	}
	
	
	
}
