package org.me.joker;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondIntent extends FragmentActivity implements OnGesturePerformedListener, DialogActivity.NoticeDialogListener{
	
	private GestureLibrary mLibrary;
	
	private int catId = 1;
	private String catName = "Kategoria";
	
	public int sort = 4;

	@Override
	public void onCreate(Bundle savedInstanceState){
	        super.onCreate(savedInstanceState);
	        
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
	        setContentView(R.layout.second); 
	        
	        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
	        
	        sort = Integer.parseInt(sharedPref.getString("pref_list", "4"));
	        
	        float fontSize = Float.parseFloat(sharedPref.getString("font_list", "20.0"));
	        
	        
	        
	        /*
	         * Przejecie danych z pierwszego okna
	         */
	        Bundle bundle = getIntent().getExtras();
	        catId = bundle.getInt("ID");
	        catName = bundle.getString("CATEGORY");
	        
	        final Joke joke;


	        if (catId == 0){
	        	joke = new Joke(getApplicationContext());
	        }	
	        else if(catId == 1){
	        	joke = new Joke(getApplicationContext(),sort);
	        	try{
	        		joke.refreshFavouriteJoke();
	        	}
	        	catch(Exception e){
	        		makeToast("Brak ulubionych kawałów");
			        SecondIntent.this.finish();
	        	}
	        	
	        }
	        else{	        	
	        	joke = new Joke(catId, getApplicationContext(), sort);
	        }
	        
	        /*
	         * Stworzenie view oraz wpisanie kategorii w naglowek
	         */
	        final TextView kat = (TextView)findViewById(R.id.category);
	        kat.setText(catName);
	        
	        final TextView kawal = (TextView)findViewById(R.id.joke);
	        /*
	         * Sprawienie, ze pole tekstowe mozna przewijac
	         */
	        kawal.setMovementMethod(new ScrollingMovementMethod());
	        kawal.setTextSize(fontSize);
	        
	        
	        /*
			 * średnia ocena
			 */
			final TextView ocena  = (TextView)findViewById(R.id.ocena); 
			
			//przerabia na int
			int sredniaInt = (Integer.parseInt(joke.getVoteUpFromDb())-Integer.parseInt(joke.getVoteDownFromDb()));
			
			
			//ustawienie sredniej oceny
			if (sredniaInt > 0){
				ocena.setText("+"+Integer.toString(sredniaInt));
			}
			else if (sredniaInt < 0){
				ocena.setText(Integer.toString(sredniaInt));
			}
			else if (sredniaInt == 0){
				ocena.setText("0");
			}
	        

	        
	        //licznik

	        	final TextView nr = (TextView)findViewById(R.id.nr);
	        	if (!catName.contains("Losowe")){
	        		nr.setText(joke.getNumber());
	        	}
	        	else
	        		nr.setText("");
	        
	        
	        ImageButton socialshare = (ImageButton)findViewById(R.id.socialshare);
	        socialshare.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){	        		
	        		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
	        		sharingIntent.setType("text/plain");
	        		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, joke.getContent() +'\n'+"Kawał znaleziony w aplikacji Joker - http://bitly.com/TxgVn6");
	        		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Dobry kawał ;)");
	        		startActivity(Intent.createChooser(sharingIntent, "Share using:"));
	        	}
	        });
	        
			
			
			final ImageButton ulub =(ImageButton)findViewById(R.id.fav);
			ulub.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					
					// Jesli jestesmy w kategorii ulubione to przycisk ten usunie kawal z tejze kategorii
					try{
						if(catName.contains("Ulubione")){
							joke.deleteFromFavouritesInFavourites();
							try{
								kawal.setText(joke.getPrevious());
								
								joke.checkNumberOfJokesInCategory();
								TextView nr = (TextView)findViewById(R.id.nr);
						        nr.setText(joke.getNumber());
								joke.onPreviousButtonClick();
								
								kawal.scrollTo(0, 0);
					        	 
					        	makeToast("Kawał został usunięty z ulubionych");
						    
						        ulub.setImageResource(R.drawable.removebutton);
					         }
					         catch(Exception e){
					        	 kawal.setText("Brak kawału do wyświetlenia w wybranej kategorii");
					        }
						}
						else if(joke.getFavourite()){
							joke.deleteFromFavouritesInOtherCategory();
							
							makeToast("Kawał został usunięty z ulubionych");
					        
					        ulub.setImageResource(R.drawable.favstarupdate);
						}
						else{
							joke.addToFavourites();
				             
							makeToast("Kawał został dodany do ulubionych!");
				            
				            ulub.setImageResource(R.drawable.removebutton);
						}
					}
					catch (Exception e){
						
					}
					
					
				}
			});
			
			ImageButton poprzedni = (ImageButton)findViewById(R.id.previous);
			poprzedni.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					if (!catName.contains("Losowe")){
						try{				
							kawal.setText(joke.getPrevious());
							joke.onPreviousButtonClick();
							//licznik
					        final TextView nr = (TextView)findViewById(R.id.nr);
					        nr.setText(joke.getNumber());
					        
					        /*
							 * średnia ocena
							 */
							final TextView ocena  = (TextView)findViewById(R.id.ocena); 
							
							//przerabia na int
							int sredniaInt = (Integer.parseInt(joke.getVoteUpFromDb())-Integer.parseInt(joke.getVoteDownFromDb()));
							
							//ustawienie sredniej oceny
							if (sredniaInt > 0){
								ocena.setText("+"+Integer.toString(sredniaInt));
							}
							else if (sredniaInt < 0){
								ocena.setText(Integer.toString(sredniaInt));
							}
							else if (sredniaInt == 0){
								ocena.setText("0");
							}
					        
					        kawal.scrollTo(0, 0);
					        
					        checkGraph(ulub, joke);
						}
						catch(Exception e){
							
						}
					}
					
					else{
						joke.setRandomJoke();
						kawal.setText(joke.getContent());
						
						kawal.scrollTo(0, 0);
						
						checkGraph(ulub, joke);
					}
						
				}
			});
			ImageButton nastepny = (ImageButton)findViewById(R.id.next);
			nastepny.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					if (!catName.contains("Losowe")){
							try{
							kawal.setText(joke.getNext());
							joke.onNextButtonClick();
							//licznik
					        final TextView nr = (TextView)findViewById(R.id.nr);
					        nr.setText(joke.getNumber());
					        
					        /*
							 * średnia ocena
							 */
							final TextView ocena  = (TextView)findViewById(R.id.ocena); 
							
							//przerabia na int
							int sredniaInt = (Integer.parseInt(joke.getVoteUpFromDb())-Integer.parseInt(joke.getVoteDownFromDb()));
							
							//ustawienie sredniej oceny
							if (sredniaInt > 0){
								ocena.setText("+"+Integer.toString(sredniaInt));
							}
							else if (sredniaInt < 0){
								ocena.setText(Integer.toString(sredniaInt));
							}
							else if (sredniaInt == 0){
								ocena.setText("0");
							}
					        
					        kawal.scrollTo(0, 0);
					        
					        checkGraph(ulub, joke);
						}
						catch(Exception e){
							
						}
					}
					else{
						joke.setRandomJoke();
						kawal.setText(joke.getContent());
						
						kawal.scrollTo(0, 0);
						
						checkGraph(ulub, joke);
					}
					
				}
			});
    
			
			//button oceniania
			ImageButton ocen = (ImageButton)findViewById(R.id.ocen);
			ocen.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				        
				    boolean connectionAllow = sharedPref.getBoolean("internet", true);
				    NetworkActivity networkManager = new NetworkActivity();
				    
			        if(networkManager.haveNetworkConnection(getApplicationContext()) && connectionAllow){  
						//pobranie informacji o tym czy użytkownik głosował czy nie
						String voted = joke.checkIfVoted();					
						//sprawdzenie czy już głosowano
						if(voted.equals("0")){  
				        
					        //sprawdzenie czy jest połączenie 
					        if(networkManager.haveNetworkConnection(getApplicationContext()) && connectionAllow){
					        	showNoticeDialog();
				            	            	
				        	}
				        	else makeToast("Nie można ocenić - brak łączności, lub nieaktywna opcja.");
						}
						else makeToast("Już zagłosowałeś na ten kawał. Zobaczysz swoją ocenę przy następnej aktualizacji bazy.");					
			        }
			        else makeToast("Nie możesz głosować, ponieważ nie masz połączenia z internetem lub nie włączyłeś opcji oceniania.");
				}
			});
			
			//zamiana grafiki ulubionych
			
			checkGraph(ulub, joke);
			
			//zmiana buttonu na lewo od nazwy kategorii
			ImageView img = (ImageView) findViewById(R.id.changingImage);			
			
			if(catName.contains("Chuck Norris")){
				img.setImageResource(R.drawable.chuck);
			}
			
			if(catName.contains("O studentach")){
				img.setImageResource(R.drawable.ostudentach);
			}
			
			if(catName.contains("O facetach")){
				img.setImageResource(R.drawable.ofacetach);
			}
			
			if(catName.contains("O zwierzętach")){
				img.setImageResource(R.drawable.ozwierzetach);
			}
			
			if(catName.contains("Turbo Suchary")){
				img.setImageResource(R.drawable.turbosuchary);
			}
			
			if(catName.contains("Chamskie")){
				img.setImageResource(R.drawable.chamskie);
			}
			
			if(catName.contains("Losowe")){
				img.setImageResource(R.drawable.random);
			}
			
			if(catName.contains("Ulubione")){
				img.setImageResource(R.drawable.heart);
			}
			
			if(catName.contains("O Blondynkach")){
				img.setImageResource(R.drawable.blondynka);
			}		
			
			if(catName.contains("Zboczone")){
				img.setImageResource(R.drawable.zboczone);
			}		
						
						
			if(catName.contains("O Kobietach")){
				img.setImageResource(R.drawable.kobiety);
			}
			
			if(catName.contains("O Jasiu")){
					img.setImageResource(R.drawable.johnny);
				}
		

	        
	        /*Otworzenie bazy danych
	         * Pobranie kawalu do TextView
	         */
	         try{
	        	kawal.setText(joke.getContent());
	         }
	         catch(Exception e1){
	        	kawal.setText("Brak kawału do wyświetlenia w wybranej kategorii.");
	         }
	         
	        	 
			 
		     
		   //wczytanie biblioteki gestów
		        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		        if (!mLibrary.load()) {
		        	finish();
		        }
		        
		        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		        gestures.setGestureVisible(false);
		        gestures.addOnGesturePerformedListener(this);
        
}
		

	public void checkGraph(ImageButton ulub, Joke joke){
    	if(catName.contains("ULUBIONE") || joke.getFavourite()){
        	ulub.setImageResource(R.drawable.removebutton);
        }
    	else{
    		ulub.setImageResource(R.drawable.favstarupdate);
    	}
    }
	
	//metoda obslugujaca gesty
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		
		final Joke joke;
		if (!catName.contains("Losowe")){
			joke = new Joke(catId, getApplicationContext(), sort);
		}
		else{
			joke = new Joke(getApplicationContext());
		}
			
        final TextView kawal = (TextView)findViewById(R.id.joke);
        final ImageButton ulub = (ImageButton)findViewById(R.id.fav);
		
        
        try{	
			if (predictions.size() > 0) {
				Prediction prediction = predictions.get(0);
				if (prediction.score > 1.0) {
					
					if(prediction.name.contains("previous")){
						if (!catName.contains("Losowe")){
							try{				
								kawal.setText(joke.getNext());
								joke.onNextButtonClick();
								//licznik
						        final TextView nr = (TextView)findViewById(R.id.nr);
						        nr.setText(joke.getNumber());
						        
						        /*
								 * średnia ocena
								 */
								final TextView ocena  = (TextView)findViewById(R.id.ocena); 
								
								//przerabia na int
								int sredniaInt = (Integer.parseInt(joke.getVoteUpFromDb())-Integer.parseInt(joke.getVoteDownFromDb()));
								
								//ustawienie sredniej oceny
								if (sredniaInt > 0){
									ocena.setText("+"+Integer.toString(sredniaInt));
								}
								else if (sredniaInt < 0){
									ocena.setText(Integer.toString(sredniaInt));
								}
								else if (sredniaInt == 0){
									ocena.setText("0");
								}
						        
						        kawal.scrollTo(0, 0);
						        
						        checkGraph(ulub, joke);
							}
							catch(Exception e){
								
							}
						}
						else{
							joke.setRandomJoke();
							kawal.setText(joke.getContent());
							
							kawal.scrollTo(0, 0);
							
							checkGraph(ulub, joke);
						}
						
						
					}  
					if(prediction.name.contains("next")){
						if(!catName.contains("Losowe")){
							try{				
								kawal.setText(joke.getPrevious());
								joke.onPreviousButtonClick();
								//licznik
						        final TextView nr = (TextView)findViewById(R.id.nr);
						        nr.setText(joke.getNumber());
						        
						        /*
								 * średnia ocena
								 */
								final TextView ocena  = (TextView)findViewById(R.id.ocena); 
								
								//przerabia na int
								int sredniaInt = (Integer.parseInt(joke.getVoteUpFromDb())-Integer.parseInt(joke.getVoteDownFromDb()));
								
								//ustawienie sredniej oceny
								if (sredniaInt > 0){
									ocena.setText("+"+Integer.toString(sredniaInt));
								}
								else if (sredniaInt < 0){
									ocena.setText(Integer.toString(sredniaInt));
								}
								else if (sredniaInt == 0){
									ocena.setText("0");
								}
						        
						        kawal.scrollTo(0, 0);
						        
						        checkGraph(ulub, joke);
							}
							catch(Exception e){
								
							}
						}
						else{
							joke.setRandomJoke();
							kawal.setText(joke.getContent());
							
							kawal.scrollTo(0, 0);
							
							checkGraph(ulub, joke);
						}
						
					}
				}
			}
		}
		catch(NullPointerException e){
			joke.setRandomJoke();
			kawal.setText(joke.getContent());
			
			kawal.scrollTo(0, 0);
			
			checkGraph(ulub, joke);	
		}

	}
	
	
	
	//Metoda przesuwajaca kawaly na przycisniecie volume 
	
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		final Joke joke;
		if (!catName.contains("Losowe")){
			joke = new Joke(catId, getApplicationContext(), sort);
		}
		else{
			joke = new Joke(getApplicationContext());
		}
			
        final TextView kawal = (TextView)findViewById(R.id.joke);
        final ImageButton ulub = (ImageButton)findViewById(R.id.fav);
        
	    int action = event.getAction();
	    int keyCode = event.getKeyCode();
	        switch (keyCode) {
	        case KeyEvent.KEYCODE_VOLUME_UP:
	            if (action == KeyEvent.ACTION_UP) {
	            	if (!catName.contains("Losowe")){
						try{				
							kawal.setText(joke.getNext());
							joke.onNextButtonClick();
							//licznik
					        final TextView nr = (TextView)findViewById(R.id.nr);
					        nr.setText(joke.getNumber());
					        
					        /*
							 * średnia ocena
							 */
							final TextView ocena  = (TextView)findViewById(R.id.ocena); 
							
							//przerabia na int
							int sredniaInt = (Integer.parseInt(joke.getVoteUpFromDb())-Integer.parseInt(joke.getVoteDownFromDb()));
							
							//ustawienie sredniej oceny
							if (sredniaInt > 0){
								ocena.setText("+"+Integer.toString(sredniaInt));
							}
							else if (sredniaInt < 0){
								ocena.setText(Integer.toString(sredniaInt));
							}
							else if (sredniaInt == 0){
								ocena.setText("0");
							}
					        
					        kawal.scrollTo(0, 0);
					        
					        checkGraph(ulub, joke);
						}
						catch(Exception e){
							
						}
					}
					else{
						joke.setRandomJoke();
						kawal.setText(joke.getContent());
						
						kawal.scrollTo(0, 0);
						
						checkGraph(ulub, joke);
					}
	            }
	            return true;
	        case KeyEvent.KEYCODE_VOLUME_DOWN:
	            if (action == KeyEvent.ACTION_DOWN) {
	            	if(!catName.contains("Losowe")){
						try{				
							kawal.setText(joke.getPrevious());
							joke.onPreviousButtonClick();
							//licznik
					        final TextView nr = (TextView)findViewById(R.id.nr);
					        nr.setText(joke.getNumber());
					        
					        /*
							 * średnia ocena
							 */
							final TextView ocena  = (TextView)findViewById(R.id.ocena); 
							
							//przerabia na int
							int sredniaInt = (Integer.parseInt(joke.getVoteUpFromDb())-Integer.parseInt(joke.getVoteDownFromDb()));
							
							//ustawienie sredniej oceny
							if (sredniaInt > 0){
								ocena.setText("+"+Integer.toString(sredniaInt));
							}
							else if (sredniaInt < 0){
								ocena.setText(Integer.toString(sredniaInt));
							}
							else if (sredniaInt == 0){
								ocena.setText("0");
							}
					        
					        kawal.scrollTo(0, 0);
					        
					        checkGraph(ulub, joke);
						}
						catch(Exception e){
							
						}
					}
					else{
						joke.setRandomJoke();
						kawal.setText(joke.getContent());
						
						kawal.scrollTo(0, 0);
						
						checkGraph(ulub, joke);
					}
	            }
	            return true;
	        default:
	            return super.dispatchKeyEvent(event);
	        }
	    }
	
	public void makeToast(String text){
		Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT);
   		
   		LayoutInflater inflater = getLayoutInflater();
   		View toastView = inflater.inflate(R.layout.toast, (ViewGroup)findViewById(R.id.toast));
   		
   		TextView toastText = (TextView)toastView.findViewById(R.id.textView1);
   		
   		toastText.setText(text);
   		
   		toast.setView(toastView);
   		toast.show();
	}
	
	
	
	/*
	 * 3 metody odpowiadające za reakcje na pojawiający się dialog
	 */
	public void showNoticeDialog() {
		NetworkActivity networkManager = new NetworkActivity();
		Joke joke = new Joke(getApplicationContext());
		
		DialogFragment newFragment = new DialogActivity(joke, networkManager);
    	newFragment.show(getSupportFragmentManager(), "ocenianie");
    }


	public void onDialogPositiveClick(DialogFragment dialog) {
		DatabaseAdapter dba = new DatabaseAdapter(catId, null, 0);
		final Joke joke;
		joke = new Joke(getApplicationContext());
    	dba.saveToDb("voted", "1", joke.getId(), catId);		
	}


	public void onDialogNegativeClick(DialogFragment dialog) {
		DatabaseAdapter dba = new DatabaseAdapter(catId, null, 0);
		final Joke joke;
		joke = new Joke(getApplicationContext());
    	dba.saveToDb("voted", "1", joke.getId(), catId);		
	}
}
