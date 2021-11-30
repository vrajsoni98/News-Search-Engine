package ACCProject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;

import textprocessing.TST;

public class FrequencyList {

	HashMap<String, Integer> freq_list;
	LinkedHashMap<String, String> origin_list;
	protected static boolean has_results = true;

	public FrequencyList() {
		this.freq_list = new HashMap<>();
		this.origin_list = new LinkedHashMap<>();
	}
	 // This method will be able to make statistic for the word frequency
	public void get_word_freq_list(ArrayList<String> query) throws FileNotFoundException {

		File folder = new File("ParsedFiles");
		File[] list_of_files = folder.listFiles();
		//Read in all page files
		for (File file : list_of_files) {
			if (file.isFile()) {
				int occurrence = 0;
				TST<Integer> st = new TST<Integer>();
				//Convert content of file into a string
				StringBuilder sb = new StringBuilder();
				Scanner s = new Scanner(new FileReader(file));
				while (s.hasNextLine()) {
					sb.append(s.nextLine());
				}
				String text = sb.toString();
				//Extract words
				StringTokenizer stk = new StringTokenizer(text);
				while (stk.hasMoreTokens()) {
					String token = stk.nextToken().toLowerCase();
					//Build a TST
					if (st.contains(token)) {
						st.put(token, st.get(token) + 1);
					} else {
						st.put(token, 1);
					}
				}
				// Frequency of each word from input
				for (String word : query) {
					if (st.contains(word)) {
						occurrence += st.get(word);
					}
				}
				//Puts occurrences and corresponding file name in hash table
				freq_list.put(file.getName(), occurrence);
				
			}
		}		
	}
	
	// sorts the related entries by reverse order
	public LinkedHashMap<String, String> sort_list() throws FileNotFoundException {

		ArrayList<Integer> list = new ArrayList<>();
		LinkedHashMap<String, Integer> sorted_freq_list = new LinkedHashMap<>();
		ArrayList<String> webpage = new ArrayList<>();
		ArrayList<String> url = new ArrayList<>();
		LinkedHashMap<String, String> complete_list = new LinkedHashMap<>();
		//Create a new list to store occurrences
		for (Map.Entry<String, Integer> entry : freq_list.entrySet()) {
			list.add(entry.getValue());
		}
		//Sort occurrences by descending order
		Collections.sort(list, Collections.reverseOrder());
		//Generate a new hash table to save all sorted results
		for (int num : list) {
			for (Entry<String, Integer> entry : freq_list.entrySet()) {
				if (entry.getValue().equals(num)) {
					sorted_freq_list.put(entry.getKey(), num);
				}
			}
		}
		//Get a sorted web page list
		for (String key : sorted_freq_list.keySet()) {
			webpage.add(key);
		}
		//Get a URL list based on the web page list
		get_origin_list();
		for (String page : webpage) {
			url.add(origin_list.get(page));
		}
		
	
		
		for (int i=0; i<10; i++) {
			complete_list.put(url.get(i), webpage.get(i));
		}
		
		return complete_list;
	}

	
	private void get_origin_list() throws FileNotFoundException {

		ArrayList<String> page_list = new ArrayList<>();
		ArrayList<String> url_list = new ArrayList<>();
		Scanner page = new Scanner(new File("files/web_pages.txt"));
		//Create a list with page file names
		while (page.hasNextLine()) {
			page_list.add(page.nextLine());
		}
		Scanner URL = new Scanner(new File("files/links.txt"));
		//Create a list with URL
		while (URL.hasNextLine()) {
			url_list.add(URL.nextLine());
		}
		//Combine the page names and corresponding URLs
		Iterator<String> itr = url_list.listIterator();
		for (String p : page_list) {
			if (itr.hasNext()) {
				origin_list.put(p, itr.next());
			}
		}
	}
}
