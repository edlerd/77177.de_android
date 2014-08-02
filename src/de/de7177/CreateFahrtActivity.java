package de.de7177;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

@SuppressLint("SimpleDateFormat")
public class CreateFahrtActivity extends DateListenerActivity {

	static final String EXTRA_TIME = "de.de7177.TIME_STR";
	static final String EXTRA_FROM = "de.de7177.FROM_STR";
	static final String EXTRA_DEST = "de.de7177.DEST_STR";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_fahrt);

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_create) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, SaveFahrtActivity.class);
    	
    	Button dateBtn = (Button) findViewById(R.id.pick_date);
    	String dateStr = dateBtn.getText().toString().split(" ")[1];
    	
    	Button timeBtn = (Button) findViewById(R.id.pick_time);
    	String timeStr = timeBtn.getText().toString().split(" ")[1];

    	DateFormat formatter = new SimpleDateFormat("dd.MM.yyyyHH:mm");
    	Date date;
		try {
			date = (Date)formatter.parse(dateStr + timeStr);
			timeStr = Long.toString(date.getTime() / 1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	intent.putExtra(EXTRA_TIME, timeStr);
    	
    	Spinner fromSpn = (Spinner) findViewById(R.id.start_spinner);
    	String fromStr = fromSpn.getSelectedItem().toString();
    	
    	intent.putExtra(EXTRA_FROM, fromStr);
    	
    	Spinner destSpn = (Spinner) findViewById(R.id.destination_spinner);
    	String destStr = destSpn.getSelectedItem().toString();
    	
    	intent.putExtra(EXTRA_DEST, destStr);
    	
    	startActivity(intent);
    }
}
