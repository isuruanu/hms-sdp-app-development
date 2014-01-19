package com.example.cookbook;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ReciepeItemListAdapter extends BaseAdapter{
	
    private LayoutInflater mLayoutInflater;       
	private List<String> mEntries = new ArrayList<String>();
	
	public ReciepeItemListAdapter(Context context, List<String> list) {		
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
		return layout;
	}

}
