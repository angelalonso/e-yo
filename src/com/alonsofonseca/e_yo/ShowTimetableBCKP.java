/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Author:
 * Ángel Alonso Fonseca <alonsofonseca.angel@gmail.com>
 * 
 */

package com.alonsofonseca.e_yo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowTimetableBCKP extends ListFragment{

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
       
        sa = new SpecialAdapter(getActivity(), list,
                R.layout.entries_show_list,
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
        	for (int hour = 0; hour < 24; hour++){
        		String title=""; 
		 		String content="       - " + hour + ":00"; 
		 		String entry_id="0";
		 		auxlist.add(putData(title,content,entry_id));
        	
   		 		for (int c = 0; c < aux_array.length; c++){
   		 			String[] line_tokens = aux_array[c].split(delim);
   		 			if (line_tokens[0].substring(8,10).equals(Integer.toString(hour))){
   		 				System.out.println(line_tokens[0] + line_tokens[1]);   		 			
   		 				title=line_tokens[0].substring(6,8) + "-" + line_tokens[0].substring(4,6) + "-" + line_tokens[0].substring(0,4) + " " + line_tokens[0].substring(8,10) + ":" + line_tokens[0].substring(10,12); 
   		 				content=line_tokens[1];
   		 				entry_id=line_tokens[2];
   		 				auxlist.add(putData(title,content,entry_id));
   		 			}
   		 		}
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

