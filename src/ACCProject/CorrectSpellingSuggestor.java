package ACCProject;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;





public class CorrectSpellingSuggestor {
	
	Trie trie = new Trie();
	Map<String, Integer> dict = new HashMap<>();
	final static List<String> invalid = Arrays.asList("lol", "abcdefghijklmnopqrstuvwxyz");
	
	public void use_dictionary() throws IOException {	
		try {
			FileReader fr = new FileReader("files/dictionary.txt");
			BufferedReader br = new BufferedReader(fr);	       
			String line = null;
			while ((line = br.readLine()) != null) {          			        					
				String word = line.toLowerCase();
				//System.out.println(word);
				if (!line.contains(" ")) {
					dict.put(word, dict.getOrDefault(word, 0)+1);
					trie.add(word);
				} else {
					String[] strs= line.split("\\s");
					for(String str: strs) {
						dict.put(str, dict.getOrDefault(str, 0)+1);
						trie.add(str);
					}
				}
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	
	public String suggest_similar_word(String input_word) {
		
		if (input_word.length()==0 || input_word==null || invalid.contains(input_word.toLowerCase()) )
			return null;
		String s = input_word.toLowerCase();
		String res=null;
		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();		
		TrieNode node = trie.find(s);			
		if(node == null) {
			//System.out.println("\nnot find:" +s);
			for (String w: dict.keySet()) {
				int dist = editDistance(w, s);				
				TreeMap<Integer, TreeSet<String>> similarWords = map.getOrDefault(dist, new TreeMap<>());
				int freq = dict.get(w);
				TreeSet<String> set = similarWords.getOrDefault(freq, new TreeSet<>());
				set.add(w);			
				similarWords.put(freq, set);
				map.put(dist, similarWords);		
			}		
			res= map.firstEntry().getValue().lastEntry().getValue().first();
			//System.out.println(res+ " "+dict.get(res));
		 } else if (node !=null) {
			 //System.out.println("\nfind:" +s+" "+dict.get(s));
			 res = s;
		 }
		 return res;
	}
	
	
	private int editDistance(String word1, String word2) {
		int n = word1.length();
		int m = word2.length();
	    int dp[][] = new int[n+1][m+1];
	    for (int i = 0; i <= n; i++) {
	        for (int j = 0; j <= m; j++) {
	            if (i == 0)
	                dp[i][j] = j;      
	            else if (j == 0)
	                dp[i][j] = i; 
	            else if (word1.charAt(i-1) == word2.charAt(j-1))
	                dp[i][j] = dp[i-1][j-1];	            
	            else if (i>1 && j>1  && word1.charAt(i-1) == word2.charAt(j-2) 
	            		&& word1.charAt(i-2) == word2.charAt(j-1))
	            	dp[i][j] = 1+Math.min(Math.min(dp[i-2][j-2], dp[i-1][j]), Math.min(dp[i][j-1], dp[i-1][j-1]));
	            else
	                dp[i][j] = 1 + Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1])); 
	        }
	    } 
	    return dp[n][m];
	}


	public static void main(String[] args) throws IOException {
		
		/**
		
		DictionaryFromFile.create_dictionary_from_extracted_files();
		HashSet<String> dictionary_words = DictionaryFromFile.dictionary_words;
		
		use_dictionary(dictionary_words);
		
		
		
		DictionaryFromFile.create_dictionary_from_extracted_files();
		HashSet<String> dictionary_words = DictionaryFromFile.dictionary_words;
		use_dictionary(dictionary_words);
		
		System.out.println(trie.find("true")==null);
		*/
		
		
		
	}
	

}
