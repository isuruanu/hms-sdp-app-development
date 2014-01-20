package com.example.cookbook.repo.domain;

public class Recipe {
	
	private int id;
	private String status;		
	private Description descriptions;
		
	public Recipe(int id, String status, Description descriptions) {
		super();
		this.id = id;
		this.status = status;
		this.descriptions = descriptions;
	}	
	
	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public Description getDescriptions() {
		return descriptions;
	}

	public static class Description {
		private String languageId;
		private String shortDescription;
		private String longDescription;
		private String title;
		private int recipeId;
						
		public Description(String languageId, String shortDescription,
				String longDescription, String title, int recipeId) {
			super();
			this.languageId = languageId;
			this.shortDescription = shortDescription;
			this.longDescription = longDescription;
			this.title = title;
			this.recipeId = recipeId;
		}
		public String getLanguageId() {
			return languageId;
		}
		public String getShortDescription() {
			return shortDescription;
		}
		public String getLongDescription() {
			return longDescription;
		}
		public String getTitle() {
			return title;
		}
		public int getRecipeId() {
			return recipeId;
		}			
	}			
}
