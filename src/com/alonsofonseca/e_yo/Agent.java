package com.alonsofonseca.e_yo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import android.os.Environment;

public class Agent {
	String archname;
	ArrayList<EntryClass> data_arr = new ArrayList<EntryClass>();
	
	// Class that defines a typical entry
	public class EntryClass implements Serializable{
			
		public String date_time;
		public String description;
	}
	
	public int filesize(String file) throws IOException {
	    File card = Environment.getExternalStorageDirectory();
        File filename = new File(card.getAbsolutePath(), file );            
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	void onCreate(String archivename) {
		archname = archivename;
    	
    	Integer archive_size = 0;
    	String all = "";
    	try {
    		archive_size = filesize(archname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	EntryClass[] lines = new EntryClass[archive_size];
    	
    	try {
            
        	File card = Environment.getExternalStorageDirectory();
            File file = new File(card.getAbsolutePath(), archname );    	
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader archive = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(archive);
            String line = br.readLine();
            Integer array_counter = 0;
        	
            while (line != null) {
            	//new line to array
            	
            	// TODO Continuar jugando con estos resultados
            	
            	String delim = ",";
            	String[] line_tokens = line.split(delim);
            	lines[array_counter] = new EntryClass();
            	lines[array_counter].date_time = line_tokens[0];
            	lines[array_counter].description = line_tokens[1];
            	
            	data_arr.add(lines[array_counter]);
            	line = br.readLine();
                array_counter++; 
            }

            
            br.close();
            archive.close();

        } catch (IOException e) {
        }
     
	}
	public String[] readArray() {
		EntryClass line = new EntryClass();
		String [] data_to_return = new String[data_arr.size()];
		String format_line = new String();
		//System.out.println(data_arr.size());
		for (int counter = 0; counter < data_arr.size(); ++counter) {
			line = data_arr.get(counter);
			data_to_return[counter] = line.date_time + "," + line.description;
		}
		return data_to_return;
	}
	public String[] searchArray(String type, String text_to_search) {
		String [] data_read = readArray();
		ArrayList<String> aux_arr = new ArrayList<String>();
		//TODO
		
		// NOT WORKING!!!
		
		
		if (type == "date"){
			for (int i = 0; i < data_read.length; ++i) {
				if ((translateToEntry(data_read[i]).date_time.toString().substring(0,8)).equals(text_to_search.toString())) {
					aux_arr.add(data_read[i] + "," + i);
					//System.out.println("encontrado!" + translateDateTimeToReadable(translateToEntry(data_read[i]).date_time));
				} else {
					
				}
			}
		} else if (type == "datehour"){
			for (int i = 0; i < data_read.length; ++i) {
				if ((translateToEntry(data_read[i]).date_time.toString()).equals(text_to_search.toString())) {
					aux_arr.add(data_read[i] + "," + i);
				} else {
					
				}
			}
		} else if (type == "entrytype"){
			for (int i = 0; i < data_read.length; ++i) {
				String delim = "##";
				String[] line_tokens = translateToEntry(data_read[i]).description.toString().split(delim);
		    			    	
				if (line_tokens[0].contains(text_to_search)) {
					aux_arr.add(data_read[i] + "," + i);
					//System.out.println("return" + data_read[i]);
				} else {
					
				}
			}
		} else {
			
		} 
		String[] data_to_return = aux_arr.toArray(new String[0]);
		return data_to_return;
	}
	public EntryClass translateToEntry(String line) {
		// This array should 
		EntryClass data_to_return = new EntryClass();
		String delim = ",";
    	String[] line_tokens = line.split(delim);
    	data_to_return.date_time = line_tokens[0];
    	data_to_return.description = line_tokens[1];
		return data_to_return;
	}
	public String translateDateTimeToReadable(String datetime) {
		// This array should 
		String data_to_return = new String();
		data_to_return = datetime.substring(6,8) + "-" + datetime.substring(4,6) + "-" + datetime.substring(0,4) + " " + datetime.substring(8,10) + ":" + datetime.substring(10,12); 
		return data_to_return;
	}
	void newEntry(String entry_to_add){
		System.out.println("entry_to_add: " + entry_to_add);
		String delim = ",";
    	String[] line_tokens = entry_to_add.split(delim);
    	EntryClass line = new EntryClass();
    	line.date_time = line_tokens[0];
    	line.description = line_tokens[1];
    	System.out.println("line.date_time: " + line.date_time);
    	System.out.println("line.description: " + line.description);
    	        	
    	data_arr.add(line);
    	save_to_file();
	}
	void DeleteEntry(int id){
		System.out.println("entry_to_delete: " + data_arr.get(id).date_time + "," + data_arr.get(id).description);
		data_arr.remove(id);
		save_to_file();
	}
	void save_to_file(){

		File card = Environment.getExternalStorageDirectory();
		//Step 1: Make a backup
		try { 
		File src = new File(card.getAbsolutePath(), archname); 
        File bckdst = new File(card.getAbsolutePath(), archname + ".bck");
		InputStream in = new FileInputStream(src);
		    OutputStream out = new FileOutputStream(bckdst);

		    // Transfer bytes from in to out
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		}  catch (Exception e) {
			System.out.println(e);
		}
		//Step 2: Write down the Array
		try { 
			//File outputFile = new File(card.getAbsolutePath(), archname);
			File outputFile = new File(card.getAbsolutePath(), archname + ".aux");
			    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

			    String line = data_arr.get(0).date_time + "," + data_arr.get(0).description ;
		    	writer.write(line);
			    for (int count = 1; count < data_arr.size(); ++count) {			    	
			    	line = data_arr.get(count).date_time + "," + data_arr.get(count).description ;
			    	writer.newLine();
			    	writer.write(line);
			    }
			    writer.flush();
			    writer.close();
		}  catch (Exception e) {
			System.out.println(e);
		}
		//Step 3: Cleanup the auxiliary file
		//Thanks http://stackoverflow.com/questions/7190639/remove-all-blank-spaces-and-empty-lines
		try {
			
			Scanner scanner = new Scanner(new File(card.getAbsolutePath(), archname + ".aux"));
			PrintStream out = new PrintStream(new File(card.getAbsolutePath(), archname));
			while(scanner.hasNextLine()){
			    String line = scanner.nextLine();
			    line = line.trim();
			    if(line.length() > 0)
			        out.println(line);
			}
			 
			scanner.close();
			out.close();
		}  catch (Exception e) {
			System.out.println(e);
		}			
	}
	void sortArray() {
		//Collections.sort(data_arr);
	}
}
