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

	Button bt_add, bt_config;
	
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

	        Button bt_config = (Button) v.findViewById(R.id.bt_config);
	        bt_config.setOnClickListener(this);
		 	
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
		 switch (v.getId()) {
	        case R.id.bt_config:
	        		upperMenuCallback.onUpperMenuSelected("TEST","blabla");
	        	break;
	        case R.id.bt_add:
	        	upperMenuCallback.onUpperMenuSelected("ADD","blabla");
		 	}
	    }
	}