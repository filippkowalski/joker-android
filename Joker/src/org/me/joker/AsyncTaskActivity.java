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
        // aktualizacja stanu podczas dzia�ania, tutaj zmiana tresci textview na inny

    }
 
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // dzia�ania po wykonaniu zdania w doInBackground
        // ta metoda dzia�a w UI, wi�c mo�esz zmienia� r�ne elementy!

    }
 
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // dzia�ania przed wykonaniem zada�
        // ta metoda dzia�a w UI, wi�c mo�esz zmienia� r�ne elementy!

    }
 
    @Override
    protected String doInBackground(String... params) {
        // zadanie do wykonania
        // tutaj nie mo�esz nic zmienia� w UI, tylko wykonywa� obliczenia i zadania
         
    	NetworkActivity networkManager = new NetworkActivity();
    	networkManager.updateSqliteVoteDb();
         
        return "Baza zaktualizowana";
    }
 
    }
 
}