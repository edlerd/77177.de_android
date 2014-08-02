package de.de7177;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends DateListenerActivity {

	public final static String EXTRA_TIME = "de.de7177.TIME";
	public final static String EXTRA_FROM = "de.de7177.FROM";
	public final static String EXTRA_DEST = "de.de7177.DEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.prepareStartCitySpinner();
        this.prepareDestinationCitySpinner();
        
        Button dateBtn = (Button) findViewById(R.id.pick_date);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
    	dateBtn.setText(dateBtn.getText() + ": " + dateFormat.format(new Date()));
    	
    	Button timeBtn = (Button) findViewById(R.id.pick_time);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.GERMANY);
        timeBtn.setText(timeBtn.getText() + ": " + timeFormat.format(new Date()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_create) {
        	Intent intent = new Intent(this, CreateFahrtActivity.class);
        	startActivity(intent);
        }
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayFahrtResultsActivity.class);
    	
    	Button dateBtn = (Button) findViewById(R.id.pick_date);
    	String date = dateBtn.getText().toString();

    	intent.putExtra(EXTRA_TIME, date);

    	Spinner startSpn = (Spinner) findViewById(R.id.start_spinner);
    	String start = startSpn.getSelectedItem().toString();
    	intent.putExtra(EXTRA_FROM, start);
    	
    	Spinner endSpn = (Spinner) findViewById(R.id.destination_spinner);
    	String end = endSpn.getSelectedItem().toString();
    	intent.putExtra(EXTRA_DEST, end);
    	startActivity(intent);
    }

	public void processResult(String json, int target) {
		Spinner spn = (Spinner) findViewById(target);
		ArrayAdapter myAdap = (ArrayAdapter) spn.getAdapter();
		int spinnerPosition = myAdap.getPosition(json);
		spn.setSelection(spinnerPosition);
	}
	
	private String getApiLatLong() {
		// Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double lat=0.,lon=0.;
        try {
          lat = location.getLatitude ();
          lon = location.getLongitude ();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

		String loadme = "http://77177.de/api/city/nearest/?lat="+lat.toString()+"&lon="+lon.toString();
		return loadme;
	}
	
	public void loadGpsStart(View view) {
		String loadme = this.getApiLatLong();
		RequestTask rt = new RequestTask(this);
		rt.setTarget(R.id.start_spinner);
		rt.execute(loadme);
	}
	
	public void loadGpsDest(View view) {
		String loadme = this.getApiLatLong();
		RequestTask rt = new RequestTask(this);
		rt.setTarget(R.id.destination_spinner);
		rt.execute(loadme);
	}
}
