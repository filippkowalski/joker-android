package org.me.joker;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static String DB_PATH = "/data/data/org.me.joker/databases/";
	private static String DB_NAME = "jokes.db";
	private SQLiteDatabase jokes;
	private final Context myContext;
	
	
	public DatabaseHelper(Context context){
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}
	
	
	/* Tworzy pusta baze danych i przepisuje do niej
	 * baze wczesniej utworzona
	 */
	
	public void createDatabase() throws IOException{
		
		boolean dbExist = checkDatabase();
		
		if(dbExist){
			
		}
		else{
			
			this.getReadableDatabase();
			
			try{
				
				copyDatabase();
				
			}
			catch(IOException e){
				throw new Error("Error copying database");
			}
		}
	}
	
	
	/*sprawdza czy istnieje
	 * baza danych, aby uniknac kopiowania
	 * przy kolejnym otwarciu aplikacji
	 * Zwraca true gdy istnieje
	 */
	
	private boolean checkDatabase(){
		
		SQLiteDatabase checkDB = null;
		
		try{
			
			String myPath = DB_PATH + DB_NAME;
			
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			
		}
		catch(SQLiteException e){
			
			// database does not exist yet
			
		}
		
		if (checkDB != null){
			
			checkDB.close();
			
		}
		
		return checkDB != null ? true : false;
	}
	
	
	/*Kopiuje istniejaca baze do pustej bazy
	 * stworzonej w systemie telefonu
	 */
	private void copyDatabase() throws IOException{
		
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		
		String outFileName = DB_PATH + DB_NAME;
		
		OutputStream myOutput = new FileOutputStream(outFileName);
		
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0){
			
			myOutput.write(buffer, 0, length);
			
		}
		
		
		myOutput.flush();
		myOutput.close();
		myInput.close();
		
	}
	
	
	public void openDatabase() throws SQLiteException{
		
		String myPath = DB_PATH + DB_NAME;
		jokes = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		
	}
	
	
	public synchronized void close(){
		
		if (jokes != null){
			jokes.close();
		}
		
		super.close();
		
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db){
		
	}
	


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
