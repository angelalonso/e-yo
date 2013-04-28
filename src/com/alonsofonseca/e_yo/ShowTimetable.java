package com.alonsofonseca.e_yo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.alonsofonseca.e_yo.ShowTimetableBCKP.OnArticleSelectedListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowTimetable extends ListFragment{

	OnTimeTableListener TimetableCallback;
	
    Bundle bundled = new Bundle();
    
    private MyCustomAdapter TimeTableAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
    	return inflater.inflate(R.layout.fragment_timetable, container, false);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        TimeTableAdapter = new MyCustomAdapter();
        dataLoad();
        
        setListAdapter(TimeTableAdapter);
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
        	TimetableCallback = (OnTimeTableListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTimeTableListener");
        }

    }
    

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
 	   //Thanks to http://stackoverflow.com/questions/13425288/how-to-get-info-from-listfragment
 	   String value = "#id " + TimeTableAdapter.getItem(position)[0] + ", " + TimeTableAdapter.getItem(position)[1];
 	   System.out.println(value);
 	   //String value = "#id " + c.get("id") + ", " + c.get("time") + "," + c.get("appointment");
 	  TimetableCallback.onTimeTableSelected(value);

    } 	
    
    public interface OnTimeTableListener {
             void onTimeTableSelected(String value);
    }
    
    public void dataLoad() {
		bundled =  this.getArguments();
    	
		String[] new_arr = bundled.getStringArray("DATA");
    	    	
    	ArrayList<String> list = new ArrayList<String>();
        list = dataPrepare(new_arr);
       
        setListAdapter(TimeTableAdapter);
    }
    public ArrayList<String> dataPrepare(String[] aux_array){

		ArrayList<String> auxlist = new ArrayList<String>();
		
    	String delim = ",";
    	    	
    	for (int hour = 0; hour < 24; hour++){
    		String content=hour + ":00"; 
    		TimeTableAdapter.addSeparatorItem(content);
	 		for (int c = 0; c < aux_array.length; c++){
		 			String[] line_tokens = aux_array[c].split(delim);
		 			if (line_tokens[0].substring(8,10).equals(Integer.toString(hour))){
		 				content=line_tokens[0].substring(8,10) + ":" + line_tokens[0].substring(10,12) + " - " + line_tokens[1];
		 				String id= line_tokens[2];
		 				TimeTableAdapter.addItem(content, id);
		 			}
		 		}
    	}
    	return auxlist;
}
    

    private class MyCustomAdapter extends BaseAdapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;
        private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

        private ArrayList<String> mData = new ArrayList<String>();
        private ArrayList<String> mId = new ArrayList<String>();
        private LayoutInflater mInflater;

        private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

        public MyCustomAdapter() {
        	mInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item, final String id) {
            mData.add(item);
            mId.add(id);
            notifyDataSetChanged();
        }

        public void addSeparatorItem(final String item) {
            mData.add(item);
            String noId="-1";
            mId.add(noId);
            // save separator position
            mSeparatorsSet.add(mData.size() - 1);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String[] getItem(int position) {
        	String[] item = new String[] {mId.get(position),mData.get(position)};
        	
            return item;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            int type = getItemViewType(position);
            if (convertView == null) {
                holder = new ViewHolder();
                switch (type) {
                    case TYPE_ITEM:
                        convertView = mInflater.inflate(R.layout.item1, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.text);
                        break;
                    case TYPE_SEPARATOR:
                        convertView = mInflater.inflate(R.layout.item2, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.textSeparator);
                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.textView.setText(mData.get(position));
            return convertView;
        }

    }

    public static class ViewHolder {
        public TextView textView;
    }
}