package de.de7177;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DisplayFahrtDetailsActivity extends RequestTaskActivity {
	
	ArrayList<HashMap<String, String>> resultArrayList = new ArrayList<HashMap<String, String>>();

	private static final String TAG_DRIVER_NAME = "driver_name";
	private static final String TAG_DRIVER_PHONE = "driver_phone";
	private static final String TAG_DRIVER_EMAIL = "driver_email";
	private static final String TAG_FROM = "from";
	private static final String TAG_DEST= "dest";
	private static final String TAG_SEATS= "seats";
	private static final String TAG_COSTS= "costs";
	private static final String TAG_DATE= "date";
	private static final String TAG_DETAILS= "details";
	private static final String TAG_VIA= "via";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_fahrt);
		
		Intent intent = getIntent();
		String fahrt_id = intent.getStringExtra(DisplayFahrtResultsActivity.FAHRT_ID);
		String route_id = intent.getStringExtra(DisplayFahrtResultsActivity.ROUTE_ID);
		
		String loadme = "http://77177.de/api/fahrt/detail/?id=" + fahrt_id + "&route=" + route_id;
		new RequestTask(this).execute(loadme);
	
		TextView detailView = (TextView) findViewById(R.id.detail);
		detailView.setText(R.string.loading);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_fahrt, menu);
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
	
	public void processResult(String json) {

		TextView detailView = (TextView) findViewById(R.id.detail);
		detailView.setText("");
		
		try {
			JSONObject jObj = new JSONObject(json);	
				
			
	        // Adding value HashMap key => value
	        HashMap<String, String> mapName = new HashMap<String, String>();
	        mapName.put("desc", "Fahrer");
	        mapName.put("content", jObj.getString(TAG_DRIVER_NAME));
	        resultArrayList.add(mapName);


	        HashMap<String, String> mapTel = new HashMap<String, String>();
	        mapTel.put("desc", "Tel");
	        mapTel.put("content", jObj.getString(TAG_DRIVER_PHONE));
	        resultArrayList.add(mapTel);

	        HashMap<String, String> mapEmail = new HashMap<String, String>();
	        mapEmail.put("desc", "Email");
	        mapEmail.put("content", jObj.getString(TAG_DRIVER_EMAIL));
	        resultArrayList.add(mapEmail);

	        HashMap<String, String> mapFrom = new HashMap<String, String>();
	        mapFrom.put("desc", "Start");
	        mapFrom.put("content", jObj.getString(TAG_FROM));
	        resultArrayList.add(mapFrom);

	        HashMap<String, String> mapDest = new HashMap<String, String>();
	        mapDest.put("desc", "Ziel");
	        mapDest.put("content", jObj.getString(TAG_DEST));
	        resultArrayList.add(mapDest);

	        HashMap<String, String> mapSeats = new HashMap<String, String>();
	        mapSeats.put("desc", "Sitze");
	        mapSeats.put("content", jObj.getString(TAG_SEATS));
	        resultArrayList.add(mapSeats);

	        HashMap<String, String> mapCosts = new HashMap<String, String>();
	        mapCosts.put("desc", "Kosten");
	        mapCosts.put("content", jObj.getString(TAG_COSTS));
	        resultArrayList.add(mapCosts);

	        HashMap<String, String> mapDate = new HashMap<String, String>();
	        mapDate.put("desc", "Datum");
	        mapDate.put("content", jObj.getString(TAG_DATE));
	        resultArrayList.add(mapDate);

	        HashMap<String, String> mapDetails = new HashMap<String, String>();
	        mapDetails.put("desc", "Details");
	        mapDetails.put("content", jObj.getString(TAG_DETAILS).replaceAll("<br />","\n"));
	        resultArrayList.add(mapDetails);

	        HashMap<String, String> mapVia = new HashMap<String, String>();
	        mapVia.put("desc", "Über");
	        mapVia.put("content", jObj.getString(TAG_VIA));
	        resultArrayList.add(mapVia);
			        
	        ListView list=(ListView)findViewById(R.id.fahrt_details);
	        ListAdapter adapter = new SimpleAdapter(this, resultArrayList,
	            R.layout.fahrt_detail_row,
	            new String[] { "desc", "content"}, new int[] {
	                R.id.desc,R.id.content});
	        list.setAdapter(adapter);
	              
	        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(
                		AdapterView<?> parent,
                		View view,
                		int position,
                		long id)
                {

                	if (position == 1) {
	        	        Intent callIntent = new Intent(Intent.ACTION_CALL);
                		String number = resultArrayList.get(position).get("content");
	        	        callIntent.setData(Uri.parse("tel:" + number));
	        	        startActivity(callIntent);
                	}
        	        
                }
            });

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
