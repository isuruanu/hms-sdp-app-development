package com.example.cookbook;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DisplayRecipeActivity extends Activity{

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String longDescription = intent.getStringExtra(MainActivity.longDescriptionIntentKey);
		String title = intent.getStringExtra(MainActivity.titleIntentKey);			
		
		setContentView(R.layout.recipe_full_layout);
		
		TextView titleView = (TextView)findViewById(R.id.title);
		titleView.setText(title);		
		TextView fullDescription = (TextView)findViewById(R.id.fullDescription);
		fullDescription.setText(longDescription);
		
		ActionBar actionBar = getActionBar();
		if(actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}		
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }        
        return super.onOptionsItemSelected(item);
    }
	
	
}
