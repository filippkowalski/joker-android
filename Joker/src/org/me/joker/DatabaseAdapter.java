package org.me.joker;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
 
public class DatabaseAdapter{

		private static String DB_PATH = "/data/data/org.me.joker/databases/";
        public static final String DB_NAME = "jokes.db";
        public static final String TABLE_NAME = "kategorie";
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
       
        
        /*
         * Metoda zwraca kawal o podanym ID
         */
        
        public String loadJoke(int id){
               
                DatabaseHelper dbh = new DatabaseHelper(context);
               
                dbh.openDatabase();
               
                String joke = null;
                db = dbh.getDatabase();
                Cursor c = db.rawQuery("SELECT text FROM " + DB_TABLE + " WHERE _id like " + getLastJoke(id), null);
       
                c.moveToFirst();
                joke = c.getString(c.getColumnIndex("text"));
                db.close();
                dbh.close();
                c.close();
                return joke;
        }
       
       /*
        * Metoda zwraca ID ostatniego ogladanego ID
        */
       
        private int getLastJoke(int id) {
            DatabaseHelper dbh = new DatabaseHelper(context);              
            dbh.openDatabase();        
            int ostatni = 1;
            db = dbh.getDatabase();
            Cursor c = db.rawQuery("SELECT ostatni FROM " + TABLE_NAME + " WHERE _id like " + (id + 1), null);
            c.moveToFirst();
           
            ostatni = c.getInt(c.getColumnIndex("ostatni"));
            c.close();
            dbh.close();
            db.close();
            
            return ostatni;
        }
   
        
        /*
         * Metoda zwiêksza w bazie danych
         * wartoœæ kolumny ostatni o 1
         */
        
        public void setLastJokePlus(int catID){
        	DatabaseHelper dbh = new DatabaseHelper(context);
    		dbh.openDatabase();
    		db = dbh.getDatabase();
    		int lastID = getLastJoke(catID);
    		lastID++;
    		if (lastID > getLastInsertedID(DB_TABLE)){
    			lastID = 1;
    		}
    		ContentValues data = new ContentValues();
    		data.put("ostatni", lastID);
    		String myPath = DB_PATH + DB_NAME;
    		db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    		db.update(TABLE_NAME, data, "_id=" + (catID + 1), null);
    		dbh.close();
    		db.close();
        }
        
        
        /*
         * Metoda zwraca ostatnie id w kategorii
         */
        
        public int getLastInsertedID(String TABLE){
        	db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        	Cursor c = db.query(TABLE, new String[] {"_id"}, null, null, null, null, null);
        	c.moveToLast();
        	int lastID = (int)c.getLong(c.getColumnIndex("_id"));
        	return lastID;
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
                c.close();
                dbh.close();
                db.close();
                return category;
        }
}