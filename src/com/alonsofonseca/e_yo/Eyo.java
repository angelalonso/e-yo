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

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;


public class Eyo extends FragmentActivity implements UpperMenu.OnMenuListener,LowerMenu.OnTabListener,
AddEntry.OnNewEntryListener,ShowList.OnArticleSelectedListener,ShowTimetable.OnTimeTableListener,EditEntry.OnDelEntryListener{
	//Data Agent:
	String archive_name = new String("documents/Dropbox_offline/Cal.csv.aux");
	Agent Data_Agent = new Agent();
	Bundle Frag_Bundle = new Bundle();

	//Fragment and Controls:
	Fragment fragment;
	//For a Flipper see http://www.inter-fuser.com/2009/07/android-transistions-slide-in-and-slide.html

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e_yo);

		//Data initialization
		Data_Agent.onCreate(archive_name);
		
		//Upper fragment
		FragmentManager fm_up_menu = getSupportFragmentManager();
		FragmentTransaction ft_up_menu = fm_up_menu.beginTransaction();
		 
		Fragment myUpperMenuFragment = new UpperMenu();
				
		ft_up_menu.add(R.id.UpperFragment, myUpperMenuFragment);
		ft_up_menu.commit();
		
		//Main fragment
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		 
		Fragment myFragment = new ShowTimetable();
		Calendar c = Calendar.getInstance(); 
		String Today = new String(Integer.toString(c.get(Calendar.YEAR)) +  
				String.format("%02d",c.get(Calendar.MONTH) + 1) + 
				String.format("%02d",c.get(Calendar.DAY_OF_MONTH)));
    	fillupMainFragment(myFragment,Frag_Bundle,"date",Today);
    	//FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		//transaction.replace(R.id.myFragment, ShowTimetable);
		//transaction.addToBackStack(null);
		//transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		//transaction.commit();
		//System.out.println(myFragment);
		//fillupMainFragment(myFragment,Frag_Bundle,"entrytype","To Do");
		
		ft.add(R.id.myFragment, myFragment);
		ft.commit();
		
		//Lower fragment
		FragmentManager fm_dwn_menu = getSupportFragmentManager();
		FragmentTransaction ft_dwn_menu = fm_dwn_menu.beginTransaction();
		 
		Fragment myLowerMenuFragment = new LowerMenu();
				
		ft_dwn_menu.add(R.id.LowerFragment, myLowerMenuFragment);
		ft_dwn_menu.commit();
		
		
	}
	
	public void fillupMainFragment(Fragment fragment, Bundle bundle, String searchtype, String tobesearched){
		bundle.putStringArray("DATA", Data_Agent.searchArray(searchtype,tobesearched));
		//TODO Shouldn't this be bundle??
		fragment.setArguments(Frag_Bundle);
	}		
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	return super.onCreateOptionsMenu(menu); 
    }
    //http://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity
    
    public void onUpperMenuSelected(String action, String text_in){
    	if (action == "ADD"){
	    	Fragment AddEntryFragment = new AddEntry();
    		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.myFragment, AddEntryFragment);
			transaction.addToBackStack(null);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.commit();
    	} else {
    		System.out.println(action + text_in);       	
    	}
    }
    public void onTabSelected(String tab){
    	if (tab == "TO DO"){
    		LowerMenu TabFragment = (LowerMenu)getSupportFragmentManager().findFragmentById(R.id.LowerFragment);
	    	TabFragment.tab_active(tab);
			 
			Fragment ShowListFragment = new ShowList();
	    	fillupMainFragment(ShowListFragment,Frag_Bundle,"entrytype","To Do");
	    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.myFragment, ShowListFragment);
			transaction.addToBackStack(null);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.commit();

    	} else if (tab == "TIMETABLE") {
    		LowerMenu TabFragment = (LowerMenu)getSupportFragmentManager().findFragmentById(R.id.LowerFragment);
	    	TabFragment.tab_active(tab);
	   	 
			Fragment ShowTimetable = new ShowTimetable();
			Calendar c = Calendar.getInstance(); 
			String Today = new String(Integer.toString(c.get(Calendar.YEAR)) +  
					String.format("%02d",c.get(Calendar.MONTH) + 1) + 
					String.format("%02d",c.get(Calendar.DAY_OF_MONTH)));
	    	fillupMainFragment(ShowTimetable,Frag_Bundle,"date",Today);
	    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.myFragment, ShowTimetable);
			transaction.addToBackStack(null);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.commit();
	    } else {
			System.out.println("Eyo > On Tab Selected NOTHING ");
		}
		
    
    }

    public void onNewEntryPass(String data){
    	Data_Agent.newEntry(data);
    	
    	LowerMenu TabFragment = (LowerMenu)getSupportFragmentManager().findFragmentById(R.id.LowerFragment);
    	TabFragment.tab_active("TIMETABLE");
   	 
		Fragment ShowTimetable = new ShowTimetable();
		Calendar c = Calendar.getInstance(); 
		String Today = new String(Integer.toString(c.get(Calendar.YEAR)) +  
				String.format("%02d",c.get(Calendar.MONTH) + 1) + 
				String.format("%02d",c.get(Calendar.DAY_OF_MONTH)));
    	fillupMainFragment(ShowTimetable,Frag_Bundle,"date",Today);
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.myFragment, ShowTimetable);
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
    	
    	//Fragment ShowListFragment = new ShowList();
    	//fillupMainFragment(ShowListFragment,Frag_Bundle,"entrytype","");
    	//FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		//transaction.replace(R.id.myFragment, ShowListFragment);
		//transaction.addToBackStack(null);
		//transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		//transaction.commit();
		
    }
    
    public void onDelEntryPass(int data){
    	Data_Agent.DeleteEntry(data);
       	LowerMenu TabFragment = (LowerMenu)getSupportFragmentManager().findFragmentById(R.id.LowerFragment);
    	TabFragment.tab_active("TIMETABLE");
   	 
		Fragment ShowTimetable = new ShowTimetable();
		Calendar c = Calendar.getInstance(); 
		String Today = new String(Integer.toString(c.get(Calendar.YEAR)) +  
				String.format("%02d",c.get(Calendar.MONTH) + 1) + 
				String.format("%02d",c.get(Calendar.DAY_OF_MONTH)));
    	fillupMainFragment(ShowTimetable,Frag_Bundle,"date",Today);
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.myFragment, ShowTimetable);
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
    }
    
    public void onArticleSelected(String value){
    	System.out.println(value);
    	Fragment EditEntryFragment = new EditEntry();
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    	Frag_Bundle.putString("ENTRYTOEDIT", value);
    	EditEntryFragment.setArguments(Frag_Bundle);		
    	transaction.replace(R.id.myFragment, EditEntryFragment);
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();

    	//Fragment newFragment = new EditEntry();
		//FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		//Frag_Bundle.putString("ENTRYTOEDIT", value);
		//newFragment.setArguments(Frag_Bundle);
		
		//transaction.replace(R.id.myFragment, newFragment);
		//transaction.addToBackStack(null);
		//transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		//transaction.commit();
    			
    }
    public void onTimeTableSelected(String value){
    	System.out.println(value);
    	Fragment EditEntryFragment = new EditEntry();
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    	Frag_Bundle.putString("ENTRYTOEDIT", value);
    	EditEntryFragment.setArguments(Frag_Bundle);		
    	transaction.replace(R.id.myFragment, EditEntryFragment);
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();   
    }
}