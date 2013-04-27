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

public class UpperMenu extends Fragment implements OnClickListener{

	Button bt_add, bt_update;
	EditText et_1, et_2;
	
	OnMenuListener upperMenuCallback;
	
	public interface OnMenuListener {		
	    public void onUpperMenuSelected(String text1, String text2);
	}

	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

	        View v = inflater.inflate(R.layout.fragment_upper_menu, container, false);

	        Button bt_add = (Button) v.findViewById(R.id.bt_add);
	        bt_add.setOnClickListener(this);

	        Button bt_update = (Button) v.findViewById(R.id.bt_update);
	        bt_update.setOnClickListener(this);
		 	
	        return v;
	 }

	 
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        
	        // This makes sure that the container activity has implemented
	        // the callback interface. If not, it throws an exception
	        try {
	        	upperMenuCallback = (OnMenuListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement OnMenuListener");
	        }

	 }

	
	 @Override
	 public void onClick(View v) {
		 et_1 = (EditText)this.getActivity().findViewById(R.id.et_1);
	 	 et_2 = (EditText)this.getActivity().findViewById(R.id.et_2);
		 switch (v.getId()) {
	        case R.id.bt_update:
	        		String pasa1=et_1.getText().toString();
	        		String pasa2=et_2.getText().toString();
	        		upperMenuCallback.onUpperMenuSelected("TEST",pasa1 + " " + pasa2);
	        	break;
	        case R.id.bt_add:
	        	upperMenuCallback.onUpperMenuSelected("ADD","blabla");
		 	}
	    }
	}