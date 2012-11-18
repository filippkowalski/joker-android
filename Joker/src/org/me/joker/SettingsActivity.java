package org.me.joker;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.CheckBox;
 
public class SettingsActivity extends PreferenceActivity{
	
	CheckBox checkbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
	}
 
}