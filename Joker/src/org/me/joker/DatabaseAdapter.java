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
	
	
	public DatabaseAdapter(String category, Context context){
		DB_TABLE = category;
		this.context = context;
		DBHelper = new DatabaseHelper(context);
	}
	
	public String loadJoke(){
		
		 DatabaseHelper dbh = new DatabaseHelper(context);
	       
	     dbh.openDatabase();
		
		String joke = null;
		db = dbh.getDatabase();
		Cursor c = db.rawQuery("SELECT text FROM oJasiu", null);
	
		c.moveToFirst();
		joke = c.getString(c.getColumnIndex("text"));
		
		return joke;
	}

}
