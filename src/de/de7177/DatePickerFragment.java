package de.de7177;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {
	
	DateListenerActivity listener;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		this.listener = (DateListenerActivity) getActivity(); 
		
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String formattedDate = sdf.format(c.getTime());
		
		if (listener != null)  {
			listener.returnDate(formattedDate); 
		}
	}
}