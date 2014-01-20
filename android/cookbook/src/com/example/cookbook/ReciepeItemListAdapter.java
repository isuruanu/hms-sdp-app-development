package com.example.cookbook;

import java.util.ArrayList;
import java.util.List;

import com.example.cookbook.repo.domain.Recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReciepeItemListAdapter extends BaseAdapter{
	
    private LayoutInflater mLayoutInflater;       
	private List<Recipe> mEntries = new ArrayList<Recipe>();
	
	public ReciepeItemListAdapter(Context context, List<Recipe> list) {		
		this.mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		this.mEntries = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mEntries.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mEntries.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout layout = (RelativeLayout)mLayoutInflater.inflate(R.layout.receipe_item, parent, false);
		Recipe recipe = mEntries.get(position);
		
		TextView title = (TextView)layout.findViewById(R.id.listTitle);
		title.setText(recipe.getDescriptions().getTitle());
		
		TextView description = (TextView)layout.findViewById(R.id.listDescription);
		description.setText(recipe.getDescriptions().getShortDescription());
		
		return layout;
	}

}
