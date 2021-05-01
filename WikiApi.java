package src;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.SymbolGraph;

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
	static HttpRequest request;
	static HttpClient client;
	static String filename  = "src/src/Resources/routes.txt";
	static Out filePrint = new Out(filename);
	static int count = 0;
	static int totalCount = 0;
	static ArrayList<String> searchedPages = new ArrayList<>();

	public static void main(String[] args) {
		String baseURL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&titles=Salt%20Lake%20Community%20College&plnamespace=0&pllimit=5&pltitles=";
		client = HttpClient.newHttpClient();
		request = HttpRequest.newBuilder(URI.create(baseURL)).build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
		.thenApply(HttpResponse::body)
		.thenApply(WikiApi::parse)
		.join();
		
		SymbolGraph graph = new SymbolGraph(filename, "/");
		System.out.println();
		
		System.out.println("vertex: " + graph.nameOf(8));
		for(Integer el : graph.graph().adj(2)) {
			System.out.println(graph.nameOf(el));
		}
		
		System.out.println();
		BreadthFirstPaths bfs = new BreadthFirstPaths(graph.graph(), 0);
		System.out.println("Does " + graph.nameOf(11) + " have a path to " + graph.nameOf(0) + "?");
		System.out.println(bfs.hasPathTo(11));
		
		Iterator<Integer> pathToIterator = bfs.pathTo(11).iterator();
		while (pathToIterator.hasNext()) {
			int w = pathToIterator.next();
			if (pathToIterator.hasNext()) {
				System.out.print(graph.nameOf(w) + "..");
			} else {
				System.out.print(graph.nameOf(w));
			}
		}

	}

	/**
	 * Parses data to JSON and updates routes.txt
	 * @param responseBody
	 * @return
	 */
	public static Node parse(String responseBody) {
		//TODO create system to keep track of parent nodes (recursive?)
		ArrayList<String> linksArray = new ArrayList<>();
		count++;
		Object[] pageId = new JSONObject(responseBody)
				.getJSONObject("query")
				.getJSONObject("pages")
				.keySet().toArray();

		System.out.println("pageId[0].toString(): " + pageId[0].toString());
		if(!pageId[0].toString().equalsIgnoreCase("-1") && !searchedPages.contains(pageId[0].toString()) && count < 4) {
			searchedPages.add(pageId[0].toString());
			System.out.println(searchedPages);
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

			
				for(String el : linksArray) {
					filePrint.println(pageTitle + "/" + el);
					
					String recursiveURL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&titles="
							+ el.replaceAll(" ", "%20") + "&plnamespace=0&pllimit=5&pltitles=";
					System.out.println("I'm going to: " + recursiveURL);
					
					request = HttpRequest.newBuilder(URI.create(recursiveURL)).build();
					client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
					.thenApply(HttpResponse::body)
					.thenApply(WikiApi::parse)
					.join();
				}
		}
		count--;

		return null; //TODO
	}

}
