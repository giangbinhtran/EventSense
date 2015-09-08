package de.l3s.eumssi.action;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.opensymphony.xwork2.Action;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class textsyncAction implements Action{
	private String myparam;
	private String url;
	 ArrayList<ArrayList<ArrayList<String>>> mainArrayList = new ArrayList<ArrayList<ArrayList<String>>>(); //3d Array for holding all the property-value pair of entities.
	 
	 // Function for reading dbpedia json file
	private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read); 
            }
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
	 
	//Function for writting to json file for Amalia player
	
	private void jsonWriter(  ArrayList<ArrayList<ArrayList<String>>> mainArrayList){
		String mainContent; 
		int sizeOfArrayList=0;
		
		for(int i=0;i<mainArrayList.size();i++)
			{
				sizeOfArrayList=sizeOfArrayList+mainArrayList.get(i).size();
			}
				
		
		try {
			
			
			 String dataContent=null;	
			String content1 = "{\"localisation\": [{ \"sublocalisations\": { \"localisation\": [";
            String content2= "{\"data\": {\"text\": [";
            String content3="]},";
            String content4="\"tcin\":";
            String content5=",\"tcout\":";
            String content6=",\"tclevel\": 1}";
            String content7=",";
            String content8="]},\"type\": \"text\",\"tcin\": \"00:00:00.0000\",\"tcout\": \"02:00:00.0000\",\"tclevel\": 0}],\"id\": \"text-amalia01\",\"type\": \"text\",\"algorithm\": \"demo-video-generator\",\"processor\": \"Ina Research Department - N. HERVE\", \"processed\": 1421141589288, \"version\": 1}";
            
		    String data;
            String dataContentDemo=null;		   
		    String key;
		    String value;
		    int hour=00;
		    int min=00;
		    int sec=00;
		    String tcin;
		    String tcout;
		    int  keyCounter=0;
		   
		    for(int i=0;i<mainArrayList.size();i++)
				for(int j=0;j<mainArrayList.get(i).size();j++)
					 {keyCounter++;
					   key= mainArrayList.get(i).get(j).get(0);
					  value=mainArrayList.get(i).get(j).get(1);
					  value=value.replaceAll("\"","");
					  data=key+":"+value;
					  if(sec==60){
						  sec=00;
						  min=min+1;
						  }
					  else
						  sec=sec+5;
					  if(min==60){
						  sec=00;
					      min=00;
					      hour=hour+1;
					   }
					  tcin=new DecimalFormat("00").format(hour)+":"+new DecimalFormat("00").format(min)+":"+new DecimalFormat("00").format(sec)+"."+"0000";
					  //tcin=hour+":"+min+":"+sec+"."+"0000";
					  if(sec<60)
					  sec=sec+05;
					  else{
						  sec=00;
						  min=min+1;
					  }
					  tcout=new DecimalFormat("00").format(hour)+":"+new DecimalFormat("00").format(min)+":"+new DecimalFormat("00").format(sec)+"."+"0000";
					  //System.out.println(data);
					 
					  if(keyCounter==sizeOfArrayList){
					
						 
						  dataContentDemo=content2+"\""+data+"\""+content3+content4+"\""+tcin+"\""+content5+"\""+tcout+"\""+content6;
						  dataContent=dataContent+dataContentDemo;
					  }
					  else
					  {
						 
					  dataContentDemo=content2+"\""+data+"\""+content3+content4+"\""+tcin+"\""+content5+"\""+tcout+"\""+content6+content7;
					  if(keyCounter>2)
											  
					    dataContent=dataContent+dataContentDemo;
					  else
						  dataContent=dataContentDemo;
					  }
					  
						  
					  
					 }
		  
			mainContent=content1+dataContent+content8;
		//	System.out.println(dataContent);
			//System.out.println(mainContent);
			File file = new File("G:\\workspace\\eventsense\\WebContent\\scripts\\amalia01-text.json");
		//	System.out.println(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mainContent);
			bw.close();

			//System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	

	public String execute()
	{
		System.out.println(url); 
		ArrayList<String> subSubArray;
		 ArrayList<ArrayList<String>> subArray;
		String entities[]=myparam.split(",");
		System.out.println(myparam);
         for(int i=0;i<entities.length;i++){
        	 System.out.println(entities[i]);
        
         }
         JSONParser parser = new JSONParser();
         try{
        	 for(int entityCounter=1;entityCounter<entities.length;entityCounter++ ){
        		 subArray = new ArrayList<ArrayList<String>>();
        	 String outputString=readUrl("http://dbpedia.org/data/"+entities[entityCounter]+".json");
        	//System.out.println("http://dbpedia.org/data/"+entities[1]+".json"); 
        	 Object ob;
        	 JSONObject job1;
        	 JSONObject job2;
        	 JSONArray jarray;
        	 //String outputString=readUrl("http://dbpedia.org/data/Germany.json");
        	 Object obj = parser.parse(outputString);    
             JSONObject jsonObject = (JSONObject) obj;
        	 for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
        		    String key = (String) iterator.next();
        		 // if(jsonObject.get(key) instanceof JSONObject )
        			ob= jsonObject.get(key);
        		    job1 = (JSONObject) ob;
        		    String literal;
        		    int indexOfmainArrayList=0;
        		    for(Iterator iterator1 = job1.keySet().iterator(); iterator1.hasNext();){
        		    	 String key1 = (String) iterator1.next();
        		         if(job1.get(key1) instanceof JSONArray)
        		    		 {
        		    			// System.out.println(key1);
        		    			 jarray= (JSONArray)job1.get(key1);
        		    			for( int i=0;i<jarray.size(); i++){
        		    			   
        		    			job2=(JSONObject)(jarray.get(i));
        		    			literal=(String)(job2.get("type"));
        		    			if(literal.equals("literal"))
        		    			   {
        		    		        String[] splitKey1=(String[])key1.split("/");
        		    				String mainKey=splitKey1[splitKey1.length-1].toString();
        		    		        String mainValue=job2.get("value").toString();
        		    				subSubArray = new ArrayList<String>();
        		    				
                                    subSubArray.add(mainKey);
                                    subSubArray.add(mainValue);
                                    subArray.add(subSubArray);
                           
        		    			    
        		    		 	    }
        		    			}
        		    		 }
        		  
        		    }
        		    
        		}
               mainArrayList.add(subArray);
        	 }
        	 System.out.println(mainArrayList);
        	 System.out.println(mainArrayList.get(0).get(0).get(0));
        	 System.out.println(mainArrayList.get(0).get(0).get(1));
            jsonWriter(mainArrayList);
        	 
         }
         catch (FileNotFoundException e) {
     		e.printStackTrace();
     	} catch (IOException e) {
     		e.printStackTrace();
     	} catch (ParseException e) {
     		e.printStackTrace();
     	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         

         
	    return "test";
	}
	public String getMyparam() {
		return myparam;
	}
	public void setMyparam(String myparam) {
		this.myparam = myparam;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	


	
}
