import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;
 
/**
 * @author maryannewalauskis
 * TextAnalyzer breaks text files down and counts 
 * and tracks unique words and their corresponding occurrences.
 *
 */
public class TextAnalyzer {
     
    /**  
     * @param fileName takes a text file
     * 					
     * From the file a HashMap is created with a String 
     * represents a unique word and the Integer is the number
     * of times the unique word appears.
     * 
     * The text file is taken in and broken down
     * first by line. A short hand captures things like periods, commas,etc
     * and replaces them with whitespace. Then the each word is know
     * by and separated by the whitespace. 
     * 
     * Case is ignored.
     * 
     * If word is new it is given a count of one and if it already
     * exists in the HashMap it is incremented by 1.
     * 
     * @return wordHashMap
     * 
     * The HashMap with unique words along with their count is returned
     * 					
     */
    public Map<String, Integer> getWordCount(String fileName){
 
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        Map<String, Integer> wordHashMap = new HashMap<String, Integer>();
        try {
            fis = new FileInputStream(fileName);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String s = null;
            
            while((s = br.readLine()) != null){
            	s = s.replaceAll("\\W+"," ");                  
                StringTokenizer st = new StringTokenizer(s, " ");
                while(st.hasMoreTokens()){
                    String tmp = st.nextToken().toLowerCase();
                    
                    if(wordHashMap.containsKey(tmp)){
                    	wordHashMap.put(tmp, wordHashMap.get(tmp)+1);
                    } else {
                    	wordHashMap.put(tmp, 1);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{if(br != null) br.close();}catch(Exception ex){}
        }
        return wordHashMap;
        
    }
     
    /**
     * @param wordMap
     * 
     * Takes a Map with Strings and Integers
     * to be sorted
     * 
     * @return
     * 
     * a sorted list of words and their word occurrences 
     * is returned.
     * 
     */
    public List<Entry<String, Integer>> sortByValue(Map<String, Integer> wordMap){
         
        Set<Entry<String, Integer>> set = wordMap.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> one, Map.Entry<String, Integer> two )
            {
                return (two.getValue()).compareTo( one.getValue() );
            }
        } );
        return list;
    }
     
   
    /**
     * @param args
     * 
     * Main that creates a new TextAnalyzer object and 
     * sets the file name to be sorted.
     * 
     * Uses the prior method sortByValue to return a 
     * sorted list and takes the first 20 unique words
     * with highest occurrence. 
     * 
     */
    public static void main(String [] args){
    	int count = 0;
        TextAnalyzer ta = new TextAnalyzer();
        Map<String, Integer> wordMap = ta.getWordCount("theRaven.txt");
        List<Entry<String, Integer>> list = ta.sortByValue(wordMap);
        
    
        for(Map.Entry<String, Integer> entry:list){
        	
        if(entry.getValue() >= 8 && count < 20) {
        		count++;
        		System.out.println(entry.getKey()+" : "+entry.getValue());
        	}
        }
    }
}