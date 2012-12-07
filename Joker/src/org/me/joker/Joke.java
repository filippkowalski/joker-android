package org.me.joker;


import java.util.Random;

import android.content.Context;

public class Joke {
	
	// Atrybuty
	
	private String content;
	private String next;
	private String previous;
	private int category;
	private int categoryFromFavourites;
	private int id;
	private int idFromFavourites;
	private int guid;
	private String number;
	private int numberOfJokesInCategory;
	private int numberOfJokesInFavourites = 0;
	private boolean rated;
	private boolean favourite;
	
	private final Context context;
	
	
	// Konstruktor
	
	public Joke(int catId, Context context){
		
		setCategory(catId);
		this.context = context;
		checkNumberOfJokesInCategory();
		
		refreshJoke();		
	}
	
	//Konstruktor dla kawalow ulubionych
	
	public Joke(Context context, int sort){
		this.context = context;
		setCategory(1);
		checkNumberOfJokesInFavourites();
		setFavourite(true);
		
		if (getNumberOfJokesInFavourites() != 0){
			refreshFavouriteJoke();
		}
	}
	
	
	//Konstruktor dla losowych kawalow
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
	
	public void setCategoryFromFavourites(int a){
		categoryFromFavourites = a;
	}
	
	public int getCategoryFromFavourites(){
		return categoryFromFavourites;
	}
	
	public void setId(int a){
		id = a;
	}
	
	public int getId(){
		return id;
	}
	
	public void setIdFromFavourites(int a){
		idFromFavourites = a;
	}
	
	public int getIdFromFavourites(){
		return idFromFavourites;
	}
	
	public void setGuid(int a){
		guid = a;
	}
	
	public int getGuid(){
		return guid;
	}
	
	public void setNumber(String a){
		number = a;
	}
	
	public String getNumber(){
		return number;
	}
	
	public void setNumberOfJokesInCategory(int a){
		numberOfJokesInCategory = a;
	}
	
	public int getNumberOfJokesInCategory(){
		return numberOfJokesInCategory;
	}
	
	public void setNumberOfJokesInFavourites(int a){
		numberOfJokesInFavourites = a;
	}
	
	public int getNumberOfJokesInFavourites(){
		return numberOfJokesInFavourites;
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
		String lastNumber;
		if (getCategory() != 1){
			lastNumber = Integer.toString(getNumberOfJokesInCategory());
		}
		else {
			lastNumber = Integer.toString(getNumberOfJokesInFavourites());
		}
        
        return number + "/" + lastNumber;
	}
	
	public String getGuidFromDb(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		if (getCategory() != 1){
			return dba.loadGuid(getCategory(), getId());
		}
		else{
			return dba.loadGuid(getCategoryFromFavourites(), getIdFromFavourites());
		}
		
	}
	
	public String getVoteUpFromDb(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		if (getCategory() != 1){
			return dba.loadVoteUp(getCategory(), getId());
		}
		else{
			return dba.loadVoteUp(getCategoryFromFavourites(), getIdFromFavourites());
		}
	}
	
	public String getVoteDownFromDb(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		if (getCategory() != 1){
			return dba.loadVoteDown(getCategory(), getId());
		}
		else{
			return dba.loadVoteDown(getCategoryFromFavourites(), getIdFromFavourites());
		}
	}
	
	public void checkNumberOfJokesInCategory(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		setNumberOfJokesInCategory(dba.getLastInsertedID());
	}	
	
	public void refreshJoke(){
		setId(getLastReadJokeId());
		setContent(getContentFromDatabase());
		setNext(getJokeFromDatabase(getNextJokeId()));
		setPrevious(getJokeFromDatabase(getPreviousJokeId()));
		setNumber(getJokeNumber());
		checkIfFavourite();
		
	}
	
	public void  refreshFavouriteJoke(){
		setId(getLastReadJokeId());
		checkCategoryAndId(getId());
		setContent(getLastFavouriteJoke());
		setNext(getFavouriteJoke(getNextJokeId()));
		setPrevious(getFavouriteJoke(getPreviousJokeId()));
		setNumber(getJokeNumber());
	}
	
	public int getNextJokeId(){
		if (getCategory() != 1){
			if ((getId() + 1) > getNumberOfJokesInCategory())
				return 1;
			else
				return getId() + 1;
		}
		else {
			if ((getId() + 1) > getNumberOfJokesInFavourites())
				return 1;
			else
				return getId() + 1;
		}
		
	}
	
	public int getPreviousJokeId(){
		if (getCategory() != 1){
			if ((getId() - 1) < 1)
				return getNumberOfJokesInCategory();
			else
				return getId() - 1;
		}
		else {
			if ((getId() - 1) < 1)
				return getNumberOfJokesInFavourites();
			else
				return getId() - 1;
		}
		
	}
	
	public void onNextButtonClick(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		dba.setLastJokePlus(getCategory());
		if (getCategory() != 1){
			refreshJoke();
		}
		else {
			refreshFavouriteJoke();
		}
	}
	
	public void onPreviousButtonClick(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		dba.setLastJokeMinus(getCategory());
		if (getCategory() != 1){
			refreshJoke();
		}
		else {
			refreshFavouriteJoke();
		}
	}
	
	public void addToFavourites(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		dba.addJokeToFavourites(getId());
		setFavourite(true);
	}
	
	public void deleteFromFavourites(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		
		if (getCategory() != 1){
			dba.deleteJokeFromFavourites(getCategory(), getId());
		}
		else{
			dba.deleteJokeFromFavourites(getCategoryFromFavourites(), getIdFromFavourites());
		}
		
		setFavourite(false);
	}
	
	public void setRandomJoke(){
		Random rand = new Random();
		int randomCategory = rand.nextInt(9)+2;
		
		setCategory(randomCategory);
		checkNumberOfJokesInCategory();
		
		int randomJoke = rand.nextInt(getNumberOfJokesInCategory())+1;
		setId(randomJoke);
				
		setContent(getJokeFromDatabase(getId()));
	}
	
	//metoda potrzebna do widgetu
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
	
	//sprawdza czy w bazie czy flaga jest ustawiona na voted
	public String checkIfVoted(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		if (getCategory() != 1){
			return dba.checkVoted(getCategory(), getId());
		}
		else{
			return dba.checkVoted(getCategoryFromFavourites(), getIdFromFavourites());
		}
		
	}
	
	public int getNumberOfFavsInCategory(){
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		return dba.getNumberOfFavsInCategory(getCategoryFromFavourites());
	}
	
	public void checkNumberOfJokesInFavourites(){
		setNumberOfJokesInFavourites(0);
		for (int i = 2; i <= 11; i++){
			setCategoryFromFavourites(i);
			setNumberOfJokesInFavourites(getNumberOfJokesInFavourites() + getNumberOfFavsInCategory());
		}
	}
	
	public void checkCategoryAndId(int jokeId){
		setCategoryFromFavourites(1);
		int tmp = 0;
		int tmp1 = 0;
		while (tmp < getNumberOfJokesInFavourites() && tmp < jokeId){
			setCategoryFromFavourites(getCategoryFromFavourites() + 1);
			tmp1 = tmp;
			tmp += getNumberOfFavsInCategory();
		}
		setIdFromFavourites(jokeId - tmp1);
	}
	
	public String getLastFavouriteJoke(){
		String joke = "";
		
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		joke = dba.getFavouriteJoke(getCategoryFromFavourites(), getIdFromFavourites());
		
		return joke;
	}
	
	public String getFavouriteJoke(int jokeId){
		String joke = "";
		int id = getId();
		
		checkCategoryAndId(jokeId);
		
		DatabaseAdapter dba = new DatabaseAdapter(getCategory(), context);
		joke = dba.getFavouriteJoke(getCategoryFromFavourites(), getIdFromFavourites());
		
		checkCategoryAndId(id);
		
		return joke;
	}
	
}
