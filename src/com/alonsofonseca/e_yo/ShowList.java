package com.alonsofonseca.e_yo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alonsofonseca.e_yo.UpperMenu.OnMenuListener;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowList extends ListFragment{

    OnArticleSelectedListener SelCallback;
	
    Bundle bundled = new Bundle();

    private ListView lv;
    //SimpleAdapter does the work to load the data in to the ListView
    private SimpleAdapter sa;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_show_list, container, false);
	}

    @Override
	 	public void onStart() {
		 	super.onStart();
		 	lv = (ListView)this.getActivity().findViewById(android.R.id.list);
		 	dataLoad();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
        	SelCallback = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }

    }
    
    public void dataLoad() {
		bundled =  this.getArguments();
    	
		String[] new_arr = bundled.getStringArray("DATA");
    	    	
    	ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = loadData(new_arr);
       
        sa = new SimpleAdapter(getActivity(), list,
                R.layout.entries_show_list,
        		//R.layout.showtoday,
        		new String[] { "time","appointment"},
                new int[] {R.layout.tv_f2_listitem_hours, R.layout.tv_f2_listitem_appnts});
        sa.notifyDataSetChanged();
        setListAdapter(sa);
    }
    
	public void onPause() {
		super.onPause();
	}
	
    public ArrayList<Map<String, String>> loadData(String[] aux_array){

    		ArrayList<Map<String, String>> auxlist = new ArrayList<Map<String, String>>();
        	String delim = ",";
   		 	for (int c = 0; c < aux_array.length; c++){
   		 		String[] line_tokens = aux_array[c].split(delim);
   		 		String title=line_tokens[0].substring(6,8) + "-" + line_tokens[0].substring(4,6) + "-" + line_tokens[0].substring(0,4) + " " + line_tokens[0].substring(8,10) + ":" + line_tokens[0].substring(10,12); 
   		 		String content=line_tokens[1];
   		 		String entry_id=line_tokens[2];
   		 		auxlist.add(putData(title,content,entry_id));
   		 	}
        	return auxlist;
   }
       
   private HashMap<String, String> putData(String tasktime, String taskname, String taskid) {
    		HashMap<String, String> item = new HashMap<String, String>();
    		item.put("time", tasktime);
    		item.put("appointment", taskname);
    		item.put("id", taskid);
    		return item;
   }    	

   @Override
   public void onListItemClick(ListView l, View v, int position, long id) {
	   //Thanks to http://stackoverflow.com/questions/13425288/how-to-get-info-from-listfragment
	   HashMap<String, String> c = (HashMap<String, String>) sa.getItem(position);
	   String value = "#id " + c.get("id") + ", " + c.get("time") + "," + c.get("appointment");
	   SelCallback.onArticleSelected(value);

   } 	
   
   public interface OnArticleSelectedListener {
            void onArticleSelected(String value);
   }

}  

