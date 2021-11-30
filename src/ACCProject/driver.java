package ACCProject;

import java.io.IOException;

import java.util.Scanner;



public class driver {
	
	public static String spell_check_word(String word) throws IOException  {
		
		
		CorrectSpellingSuggestor corrector = new CorrectSpellingSuggestor();
		
		
			
			corrector.use_dictionary();
			String suggestion = corrector.suggest_similar_word(word);
			if (suggestion == null) {
			    suggestion = "No similar word found";
			}
			
			System.out.println("Suggestion is: " + suggestion);
			return suggestion;
			
		
		
		
		
		
		
		
		
		
		
		
		/*
		
		Iterator<String> itr = dictionary_words.iterator();
		
		while (itr.hasNext()) {
		System.out.println(itr.next());
		}
		
		*/ 
		
		
		
		
	}
	
	/*
	public static void main(String[] args) throws IOException {
		System.out.println(spell_check_word("Trevel"));
		
	} */

}
