package ast;

public class Node {
	
	public static final Node NONE = new Node("");
	
	private String name;
	/* To store CFG */
	private Node succ;
	
	public Node(String name) {
		this.name = name;
		succ = NONE;
	}

	
	public String getName() {
		return name;
	}
	
	/* To construct CFG */	
	public Node getSucc() {
		return succ;
	}
	
	
	public void setSucc(Node n) {
		succ = n;
	}
}
