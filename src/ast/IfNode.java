package ast;

import java.util.ArrayList;
import java.util.List;

public class IfNode extends Node {
	
	private List<Node> trueBlock;
	private List<Node> falseBlock;
	
	/* For CFG */
	private Node succFalse;

	public IfNode(String name) {
		super(name);
		trueBlock = new ArrayList<Node>();
		falseBlock = new ArrayList<Node>();
		succFalse = NONE;
	}
	
	public List<Node> getTrueBlock(){
		return trueBlock;
	}
	
	public List<Node> getFalseBlock(){
		return falseBlock;
	}
	
	public void setTrueBlock(List<Node> b) {
		trueBlock = b;
	}
	
	public void setFalseBlock(List<Node> b) {
		falseBlock = b;
	}
	
	/* For CFG */
	public Node getSuccFalse() {
		return succFalse;
	}
	
	public void setSuccFalse(Node n) {
		succFalse = n;
	}

}
