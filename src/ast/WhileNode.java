package ast;

import java.util.ArrayList;
import java.util.List;

public class WhileNode extends Node {
	
	private List<Node> block;
	
	/* For CFG */
	private Node succFalse;

	public WhileNode(String name) {
		super(name);
		block = new ArrayList<Node>();
	}
	
	public void addBlock(List<Node> stmt) {
		block = stmt;
	}
	
	public List<Node> getStmt(){
		return block;
	}

	
	/* For CFG */
	public Node getSuccFalse() {
		return succFalse;
	}
	
	public void setSuccFalse(Node n) {
		succFalse = n;
	}

}
