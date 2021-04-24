package src;

import java.util.ArrayList;

/**
 * Represents a node on a website graph.
 * Each node represents a wikipedia page with a title and links found on that page.
 * @author Austin Fashimpaur + Mohamad Hajj
 *
 */
public class Node {
	String pageTitle;
	ArrayList<String> links = new ArrayList<>();
	Node parentNode = null;
	
	/**
	 * @param pageTitle
	 * @param links
	 * @param parentNode
	 */
	public Node(String pageTitle, ArrayList<String> links, Node parentNode) {
		this.pageTitle = pageTitle;
		this.links = links;
		this.parentNode = parentNode;
	}
}
