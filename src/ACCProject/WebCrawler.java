package ACCProject;

import org.jsoup.nodes.Document;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;


public class WebCrawler {
	
	private static HashSet<String> urls = new HashSet<>();
	
	
	public static void extract_dict_words_from_web_page(String url) {
		try {
			FileWriter writer = new FileWriter("files/dictionary.txt");
			Document document = Jsoup.connect(url).maxBodySize(0).get();			
			Elements dictionary_items = document.select("ul.top-g").select("li");
			int count=0;
			
			for (Element list_item: dictionary_items) {
				count+=1;
				String word = list_item.attr("data-hw").replaceAll("\\p{Punct}", "");
				
					
				writer.write(word+"\n");	
				
				
				
			}
			
			writer.close();
			
			System.out.println(count);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
	}
	
	public static void get_urls(String url) {
		if (!urls.contains(url)) {
			Document document;
			try {
				document = Jsoup.connect(url).timeout(5000).get();
				//Get 500 URLs starting from designated website
				Elements links = document.select("a[href^=\"https://www.cbc.ca/news\"]");
				for (Element link : links) {
					if (urls.size() < 500) {
						urls.add(url);
						get_urls(link.attr("abs:href"));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void get_content() {
		try {
			FileWriter link = new FileWriter("files/links.txt");
			FileWriter page = new FileWriter("files/web_pages.txt");
			//From the crawled URLs to get web page content
			for (String url : urls) {
				Document document;
				document = Jsoup.connect(url).timeout(5000).maxBodySize(0).get();
				//Save web page content by using page title as the file name
				String fileName = document.title().replace(" | CBC News", "").replaceAll("[^A-Za-z0-9\\s]", "")
						+ ".txt";
				page.write(fileName + System.getProperty("line.separator"));
				//Save files into a folder
				File f = new File("ParsedFiles/" + fileName);
				f.getParentFile().mkdirs();
				FileWriter wp = new FileWriter(f);
				wp.write(document.body().text());
				link.write(url + System.getProperty("line.separator"));
				wp.close();
			}
			link.close();
			page.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		extract_dict_words_from_web_page("https://www.oxfordlearnersdictionaries.com/wordlists/oxford3000-5000");
		
				
	}
	
	

}
