package com.mkyong.android;

import com.example.pullfromsqlite.MainActivity;
import com.mkyong.android.MyAndroidAppActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

	private Context mContext;

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
	/*	Toast.makeText(parent.getContext(), 
				"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
				Toast.LENGTH_SHORT).show();*/
	
		
/*		  Intent myIntent = new Intent(mContext, MainActivity.class);
		  mContext.startActivity(myIntent);*/
	
	/*	intent.putExtras(bundle1);
		  startActivity(intent);*/
			  
			  
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}