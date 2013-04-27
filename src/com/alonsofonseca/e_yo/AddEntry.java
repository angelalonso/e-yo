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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class AddEntry extends Fragment {
	SharedPreferences data_transfers;
	DatePicker dp1;
	TimePicker tp1;
	Spinner Task_Spinner;
	EditText et_entry_description;
	Button btn_save;
	
	OnNewEntryListener newEntryPasser;
	
	@Override
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    newEntryPasser = (OnNewEntryListener) a;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.add_entry, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();

		//data_transfers = this.getActivity().getSharedPreferences("eyo_shrdpref_data", 0);
		
		dp1 = (DatePicker) getView().findViewById(R.id.dp1);
		tp1 = (TimePicker) getView().findViewById(R.id.tp1);
		et_entry_description = (EditText) getView().findViewById(R.id.et_entry_description);
		btn_save = (Button) getView().findViewById(R.id.btn_delete);
		btn_save.setOnClickListener(btnOnClickListener);
		addItemsOnEntryTypeSpinner();
	}
	
	
	public void addItemsOnEntryTypeSpinner() {
	
		Task_Spinner = (Spinner) getView().findViewById(R.id.sp_task_type); 
		ArrayAdapter adapter = ArrayAdapter.createFromResource( this.getActivity(), R.array.tasktypes, android.R.layout.simple_spinner_item); 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Task_Spinner.setAdapter(adapter);
	}
	
	Button.OnClickListener btnOnClickListener = new Button.OnClickListener(){

		@Override
		public void onClick(View v){
			String data = return_data();
			getActivity().getSupportFragmentManager().popBackStack();
			newEntryPasser.onNewEntryPass(data);
		}
	};
	
	public interface OnNewEntryListener {
	    public void onNewEntryPass(String data);
	}
	
	public String return_data(){
		String result = "";
		
		String date_result = "000000000000";
		String task_result = "";
		date_result = Integer.toString(dp1.getYear()) + String.format("%02d",dp1.getMonth()+1) + String.format("%02d",dp1.getDayOfMonth()) + String.format("%02d",tp1.getCurrentHour()) + String.format("%02d",tp1.getCurrentMinute());
		result = date_result + ",\"" + task_result + String.valueOf(Task_Spinner.getSelectedItem() + "##" + et_entry_description.getText().toString()) + "\"\n";

		return result; 
		
	}
}