package ACCProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


	
	public class StopWords {
		
		public ArrayList<String> remove_stop_words(String query) {
			
			ArrayList<String> all_words = null;
			try {
				Scanner s = new Scanner(new File("files/stop_words.txt"));
				ArrayList<String> stop_words = new ArrayList<>();
				while (s.hasNextLine()) {
					stop_words.add(s.nextLine());
				}
				all_words = Stream.of(query.replaceAll("[^A-Za-z0-9\\s]", "").toLowerCase().split(" "))
						.collect(Collectors.toCollection(ArrayList<String>::new));
			    all_words.removeAll(stop_words);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		    return all_words;
		    
		}
	}


