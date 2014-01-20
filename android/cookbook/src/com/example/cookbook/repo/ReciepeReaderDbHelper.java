package com.example.cookbook.repo;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;


public class ReciepeReaderDbHelper extends SQLiteOpenHelper {
	private static final String dbName = "reciepe.db";
	private static final int version = 2;
	
	public ReciepeReaderDbHelper(Context context) {
		super(context, dbName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v("repo", "REPO CREATED...");
		db.execSQL(ReciepeQueryHelper.createRecipeTable);
		db.execSQL(ReciepeQueryHelper.createRecipeDescriptionTable);
				
		populateData(db);
	}

	private void populateData(SQLiteDatabase db) {		
		ContentValues values = new ContentValues();
		values.put(ReciepeQueryHelper.RecipeDbEntry.id, 1);
		values.put(ReciepeQueryHelper.RecipeDbEntry.status, "local");
		db.insert(ReciepeQueryHelper.recipeTableName, "null", values);
		
		ContentValues values1 = new ContentValues();
		values1.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.id, 1);
		values1.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.reciepeId, 1);
		values1.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.language, "en");
		values1.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.longDescription, "Italian Pizza en - long description");
		values1.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.shortDescription, "Italian Pizza en - short description");
		values1.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.title, "Italian Pizza en");
		db.insert(ReciepeQueryHelper.recipeDescriptionTableName, "null", values1);
		
		ContentValues values2 = new ContentValues();
		values2.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.id, 2);
		values2.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.reciepeId, 1);
		values2.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.language, "es");
		values2.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.longDescription, "Italian Pizza es - long description");
		values2.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.shortDescription, "Italian Pizza es - short description");
		values2.put(ReciepeQueryHelper.ReciepeDescriptionDbEntry.title, "Italian Pizza es");
		db.insert(ReciepeQueryHelper.recipeDescriptionTableName, "null", values2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
