package ACCProject;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;


import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;



public class DictionaryFromFile {
	
	public static HashSet<String> dictionary_words = new java.util.HashSet<String>();
	
	public static String parse_file(File file) throws IOException {
		Document document = Jsoup.parse(file, "UTF-8");
		
		String file_content = document.body().text();
		
		return file_content;
		
		
	}
	
	// checks if the word contains english characters
	public static boolean check_if_word_is_english(String word) {
	
		Pattern regex = Pattern.compile("[a-zA-Z]+");
		
		Matcher m = regex.matcher(word);
		
		
		return m.find();
		
	}
	
	
	// this function creates a dict from extracted files
	public static void create_dictionary_from_extracted_files() throws IOException {
		
		File folder = new File("ParsedHTML");
		File[] files = folder.listFiles();
		
		for (File file: files) {
			if (file.isFile()) {
				
				String file_content = parse_file(file);
				
				// transforms the content into lower case, removes all the punctuations and finally splits it by white space to get an array of words
				String[] splitted_content = file_content.toLowerCase().replaceAll("\\p{Punct}", "").split("\\s+");
				
				
				for (String word: splitted_content) {
					if (!dictionary_words.contains(word)) {
						
						if (check_if_word_is_english(word)) {
							
							dictionary_words.add(word);
							
						}	
						
					}
				}
				
				
				
				
				
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		/**
		
		create_dictionary_from_extracted_files();
		
		Iterator<String> itr = dictionary_words.iterator();
		
		while (itr.hasNext()) {
		System.out.println(itr.next());
		}
		
		
		//check_if_word_is_english("metal");
		
		 */
		
	}

}
