package com.example.cookbook;

import java.util.List;
import java.util.Locale;

import com.example.cookbook.repo.RepositoryService;
import com.example.cookbook.repo.domain.Recipe;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
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

	RepositoryService repoService;
	ReciepeItemListAdapter reciepeItemListAdapter; 
	
	public static final String titleIntentKey = "com.exmaple.cookbook.title";
	public static final String longDescriptionIntentKey = "com.exmaple.cookbook.long.description";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		createContentMenu();
	}

	private void createContentMenu() {		
		setContentView(R.layout.activity_main);
		repoService = new RepositoryService(this);
		String localeName = getResources().getConfiguration().locale.getLanguage();		
		List<Recipe> recipeList = repoService.getRecipeList(localeName);
		reciepeItemListAdapter = new ReciepeItemListAdapter(this, recipeList);
		setListAdapter(reciepeItemListAdapter);		
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
		Recipe recipe = (Recipe)reciepeItemListAdapter.getItem(position);
		String longDescription = recipe.getDescriptions().getLongDescription();
		String title = recipe.getDescriptions().getTitle();			
				
		Intent intent = new Intent(this, DisplayRecipeActivity.class);
		intent.putExtra(longDescriptionIntentKey, longDescription);		
		intent.putExtra(titleIntentKey, title);
		startActivity(intent);		
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
