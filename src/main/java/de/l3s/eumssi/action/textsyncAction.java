package de.l3s.eumssi.action;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.opensymphony.xwork2.Action;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class textsyncAction implements Action{
	public String entitynames;
	public String getEntitynames() {
		return entitynames;
	}

	public void setEntitynames(String entitynames) {
		this.entitynames = entitynames;
	}

	private String url;
	public String jsonFileName = "";
   public	String entities[];
   public int hour=00;
   public int min=00;
   public int sec=00;
    public String dataContent=null;	
	public String content1 = "{\"localisation\": [{ \"sublocalisations\": { \"localisation\": [";
    public String content8="]},\"type\": \"text\",\"tcin\": \"00:00:00.0000\",\"tcout\": \"02:00:00.0000\",\"tclevel\": 0}],\"id\": \"text-amalia01\",\"type\": \"text\",\"algorithm\": \"demo-video-generator\",\"processor\": \"Ina Research Department - N. HERVE\", \"processed\": 1421141589288, \"version\": 1}";
	 ArrayList<ArrayList<ArrayList<String>>> mainArrayList = new ArrayList<ArrayList<ArrayList<String>>>(); //3d Array for holding all the property-value pair of entities.
	 
	 // Function for reading dbpedia json file
	private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
           // InputStream inputStream       = new FileInputStream("c:\\data\\input.txt");
           // reader = new BufferedReader(new InputStreamReader(url.openStream()));
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
	 
	private String findingAbstract(ArrayList<ArrayList<ArrayList<String>>> mainArrayList, int entityNumber){
		String[] triviaDemo;
		String[] trivia=new String[10];
        String key;	
		  for(int i=entityNumber;i<entityNumber+1;i++){
		     for(int j=0;j<mainArrayList.get(i).size();j++)
					 { 
					   key= mainArrayList.get(i).get(j).get(0);
					  // System.out.println(key);
			
					   if(key.equals("abstract"))
						   
					   {
						    triviaDemo=mainArrayList.get(i).get(j).get(1).split("\\.");
						   
						 
						
						    trivia[i]=triviaDemo[0];
						    
						    
						    break;
					   }
					 }
		   
		  }
		  
		  return trivia[entityNumber];	
		  }
	
	private void makeData(String data, int dataCounter ){
		 String content2= "{\"data\": {\"text\": [";
         String content3="]},";
         String content4="\"tcin\":";
         String content5=",\"tcout\":";
         
         String content6=",\"tclevel\": 1}";
         String content7=",";
         String dataContentDemo=null;
         String tcin;
		 String tcout;
		 
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
		  sec=sec+10;
		  else{
			  sec=00;
			  min=min+1;
		  }
		  tcout=new DecimalFormat("00").format(hour)+":"+new DecimalFormat("00").format(min)+":"+new DecimalFormat("00").format(sec)+"."+"0000";
		  //System.out.println(data);
		 
		  if(dataCounter>1){
		      dataContentDemo=content7+content2+"\""+data+"\""+content3+content4+"\""+tcin+"\""+content5+"\""+tcout+"\""+content6;
			  dataContent=dataContent+dataContentDemo;
		  }
		  else
		  {
		  dataContentDemo=content2+"\""+data+"\""+content3+content4+"\""+tcin+"\""+content5+"\""+tcout+"\""+content6;
		  dataContent=dataContentDemo;
		  }
		 
		
	}
	//Function for writting to json file for Amalia player
	
	private void jsonWriter(  ArrayList<ArrayList<ArrayList<String>>> mainArrayList){
		String mainContent; 
		Map<String,String> mapQuestion =new HashMap<String,String>();
		Map<String,String> mapInfo =new HashMap<String,String>();
		Map<String,String> mapTrivia =new HashMap<String,String>();
		mapQuestion.put("currency", "What is the currency?");
		mapQuestion.put("populationTotal", "How much is the total population?");
		mapQuestion.put("capital", "What is the name of capital?");
		mapQuestion.put("officialLanguages", "What is the official Language?");
		mapQuestion.put("birthDate", "Do you know the birhdate?");
		mapQuestion.put("birthPlace", "do you know the bithplace?");
		mapQuestion.put("almaMater", "Which university he/she attended?");
		mapQuestion.put("founder", "Do you know who is the founder?");
		mapQuestion.put("foundingYear", "When it was founded?");
        mapInfo.put("homepage", "The homepage is: ");
        mapInfo.put("capital", "The capital is ");
        mapInfo.put("officialLanguages", "The official language is ");
        mapInfo.put("birthPlace", "The place of birth is ");
        mapInfo.put("almaMater", "Attended ");
        mapInfo.put("founder", "name of the founder is ");
        mapTrivia.put("knownFor", "known for");
        
        
		
		
		int sizeOfArrayList=0;
		
		for(int i=0;i<mainArrayList.size();i++)
			{
				sizeOfArrayList=sizeOfArrayList+mainArrayList.get(i).size();
			}
				
		
		try {
			
			
           
    
            
		    String data;
     		   
		    String key;
		    String value;
		    
		
		  
		    
		  
		    int  dataCounter=0;
		 
		    for(int i=0;i<mainArrayList.size();i++){
		         data="Abstract"+":"+"<br>"+findingAbstract(mainArrayList, i);
		         dataCounter++;
		         makeData(data, dataCounter);
		        // System.out.println(data);
		  
				for(int j=0;j<mainArrayList.get(i).size();j++)
					 { 
					   key= mainArrayList.get(i).get(j).get(0);
					//   System.out.println(key);
					 //  System.out.println(j);
					  
						   
					   Random ran = new Random();
					   int x = ran.nextInt(3) + 1;
					   System.out.println(x);
					   if(x==1){
					   key=mapQuestion.get(key);
					    }
					   if(x==2){
						   key=mapInfo.get(key);
						    }
					   if(x==3){
						   key=mapTrivia.get(key);
						    }
					   
					   if(key!=null){
						   
						  value=mainArrayList.get(i).get(j).get(1);
						  
						  value=value.replaceAll("\"","");
						  data=entities[i+1]+":"+"<br>"+key+":"+"<br>Answer:"+value;
						
						   
                            dataCounter++; 
                            makeData(data,dataCounter);
					  /*
					 
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
					 
					  if(dataCounter>1){
					      dataContentDemo=content7+content2+"\""+data+"\""+content3+content4+"\""+tcin+"\""+content5+"\""+tcout+"\""+content6;
						  dataContent=dataContent+dataContentDemo;
					  }
					  else
					  {
					  dataContentDemo=content2+"\""+data+"\""+content3+content4+"\""+tcin+"\""+content5+"\""+tcout+"\""+content6;
					  dataContent=dataContentDemo;
					  }
					  */
						  
					 }
					   
					 }
		}
		    
			mainContent=content1+dataContent+content8;
		//	System.out.println(dataContent);
			//System.out.println(mainContent);
			//hardcoded
			File file = new File("/Work/EUMSSI/mygithub/EventSense/WebContent/scripts/"+jsonFileName+".json");
			System.out.println(file.getAbsolutePath());
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
	//	System.out.println(url); 
		ArrayList<String> subSubArray;
		 ArrayList<ArrayList<String>> subArray;
		 System.out.println(entitynames);
		 entities=entitynames.split(",");
         JSONParser parser = new JSONParser();
         try{
        	 for(int entityCounter=1;entityCounter<entities.length;entityCounter++ ){
        		 subArray = new ArrayList<ArrayList<String>>();
        		 jsonFileName=jsonFileName+entities[entityCounter];
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
        		        	 String mainValue=null;
        		    			 jarray= (JSONArray)job1.get(key1);
        		    			for( int i=0;i<jarray.size(); i++){
        		    			   
        		    			job2=(JSONObject)(jarray.get(i));
        		    			literal=(String)(job2.get("type"));
        		    			if(literal.equals("literal") || literal.equals("uri"))
        		    			   {
        		    		        String[] splitKey1=(String[])key1.split("/");
        		    				String mainKey=splitKey1[splitKey1.length-1].toString();
        		    				
        		    				if(mainKey.equals("abstract")){
        		    					if(job2.get("lang").equals("en")){
        		    						   mainValue=job2.get("value").toString();	
        		    						//   System.out.println(mainValue);
        		    					}
        		    				}
        		    				else
        		    				{
        		    		            if(job2.get("type").equals("uri"))
        		    		            {
        		    		            	mainValue=job2.get("value").toString();
        		    		            	
        		    		            	String[] splitMainValue=(String[])mainValue.split("/");
        	        		    			 mainValue=splitMainValue[splitMainValue.length-1].toString();
        	        		    			// System.out.println("mainvaue1"+":"+mainValue);
        	        		    			 
        		    		            }
        		    		            else
        		    		            {
        		    		            	
        		    		            		mainValue=job2.get("value").toString();
        		    		            	
        		    		            }
        		    				}
        		    				if(mainValue==null)
        		    					continue;
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
        //	 System.out.println(mainArrayList.get(0).get(0).get(0));
        //	 System.out.println(mainArrayList.get(0).get(0).get(1));
        
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	


	
}
