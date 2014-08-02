package de.de7177;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SaveFahrtActivity extends RequestTaskActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_fahrt);
		
		Intent intent = getIntent();
		String time = intent.getStringExtra(CreateFahrtActivity.EXTRA_TIME);
		String from = intent.getStringExtra(CreateFahrtActivity.EXTRA_FROM);
		String dest = intent.getStringExtra(CreateFahrtActivity.EXTRA_DEST);
				
		String loadme = "http://77177.de/api/fahrt/create/?date="+time+"&from="+from+"&dest="+dest;
		new RequestTask(this).execute(loadme);
		
		TextView detailView = (TextView) findViewById(R.id.textView1);
		detailView.setText(R.string.loading);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
	

	public void processResult(String json) {

		TextView detailView = (TextView) findViewById(R.id.textView1);
		detailView.setText(json);
	}
}
