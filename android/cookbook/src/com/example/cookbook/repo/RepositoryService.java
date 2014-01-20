package com.example.cookbook.repo;

import java.util.ArrayList;
import java.util.List;

import com.example.cookbook.repo.ReciepeQueryHelper.RecipeDbEntry;
import com.example.cookbook.repo.domain.Recipe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RepositoryService {	
	
	ReciepeReaderDbHelper dbHelper;
	
	private static final String[] projection = { ReciepeQueryHelper.RecipeDbEntry.id,
													ReciepeQueryHelper.RecipeDbEntry.status};
	
	public RepositoryService(Context context) {
		dbHelper = new ReciepeReaderDbHelper(context);
	}

	public List<Recipe> getRecipeList(String language) {
		ArrayList<Recipe> results = new ArrayList<Recipe>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();								
		
		Cursor c = db.query(ReciepeQueryHelper.recipeTableName, 
					projection, 
					null, null, null, null, null);
				 		
		if(c.moveToFirst()) {		
			extractRecipe(language, results, db, c);			
			while(c.moveToNext()) {				
				extractRecipe(language, results, db, c);
			}
		}										
		
		c.close();
		dbHelper.close();
		return results;
	}

	private void extractRecipe(String language, ArrayList<Recipe> results,
			SQLiteDatabase db, Cursor c) {
		int id = c.getInt(c.getColumnIndex(ReciepeQueryHelper.RecipeDbEntry.id));
		String status = c.getString(c.getColumnIndex(ReciepeQueryHelper.RecipeDbEntry.status));
		Cursor description = getCursorForFullDetails(language, db, id); 			
		if(description.moveToFirst()) {
			String title = description.getString(description.getColumnIndex(ReciepeQueryHelper.ReciepeDescriptionDbEntry.title));
			String shortDescription = description.getString(description.getColumnIndex(ReciepeQueryHelper.ReciepeDescriptionDbEntry.shortDescription));
			String longDescription = description.getString(description.getColumnIndex(ReciepeQueryHelper.ReciepeDescriptionDbEntry.longDescription));
							
			Recipe recipe = new Recipe(id, status, new Recipe.Description(language, shortDescription, longDescription, title, id));
			results.add(recipe);								
		}
	}

	private Cursor getCursorForFullDetails(String language, SQLiteDatabase db, int id) {
		Cursor description = db.rawQuery(
				"select * from " + 
						ReciepeQueryHelper.recipeDescriptionTableName + 
						" where " + ReciepeQueryHelper.ReciepeDescriptionDbEntry.reciepeId + " = ? and " + 
						ReciepeQueryHelper.ReciepeDescriptionDbEntry.language + " = ? ",							
						new String[] {String.valueOf(id), language});
		return description;
	}
	
}
