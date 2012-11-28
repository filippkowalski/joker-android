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
        public static final String Key_JokeId = "jokeId";
        public static final String Key_Cat = "category";
        public String DB_ID = "1";
        public int sort = 4;
       
        public static Cursor cursor;
       
        private final Context context;
        private SQLiteDatabase db;
       
       
        public DatabaseAdapter(int category, Context context, int sort){
                DB_TABLE = getCategory(category);
                this.context = context;
                this.sort = sort;
        }    
        
        // Metoda zwraca ostatnio ogladany kawal z kategorii o podanym ID
        public String loadLastJoke(int id){
               
                DatabaseHelper dbh = new DatabaseHelper(context);
               
                dbh.openDatabase();
               
                String joke = null;
                db = dbh.getDatabase();
                
                Cursor c;
                if (sort == 1){
                	c = db.rawQuery("SELECT text FROM " + DB_TABLE + " ORDER BY rating DESC", null);
                }
                else if (sort == 2){
                	c = db.rawQuery("SELECT text FROM " + DB_TABLE + " ORDER BY rating", null);
                }
                else if (sort == 3){
                	c = db.rawQuery("SELECT text FROM " + DB_TABLE + " ORDER BY _id DESC", null);
                }
                else{
                	c = db.rawQuery("SELECT text FROM " + DB_TABLE, null);
                }
                
                
                c.moveToPosition(getLastJokeId(id) - 1);
                joke = c.getString(c.getColumnIndex("text"));
                c.close();
                db.close();
                dbh.close();
                return joke;
        }
       
        // Metoda zwraca kawal z podanej kategorii i o podanym ID kawalu
        
        public String loadJoke(int catId, int jokeId){
            
            DatabaseHelper dbh = new DatabaseHelper(context);
           
            dbh.openDatabase();
           
            String joke = null;
            db = dbh.getDatabase();
            Cursor c;
            if (sort == 1){
            	c = db.rawQuery("SELECT text FROM " + DB_TABLE + " ORDER BY rating DESC", null);
            }
            else if (sort == 2){
            	c = db.rawQuery("SELECT text FROM " + DB_TABLE + " ORDER BY rating", null);
            }
            else if (sort == 3){
            	c = db.rawQuery("SELECT text FROM " + DB_TABLE + " ORDER BY _id DESC", null);
            }
            else{
            	c = db.rawQuery("SELECT text FROM " + DB_TABLE, null);
            }
            
            c.moveToPosition(jokeId - 1);
            joke = c.getString(c.getColumnIndex("text"));
            c.close();
            db.close();
            dbh.close();
            return joke;
        }
        
       /*
        * Metoda zwraca ID ostatniego ogladanego ID
        */
       
        int getLastJokeId(int id) {
            DatabaseHelper dbh = new DatabaseHelper(context);              
            dbh.openDatabase();        
            int ostatni = 1;
            db = dbh.getDatabase();
            Cursor c = db.rawQuery("SELECT ostatni FROM " + TABLE_NAME + " WHERE _id like " + id, null);
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
    		int lastID = getLastJokeId(catID);
    		lastID++;
    		if (lastID > getLastInsertedID()){
    			lastID = 1;
    		}
    		ContentValues data = new ContentValues();
    		data.put("ostatni", lastID);
    		String myPath = DB_PATH + DB_NAME;
    		db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    		db.update(TABLE_NAME, data, "_id=" + catID, null);
    		dbh.close();
    		db.close();
        }
        
        /*
         * Metoda zmniejsza w bazie danych
         * wartosc kolumny ostatni o 1
         */
        
        public void setLastJokeMinus(int catID){
        	DatabaseHelper dbh = new DatabaseHelper(context);
    		dbh.openDatabase();
    		db = dbh.getDatabase();
    		int lastID = getLastJokeId(catID);
    		lastID--;
    		if (lastID <= 0 ){
    			lastID = getLastInsertedID();
    		}
    		ContentValues data = new ContentValues();
    		data.put("ostatni", lastID);
    		String myPath = DB_PATH + DB_NAME;
    		db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    		db.update(TABLE_NAME, data, "_id=" + catID, null);
    		dbh.close();
    		db.close();
        }
        
        
        /*
         * Metoda zwraca ostatnie id w kategorii
         */
        
        public int getLastInsertedID(){
        	db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        	Cursor c = db.query(DB_TABLE, new String[] {"_id"}, null, null, null, null, null);
        	c.moveToLast();
        	int lastID = (int)c.getLong(c.getColumnIndex("_id"));
        	db.close();
        	c.close();
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
            Cursor c = db.rawQuery("SELECT kategoria FROM kategorie WHERE _id like " + id, null);
            c.moveToFirst();
           
            category = c.getString(c.getColumnIndex("kategoria"));
            c.close();
            dbh.close();
            db.close();
            return category;
        }
        
        /*
         * Metoda dodaje przekazany kawal do bazy danych
         */
        
        public void addJokeToFavourites(String joke, int catId, int jokeId){
        	db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        	
        	ContentValues update = new ContentValues();
        	update.put("fav", "1");
        	
        	String strFilter = "_id=" + jokeId;
        	
        	db.update(DB_TABLE, update, strFilter, null);
        	
        	db.close();
        }
        
        //metoda zwaraca guid kawa³u
		public String loadGuid(int catId, int jokeId){
		            
            DatabaseHelper dbh = new DatabaseHelper(context);
           
            dbh.openDatabase();
           
            String guid = null;
            db = dbh.getDatabase();
            Cursor c;
          	c = db.rawQuery("SELECT guid FROM " + DB_TABLE + " WHERE _id like " + jokeId, null);
            c.moveToFirst();
            
            guid = c.getString(c.getColumnIndex("guid"));
            
            c.close();
            db.close();
            dbh.close();
            return guid;
		 }
		
        //metoda zwaraca voteup kawa³u
		public String loadVoteUp(int catId, int jokeId){
			DatabaseHelper dbh = new DatabaseHelper(context);
			   
		    dbh.openDatabase();
		   
		    String voteup = "0";
		    db = dbh.getDatabase();
		    
		    Cursor c;
			if (!DB_TABLE.contains("ulubione")){
            
			    c = db.rawQuery("SELECT voteup FROM " + DB_TABLE + " WHERE _id like " + jokeId, null);
			    c.moveToFirst();
			    
			    voteup = c.getString(c.getColumnIndex("voteup"));
			    
			    
			}
			else {
				c = db.rawQuery("SELECT category, jokeId FROM ulubione WHERE _id = " + jokeId, null);
				try{
		        	c.moveToFirst();
		        	
		        	int categId = c.getInt(c.getColumnIndex("category"));
		        	int jokeID = c.getInt(c.getColumnIndex("jokeId"));
		        	
		        	String category = getCategory(categId);
		        	
		        	Cursor cursor = db.rawQuery("SELECT voteup FROM " + category + " WHERE _id like " + jokeID, null);
				    cursor.moveToFirst();
				    
				    voteup = cursor.getString(cursor.getColumnIndex("voteup"));
				    
				    cursor.close();
				}
				catch(Exception e){
					
				}
				
			}
			c.close();
		    db.close();
		    dbh.close();
		    return voteup;
        }
		
        //metoda zwaraca vote down kawa³u
		public String loadVoteDown(int catId, int jokeId){
            
		    DatabaseHelper dbh = new DatabaseHelper(context);
		   
		    dbh.openDatabase();
		   
		    String votedown = "0";
		    db = dbh.getDatabase();
		    
		    Cursor c;
		    if (!DB_TABLE.contains("ulubione")){
	            
			    c = db.rawQuery("SELECT votedown FROM " + DB_TABLE + " WHERE _id like " + jokeId, null);
			    c.moveToFirst();
			    
			    votedown = c.getString(c.getColumnIndex("votedown"));
			    
			}
			else {
				c = db.rawQuery("SELECT category, jokeId FROM ulubione WHERE _id = " + jokeId, null);
				try{
					c.moveToFirst();
		        	
		        	int categId = c.getInt(c.getColumnIndex("category"));
		        	int jokeID = c.getInt(c.getColumnIndex("jokeId"));
		        	
		        	String category = getCategory(categId);
		        	
		        	Cursor cursor = db.rawQuery("SELECT votedown FROM " + category + " WHERE _id like " + jokeID, null);
				    cursor.moveToFirst();
				    
				    votedown = cursor.getString(cursor.getColumnIndex("votedown"));
				    
				    cursor.close();
				}
				catch (Exception e){
					
				}
	        	
			}
		    
		    c.close();
		    db.close();
		    dbh.close();
		    return votedown;
        }
		
		/*
		 * metoda zapisuje wskazan¹ dan¹ do wskazanej kolumny
		 */
		public void saveToDb(String kolumna, String voteValue, int jokeId, int catId){
        	DatabaseHelper dbh = new DatabaseHelper(context);
    		dbh.openDatabase();
    		db = dbh.getDatabase();
    		    				
    		ContentValues data = new ContentValues();
    		data.put(kolumna, voteValue);
    		
    		String myPath = DB_PATH + DB_NAME;
    		db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    		db.update(getCategory(catId), data, "_id=" + jokeId, null);
    		dbh.close();
    		db.close();
        }
        
        
        
        /*
         * Metoda usuwa kawal z ulubionych
         */
        public void deleteJokeFromFavouritesInFavourites(int jokeIdInFav){
        	
        	db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        	
        	Cursor c = db.rawQuery("SELECT category, jokeId FROM ulubione WHERE _id = " + jokeIdInFav, null);
        	c.moveToFirst();
        	
        	int catId = c.getInt(c.getColumnIndex("category"));
        	int jokeId = c.getInt(c.getColumnIndex("jokeId"));
        	
        	String category = getCategory(catId);
        	String strFilter = "_id=" + jokeId;
        	
        	c.close();
        	db.close();
        	
        	SQLiteDatabase db1;
        	db1 = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        	
        	ContentValues update = new ContentValues();
        	update.put("fav", "0");
        	
        	db1.update(category, update, strFilter, null);
        	
        	db1.execSQL("DELETE FROM ulubione WHERE _id = " + jokeIdInFav);
        	
        	db1.close();
        }
        
        public void deleteJokeFromFavouritesInOtherCategory(int catId, int jokeId){
        	db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        	
        	Cursor c = db.rawQuery("SELECT _id FROM ulubione WHERE category = " + catId + " AND jokeId = " + jokeId, null);
        	c.moveToFirst();
        	
        	int id = c.getInt(c.getColumnIndex("_id"));
        	
        	c.close();
        	
        	db.execSQL("DELETE FROM ulubione WHERE _id = " + id);
        	
        	String strFilter = "_id=" + jokeId;
        	String category = getCategory(catId);
        	
        	db.close();
        	
        	SQLiteDatabase db1;
        	db1 = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        	
        	ContentValues update = new ContentValues();
        	update.put("fav", "0");
        	
        	db1.update(category, update, strFilter, null);
        	
        	db1.close();
        }
        
        public boolean checkFavourite(int jokeId){
            String ulub;
            
            if(!DB_TABLE.contains("ulubione")){
	            DatabaseHelper dbh = new DatabaseHelper(context);
	            dbh.openDatabase();
	           
	            db = dbh.getDatabase();
	            Cursor c = db.rawQuery("SELECT fav FROM " + DB_TABLE + " WHERE _id like " + jokeId, null);
	            c.moveToFirst();
	            
	            ulub = c.getString(c.getColumnIndex("fav"));
	            
	            c.close();
	            dbh.close();
	            db.close();
            }
            else
            	ulub = "1";
        	
           
            
            boolean fav = false;
            if (ulub.equals("1"))
            	fav = true;
            
            return fav;
        }
        
        public String checkVoted(int catId, int jokeId){
        	DatabaseHelper dbh = new DatabaseHelper(context);
			   
		    dbh.openDatabase();
		   
		    String voted = "0";
		    db = dbh.getDatabase();
		    
		    Cursor c;
			if (!DB_TABLE.contains("ulubione")){
            
			    c = db.rawQuery("SELECT voted FROM " + DB_TABLE + " WHERE _id like " + jokeId, null);
			    c.moveToFirst();
			    
			    voted = c.getString(c.getColumnIndex("voted"));		    
			    c.close();
			}
			else {
				voted = "1";				
			}
			
		    db.close();
		    dbh.close();
		    return voted;
        }

        public int getNumberOfFavsInCategory(int catId){
        	int numberOfFavs = 0;
        	
        	db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        	
        	Cursor c = db.rawQuery("SELECT _id FROM " + getCategory(catId) + " WHERE fav LIKE 1", null);
        	
        	numberOfFavs = c.getCount();
        	
        	c.close();
        	db.close();
        	return numberOfFavs;
        }
        
        public String getFavouriteJoke(int catId, int jokeId){
        	String joke = "";
        	
        	db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        	
        	Cursor c = db.rawQuery("SELECT text FROM " + getCategory(catId) + " WHERE fav LIKE 1", null);
        	
        	c.moveToPosition(jokeId - 1);
        	
        	joke = c.getString(c.getColumnIndex("text"));
        	
        	c.close();
        	db.close();
        	return joke;
        }
}
