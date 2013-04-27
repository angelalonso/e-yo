package com.alonsofonseca.e_yo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EditEntry extends Fragment implements OnClickListener{
	SharedPreferences data_transfers;
	TextView tv_entry;
	Button btn_delete,btn_cancel;
	
	OnDelEntryListener delCallback;
	
	Bundle bundled = new Bundle();

	String entry_to_edit = new String();
	
	public interface OnDelEntryListener {
	    public void onDelEntryPass(int data);
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
        	delCallback = (OnDelEntryListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDelEntryListener");
        }

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.edit_entry, container, false);
		
		Button btn_delete = (Button) v.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
		
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();

		bundled =  this.getArguments();
    	
		entry_to_edit = bundled.getString("ENTRYTOEDIT");
		//data_transfers = this.getActivity().getSharedPreferences("eyo_shrdpref_data", 0);
		
		tv_entry = (TextView) getView().findViewById(R.id.tv_entry);
		tv_entry.setText(entry_to_edit);
	}
	
	@Override
	public void onClick(View v){
		 switch (v.getId()) {
	        case R.id.btn_delete:
	        	String delim=",";
	        	String[] line_tokens = entry_to_edit.split(delim);
	        	int id=Integer.parseInt(line_tokens[0].replaceAll("#id ", ""));
	        		delCallback.onDelEntryPass(id);
	        	break;
	        case R.id.btn_cancel:
	        	System.out.println("operation canceled");
		 	}
	    }			//DelEntryPasser.onDelEntryPass(data);

}