package com.example.cookbook.repo;

public class ReciepeQueryHelper {
	
	public static final String recipeTableName = "recipe";
	public static final String recipeDescriptionTableName = "recipe_description";
	public static final String space = " ";
	public static final String comma = ",";
	
	public static final String createRecipeTable = "create table " + recipeTableName + 
			"(" + RecipeDbEntry.id + " INTEGER PRIMARY KEY, "
				+ RecipeDbEntry.status + " TEXT"
				+ ")";
		 
	public static final String createRecipeDescriptionTable = "create table " + recipeDescriptionTableName + 
			"(" + ReciepeDescriptionDbEntry.id + " INTEGER PRIMARY KEY, "
			+ ReciepeDescriptionDbEntry.reciepeId + " INTEGER REFERENCES recipe(id), "
			+ ReciepeDescriptionDbEntry.shortDescription + " TEXT, " 
			+ ReciepeDescriptionDbEntry.longDescription + " TEXT, "
			+ ReciepeDescriptionDbEntry.language + " TEXT, "
			+ ReciepeDescriptionDbEntry.title + " TEXT "
			+ ")";
	
	public static final class RecipeDbEntry {
		public static final String id = "id";
		public static final String status = "status";
	}
	
	public static final class ReciepeDescriptionDbEntry {
		public static final String id = "id";
		public static final String reciepeId = "recipe_id";
		public static final String shortDescription = "short_description";
		public static final String longDescription = "long_description";
		public static final String language = "language";
		public static final String title = "title";
	}
}