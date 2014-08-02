package de.de7177;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {
	
	DateListenerActivity listener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		this.listener = (DateListenerActivity) getActivity(); 
		
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
		DateFormat.is24HourFormat(getActivity()));
	}
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		
		Calendar c = Calendar.getInstance();
		c.set(2000,1,1,hourOfDay, minute);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String formattedDate = sdf.format(c.getTime());
		
		if (listener != null)  {
			listener.returnTime(formattedDate); 
		}
	}
}
