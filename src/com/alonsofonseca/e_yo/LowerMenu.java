package com.alonsofonseca.e_yo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LowerMenu extends Fragment implements OnClickListener{

	Button btn_to_do,btn_timetable;
	
	OnTabListener TabCallBack;
	
	public interface OnTabListener {		
	    public void onTabSelected(String tabselected);
	}

	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

	        View v = inflater.inflate(R.layout.fragment_lower_menu, container, false);

	        Button btn_to_do = (Button) v.findViewById(R.id.btn_to_do);
	        btn_to_do.setOnClickListener(this);
	        Button btn_timetable = (Button) v.findViewById(R.id.btn_timetable);
	        btn_timetable.setOnClickListener(this);

	        return v;
	 }

	 public void tab_active(String tabselected){
		 View v = this.getView();
		 Button btn_to_do = (Button) v.findViewById(R.id.btn_to_do);
		 Button btn_timetable = (Button) v.findViewById(R.id.btn_timetable);
		 if (tabselected == "TO DO"){
			 	btn_to_do.setBackgroundColor(getResources().getColor(R.color.tab_active));
	   		 	btn_timetable.setBackgroundColor(getResources().getColor(R.color.tab_inactive));
			} else if (tabselected == "TIMETABLE"){
	        	btn_to_do.setBackgroundColor(getResources().getColor(R.color.tab_inactive));
	   		 	btn_timetable.setBackgroundColor(getResources().getColor(R.color.tab_active));
			} else {
	        	btn_to_do.setBackgroundColor(getResources().getColor(R.color.tab_inactive));
	   		 	btn_timetable.setBackgroundColor(getResources().getColor(R.color.tab_inactive));
			}
	 }
	 
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        
	        // This makes sure that the container activity has implemented
	        // the callback interface. If not, it throws an exception
	        try {
	            TabCallBack = (OnTabListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement onTabListener");
	        }

	 }

	
	 @Override
	 public void onClick(View v) {
		 switch (v.getId()) {
	        case R.id.btn_to_do:
        		TabCallBack.onTabSelected("TO DO");
	        	break;
	        case R.id.btn_timetable:
        		TabCallBack.onTabSelected("TIMETABLE");
        		break;
		 	}
    }
}