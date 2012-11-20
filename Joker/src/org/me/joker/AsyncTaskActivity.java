package org.me.joker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
 
public class AsyncTaskActivity extends Activity {
 
    TextView tv1;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
 
    setContentView(R.layout.main);
     
    new NaszAsyncTask().execute();
 
    }
 
    private class NaszAsyncTask extends AsyncTask<String, String, String> {
 
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        // aktualizacja stanu podczas dzia³ania, tutaj zmiana tresci textview na inny

    }
 
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // dzia³ania po wykonaniu zdania w doInBackground
        // ta metoda dzia³a w UI, wiêc mo¿esz zmieniaæ ró¿ne elementy!

    }
 
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // dzia³ania przed wykonaniem zadañ
        // ta metoda dzia³a w UI, wiêc mo¿esz zmieniaæ ró¿ne elementy!

    }
 
    @Override
    protected String doInBackground(String... params) {
        // zadanie do wykonania
        // tutaj nie mo¿esz nic zmieniaæ w UI, tylko wykonywaæ obliczenia i zadania
         
    	NetworkActivity networkManager = new NetworkActivity();
    	networkManager.updateSqliteVoteDb();
         
        return "Baza zaktualizowana";
    }
 
    }
 
}