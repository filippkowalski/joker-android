package org.me.joker;

import java.util.Random;

import android.content.Context;

public class Joke {
	
	// Atrybuty
	
	private String content;
	private String next;
	private String previous;
	private int category;
	private int id;
	private String number;
	private int numberOfJokesInCategory;
	private boolean rated;
	private boolean favourite;
	
	private final Context context;
	
	
	// Konstruktor
	
	public Joke(int catId, Context context) throws RuntimeException{
		
		setCategory(catId);
		this.context = context;
		checkNumberOfJokesInCategory();
		refreshJoke();
		
	}
	
	public Joke(Context context){
		setRandomJoke();
		this.context = context;
	}
	
	// Gettery i Settery
	
	public void setContent(String a){
		content = a;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setNext(String a){
		next = a;
	}
	
	public String getNext(){
		return next;
	}
	
	public void setPrevious(String a){
		previous = a;
	}
	
	public String getPrevious(){
		return previous;
	}
	
	public void setCategory(int a){
		category = a;
	}
	
	public int getCategory(){
		return category;
	}
	
	public void setId(int a){
		id = a;
	}
	
	public int getId(){
		return id;
	}
	
	public void setNumber(String a){
		number = a;
	}
	
	public String getNumber(){
		return number;
	}
	
	public int getNumberOfJokesInCategory(){
		return numberOfJokesInCategory;
	}
	
	public void setRated(boolean a){
		rated = a;
	}
	
	public boolean getRated(){
		return rated;
	}
	
	public void setFavourite(boolean a){
		favourite = a;
	}
	
	public boolean getFavourite(){
		return favourite;
	}
	
	// Metody
	
	public int getLastReadJokeId(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		return dba.getLastJokeId(getCategory());
	}
	
	public String getContentFromDatabase(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		return dba.loadLastJoke(getCategory());
	}
	
	public String getJokeFromDatabase(int jokeId){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		return dba.loadJoke(getCategory(), jokeId);
	}
	
	public String getJokeNumber(){
		String number = Integer.toString(getId());
        String lastNumber = Integer.toString(getNumberOfJokesInCategory());
        return number + "/" + lastNumber;
	}
	
	public void checkNumberOfJokesInCategory(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		numberOfJokesInCategory = dba.getLastInsertedID();
	}	
	
	public void refreshJoke(){
		setId(getLastReadJokeId());
		setContent(getContentFromDatabase());
		setNext(getJokeFromDatabase(getNextJokeId()));
		setPrevious(getJokeFromDatabase(getPreviousJokeId()));
		setNumber(getJokeNumber());
		checkIfFavourite();
	}
	
	public int getNextJokeId(){
		if ((getId() + 1) > getNumberOfJokesInCategory())
			return 1;
		else
			return getId() + 1;
	}
	
	public int getPreviousJokeId(){
		if ((getId() - 1) < 1)
			return getNumberOfJokesInCategory();
		else
			return getId() - 1;
	}
	
	public void onNextButtonClick(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		dba.setLastJokePlus(getCategory());
		refreshJoke();
	}
	
	public void onPreviousButtonClick(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		dba.setLastJokeMinus(getCategory());
		refreshJoke();
	}
	
	public void addToFavourites(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		dba.addJokeToFavourites(getContent(), getCategory(), getId());
		setFavourite(true);
	}
	
	public void deleteFromFavouritesInFavourites(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		dba.deleteJokeFromFavouritesInFavourites(getId());
		setFavourite(false);
	}
	
	public void deleteFromFavouritesInOtherCategory(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		dba.deleteJokeFromFavouritesInOtherCategory(getCategory(), getId());
		setFavourite(false);
	}
	
	public void setRandomJoke(){
		Random rand = new Random();
		int randomCategory = rand.nextInt(10)+1;
		
		setCategory(randomCategory);
		checkNumberOfJokesInCategory();
		
		int randomJoke = rand.nextInt(getNumberOfJokesInCategory())+1;
		setId(randomJoke);
				
		setContent(getJokeFromDatabase(getId()));
	}
	
	public void setRandomTurboSucharJoke(){
		Random rand = new Random();
		
		setCategory(10);
		checkNumberOfJokesInCategory();
		
		int randomJoke = rand.nextInt(getNumberOfJokesInCategory())+1;
		setId(randomJoke);
				
		setContent(getJokeFromDatabase(getId()));
	}
	
	public void checkIfFavourite(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		setFavourite(dba.checkFavourite(getId()));
	}
}
