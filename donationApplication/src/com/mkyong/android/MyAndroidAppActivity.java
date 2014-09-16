package com.mkyong.android;

import com.example.pullfromsqlite.MainActivity;
import com.example.pullfromsqlite.MyDatabase;
import com.paypal.android.simpledemo.R;

import com.paypal.android.simpledemo.R.layout;
import com.paypal.android.simpledemo.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;



import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MyAndroidAppActivity extends Activity {

	private Spinner spinner1, spinner2;
	private Button btnSubmit;
	public static SharedPreferences myPreference;
	public static Object PreferenceKey;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);

		//addItemsOnSpinner2();
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();

	}

/*	//add items into spinner dynamically
	public void addItemsOnSpinner2() {

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list = new ArrayList<String>();
		list.add("list 1");
		list.add("list 2");
		list.add("list 3");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
	}*/

	public void addListenerOnSpinnerItemSelection(){
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	//get the selected dropdown list value
	public void addListenerOnButton() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
	//	spinner2 = (Spinner) findViewById(R.id.spinner2);
		
		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*Toast.makeText(MyAndroidAppActivity.this,
						"OnClickListener : " + 
						"\nSpinner 1 : " + String.valueOf(spinner1.getSelectedItem()) ,
						//"\nSpinner 2 : " + String.valueOf(spinner2.getSelectedItem()),
						Toast.LENGTH_SHORT).show();*/
				
				String cname=String.valueOf(spinner1.getSelectedItem());
				
			
				myPreference = getSharedPreferences("ConfigList",
						Context.MODE_PRIVATE);
				
				// get a new editor for the preference
				Editor editor = myPreference.edit();

				// write string to the preference
				editor.putString("myString", cname);			
				
				// commit the changes to the editor
				editor.commit();
				

				  Intent myIntent = new Intent(MyAndroidAppActivity.this, com.example.pullfromsqlite.MainActivity.class);
				 
				  startActivity(myIntent);
			}

		});

	}

}