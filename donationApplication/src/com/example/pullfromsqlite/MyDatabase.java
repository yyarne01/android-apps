package com.example.pullfromsqlite;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "dd";
	private static final int DATABASE_VERSION = 1;
	private static final int MODE_PRIVATE=0 ;
	SharedPreferences m;
	
	public MyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	
		
		 m = context.getSharedPreferences("ConfigList", MODE_PRIVATE);
	}

	public Cursor getCharities() {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

/*	String [] sqlSelect = {"0 _id", "Contact", "Name", "Category"}; 
		String sqlTables = "Charities";	
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null,null, null, null);		
		c.moveToFirst();
		return c;*/
			
		String vg = m.getString("myString", "empty");
		Log.v("category;", vg);		
		
		
		String q = "SELECT _id,name,contact,category FROM Charities WHERE category= '" +vg+"'" ;		
		
		Cursor c= db.rawQuery(q, null);
		
	//Cursor c= db.rawQuery("SELECT _id, name FROM Charities WHERE category = 'homeless' ", null);
	c.moveToFirst();
	return c;	
		

	}



	



}
