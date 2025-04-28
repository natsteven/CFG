package ast;

import java.util.ArrayList;
import java.util.List;

public class MethodNode extends Node {
	
	private List<Node> block;

	public MethodNode(String name) {
		super(name);
		block = new ArrayList<Node>();
	}
	
	public void addNode(Node n) {
		block.add(n);
	}
	
	public List<Node> getStmt(){
		return block;
	}

}
