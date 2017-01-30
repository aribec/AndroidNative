package edu.uoc.TDDM.practica2;

import java.util.ArrayList;

import edu.uoc.TDDM.practica2.model.Shipping;
import edu.uoc.TDDM.practica2.model.ShippingsSource;
import edu.uoc.TDDM.practica2.model.User;
import edu.uoc.TDDM.practica2.model.UsersSource;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {
	private EditText username=null;
	private EditText password=null;
	private Button login;
	int counter = 3;
	ArrayList<User> users;
	
	//Es crea un origen de dades local per realitzar les proves
	private void setDataStore(){		
		UsersSource.instance().addUser(new User("admin","admin1"));
		UsersSource.instance().addUser(new User("usuari","usuari1"));
		
		ShippingsSource.instance().clear();
		ShippingsSource.instance().addShipping(new Shipping("Enviament 1",getResources().getString(R.string.preparing),"12/10/2014 08:00","12/10/2014 15:00","Direcció D", "Al magatzem","Direcció O"));
		ShippingsSource.instance().addShipping(new Shipping("Enviament 2",getResources().getString(R.string.inprocess),"12/10/2014 08:00","12/10/2014 16:00","Direcció D", "Al magatzem","Direcció O"));
		ShippingsSource.instance().addShipping(new Shipping("Enviament 3",getResources().getString(R.string.pending),"12/10/2014 08:00","12/10/2014 24:00","Direcció D", "Al magatzem","Direcció O"));
		ShippingsSource.instance().addShipping(new Shipping("Enviament 4",getResources().getString(R.string.dispatched),"12/10/2014 08:00","12/10/2014 16:00","Direcció D", "Al magatzem","Direcció O"));
		ShippingsSource.instance().addShipping(new Shipping("Enviament 5",getResources().getString(R.string.dispatched),"12/10/2014 08:00","12/10/2014 24:00","Direcció D", "Al magatzem","Direcció O"));
		ShippingsSource.instance().addShipping(new Shipping("Enviament 6",getResources().getString(R.string.dispatched),"12/10/2014 08:00","12/10/2014 16:00","Direcció D", "Al magatzem","Direcció O"));
		ShippingsSource.instance().addShipping(new Shipping("Enviament 7",getResources().getString(R.string.pending),"12/10/2014 08:00","12/10/2014 24:00","Direcció D", "Al magatzem","Direcció O"));
		
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.usertxt);
        password = (EditText)findViewById(R.id.passwordtxt);
        login = (Button)findViewById(R.id.loginbtn);
        username.setText("admin");
        password.setText("admin1");
        
        users=UsersSource.instance().getUsers();
        setDataStore();
        
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				login();
			}
		});
		
		//quan es prem enter a la contrasenya 
		//efectua l'operació de login igual que 
		//quan es prem el botó
		password.setOnKeyListener(new View.OnKeyListener(){
			   public boolean onKey(View v, int keyCode, KeyEvent event) {
			        // If the event is a key-down event on the "enter" button
			        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
			            (keyCode == KeyEvent.KEYCODE_ENTER)) {
			          // Perform action on key press
			        	login();
			        	return true;
			        }
			        return false;
			    }
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
        return super.onOptionsItemSelected(item);
    }
    
    public void login(){
    	boolean loginOk = false;
    	
    	for(User u: users){
    		if(u.getuserName().equals(username.getText().toString()) &
    				u.getPassword().equals(password.getText().toString())){
    			loginOk = true;
    			break;
    		}
    	}
    	
    	if(loginOk){
  			//usuari i contrasenya correctes
        	Intent i = new Intent(getApplicationContext(),StateActivity.class);
        	startActivity(i);
    	}else{
    		//contrasenya incorrecte
	        Toast.makeText(getApplicationContext(),  getResources().getString(R.string.wrongCredentials),
	        Toast.LENGTH_SHORT).show();
	        counter--;
	        if(counter==0)
	        {
		       Toast.makeText(getApplicationContext(),  getResources().getString(R.string.wrongCredentialsMaxAttempts),
    		   Toast.LENGTH_SHORT).show();
	           login.setEnabled(false);
	        }

    	}
    }
}
