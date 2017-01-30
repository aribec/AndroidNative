package edu.uoc.TDDM.practica2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class TimePickerFragment extends DialogFragment {
		private int year;
		private int month;
		private int day;
			
		public TimePickerFragment (int day, int month, int year){
	        // Per defecte el dialog utilitza la data actual
			// establerta a la activitat
			this.day = day;
			this.month = month;
			this.year = year;
		}
		
		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        return new DatePickerDialog(getActivity(), (ShippingPointActivity)getActivity(), year, month, day);
	    }

	    public void onDateSet(DatePicker view, int year, int month, int day) {
	        // Do something with the date chosen by the user
	    }
}
