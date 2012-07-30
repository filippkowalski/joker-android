package org.me.joker;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
 
public class DatabaseAdapter{
 
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
       
        public String loadJoke(int id){
               
                DatabaseHelper dbh = new DatabaseHelper(context);
               
            dbh.openDatabase();
               
                String joke = null;
                db = dbh.getDatabase();
                Cursor c = db.rawQuery("SELECT text FROM " + DB_TABLE + " WHERE _id like " + getLastJoke(id), null);
       
                c.moveToFirst();
                joke = c.getString(c.getColumnIndex("text"));
               
                dbh.close();
                c.close();
                return joke;
        }
       
        /*
         * Metoda ta zwraca nazwe kategorii
         * o zadanym id z bazy danych
         */
       
        private int getLastJoke(int id) {
                DatabaseHelper dbh = new DatabaseHelper(context);              
            dbh.openDatabase();        
                int ostatni = 1;
                db = dbh.getDatabase();
                Cursor c = db.rawQuery("SELECT ostatni FROM kategorie WHERE _id like " + (id + 1), null);
                c.moveToFirst();
               
                ostatni = c.getInt(c.getColumnIndex("ostatni"));
               
                dbh.close();
                c.close();
                return ostatni;
        }
       
        public void setLastJokePlus(int catID){
            int lastID = getLastJoke(catID);
            DatabaseHelper dbh = new DatabaseHelper(context);              
            dbh.openDatabase();        
            int ostatni = 1;
            db = dbh.getDatabase();
            //to do chuja powinno dzialac ale nie dziala, nie edytuje tego jebanego rekordu, WTF
            db.rawQuery("UPDATE kategorie SET ostatni = 123 WHERE _id = 1", null);
            
            dbh.close();
           
        }
 
 
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
               
                return category;
        }
}