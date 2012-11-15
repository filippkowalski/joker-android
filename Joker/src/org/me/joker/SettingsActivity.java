package org.me.joker;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
 
public class SettingsActivity extends PreferenceActivity implements OnClickListener {
	
	CheckBox checkbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);

		checkbox = (CheckBox) findViewById(R.id.checkBoxInternet);
		checkbox.setOnClickListener(this);
		loadPrefs();
	}
	
	private void loadPrefs() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean cbValue = sp.getBoolean("CHECKBOX", false);
		String name = sp.getString("NAME", "YourName");
		if(cbValue){
			checkbox.setChecked(true);
		}else{
			checkbox.setChecked(false);
		}
	}

	private void savePrefs(String key, boolean value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	private void savePrefs(String key, String value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	public void onClick(View v) {
		savePrefs("CHECKBOX", checkbox.isChecked());
		if (checkbox.isChecked())
			savePrefs("NAME", "yes");
		finish();
	}
 
}