package sowatec.com.TBI.WebCrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LoginCrawler {

	public static void main(String[] args) throws IOException {
		githublogin();
	}
	
	/*

	public static void executeLogin() {


		String url = "https://github.com/login";
		String email = "bieler@sowatec.com";
		String username = "SowaTBI";
		String password = "ibdmigadsukmmTimon505*";

		try {

			Connection.Response response = Jsoup.connect(url).ignoreHttpErrors(true)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
					.data("username", username, "password", password).method(Connection.Method.POST).execute();

			Map<String, String> cookie = response.cookies();

			Document doc = Jsoup.connect("https://github.com/SowaTBI/Addressbook/settings").ignoreHttpErrors(true)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
					.cookies(cookie).get();

			Elements links = doc.select("a[href]");

			for (Element link : links) {
				System.out.println("\nlink: " + link.attr("abs:href"));
				System.out.println("text: " + link.text());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	public static void githublogin() {

		/*
		 * Code : https://stackoverflow.com/questions/68070362/log-into-github-with-jsoup
		 */
		
		try {
			// # Constants used in this example
			final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
			final String LOGIN_FORM_URL = "https://github.com/login";
			final String LOGIN_ACTION_URL = "https://github.com/session";
			final String USERNAME = "SowaTBI";
			final String PASSWORD = "ibdmigadsukmmTimon505*";

			// # Go to login page and grab cookies sent by server
			Connection.Response loginForm = Jsoup.connect(LOGIN_FORM_URL)
					.method(Connection.Method.GET)
					.userAgent(USER_AGENT)
					.execute();
			
			Document loginDoc = loginForm.parse(); // this is the document containing response html
			HashMap<String, String> cookies = new HashMap<>(loginForm.cookies()); // save the cookies to be passed on to
																					// next request
			
			
			String authToken = loginDoc.selectFirst("input[name=authenticity_token]") != null ?
					loginDoc.selectFirst("input[name=authenticity_token]").val() : null;

			HashMap<String, String> formData = new HashMap<>();
			formData.put("commit", "Sign in");
			formData.put("utf8", "e2 9c 93");
			formData.put("login", USERNAME);
			formData.put("password", PASSWORD);
			formData.put("authenticity_token", authToken);

			// # Now send the form for login
			Connection.Response homePage = Jsoup.connect(LOGIN_ACTION_URL)
					.cookies(cookies).data(formData)
					.method(Connection.Method.POST)
					.userAgent(USER_AGENT)
					.execute();

			System.out.println(homePage.statusMessage());
			
			// WEB CRAWLER
			crawl(1, LOGIN_ACTION_URL, new ArrayList<String>());
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void crawl(int level, String LOGIN_ACTION_URL, ArrayList<String> visited) {
		
		if(level <= 2) {
			Document doc = request(LOGIN_ACTION_URL, visited);
			if(doc != null) {
				for (Element link : doc.select("a[href]")) {
					String next_Link = link.attr("abs:href");
					if(visited.contains(next_Link) == false) {
						crawl(level++, next_Link, visited);
					}
				}	
			}
		}
	}
	
	private static Document request(String url, ArrayList<String> v) {
		
		try {
			
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			if(con.response().statusCode() == 200) {
				System.out.println("Link: " + url);
				System.out.println(doc.title());
				v.add(url);
				return doc;
			}
			return null;
			
		} catch(IOException e) {
			return null;
		}
	}
}
