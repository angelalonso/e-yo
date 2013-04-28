package com.alonsofonseca.e_yo;

import java.util.ArrayList;
import java.util.Map;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;


public class SpecialAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };
	public SpecialAdapter(FragmentActivity context, ArrayList<Map<String, String>> items, int resource, String[] from, int[] to) {
		super(context, items, resource, from, to);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	  View view = super.getView(position, convertView, parent);
	  int type = getItemViewType(position);
	  System.out.println(type);
	  if (Integer.toString(position).equals("5")){
		  view.setBackgroundColor(colors[1]);
	  }
	  return view;
	}
}
