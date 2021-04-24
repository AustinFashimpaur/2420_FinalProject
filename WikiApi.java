package src;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REQUIRED DEPENDENCIES:
 * <ul>
 * <li>JDK 11
 * <li>JSON-java <a href="https://github.com/stleary/JSON-java">(Github
 * Here)</a>
 * </ul>
 * 
 * Uses async Java 11 HttpClient to connect with Wikipedia API and JSON-java to
 * parse JSON data to create nodes to form a graph.
 * 
 * @author Austin Fashimpaur + Mohamad Hajj
 */
public class WikiApi {
	
	public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&titles=Salt%20Lake%20Community%20College&plnamespace=0&pllimit=500&pltitles=")).build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
			.thenApply(HttpResponse::body)
			.thenApply(WikiApi::parse)
			.join();
	}
	
	/**
	 * Parses data to JSON and returns Node
	 * @param responseBody
	 * @return
	 */
	public static Node parse(String responseBody) {
		//TODO create system to keep track of parent nodes (recursive?)
		ArrayList<String> linksArray = new ArrayList<>();
		
		Object[] pageId = new JSONObject(responseBody)
				.getJSONObject("query")
				.getJSONObject("pages")
				.keySet().toArray();
		
		JSONArray linksJSON = new JSONObject(responseBody)
				.getJSONObject("query")
				.getJSONObject("pages")
				.getJSONObject(pageId[0].toString())
				.getJSONArray("links");
		
		String pageTitle = new JSONObject(responseBody)
				.getJSONObject("query")
				.getJSONObject("pages")
				.getJSONObject(pageId[0].toString())
				.get("title").toString();
		
		for (int i = 0; i < linksJSON.length(); i++) {
			JSONObject link = linksJSON.getJSONObject(i);
			String title = link.getString("title");
			linksArray.add(title);
		}
		
		System.out.println("Adding new node: " + pageTitle);
		System.out.println("\nBranches of new node: ");
		for(String el : linksArray) {
			System.out.println("   " + el);
		}
		return new Node(pageTitle, linksArray, null);
	}

}
