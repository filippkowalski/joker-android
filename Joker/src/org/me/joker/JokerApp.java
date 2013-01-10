package org.me.joker;

import java.io.IOException;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public final class JokerApp extends Application{
	
	private static DatabaseHelper mDBHelper;
	
	@Override
	public void onCreate(){
		mDBHelper = new DatabaseHelper(this);
		
        try{
        	mDBHelper.createDatabase();
        }
        catch (IOException e){
        	throw new Error("Error creating database.");
        }
        
        mDBHelper.openDatabase();
        
	}

	@Override
	public void onTerminate(){
		mDBHelper.close();
		mDBHelper = null;
	}
	
	public static DatabaseHelper getDatabaseHelper() {
        return mDBHelper;
    }
	
}