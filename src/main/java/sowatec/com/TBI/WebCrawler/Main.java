package sowatec.com.TBI.WebCrawler;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<MultiCrawler> bots = new ArrayList<>();
		bots.add(new MultiCrawler("https://abcnews.go.com", 1));
		bots.add(new MultiCrawler("https://www.npr.org", 2));
		bots.add(new MultiCrawler("https://www.nytimes.com", 3));
		
		for(MultiCrawler m : bots) {
			try {
				m.getThread().join();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
