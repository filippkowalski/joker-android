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
       
        private final Context context;
       
       
        public DatabaseAdapter(int category, Context context){
                DB_TABLE = getCategory(category);
                this.context = context;
        }    
        
        // Metoda zwraca ostatnio ogladany kawal z kategorii o podanym ID
        public String loadLastJoke(int id){
               
                String joke = null;
                
                Cursor c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT text FROM " + DB_TABLE + " WHERE _id = " + getLastJokeId(id), null);
                
                c.moveToFirst();
                
                joke = c.getString(c.getColumnIndex("text"));
                c.close();
                return joke;
        }
       
        // Metoda zwraca kawal z podanej kategorii i o podanym ID kawalu
        
        public String loadJoke(int catId, int jokeId){
            
            String joke = null;
            
            Cursor c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT text FROM " + DB_TABLE + " WHERE _id = " + jokeId, null);
            
            c.moveToFirst();
            
            joke = c.getString(c.getColumnIndex("text"));
            c.close();
            return joke;
        }
        
       /*
        * Metoda zwraca ID ostatniego ogladanego kawalu
        */
       
        int getLastJokeId(int id) {    
            int ostatni = 1;
            
            Cursor c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT ostatni FROM " + TABLE_NAME + " WHERE _id = " + id, null);
            c.moveToFirst();
           
            ostatni = c.getInt(c.getColumnIndex("ostatni"));
            
            if(DB_TABLE.contains("ulubione")){
            	int favs = 0;
            	
            	for (int i = 2; i <= 11; i++){
        			favs += getNumberOfFavsInCategory(i);
        		}
            	
            	if (ostatni > favs){
            		if (favs <= 0){
            			favs = 1;
            		}
            		
                	ContentValues update = new ContentValues();
                	update.put("ostatni", "" + favs);
                	
                	String strFilter = "_id=" + "1";
                	
                	JokerApp.getDatabaseHelper().getDatabase().update("kategorie", update, strFilter, null);
                	
                	ostatni = favs;
            	}
            }
            
            c.close();
            
            return ostatni;
        }
   
        
        /*
         * Metoda zwiêksza w bazie danych
         * wartoœæ kolumny ostatni o 1
         */
        
        public void setLastJokePlus(int catID){
    		int lastID = getLastJokeId(catID);
    		lastID++;
    		
    		if (lastID > getLastInsertedID()){
    			lastID = 1;
    		}
    		
    		ContentValues data = new ContentValues();
    		data.put("ostatni", lastID);
    		
    		JokerApp.getDatabaseHelper().getDatabase().update(TABLE_NAME, data, "_id=" + catID, null);
        }
        
        /*
         * Metoda zmniejsza w bazie danych
         * wartosc kolumny ostatni o 1
         */
        
        public void setLastJokeMinus(int catID){
    		int lastID = getLastJokeId(catID);
    		lastID--;
    		
    		if (lastID <= 0 ){
    			lastID = getLastInsertedID();
    			if(lastID == 0){
    				lastID = 1;
    			}
    		}
    		
    		ContentValues data = new ContentValues();
    		data.put("ostatni", lastID);
    		
    		JokerApp.getDatabaseHelper().getDatabase().update(TABLE_NAME, data, "_id=" + catID, null);
        }
        
        
        /*
         * Metoda zwraca ostatnie id w kategorii
         */
        
        public int getLastInsertedID(){
        	int lastID = 0;
        	
        	if (!DB_TABLE.contains("ulubione")){
	        	
	        	Cursor c = JokerApp.getDatabaseHelper().getDatabase().query(DB_TABLE, new String[] {"_id"}, null, null, null, null, null);
	        	c.moveToLast();
	        	
	        	lastID = (int)c.getLong(c.getColumnIndex("_id"));
	        	
	        	c.close();
        	}
        	else{
        		for (int i = 2; i <= 11; i++){
        			lastID += getNumberOfFavsInCategory(i);
        		}
        	}
        	
        	return lastID;
        	
        }
 
        /*
         * Metoda ta zwraca nazwe kategorii
         * o zadanym id z bazy danych
         */
        
        public String getCategory(int id){           
            String category = null;

            Cursor c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT kategoria FROM kategorie WHERE _id = " + id, null);
            c.moveToFirst();
           
            category = c.getString(c.getColumnIndex("kategoria"));
            c.close();
            
            return category;
        }
        
       
        
        //metoda zwaraca guid kawa³u
		public String loadGuid(int catId, int jokeId){		    
            String guid = null;
            
            Cursor c;
            
            if (!DB_TABLE.contains("ulubione")){
            	c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT guid FROM " + DB_TABLE + " WHERE _id = " + jokeId, null);
            }
            else{
            	c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT guid FROM " + getCategory(catId) + " WHERE _id = " + jokeId, null);
            }
            
            c.moveToFirst();
            
            guid = c.getString(c.getColumnIndex("guid"));
            
            c.close();

            return guid;
		 }
		
        //metoda zwaraca voteup kawa³u
		public String loadVoteUp(int catId, int jokeId){
		    String voteup = "0";
		    
		    Cursor c;
			if (!DB_TABLE.contains("ulubione")){
			    c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT voteup FROM " + DB_TABLE + " WHERE _id = " + jokeId, null);
			}
			else {
				c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT voteup FROM " + getCategory(catId) + " WHERE _id = " + jokeId, null);
			}
			
		    c.moveToFirst();
		    
		    voteup = c.getString(c.getColumnIndex("voteup"));
		    
			c.close();

		    return voteup;
        }
		
        //metoda zwaraca vote down kawa³u
		public String loadVoteDown(int catId, int jokeId){
		    String votedown = "0";
		    
		    Cursor c;
		    if (!DB_TABLE.contains("ulubione")){
	            
			    c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT votedown FROM " + DB_TABLE + " WHERE _id = " + jokeId, null);
			    
			}
			else {
				c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT votedown FROM " + getCategory(catId) + " WHERE _id = " + jokeId, null);
			}
		    
		    c.moveToFirst();
		    
		    votedown = c.getString(c.getColumnIndex("votedown"));
		    
		    c.close();

		    return votedown;
        }
		
		/*
		 * metoda zapisuje wskazan¹ dan¹ do wskazanej kolumny
		 */
		public void saveToDb(String kolumna, String voteValue, int jokeId, int catId){
    		    				
    		ContentValues data = new ContentValues();
    		data.put(kolumna, voteValue);
    		
    		JokerApp.getDatabaseHelper().getDatabase().update(getCategory(catId), data, "_id=" + jokeId, null);
        }
		
		 /*
         * Metoda dodaje przekazany kawal do ulubionych
         */
        
        public void addJokeToFavourites(int jokeId){
        	
        	ContentValues update = new ContentValues();
        	update.put("fav", "1");
        	
        	String strFilter = "_id=" + jokeId;
        	
        	JokerApp.getDatabaseHelper().getDatabase().update(DB_TABLE, update, strFilter, null);
        }
        
        
        
        /*
         * Metoda usuwa kawal z ulubionych
         */
        public void deleteJokeFromFavourites(int catId, int jokeId){
        	
        	ContentValues update = new ContentValues();
        	update.put("fav", "0");
        	
        	String strFilter = "_id=" + jokeId;
        	
        	JokerApp.getDatabaseHelper().getDatabase().update(getCategory(catId), update, strFilter, null);
        }
        
        public boolean checkFavourite(int jokeId){
            String ulub;
            
            if(!DB_TABLE.contains("ulubione")){
	            Cursor c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT fav FROM " + DB_TABLE + " WHERE _id = " + jokeId, null);
	            c.moveToFirst();
	            
	            ulub = c.getString(c.getColumnIndex("fav"));
	            
	            c.close();
            }
            else
            	ulub = "1";
        	
           
            
            boolean fav = false;
            if (ulub.equals("1"))
            	fav = true;
            
            return fav;
        }
        
        public String checkVoted(int catId, int jokeId){
		    String voted = "0";
		    
		    Cursor c;
			if (!DB_TABLE.contains("ulubione")){
			    c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT voted FROM " + DB_TABLE + " WHERE _id = " + jokeId, null);
			}
			else {
				c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT voted FROM " + getCategory(catId) + " WHERE _id = " + jokeId, null);				
			}

		    c.moveToFirst();
		    
		    voted = c.getString(c.getColumnIndex("voted"));	
		    
		    c.close();

		    return voted;
        }

        public int getNumberOfFavsInCategory(int catId){
        	int numberOfFavs = 0;
        	
        	Cursor c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT _id FROM " + getCategory(catId) + " WHERE fav = 1", null);
        	
        	numberOfFavs = c.getCount();
        	
        	c.close();
        	
        	return numberOfFavs;
        }
        
        public String getFavouriteJoke(int catId, int jokeId){
        	String joke = "";
        	
        	Cursor c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT text FROM " + getCategory(catId) + " WHERE fav = 1", null);
        	
        	c.moveToPosition(jokeId - 1);
        	
        	joke = c.getString(c.getColumnIndex("text"));
        	
        	c.close();
        	
        	return joke;
        }
        
        public int searchIfTheresGuid(int catId, String guid){
        	        	
        	Cursor c = JokerApp.getDatabaseHelper().getDatabase().rawQuery("SELECT _id FROM " + getCategory(catId) + " WHERE guid = \'" + guid + "\'", null);
        	
        	if(c.getCount() == 0){
        		c.close();
        		
        		return 0;
        	}
        	else{
        		c.moveToFirst();
        		
        		int jokeId = Integer.parseInt(c.getString(c.getColumnIndex("_id")));
        		
        		c.close();
        		
        		return jokeId;
        	}
        	
        	
        }
}
