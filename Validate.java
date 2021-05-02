package src;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

/**
 * Validates that input in the Java Swing form are existing Wikipedia Pages
 * @author Austin Fashimpaur + Mohamad Hajj
 *
 */
public class Validate {
	
	/**
	 * Validates the provided page title as a wikipedia page
	 * @param pageTitle
	 * @return
	 */
	public static boolean validatePage(String pageTitle) {
		if(pageTitle.isBlank()) {
			return false;
		}
		String formattedURL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&titles="
				+ pageTitle.replaceAll(" ", "%20") + "&plnamespace=0&pllimit=2&pltitles=";
	
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(formattedURL)).build();
		
		
		 try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == 200) {
				return parse(response.body());
			} else {
				return false;
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean parse(String responseBody) {
		Object[] pageId = new JSONObject(responseBody)
				.getJSONObject("query")
				.getJSONObject("pages")
				.keySet().toArray();
		
		if(pageId[0].toString().equalsIgnoreCase("-1")) {
			return false;
		} else {
			return true;
		}
	}
}
