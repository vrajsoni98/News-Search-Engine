package ACCProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;



public class SearchEngine {
	
	public static void main(String arg[]) throws IOException {

		Scanner s = new Scanner(System.in);
		System.out.println("Search for Relevant news\n");
		String input = "";
		String corrected_words = "";
																									
																										
																									
		while (true) {
				System.out.println("Please type in your search query \n");
				input = s.nextLine();
																																																															
				String[] words = input.split(" ");
				StringBuilder sb = new StringBuilder();
				System.out.println("\nCheking for corrections...\n");
				for (int i = 0; i < words.length; i++) {
					if (!words[i].matches(".*\\d.*")) {
						corrected_words = driver.spell_check_word(words[i].replaceAll("[^A-Za-z]", ""));
						sb.append(corrected_words + " ");
					} else {
						sb.append(words[i] + " ");
					}
				}
				input = sb.toString();
					// remove stop words
				StopWords sw = new StopWords();
				ArrayList<String> input_remove_stop_words = sw.remove_stop_words(input);
					// unnecessary part of words are deleted by stemming
				Stemmer st = new Stemmer();
				ArrayList<String> stem_input = st.wordsStemming(input_remove_stop_words);
//					
				FrequencyList f = new FrequencyList();
				f.get_word_freq_list(stem_input);
					
				LinkedHashMap<String, String> results = f.sort_list();
				if (FrequencyList.has_results) {
					System.out.println("Found: "+results.size() + " related results: \n");
					results.entrySet().forEach(entry -> {
						System.out.println("_________________________________________________________________");
						System.out.println(entry.getKey() + "\n" + entry.getValue().replace(".txt", "") + "\n");
						System.out.println("________________________________________________________________");
					});
					
				
					} else {
						System.out.println("No more results found\n");
						
					}
				
			
			
		}
																										
		
	}

}
