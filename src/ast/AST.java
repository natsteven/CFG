package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AST {
	
	private MethodNode root;
	
	public AST(MethodNode root) {
		this.root = root;
	}
	
	public MethodNode getRoot() {
		return root;
	}
	
	
	/**
	 * Creates a file in the root directory
	 * named as the name of root node
	 */
	public void toDot() {
		String rootName = root.getName();
		StringBuffer graph = new StringBuffer("digraph " + rootName + " {\n");
		//create a queue
		List<Node> queue = new ArrayList<Node>();
		//get all the children of the root node separately
		for(Node chld : root.getStmt()) {
			graph.append(genEdge(rootName, chld.getName())+ ";\n");
			//add root's child to the queue
			queue.add(chld);
		}
		
		while(!queue.isEmpty()) {
			//difference cases for different node instances
			Node next = queue.remove(0);
			if(next instanceof IfNode) {
				//will have true and false edges
				IfNode ifNext = (IfNode) next;
				String ifNextName = ifNext.getName();
				for(Node n : ifNext.getTrueBlock()) {
					graph.append(genEdge(ifNextName, n.getName()) + ";\n");
					queue.add(n);
				}
				for(Node n : ifNext.getFalseBlock()) {
					graph.append(genEdge(ifNextName, n.getName()) + "[style = dashed];\n");
					queue.add(n);
				}
			} else if (next instanceof WhileNode) {
				//will have only true edges
				WhileNode whileNext = (WhileNode) next;
				String whileNextName = whileNext.getName();
				for(Node n : whileNext.getStmt()) {
					graph.append(genEdge(whileNextName, n.getName()) + ";\n");
					queue.add(n);
				}
				// a regular statement is a leaf in an AST
			}
		}
		//end of graph
		graph.append(" }");
		try {
			FileWriter dotFile = new FileWriter(rootName+".dot");
			dotFile.write(graph.toString());
			dotFile.flush();
			dotFile.close();
		} catch (IOException e) {
			System.out.println("IOError ");
			e.printStackTrace();
		}
		
	}
	
	private static String genEdge(String from, String to) {
		return "\""+ from +"\" -> \"" + to + "\"";
	}

}
