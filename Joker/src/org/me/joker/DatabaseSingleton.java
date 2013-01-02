package org.me.joker;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

public final class DatabaseSingleton implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private SQLiteDatabase db;

	
	public SQLiteDatabase getDatabase() {
		return db;
	}

	public void setDatabase(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void close(){
		db.close();
	}
}