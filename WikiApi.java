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
	static ArrayList<String> pathTo = new ArrayList<>();

	public static ArrayList<String> findPath(String source, String destination, int linksPerPage) {
		String sourceURL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&titles="
				+ source.replaceAll(" ", "%20") + "&plnamespace=0&pllimit=" + linksPerPage + "&pltitles=";

		client = HttpClient.newHttpClient();
		request = HttpRequest.newBuilder(URI.create(sourceURL)).build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
		.thenApply(HttpResponse::body)
		.thenApply(WikiApi::parse)
		.join();

		SymbolGraph graph = new SymbolGraph(filename, "/");

		BreadthFirstPaths bfs = new BreadthFirstPaths(graph.graph(), 0);
		System.out.println("Does " + source + " have a path to " + destination + "?");
		if(graph.contains(destination)) {
			System.out.println(bfs.hasPathTo(graph.indexOf(destination)));
			Iterator<Integer> pathToIterator = bfs.pathTo(graph.indexOf(destination)).iterator();
			while (pathToIterator.hasNext()) {
				int w = pathToIterator.next();
				pathTo.add(graph.nameOf(w));
			}
		} else {
			System.out.println("It does not!");
		}

		return pathTo;
	}

	/**
	 * Parses data to JSON and updates routes.txt
	 * @param responseBody
	 * @return
	 */
	public static Validate parse(String responseBody) {
		//TODO create system to keep track of parent nodes (recursive?)
		ArrayList<String> linksArray = new ArrayList<>();
		count++;
		Object[] pageId = new JSONObject(responseBody)
				.getJSONObject("query")
				.getJSONObject("pages")
				.keySet().toArray();

		if(!pageId[0].toString().equalsIgnoreCase("-1") && !searchedPages.contains(pageId[0].toString()) && count < 3) {
			searchedPages.add(pageId[0].toString());
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
						+ el.replaceAll(" ", "%20") + "&plnamespace=0&pllimit=3&pltitles=";

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
