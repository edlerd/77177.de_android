package de.de7177;

import android.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

abstract class DateListenerActivity extends RequestTaskActivity {
	
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    
    public void prepareStartCitySpinner() {
    	Spinner spinner = (Spinner) findViewById(R.id.start_spinner);
    	String description = this.getResources().getString(R.string.pick_start_city);
    	this.fillSpinnerWithCities(spinner, description);
    	
    	ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
    	spinner.setSelection(myAdap.getPosition("Hamburg"));
    }
    
    public void  prepareDestinationCitySpinner() {
    	Spinner spinner = (Spinner) findViewById(R.id.destination_spinner);
    	String description = this.getResources().getString(R.string.pick_destination_city);
    	this.fillSpinnerWithCities(spinner, description);    
    	
    	ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
    	spinner.setSelection(myAdap.getPosition("München"));
    }
    
    public void fillSpinnerWithCities(Spinner spinner, String description) {
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    	        R.array.cities_array, android.R.layout.simple_spinner_item);
    	//adapter.add(description);
    	// Specify the layout to use when the list of choices appears
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	spinner.setAdapter(adapter);
    }


	public void returnDate(String formattedDate) {
    	Button button = (Button) findViewById(R.id.pick_date);
		String prefix = this.getResources().getString(R.string.pick_date);
    	String text = (prefix + ": " + formattedDate);
    	button.setText(text);
	}
	
	public void returnTime(String formattedDate) {
		Button button = (Button) findViewById(R.id.pick_time);
		String prefix = this.getResources().getString(R.string.pick_time);
    	String text = (prefix + ": " + formattedDate);
    	button.setText(text);
	}
	
	

}
