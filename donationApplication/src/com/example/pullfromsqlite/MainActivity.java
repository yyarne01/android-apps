package com.example.pullfromsqlite;


import com.paypal.android.simpledemo.SimpleDemo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnItemClickListener{

	private Cursor charities;
	private MyDatabase db;
	private SharedPreferences myPreference;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
	
		
		// pull string from the preference
		//String vg = myPreference.getString("myString", "empty");

		
		// log all pulled values	
		//Log.v("log","Name = " + vg);

		db = new MyDatabase(this);
	
		charities = db.getCharities(); // you would not typically call this on the main thread
		
		

		@SuppressWarnings("deprecation")
		ListAdapter adapter = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_1, 
				charities, 
				new String[] {"name"},
				new int[] {android.R.id.text1});		

	getListView().setAdapter(adapter);		

	
        
       // ListView.setOnItemClickListener(this);
		 ListView lv = getListView();
		  lv.setOnItemClickListener(this) ;   	
    	
	}


	@Override
	public void onItemClick(AdapterView<?>listView, View view, 
		     int position, long id) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "Clicked on :" + position, Toast.LENGTH_SHORT).show();
		

		db = new MyDatabase(this);
		charities = db.getCharities();
		
		  charities =  (Cursor) listView.getItemAtPosition(position);
		  String email =   charities.getString( charities.getColumnIndex("contact"));
				
		  /*   Toast.makeText(getApplicationContext(),
				     email, Toast.LENGTH_SHORT).show();*/
		  
					  Intent intent = new Intent(MainActivity.this,com.paypal.android.simpledemo.SimpleDemo.class);
					
						
								Bundle bundle1 = new Bundle();
								bundle1.putString("email", email);
								intent.putExtras(bundle1);
								  startActivity(intent);		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		charities.close();
		db.close();
	}



}