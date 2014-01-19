package com.example.cookbook;

import java.util.Arrays;
import java.util.Locale;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressLint("NewApi")
public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createContentMenu();
	}

	private void createContentMenu() {		
		setContentView(R.layout.activity_main);	
		setListAdapter(new ReciepeItemListAdapter(this, Arrays.asList("1", "2", "3")));		
 	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId() == R.id.action_language) {
			View languageSlectView = findViewById(R.id.action_language);
			PopupMenu popupMenu = new PopupMenu(this, languageSlectView);
			popupMenu.inflate(R.menu.language_select_popup);
			popupMenu.show();			
		}		
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Log.v("hello", "hello world");
	}

	public void changeLanguage(MenuItem item) {		
		if(item.getItemId() == R.id.language_spanish) {
			Locale locale = new Locale("es");
			changeLanguage(locale); 
		} else {
			Locale locale = new Locale("en");
			changeLanguage(locale);			
		}
	}

	private void changeLanguage(Locale locale) {
		Resources res = getResources(); 
		DisplayMetrics dm = res.getDisplayMetrics(); 
		Configuration conf = res.getConfiguration(); 
		conf.locale = locale; 
		res.updateConfiguration(conf, dm);
		createContentMenu();
	}
	
}
