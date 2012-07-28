package org.me.joker;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;

public class DatabaseAdapter{

	public static final String DB_NAME = "jokes.db";
	public String DB_TABLE;
	public static final String Key_ID = "_id";
	public static final String Key_Joke = "text";
	public String DB_ID = "1";
	
	public static Cursor cursor;
	
	private final Context context;
	public DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	
	public DatabaseAdapter(int category, Context context){
		DB_TABLE = getCategory(category);
		this.context = context;
		DBHelper = new DatabaseHelper(context);
	}
	
	public String loadJoke(){
		
		 DatabaseHelper dbh = new DatabaseHelper(context);
	       
	     dbh.openDatabase();
		
		String joke = null;
		db = dbh.getDatabase();
		Cursor c = db.rawQuery("SELECT text FROM " + DB_TABLE + " WHERE _id like 1", null);
	
		c.moveToFirst();
		joke = c.getString(c.getColumnIndex("text"));
		
		dbh.close();
		
		return joke;
	}
	
	/*
	 * Metoda ta zwraca nazwe kategorii
	 * o zadanym id z bazy danych
	 */
	
	public String getCategory(int id){
		 
		DatabaseHelper dbh = new DatabaseHelper(context);
	       
	    dbh.openDatabase();
		
		String category = null;
		db = dbh.getDatabase();
		Cursor c = db.rawQuery("SELECT kategoria FROM kategorie WHERE _id like " + (id + 1), null);
		c.moveToFirst();
		
		category = c.getString(c.getColumnIndex("kategoria"));
		
		dbh.close();
		
		return category;
	}

}
