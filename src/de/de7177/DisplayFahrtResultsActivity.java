package de.de7177;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DisplayFahrtResultsActivity extends RequestTaskActivity {
		
	ArrayList<HashMap<String, String>> resultArrayList = new ArrayList<HashMap<String, String>>();

	private static final String TAG_RESULTS = "results";
	private static final String TAG_ROUTEID = "routeid";
	private static final String TAG_FAHRTID = "fahrtid";
	private static final String TAG_START = "start";
	private static final String TAG_TARGET = "end";
	private static final String TAG_SEATS = "seats";
	private static final String TAG_COSTS = "costs";
	private static final String TAG_DATETIME = "date";

	protected static final String FAHRT_ID = "de.de7177.fahrt_id";
	protected static final String ROUTE_ID = "de.de7177.route_id";

	protected static final String TAG_ROUTE_ID = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);

		Intent intent = getIntent();
		String time = intent.getStringExtra(MainActivity.EXTRA_TIME);
		String from = intent.getStringExtra(MainActivity.EXTRA_FROM);
		String dest = intent.getStringExtra(MainActivity.EXTRA_DEST);
				
		String loadme = "http://77177.de/api/search/?from=" + from + "&dest=" + dest + "&date=1406751120&tollerance=11";
		new RequestTask(this).execute(loadme);
 	
		TextView detailView = (TextView) findViewById(R.id.textView1);
		detailView.setText(R.string.loading);
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

	/**
	 * example json:

{
   "results":[
      {
         "start":"Hamburg",
         "target":"Berlin",
         "seats":"3",
         "costs":"10",
         "datetime":"1.1.2015"
      },
      {
         "start":"München",
         "target":"Berlin",
         "seats":"1",
         "costs":"12",
         "datetime":"2.1.2015"
      }
   ]
}

	 *
	 */
	public void processResult(String json) {
		
		TextView detailView = (TextView) findViewById(R.id.textView1);
		detailView.setText("");
				
		try {
			JSONObject jObj = new JSONObject(json);
			JSONArray jObjRows = jObj.getJSONArray(TAG_RESULTS);	

			for(int i = 0; i < jObjRows.length(); i++){
				JSONObject c = jObjRows.getJSONObject(i);
				
		        // Adding value HashMap key => value
		        HashMap<String, String> map = new HashMap<String, String>();
		        map.put("start", c.getString(TAG_START));
		        map.put("target", c.getString(TAG_TARGET));
		        map.put("seats", c.getString(TAG_SEATS));
		        map.put("costs", c.getString(TAG_COSTS));
		        map.put("datetime", c.getString(TAG_DATETIME));
		        map.put("routeid", c.getString(TAG_ROUTEID));
		        map.put("fahrtid", c.getString(TAG_FAHRTID));
		        
		        resultArrayList.add(map);
			        
		        ListView list=(ListView)findViewById(R.id.list);
		        ListAdapter adapter = new SimpleAdapter(this, resultArrayList,
		            R.layout.result_row,
		            new String[] { "start", "target", "seats", "costs", "datetime", "routeid", "fahrtid"}, new int[] {
		                R.id.start,R.id.target, R.id.seats, R.id.costs, R.id.datetime, R.id.routeid, R.id.fahrtid });
		        list.setAdapter(adapter);
		        
		        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	                @Override
	                public void onItemClick(
	                		AdapterView<?> parent,
	                		View view,
	                		int position,
	                		long id)
	                {

	                	Intent intent = new Intent(parent.getContext(), DisplayFahrtDetailsActivity.class);
	                	
	                	
	                	String fahrt_id = resultArrayList.get(+position).get("fahrtid");
	                	String route_id = resultArrayList.get(+position).get("routeid");

	                	intent.putExtra(FAHRT_ID, fahrt_id);
	                	intent.putExtra(ROUTE_ID, route_id);
	                	startActivity(intent);
	                }
	            });
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
