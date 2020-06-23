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
 
public class TextAnalyzer {
     
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
     
    public static void main(String a[]){
        TextAnalyzer ta = new TextAnalyzer();
        Map<String, Integer> wordMap = ta.getWordCount("theRaven.txt");
        List<Entry<String, Integer>> list = ta.sortByValue(wordMap);
        
    
        for(Map.Entry<String, Integer> entry:list){
        	
        // String[] arr = new String[20]; //needs to only output top 20 elements
        	if(entry.getValue() >= 8) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        	}
        }
    }
}